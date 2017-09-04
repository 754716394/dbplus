package com.pamirs.dbplus.core.service.table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.DisplayFiledDO;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.service.JadeConfService;
import com.pamirs.dbplus.api.service.LogicTableFiledsService;
import com.pamirs.dbplus.api.service.LogicTableService;
import com.pamirs.dbplus.api.service.PddlSqlService;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.*;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;
import com.pamirs.dbplus.configure.utils.JadePage;
import com.pamirs.pddl.rule.PddlRule;
import com.pamirs.pddl.rule.TableRule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pamirs.dbplus.core.constant.ErrorCodeConstants.*;

/**
 * config-server服务接口
 *
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/25
 */
public class JadeConfServiceImpl implements JadeConfService {

    private static final Logger logger = LoggerFactory.getLogger(JadeConfServiceImpl.class);

    private static String GROUPNAMEPATTERN = "DEFAULT_GROUP";

    @Autowired
    private JadeConfigManager jadeConfigManager;

    @Autowired
    private LogicTableService logicTableService;

    @Autowired
    private LogicTableFiledsService logicTableFiledsService;

    @Autowired
    private PddlSqlService pddlSqlService;

    @Override
    public Result<Void> syncAppNameRule(String environment, String appNamePattern) {

        Result<Void> result = new Result<>();
        ResultList<JadeAppRuleDO> jadeAppRules = this.getAppNameRuleFromConfigServer(environment, appNamePattern);
        String ruleStr = "";
        List<JadeAppRuleDO> o = (List<JadeAppRuleDO>) jadeAppRules.getDatalist();
        if (null != o && o.size() > 0) {
            JadeAppRuleDO jadeAppRuleDO = o.get(0);
            JadeAppRuleVersionDO versionDO = jadeAppRuleDO.getJadeAppRuleVersionDO();
            // 实际使用版本
            String version = versionDO.getVersionStr();
            for (JadeAppRuleDO rule : jadeAppRules.getDatalist()) {
                Map<String, JadeAppSingleRuleDO> jadeRule = rule.getRules();
                JadeAppSingleRuleDO jadeAppSingleRuleDO = jadeRule.get(version);
                ruleStr = jadeAppSingleRuleDO.getRuleStr();
            }
        }

        synchronized (appNamePattern + environment) {
            if (StringUtils.isNotBlank(ruleStr)) {
                // 初始化rule
                PddlRule pddlRule = new PddlRule();
                pddlRule.setAppRuleString(ruleStr);
                pddlRule.setAppName("PDDL_RULE");
                pddlRule.doInit();


                List<TableRule> tableRules = pddlRule.getTables();
                for (TableRule t : tableRules) {
//                    System.out.println("dbName pattern:" + t.getDbNamePattern() +
//                            "\ntb pattern:" + t.getTbNamePattern() + "\nvirt-tbName:" + t.getVirtualTbName() +
//                            "\ntb rule:" + t.getTbRulesStrs()[0] + "\nshard_key" + t.getShardColumns());
//                    System.out.println("---------------------------------------------------------------------");
                    LogicTableDO logicTableDO = new LogicTableDO();
                    logicTableDO.setMatrixName(appNamePattern);
                    logicTableDO.setTableName(t.getVirtualTbName());
                    logicTableDO.setDbGroup(t.getDbNamePattern());
                    logicTableDO.setDbCount(t.getDbCount());
                    logicTableDO.setTbCount(t.getTbCount());
                    List<String> shards = t.getShardColumns();
                    if (null != shards && shards.size() > 0) {
                        String shardColumn = JSONObject.toJSONString(shards, SerializerFeature.WriteClassName);
                        logicTableDO.setShardColumn(shardColumn);
                    }
                    List<String> rules = new ArrayList<>();
                    for (int i = 0; i < t.getTbRulesStrs().length; i++) {
                        rules.add(t.getTbRulesStrs()[i]);
                    }
                    String tbRule = JSONObject.toJSONString(rules, SerializerFeature.WriteClassName);
                    logicTableDO.setShardRule(tbRule);

                    Result<LogicTableDO> queryResult = logicTableService.queryByMatrixNameAndTableName(appNamePattern, t.getVirtualTbName());
                    LogicTableDO l = queryResult.getData();
                    if (null == l) {
                        Result<Void> result1 = logicTableService.add(logicTableDO);
                        if (!result1.isSuccess()) {
                            logger.error(MessageFormat.format("error:{0},param:{1}", DBPLUS_1000080.toString(),
                                    JSON.toJSONString(t)), result1.getErrorMessage());
                            result.setSuccess(Boolean.FALSE);
                            DBPLUS_1000080.setError(result);
                        }
                    } else {
                        logicTableDO.setId(l.getId());
                        Result<Void> result1 = logicTableService.modify(logicTableDO);
                        if (!result1.isSuccess()) {
                            logger.error(MessageFormat.format("error:{0},param:{1}", DBPLUS_1000081.toString(),
                                    appNamePattern), result1.getErrorMessage());
                            result.setSuccess(Boolean.FALSE);
                            DBPLUS_1000081.setError(result);
                        }
                    }

                    //获取逻辑表mysql表字段
                    Result<Map<String, String>> result2 = pddlSqlService
                            .fetchPddlTableFields(t.getVirtualTbName(), appNamePattern, shards.get(0));
                    if (result2.isSuccess()) {
                        int i = 0;
                        Map<String, String> filedsMap = result2.getData();
                        List<DisplayFiledDO> displayFiledDOS = new ArrayList<>();

                        for (Map.Entry<String, String> entry : filedsMap.entrySet()) {
                            DisplayFiledDO displayFiledDO = new DisplayFiledDO();
                            displayFiledDO.setRank(i);
                            displayFiledDO.setDisplay(Boolean.TRUE);
                            displayFiledDO.setName(entry.getKey());
                            displayFiledDO.setType(entry.getValue());
                            displayFiledDOS.add(displayFiledDO);
                            i++;
                        }
                        String fields = JSON.toJSONString(displayFiledDOS, SerializerFeature.WriteMapNullValue);
                        LogicTableFiledsDO logicTableFiledsDO = new LogicTableFiledsDO();
                        logicTableFiledsDO.setRelationId(logicTableDO.getId());
                        logicTableFiledsDO.setMatrixName(appNamePattern);
                        logicTableFiledsDO.setTableName(t.getVirtualTbName());
                        logicTableFiledsDO.setFileds(fields);
                        logicTableFiledsDO.setShardDbNum(logicTableDO.getDbCount());
                        logicTableFiledsDO.setShardTbNum(logicTableDO.getTbCount());
                        Result<Void> addResult = logicTableFiledsService.add(logicTableFiledsDO);
                        // fixme 重新同步会将排序打乱
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ResultList<JadeAppRuleDO> getAppNameRuleFromConfigServer(String environment, String appNamePattern) {

        ResultList<JadeAppRuleDO> resultList = new ResultList<>();

        appNamePattern = (appNamePattern == null ? "" : appNamePattern);
        String dataIdPattern = JadeAppRuleDO
                .getAppRuleVersionsDataIdPattern(appNamePattern);
        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, GROUPNAMEPATTERN, environment,
                        1, 20);


        JadePage<JadeContentDO> temp = basePageResult.getJadePage();
        List<JadeAppRuleDO> jadeRuleDOList = new ArrayList<JadeAppRuleDO>();
        for (JadeContentDO jadeContentDO : temp.getData()) {
            JadeAppRuleDO jadeAppRuleDO = new JadeAppRuleDO();
            String dataId = jadeContentDO.getDataId();
            String groupName = jadeContentDO.getGroupName();
            String content = jadeContentDO.getContent();
            try {
                Object[] appNameArray = (Object[]) new MessageFormat(
                        JadeConstants.JADE_RULE_LE_VERSIONS_DATA_ID_FORMAT)
                        .parseObject(dataId);
                String appName = String.valueOf(appNameArray[0]);
                jadeAppRuleDO.setAppName(appName);

                JadeAppRuleVersionDO usedVer = new JadeAppRuleVersionDO();
                usedVer.setAppName(appName);
                usedVer.setGroupName(groupName);
                usedVer.setVersionStr(content);
                jadeAppRuleDO.setJadeAppRuleVersionDO(usedVer);

                String ruleDataIdPattern = JadeAppRuleDO
                        .getAppRuleDataIdPattern(appName);
                JadePageResult<JadeContentDO> cbasePageResult = jadeConfigManager
                        .queryBy(ruleDataIdPattern, groupName, environment, 1,
                                Integer.MAX_VALUE);
                Map<String, JadeAppSingleRuleDO> allRuleMap = new HashMap<String, JadeAppSingleRuleDO>();
                JadePage<JadeContentDO> ctemp = cbasePageResult.getJadePage();
                for (JadeContentDO ver : ctemp.getData()) {
                    JadeAppSingleRuleDO ruleDO = new JadeAppSingleRuleDO();
                    String cdataId = ver.getDataId();
                    int i = cdataId.lastIndexOf(".");
                    String verStr = cdataId.substring(i + 1);
                    if (JadeAppRuleDO.isControllDataIdOrNoVersion(verStr)) {
                        continue;
                    }
                    ruleDO.setAppName(appName);
                    ruleDO.setGroupName(groupName);
                    ruleDO.setRuleStr(ver.getContent());
                    ruleDO.setVersion(verStr);
                    allRuleMap.put(verStr, ruleDO);
                }
                jadeAppRuleDO.setRules(allRuleMap);
            } catch (ParseException e) {
                logger.error(MessageFormat.format("error:{0},param:{1}",
                        DBPLUS_1000082.toString(),
                        JSON.toJSONString(jadeAppRuleDO)), e);
                resultList.setSuccess(Boolean.FALSE);
                DBPLUS_1000082.setError(resultList);
            }
            jadeRuleDOList.add(jadeAppRuleDO);
        }
        resultList.setDatalist(jadeRuleDOList);

        return resultList;
    }
}
