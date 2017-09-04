package com.pamirs.dbplus.configure.ao.impl;

import com.pamirs.dbplus.configure.ao.JadeMatrixAO;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.*;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import com.pamirs.dbplus.configure.utils.JadeLog;
import com.pamirs.dbplus.configure.utils.JadePage;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;

public class JadeMatrixAOImpl extends JadeBaseAOImpl implements JadeMatrixAO {

    @Override
    public JadeContentResult<JadeMatrixDO> publish(JadeMatrixDO jadeMatrixDO,
                                                   String environment) {
        if (jadeMatrixDO.checkHasNull()) {
            JadeContentResult<JadeMatrixDO> res = new JadeContentResult<JadeMatrixDO>(
                    false, "您提供的配置属性不足，提交失败！");
            JadeLog.doLog("publishJadeMatrixDO", false, "您提供的配置属性不足，提交失败！",
                    environment, jadeMatrixDO.toString());
            return res;
        }
        JadeContentResult<JadeContentDO> baseResult = jadeConfigManager
                .publish(JadeConvert.toBaseDO(jadeMatrixDO), environment); // 发布数据

        JadeContentResult<JadeMatrixDO> res = new JadeContentResult<JadeMatrixDO>(
                baseResult, jadeMatrixDO);// 创建返回值
        JadeLog.doLog("publishJadeMatrixDO", res.isSuccess(), res.getMsg(),
                environment, jadeMatrixDO.toString());
        return res;
    }

    @Override
    public JadeContentResult<JadeMatrixDO> publishAfterModified(
            JadeMatrixDO jadeMatrixDO, String environment) {
        if (jadeMatrixDO.checkHasNull()) {
            JadeContentResult<JadeMatrixDO> res = new JadeContentResult<JadeMatrixDO>(
                    false, "您提供的配置属性不足，提交失败！");
            JadeLog.doLog("publishJadeMatrixDO", false, "您提供的配置属性不足，提交失败！",
                    environment, jadeMatrixDO.toString());
            return res;
        }
        JadeContentResult<JadeContentDO> baseResult = jadeConfigManager
                .publishAfterModified(JadeConvert.toBaseDO(jadeMatrixDO),
                        environment); // 发布数据

        JadeContentResult<JadeMatrixDO> res = new JadeContentResult<JadeMatrixDO>(
                baseResult, jadeMatrixDO);// 创建返回值
        JadeLog.doLog("publishJadeMatrixDO", res.isSuccess(), res.getMsg(),
                environment, jadeMatrixDO.toString());
        return res;
    }

    @Override
    public JadeContentResult<JadeMatrixDO> query(String appName, String groupName, String environment) {
        appName = (appName == null ? "" : appName);
        String dataId = JadeConstants.JADE_MATRIX_DATA_ID + appName
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;
        JadeContentResult<JadeContentDO> baseResult = null;
        JadeContentResult<JadeMatrixDO> res = null;

        baseResult = jadeConfigManager.queryByDataIdAndGroupName(dataId,
                groupName, environment);

        if (baseResult.isSuccess()) {
            if (null != baseResult.getAbstractDO()) {

                res = new JadeContentResult<JadeMatrixDO>(baseResult,
                        JadeConvert.toMatrixDO(baseResult.getAbstractDO()));
            } else {
                res = new JadeContentResult<JadeMatrixDO>(baseResult, null);
            }
        } else {
            res = new JadeContentResult<JadeMatrixDO>(baseResult,
                    new JadeMatrixDO(appName));
        }
        return res;
    }

    @Override
    public JadePageResult<JadeMatrixDO> pagequery(String groupKeyPattern,
                                                  String groupNamePattern, String environment, long currentPage,
                                                  long sizeOfPerPage) {

        groupKeyPattern = (groupKeyPattern == null ? "" : groupKeyPattern);
        String dataIdPattern = JadeConstants.JADE_MATRIX_DATA_ID + "*"
                + groupKeyPattern + "*"
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;// 构建匹配dataId,
        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, groupNamePattern, environment,
                        currentPage, sizeOfPerPage);

        if (!basePageResult.isSuccess()) {
            return new JadePageResult<JadeMatrixDO>(false,
                    basePageResult.getMsg());// 失败的话，直接包装，返回
        }
        JadePage<JadeContentDO> temp = basePageResult.getJadePage();// 取出原来的页对象

        List<JadeMatrixDO> list = new Vector<JadeMatrixDO>();
        for (JadeContentDO jadeContentDO : temp.getData()) {
            list.add(JadeConvert.toMatrixDO(jadeContentDO));
        }
        JadePage<JadeMatrixDO> jadePage = new JadePage<JadeMatrixDO>(temp, list);

        return new JadePageResult<JadeMatrixDO>(basePageResult, jadePage);
    }

    @Override
    public List<JadeMoveResult> move(String fromEnvironment,
                                     String targetEnvironment, String groupName, boolean overwrite,
                                     String... appNames) {
        List<JadeMoveResult> reslist = new Vector<JadeMoveResult>();
        for (String appName : appNames) {
            JadeContentResult<JadeMatrixDO> fromres = this.query(appName,
                    groupName, fromEnvironment);

            if (!fromres.isSuccess()) {
                reslist.add(new JadeMoveResult(appName, false, false,
                        "源环境的Matrix不存在" + fromres.getMsg()));
                continue;
            }
            JadeContentResult<JadeMatrixDO> tores = this.query(appName,
                    groupName, targetEnvironment);
            if (!tores.isSuccess()) {
                reslist.add(new JadeMoveResult(appName, false, false, tores
                        .getMsg()));
            } else {
                if (null != tores.getAbstractDO()) {
                    if (!overwrite) {
                        reslist.add(new JadeMoveResult(appName, false, false,
                                "该配置在目标环境中已经存在！且未选择覆盖选项"));
                    } else {
                        JadeContentResult<JadeMatrixDO> pubres = this
                                .publishAfterModified(fromres.getAbstractDO(),
                                        targetEnvironment);
                        reslist.add(new JadeMoveResult(appName, pubres
                                .isSuccess(), pubres.isSuccess(), pubres
                                .getMsg()));
                    }
                }
            }
        }
        return reslist;
    }

    @Override
    public JadeContentResult<JadeMatrixDO> remove(String appName,
                                                  String groupName, String environment) {
        if (StringUtils.isBlank(appName)) {
            return new JadeContentResult<JadeMatrixDO>(false, "appName为空！");
        }
        String dataId = JadeConstants.JADE_MATRIX_DATA_ID + appName
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;

        JadeContentResult<JadeContentDO> res = jadeConfigManager.remove(dataId,
                groupName, environment);
        if (res.isSuccess()) {
            JadeMatrixDO jadeMatrixDO = JadeConvert.toMatrixDO(res
                    .getAbstractDO());
            return new JadeContentResult<JadeMatrixDO>(true, res.getMsg(),
                    jadeMatrixDO);
        } else {
            return new JadeContentResult<JadeMatrixDO>(false, res.getMsg());
        }
    }

    @Override
    public Map<String, JadeContentResult<JadeMatrixDO>> batchCreateOrUpdate(
            List<JadeMatrixDO> jadeDOs, String envId, String groupName) {
        List<JadeContentDO> jadeContentDOs = new Vector<JadeContentDO>();
        Map<String, JadeContentResult<JadeMatrixDO>> res = new HashMap<String, JadeContentResult<JadeMatrixDO>>();
        Map<String, String> serverEvns = jadeConfigManager
                .getServerEnvironments();

        for (JadeMatrixDO jadeMatrixDO : jadeDOs) {
            JadeContentDO jadeContentDO = JadeConvert.toBaseDO(jadeMatrixDO);
            jadeContentDOs.add(jadeContentDO);
        }

        Map<String, JadeContentResult<JadeContentDO>> resJade = jadeConfigManager
                .batchCreateOrUpdate(jadeContentDOs, envId, groupName);

        for (Entry<String, JadeContentResult<JadeContentDO>> en : resJade
                .entrySet()) {
            JadeContentResult<JadeContentDO> jadeContent = en.getValue();
            JadeMatrixDO jadeDO = JadeConvert.toMatrixDO(jadeContent
                    .getAbstractDO());
            JadeContentResult<JadeMatrixDO> jadeContentResult = new JadeContentResult<JadeMatrixDO>(
                    jadeContent.isSuccess(), jadeContent.getMsg(), envId,
                    serverEvns.get(envId), jadeDO);
            res.put(fromDataId2AppName(en.getKey()), jadeContentResult);
        }
        return res;
    }

    @Override
    public Map<String, JadeContentResult<JadeMatrixDO>> batchQuery(
            String groupName, List<String> dataIds, String envId) {
        Map<String, JadeContentResult<JadeContentDO>> resJade = jadeConfigManager
                .batchQuery(groupName, dataIds, envId);
        Map<String, String> serverEvns = jadeConfigManager
                .getServerEnvironments();
        Map<String, JadeContentResult<JadeMatrixDO>> res = new HashMap<String, JadeContentResult<JadeMatrixDO>>();
        for (Entry<String, JadeContentResult<JadeContentDO>> en : resJade
                .entrySet()) {
            JadeContentResult<JadeContentDO> jadeContent = en.getValue();
            JadeMatrixDO jadeDO = JadeConvert.toMatrixDO(jadeContent.getAbstractDO());
            JadeContentResult<JadeMatrixDO> jadeContentResult = new JadeContentResult<JadeMatrixDO>(
                    jadeContent.isSuccess(), jadeContent.getMsg(), envId,
                    serverEvns.get(envId), jadeDO);
            res.put(fromDataId2AppName(en.getKey()), jadeContentResult);
        }
        return res;
    }

    @Override
    public List<JadeMoveResult> moveByBatch(String fromEnvironment,
                                            String targetEnvironment, String groupName, boolean overwrite,
                                            String... keys) {
        List<JadeMoveResult> reslist = new Vector<JadeMoveResult>();
        if (fromEnvironment.equals(targetEnvironment)) {
            reslist.add(new JadeMoveResult("same environment", false,
                    overwrite, "目标环境和原环境相同:" + fromEnvironment));
            return reslist;
        }
        List<String> dataIds = new Vector<String>();

        // 构造批量查询条件
        for (String key : keys) {
            String dataId = JadeConstants.JADE_MATRIX_DATA_ID + key
                    + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;
            dataIds.add(dataId);
        }
        // 查询2个环境下的结果
        Map<String, JadeContentResult<JadeMatrixDO>> fromJadeContentResults = batchQuery(
                groupName, dataIds, fromEnvironment);
        Map<String, JadeContentResult<JadeMatrixDO>> toJadeContentResults = batchQuery(
                groupName, dataIds, targetEnvironment);

        // 构造符合条件的批量修改的队列
        List<JadeMatrixDO> preparedJades = new Vector<JadeMatrixDO>();
        for (String key : keys) {
            JadeContentResult<JadeMatrixDO> fromres = fromJadeContentResults
                    .get(key);
            if (!fromres.isSuccess()) {
                reslist.add(new JadeMoveResult(fromres.getAbstractDO()
                        .getAppName(), false, false, fromres.getMsg()));
                continue;
            }
            JadeContentResult<JadeMatrixDO> tores = toJadeContentResults
                    .get(key);
            if (!tores.isSuccess()) {
                reslist.add(new JadeMoveResult(fromres.getAbstractDO()
                        .getAppName(), false, false, tores.getMsg()));
                continue;
            } else if (tores.isSuccess() && !overwrite) {
                reslist.add(new JadeMoveResult(key, false, false,
                        "该配置已经在目标环境存在，且未选中覆盖选项！"));
                continue;
            } else {
                preparedJades.add(fromres.getAbstractDO());
            }
        }
        // 将符合条件的JadeAppDO队列批量修改或者创建
        Map<String, JadeContentResult<JadeMatrixDO>> opJadeContentResults = batchCreateOrUpdate(
                preparedJades, targetEnvironment, groupName);
        // 将操作结果记录
        for (Entry<String, JadeContentResult<JadeMatrixDO>> en : opJadeContentResults
                .entrySet()) {
            JadeContentResult<JadeMatrixDO> opres = en.getValue();
            String key = fromDataId2AppName(en.getKey());
            reslist.add(new JadeMoveResult(key,
                    opres.isSuccess(), overwrite, opres.getMsg()));
        }

        return reslist;
    }

    @Override
    public String fromDataId2AppName(String dataId) {
        if (dataId.equals(JadeConstants.JADE_ERROR_DATA_KEY) ||
                !StringUtils.contains(dataId, JadeConstants.JADE_MATRIX_DATA_ID)) {
            return dataId;
        }
        String tmpKey = dataId.substring(JadeConstants.JADE_MATRIX_DATA_ID.length());
        return tmpKey.substring(0, tmpKey.length() - JadeConstants.JADE_MATRIX_DATA_ID_TAIL.length());
    }

    @Override
    public JadePageResult<JadeMatrixDO> pagequeryByContent(String content,
                                                           String groupName, String environment, long currentPage,
                                                           long sizeOfPerPage) {
        if (StringUtils.isBlank(content)) {
            return new JadePageResult<JadeMatrixDO>(false, "您输入的值为空！");
        }
        String dataIdPattern = JadeConstants.JADE_MATRIX_DATA_ID + "*"
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;

        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, groupName, "*" + content + "*",
                        environment, currentPage, sizeOfPerPage);
        if (!basePageResult.isSuccess()) {
            return new JadePageResult<JadeMatrixDO>(false,
                    basePageResult.getMsg());// 失败的话，直接包装，返回
        }

        JadePage<JadeContentDO> temp = basePageResult.getJadePage();// 取出原来的页对象
        List<JadeMatrixDO> list = new ArrayList<JadeMatrixDO>();
        for (JadeContentDO jadeContentDO : temp.getData()) {

            list.add(JadeConvert.toMatrixDO(jadeContentDO));
        }
        JadePage<JadeMatrixDO> jadePage = new JadePage<JadeMatrixDO>(temp, list);

        JadePageResult<JadeMatrixDO> result = new JadePageResult<JadeMatrixDO>(
                basePageResult, jadePage);

        result.setEnvId(basePageResult.getEnvId());
        result.setEnvName(basePageResult.getEnvName());
        return result;
    }
}
