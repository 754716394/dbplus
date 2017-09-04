package com.pamirs.dbplus.configure.manager.impl;

import com.pamirs.configure.domain.*;
import com.pamirs.configure.sdkapi.ConfigureSDKManager;
import com.pamirs.configure.sdkapi.impl.ConfigureSDKManagerImpl;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.*;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;
import com.pamirs.dbplus.configure.utils.Constants;
import com.pamirs.dbplus.configure.utils.EnvironmentTool;
import com.pamirs.dbplus.configure.utils.JadeLog;
import com.pamirs.dbplus.configure.utils.PtoolsThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JadeConfigManagerImpl implements JadeConfigManager {

    private static final Logger log = LoggerFactory
            .getLogger("com.pamirs.archdp.ptools.jade");
    private String server;

    private Map<String, String> serverEnvironments;

    private Map<String, JadeConfigureConfig> jadeConfigureConfigMap = new HashMap<String, JadeConfigureConfig>();

    public Map<String, JadeConfigureConfig> getJadeConfigureConfigMap() {
        return jadeConfigureConfigMap;
    }

    private static Map<String/* env */, BlockingQueue<ConfigureSDKManager>> configureMap = new HashMap<String/* env */, BlockingQueue<ConfigureSDKManager>>();

    private int configureTotal = 5;

    private int timeout = 4000;

    /**
     * 初始化
     *
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public void init() throws Exception {
        String servers[] = StringUtils.split(server, Constants.SEMICOLON_MARK);
        int count = servers.length;
        this.serverEnvironments = new HashMap<String, String>();
        for (int i = 0; i < count; i++) {
            String temp[] = StringUtils.split(servers[i], Constants.STICK_MARK);// 取得服务器配置列表
            String enviromentname = temp[0];// 环境名称
            JadeConfigureConfig jadeConfigureConfig = new JadeConfigureConfig();// 创建一个config
            jadeConfigureConfig.setEnvironment(enviromentname); // 设置config所在的环境
            String items[] = StringUtils.split(temp[1], Constants.AND_MARK);// 配置项字符串数组
            for (int j = 0; j < items.length; j++) {
                String item = StringUtils.substring(items[j], 1,
                        items[j].length() - 1);// 去掉两边的括弧
                String val[] = StringUtils.split(item, Constants.COMMA_MARK); // 得到参数列表
                JadeConfigureServerConfig jadeConfigureServerConfig = new JadeConfigureServerConfig(
                        val[0], val[1], val[2], val[3]);

                jadeConfigureConfig.getConfigures().add(jadeConfigureServerConfig);// 添加配置信息
            }
            jadeConfigureConfigMap.put(temp[0], jadeConfigureConfig);// 放到本地map中
            serverEnvironments.put(String.valueOf(i + 1), enviromentname);
        }

        Map<String, ConfigureSDKConf> configureSDKConfMaps = new HashMap<String, ConfigureSDKConf>();

        StringBuilder str = new StringBuilder("\r\n初试化configure的参数：\r\n");//
        // 把jade的配置对象转成configure的配置对象
        for (Iterator i$ = jadeConfigureConfigMap.entrySet().iterator(); i$
                .hasNext(); ) {
            {
                java.util.Map.Entry entry = (java.util.Map.Entry) i$.next(); // 取第一个数
                JadeConfigureConfig jadeConfigureConfig = (JadeConfigureConfig) entry
                        .getValue();// 取对象
                str.append(jadeConfigureConfig.getEnvironment()).append(":\r\n"); // 环境
                List<ConfigureConf> configureConfs = new Vector<ConfigureConf>();
                for (JadeConfigureServerConfig t : jadeConfigureConfig.getConfigures()) {
                    ConfigureConf configureConf = new ConfigureConf(t.getConfigureIp(),
                            t.getConfigurePort(), t.getConfigureUsername(),
                            t.getConfigurePassword());
                    str.append("configure:[").append(configureConf.getConfigureIp())
                            .append(":").append(configureConf.getConfigurePort())
                            .append("]\r\n"); // configure的配置
                    configureConfs.add(configureConf);
                }
                ConfigureSDKConf configureSDKConf = new ConfigureSDKConf(configureConfs);
                configureSDKConfMaps.put(jadeConfigureConfig.getEnvironment(),
                        configureSDKConf);
            }

        }
        if (configureTotal == 0) {
            configureTotal = 3;
        }
        for (String envStr : serverEnvironments.values()) {
            BlockingQueue<ConfigureSDKManager> configureQueue = new LinkedBlockingQueue<ConfigureSDKManager>();
            for (int n = 0; n < configureTotal; n++) {
                ConfigureSDKManagerImpl configureSDKManagerImpl = new ConfigureSDKManagerImpl(
                        timeout, timeout);
                configureSDKManagerImpl.setConfigureSDKConfMaps(configureSDKConfMaps);
                configureSDKManagerImpl.init();
                configureQueue.add(configureSDKManagerImpl);
            }
            configureMap.put(envStr, configureQueue);
        }
        str.append("configureTotal is :" + configureTotal * serverEnvironments.size());
        str.append("\r\nconntion_time_out:" + timeout + "ms\r\n");
        str.append("request_time_out:" + timeout + "ms\r\n");
        str.append("初始化configure参数结束。\r\n");
        log.warn(str.toString());
        EnvironmentTool.setServerEnvironments(serverEnvironments);
    }

    /**
     * 得到Configure应用服务器的地址
     *
     * @return
     */
    @Override
    public String getConfigureIp(String environment) {
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ConfigureConf> list = configureSDKManager.getConfigureSDKConfMaps()
                .get(environment).getConfigureConfs();
        configureQueue.add(configureSDKManager);
        StringBuilder res = new StringBuilder();
        for (ConfigureConf conf : list) {
            res.append("[").append(conf.getConfigureIp()).append(",")
                    .append(conf.getConfigurePort()).append("],");
        }
        return res.toString();
    }

    @Override
    public JadeContentResult<JadeContentDO> publish(
            JadeContentDO jadeContentDO, String environment) {
        String environmentKey = environment;
        environment = serverEnvironments.get(environment);
        if (!this.hasEnvironment(environment)) {
            JadeLog.doLog("publish", false, "您选中的环境不存在", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：您选择的环境不存在");
        }
        if (StringUtils.isBlank(jadeContentDO.getDataId())) {
            JadeLog.doLog("publish", false, "dataId为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：dataId为空");
        }
        if (StringUtils.isBlank(jadeContentDO.getGroupName())) {
            JadeLog.doLog("publish", false, "groupName为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败groupName为空");
        }
        if (StringUtils.isBlank(jadeContentDO.getContent())) {
            JadeLog.doLog("publish", false, "Content为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：Content为空");
        }
        ContextResult contextResult = null;
        JadeContentResult<JadeContentDO> res = queryByDataIdAndGroupName(
                jadeContentDO.getDataId(), jadeContentDO.getGroupName(),
                environmentKey);
        JadeContentResult<JadeContentDO> result;
        if (res.isSuccess()) {
            if (null != res.getAbstractDO()) {
                JadeLog.doLog("publish", false, "该配置已经存在,请联系管理员，删除后重新配置",
                        environment, jadeContentDO);
                result = new JadeContentResult<JadeContentDO>(false,
                        "失败：该配置已经存在,请联系管理员，删除后重新配置");
            } else {
                long time = System.currentTimeMillis();
                String nick = StringUtils.defaultIfBlank(
                        (String) PtoolsThreadLocal
                                .get(PtoolsThreadLocal.ATTRIBUTE_NICK),
                        "nobody");
                BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
                ConfigureSDKManager configureSDKManager = null;
                try {
                    configureSDKManager = configureQueue.take();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                long stime = System.currentTimeMillis();
                contextResult = configureSDKManager.publish(
                        jadeContentDO.getDataId(),
                        jadeContentDO.getGroupName(),
                        jadeContentDO.getContent(), environment, nick); // 调用Configure的发布接口
                configureQueue.add(configureSDKManager);
                JadeLog.doLog("publish", contextResult.isSuccess(),
                        contextResult.getStatusMsg() + ".configure cost time:"
                                + (System.currentTimeMillis() - stime)
                                + "ms,get configure  " + (stime - time) + "ms",
                        environment, jadeContentDO);
                result = new JadeContentResult<JadeContentDO>(
                        contextResult.isSuccess(),
                        contextResult.getStatusMsg(), jadeContentDO);
            }
        } else {
            JadeLog.doLog("publish", false, res.getMsg(), environment,
                    jadeContentDO);
            result = new JadeContentResult<JadeContentDO>(false, res.getMsg());
        }
        return result;
    }

    @Override
    public JadePageResult<JadeContentDO> queryBy(String dataIdPattern,
                                                 String groupNamePattern, String environment, long currentPage,
                                                 long sizeOfPerPage) {

        environment = serverEnvironments.get(environment);
        // 如果查询的环境不存在
        if (!this.hasEnvironment(environment)) {
            JadeLog.doLog("pagequery", false, "失败：您选择的环境不存在", environment,
                    "dataId:" + dataIdPattern + "  groupName:"
                            + groupNamePattern);
            return new JadePageResult<JadeContentDO>(false, "[查询]失败：您选择的环境不存在");
        }
        // 匹配串是否null,为null的话，用""代替
        dataIdPattern = (dataIdPattern == null ? "" : dataIdPattern.trim());
        groupNamePattern = (groupNamePattern == null ? "" : groupNamePattern
                .trim());

        long time = System.currentTimeMillis();
        // 调用查询结果
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long stime = System.currentTimeMillis();
        PageContextResult<ConfigInfo> pageContextResult = configureSDKManager
                .queryBy(dataIdPattern, groupNamePattern, environment,
                        currentPage, sizeOfPerPage); // 调用ConfigureSDK的查询接口
        configureQueue.add(configureSDKManager);
        JadeLog.doLog("pagequery", pageContextResult.isSuccess(),
                pageContextResult.getStatusMsg() + ".configure cost time:"
                        + (System.currentTimeMillis() - stime)
                        + "ms,get configure  " + (stime - time) + "ms",
                environment, "dataId:" + dataIdPattern + "  groupName:"
                        + groupNamePattern);

        JadePageResult<JadeContentDO> result = null;
        List<JadeContentDO> list = new Vector<JadeContentDO>();
        if (pageContextResult.isSuccess()) {
            if (null != pageContextResult.getConfigureData()) {
                for (ConfigInfo cf : pageContextResult.getConfigureData()) {
                    list.add(new JadeContentDO(cf.getDataId(), cf.getGroup(),
                            cf.getContent(), cf.getId()));
                }
            }
            result = new JadePageResult<JadeContentDO>(pageContextResult, list);
        } else {
            result = new JadePageResult<JadeContentDO>(false,
                    pageContextResult.getStatusMsg());
        }
        return result;
    }

    @Override
    public JadeContentResult<JadeContentDO> queryByDataIdAndGroupName(
            String dataId, String groupName, String envId) {
        String environment = serverEnvironments.get(envId);
        // 判断环境是否存在！
        if (!this.hasEnvironment(environment)) {
            JadeLog.doLog("query", false, "失败：您选择的环境不存在", environment,
                    "dataId:" + dataId + "  groupName:" + groupName);
            return new JadeContentResult<JadeContentDO>(false,
                    "[查询]失败：您选择的环境不存在");
        }
        if (StringUtils.isBlank(dataId)) {
            JadeLog.doLog("query", false, "失败：dataId为空", environment, "dataId:"
                    + dataId + "  groupName:" + groupName);
            return new JadeContentResult<JadeContentDO>(false,
                    "[查询]失败:dataId为空");
        }
        if (StringUtils.isBlank(groupName)) {
            JadeLog.doLog("query", false, "失败:groupName为空", environment,
                    "dataId:" + dataId + "  groupName:" + groupName);
            return new JadeContentResult<JadeContentDO>(false,
                    "[查询]失败:groupName为空");
        }

        long time = System.currentTimeMillis();
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long stime = System.currentTimeMillis();
        ContextResult contextResult = configureSDKManager
                .queryByDataIdAndGroupName(dataId, groupName, environment);
        configureQueue.add(configureSDKManager);
        JadeLog.doLog(
                "queryByDataIdAndGroupName",
                contextResult.isSuccess(),
                contextResult.getReceiveResult(),
                environment,
                "dataId:" + dataId + ".configure cost time:"
                        + (System.currentTimeMillis() - stime)
                        + "ms,get configure  " + (stime - time) + "ms");

        JadeContentResult<JadeContentDO> jadeResult = new JadeContentResult<JadeContentDO>();// 创建精确查询对象
        jadeResult.setEnvId(envId);
        jadeResult.setEnvName(environment);
        jadeResult.setSuccess(contextResult.isSuccess());
        jadeResult.setMsg(contextResult.getStatusMsg());

		/*
         * if (!jadeResult.isSuccess()) { if
		 * (contextResult.getStatusMsg().equals("查询结果：无"))
		 * jadeResult.setSuccess(true); }
		 */

        if (!jadeResult.isSuccess()) {
            // JadeLog.doLog("query", contextResult.isSuccess(),
            // contextResult.getReceiveResult(), environment, "dataId:"
            // + dataId + "  groupName:" + groupName);
            return jadeResult;
        }
        if (null != contextResult.getConfigInfo()) {
            JadeContentDO jadeContentDO = new JadeContentDO(contextResult
                    .getConfigInfo().getDataId(), contextResult.getConfigInfo()
                    .getGroup(), contextResult.getConfigInfo().getContent(),
                    contextResult.getConfigInfo().getId());

            jadeResult.setAbstractDO(jadeContentDO);
            // JadeLog.doLog("query", contextResult.isSuccess(),
            // contextResult.getStatusMsg(), environment, jadeContentDO);
        }
        return jadeResult;
    }

    @Override
    public JadeContentResult<JadeContentDO> publishAfterModified(
            JadeContentDO jadeContentDO, String environment) {
        String environmentKey = environment;
        environment = serverEnvironments.get(environment);
        if (!this.hasEnvironment(environment)) {
            JadeLog.doLog("republish", false, "您选中的环境不存在", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：您选择的环境不存在");
        }
        if (StringUtils.isBlank(jadeContentDO.getDataId())) {
            JadeLog.doLog("republish", false, "dataId为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：dataId为空");
        }
        if (StringUtils.isBlank(jadeContentDO.getGroupName())) {
            JadeLog.doLog("republish", false, "groupName为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败groupName为空");
        }
        if (StringUtils.isBlank(jadeContentDO.getContent())) {
            JadeLog.doLog("republish", false, "Content为空", environment,
                    jadeContentDO);
            return new JadeContentResult<JadeContentDO>(false, "失败：Content为空");
        }

        JadeContentResult<JadeContentDO> res = queryByDataIdAndGroupName(
                jadeContentDO.getDataId(), jadeContentDO.getGroupName(),
                environmentKey);
        JadeContentResult<JadeContentDO> jadeContentResult = null;

        if (res.isSuccess()) {
            long time = System.currentTimeMillis();
            ContextResult contextResult = null;
            String nick = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                    .get(PtoolsThreadLocal.ATTRIBUTE_NICK), "nobody");
            BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
            ConfigureSDKManager configureSDKManager = null;
            try {
                configureSDKManager = configureQueue.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (null != res.getAbstractDO()) {
                contextResult = configureSDKManager.pulishAfterModified(
                        jadeContentDO.getDataId(),
                        jadeContentDO.getGroupName(),
                        jadeContentDO.getContent(), environment);
            } else {
                contextResult = configureSDKManager.publish(
                        jadeContentDO.getDataId(),
                        jadeContentDO.getGroupName(),
                        jadeContentDO.getContent(), environment, nick); // 调用Configure的发布接口
            }
            long stime = System.currentTimeMillis();
            configureQueue.add(configureSDKManager);
            JadeLog.doLog("publishAfterModified", contextResult.isSuccess(),
                    contextResult.getStatusMsg() + ".configure cost time:"
                            + (System.currentTimeMillis() - stime)
                            + "ms,get configure  " + (stime - time) + "ms",
                    environment, jadeContentDO);
            jadeContentResult = new JadeContentResult<JadeContentDO>(
                    contextResult.isSuccess(), contextResult.getStatusMsg(),
                    jadeContentDO);
        } else {
            jadeContentResult = new JadeContentResult<JadeContentDO>(false,
                    res.getMsg());

            JadeLog.doLog("publishAfterModified", false, res.getMsg(),
                    environment, jadeContentDO);
        }
        return jadeContentResult;
    }

    /**
     * 判断是否存在该环境
     *
     * @return
     */
    private boolean hasEnvironment(String environment) {
        for (Entry<String, String> en : serverEnvironments.entrySet()) {
//            log.error("hasEnvironment key=" + en.getKey() + " has value=" + en.getValue());
            if (StringUtils.equals(en.getValue(), environment))
                return true;
        }
        return false;
    }

    /*
     * public void setConfigureSDKManager(ConfigureSDKManager configureSDKManager) {
     * this.configureSDKManager = configureSDKManager; }
     */
    public ConfigureSDKManager getConfigureSDKManager(String environment) {
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDK = configureQueue.peek();
        if (configureSDK == null) {
            do {
                configureSDK = configureQueue.peek();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (configureSDK == null);
        }
        return configureSDK;
    }

    public void setServer(String server) {
        this.server = server;
    }

	/*
	 * public void setDb(String db) { this.db = db; }
	 */

    public int getConfigureTotal() {
        return configureTotal;
    }

    public void setConfigureTotal(int configureTotal) {
        this.configureTotal = configureTotal;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public JadePageResult<JadeContentDO> queryBy(String dataIdPattern,
                                                 String groupNamePattern, String contentPattern, String environment,
                                                 long currentPage, long sizeOfPerPage) {
        String envId = environment;
        environment = serverEnvironments.get(environment);
        // 如果查询的环境不存在
        if (!this.hasEnvironment(environment)) {
            JadeLog.doLog("pagequery", false, "失败：您选择的环境不存在", environment,
                    "dataId:" + dataIdPattern + "  groupName:"
                            + groupNamePattern + " contentpattern:"
                            + contentPattern);
            return new JadePageResult<JadeContentDO>(false, "[查询]失败：您选择的环境不存在");
        }
        // 匹配串是否null,为null的话，用""代替
        dataIdPattern = (dataIdPattern == null ? "" : dataIdPattern.trim());
        groupNamePattern = (groupNamePattern == null ? "" : groupNamePattern
                .trim());

        long time = System.currentTimeMillis();
        // 调用查询结果
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long stime = System.currentTimeMillis();
        PageContextResult<ConfigInfo> pageContextResult = configureSDKManager
                .queryBy(dataIdPattern, groupNamePattern, contentPattern,
                        environment, currentPage, sizeOfPerPage); // 调用ConfigureSDK的查询接口
        configureQueue.add(configureSDKManager);

        JadeLog.doLog("pagequery", pageContextResult.isSuccess(),
                pageContextResult.getStatusMsg(), environment, "dataId:"
                        + dataIdPattern + "  groupName:" + groupNamePattern
                        + " contentpattern:" + contentPattern
                        + ".configure cost time:"
                        + (System.currentTimeMillis() - stime)
                        + "ms,get configure  " + (stime - time) + "ms");

        JadePageResult<JadeContentDO> jadeResult = null;
        if (pageContextResult.isSuccess()) {
            List<JadeContentDO> list = new Vector<JadeContentDO>();
            if (null != pageContextResult.getConfigureData()) {
                for (ConfigInfo cf : pageContextResult.getConfigureData()) {
                    list.add(new JadeContentDO(cf.getDataId(), cf.getGroup(),
                            cf.getContent(), cf.getId()));
                }
            }
            jadeResult = new JadePageResult<JadeContentDO>(pageContextResult,
                    list); // 创建返回对象
            jadeResult.setEnvId(envId);
            jadeResult.setEnvName(environment);
        } else {
            jadeResult = new JadePageResult<JadeContentDO>(false,
                    pageContextResult.getStatusMsg());

        }
        return jadeResult; // 创建返回对象
    }

    // 一定要带一个dataId来，先查一下内容，取id，打日志！！执行删除！！！！！！
    @Override
    public JadeContentResult<JadeContentDO> remove(String dataId,
                                                   String groupName, String environment) {
        environment = serverEnvironments.get(environment);
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PageContextResult<ConfigInfo> contextResult = configureSDKManager
                .queryBy(dataId, groupName, environment, 1, 100000);
        configureQueue.add(configureSDKManager);
        if (!contextResult.isSuccess()) {
            JadeLog.doLog("remove", false, "尝试删除" + dataId + "之前，查询失败",
                    environment, "错误原因：" + contextResult.getStatusMsg());
            return new JadeContentResult<JadeContentDO>(false, "查询" + dataId
                    + "失败!错误原因：" + contextResult.getStatusMsg());
        }
        if (contextResult.getConfigureData().isEmpty()) {
            JadeContentResult<JadeContentDO> result = new JadeContentResult<JadeContentDO>();
            result.setSuccess(true);
            result.setMsg("没有该配置，所以删除成功");
            return result;
        }

        if (contextResult.getConfigureData().size() > 1) {
            JadeLog.doLog("remove", false, "尝试删除" + dataId + "失败", environment,
                    "错误原因：数据库中有多个同名对象，无法删除");
            return new JadeContentResult<JadeContentDO>(false,
                    "错误原因：数据库中有多个同名对象，无法删除");
        }

        ConfigInfo configInfo = contextResult.getConfigureData().get(0);

        JadeContentDO jadeContentDO = new JadeContentDO(configInfo.getDataId(),
                configInfo.getGroup(), configInfo.getContent(),
                configInfo.getId());

        ContextResult removeRes = configureSDKManager.unpublish(environment,
                configInfo.getId());

        if (removeRes.isSuccess()) {
            JadeLog.doLog("remove", true, "删除数据成功", environment, jadeContentDO);
            return new JadeContentResult<JadeContentDO>(true,
                    removeRes.getStatusMsg(), jadeContentDO);
        } else {
            JadeLog.doLog("remove", false, "删除失败！", environment,
                    removeRes.getStatusMsg());
            return new JadeContentResult<JadeContentDO>(
                    contextResult.isSuccess(), contextResult.getStatusMsg());
        }

    }

    public Map<String, String> getServerEnvironments() {
        return this.serverEnvironments;
    }

    // 批量接口
    @Override
    public Map<String, JadeContentResult<JadeContentDO>> batchQuery(
            String groupName, List<String> dataIds, String envId) {
        Map<String, JadeContentResult<JadeContentDO>> res = new HashMap<String, JadeContentResult<JadeContentDO>>();
        String environment = serverEnvironments.get(envId);
        String errorKey = JadeConstants.JADE_ERROR_DATA_KEY;
        // 如果查询的环境不存在，操作失败
        if (!this.hasEnvironment(environment)) {
            String errorMsg = "[操作]失败：您选择的环境不存在";
            JadeLog.doLog("batchQuery", false, errorMsg, environment, "");
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    errorMsg, envId, environment, null));
            return res;
        }
        // 查询条件检查
        if (dataIds.isEmpty()) {
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    "[操作]失败：dataId 不能为空", envId, environment, null));
            return res;
        }
        groupName = null == groupName ? JadeConstants.JADE_DEFAULT_GROUP_NAME
                : groupName;

        long time = System.currentTimeMillis();
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long stime = System.currentTimeMillis();
        BatchContextResult<ConfigInfoEx> contextResults = configureSDKManager
                .batchQuery(environment, groupName, dataIds);
        configureQueue.add(configureSDKManager);
        JadeLog.doLog("batchQuery", true,
                ".configure cost time:" + (System.currentTimeMillis() - stime)
                        + "ms,get configure  " + (stime - time) + "ms",
                environment, "");

        if (contextResults.getStatusCode() != 200) {
            JadeLog.doLog("batchQuery", false, "Http返回状态码不为200", environment,
                    contextResults.getStatusMsg());
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    "Http返回状态码不为200", envId, environment, null));
            return res;
        }

        for (ConfigInfoEx configInfo : contextResults.getResult()) {
            JadeContentDO jadeContentDO = null;
            if (!StringUtils.isBlank(configInfo.getContent())) {
                jadeContentDO = new JadeContentDO(configInfo.getDataId(),
                        groupName, configInfo.getContent(), configInfo.getId());
            }
            JadeContentResult<JadeContentDO> tmpJade = new JadeContentResult<JadeContentDO>(
                    true, configInfo.getMessage(), jadeContentDO);
            tmpJade.setEnvId(envId);
            tmpJade.setEnvName(environment);
            res.put(configInfo.getDataId(), tmpJade);
        }
        return res;
    }

    @Override
    public Map<String, JadeContentResult<JadeContentDO>> batchCreateOrUpdate(
            List<JadeContentDO> jadeContentDOs, String envId, String groupName) {
        Map<String, JadeContentResult<JadeContentDO>> res = new HashMap<String, JadeContentResult<JadeContentDO>>();
        String environment = serverEnvironments.get(envId);
        Map<String, String> dataId2ContentMap = new HashMap<String, String>();
        String errorKey = JadeConstants.JADE_ERROR_DATA_KEY;
        if (jadeContentDOs.isEmpty()) {
            String errorMsg = "[操作]失败:批量内容不能为空";
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    errorMsg, envId, environment, null));
            return res;
        }

        // 如果查询的环境不存在
        if (!this.hasEnvironment(environment)) {
            String errorMsg = "[操作]失败：您选择的环境不存在";
            JadeLog.doLog("batchCreateOrUpdate", false, errorMsg, environment,
                    "");
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    errorMsg, envId, environment, null));
            return res;
        }
        // 构造修改或者发布条件
        for (JadeContentDO j : jadeContentDOs) {
            dataId2ContentMap.put(j.getDataId(), j.getContent());
        }
        groupName = null == groupName ? JadeConstants.JADE_DEFAULT_GROUP_NAME
                : groupName;

        String nick = StringUtils.defaultIfBlank((String) PtoolsThreadLocal
                .get(PtoolsThreadLocal.ATTRIBUTE_NICK), "nobody");

        long time = System.currentTimeMillis();
        BlockingQueue<ConfigureSDKManager> configureQueue = configureMap.get(environment);
        ConfigureSDKManager configureSDKManager = null;
        try {
            configureSDKManager = configureQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long stime = System.currentTimeMillis();
        BatchContextResult<ConfigInfoEx> contextResults = configureSDKManager
                .batchAddOrUpdate(environment, groupName, nick,
                        dataId2ContentMap);
        configureQueue.add(configureSDKManager);
        JadeLog.doLog("batchCreateOrUpdate", true, ".configure cost time:"
                + (System.currentTimeMillis() - stime) + "ms,get configure  "
                + (stime - time) + "ms", environment, "");

        if (contextResults.getStatusCode() != 200) {
            JadeLog.doLog("batchCreateOrUpdate", false, "Http返回状态码不为200",
                    environment, contextResults.getStatusMsg());
            res.put(errorKey, new JadeContentResult<JadeContentDO>(false,
                    contextResults.getStatusMsg(), envId, environment, null));
            return res;
        }

        for (ConfigInfoEx configInfo : contextResults.getResult()) {
            boolean isSuccess = configInfo.getStatus() >= com.pamirs.configure.common.Constants.BATCH_ADD_SUCCESS;
            JadeContentDO jadeContentDO = new JadeContentDO(
                    configInfo.getDataId(), groupName, configInfo.getContent(),
                    configInfo.getId());
            JadeContentResult<JadeContentDO> tmpJade = new JadeContentResult<JadeContentDO>(
                    isSuccess, configInfo.getMessage(), jadeContentDO);
            tmpJade.setEnvId(envId);
            tmpJade.setEnvName(environment);
            res.put(configInfo.getDataId(), tmpJade);
            JadeLog.doLog("batchCreateOrUpdate." + configInfo.getDataId(),
                    isSuccess, configInfo.getMessage(), environment,
                    jadeContentDO);
        }
        return res;
    }
}
