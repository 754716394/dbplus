package com.pamirs.dbplus.configure;

import com.pamirs.dbplus.configure.ao.JadeMatrixAO;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.*;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import com.pamirs.dbplus.configure.utils.JadePage;
import com.pamirs.pddl.rule.PddlRule;
import com.pamirs.pddl.rule.TableRule;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/23
 */
public class ConfigureTest extends org.springframework.test.AbstractDependencyInjectionSpringContextTests {

    private JadeMatrixAO jadeMatrixAO;

    private JadeConfigManager jadeConfigManager;

    @Override
    protected String[] getConfigLocations() {
        String[] config = {
                "classpath:spring/spring-test-config.xml"
        };
        return config;
    }

    @Test
    public void testQueryMatrixAO() {

        JadeContentResult<JadeMatrixDO> result = jadeMatrixAO.query("", "ASSET-PRE", "2");

        JadeMatrixDO jadeMatrixDO = result.getAbstractDO();
        if (jadeMatrixDO != null) {
            String appName = result.getAbstractDO().getAppName();
        }

        System.out.println("id: " + result.getEnvId() + " do:" + result.getAbstractDO());
        assertEquals(result != null, true);

    }

    /**
     * 获取goupid=DEFAULT_GROUP所有的appName
     */
    @Test
    public void testPageQueryMatrixName() {

        String groupKeyPattern = "";
        String groupNamePattern = "DEFAULT_GROUP";
        String environment = "1";

        groupKeyPattern = (groupKeyPattern == null ? "" : groupKeyPattern);
        String dataIdPattern = JadeConstants.JADE_MATRIX_DATA_ID + "*"
                + groupKeyPattern + "*"
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;   // 构建匹配dataId,
        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, groupNamePattern, environment,
                        1, 20);

        JadePage<JadeContentDO> temp = basePageResult.getJadePage();// 取出原来的页对象

        if (null != temp) {
            List<JadeMatrixDO> list = new Vector<JadeMatrixDO>();
            for (JadeContentDO jadeContentDO : temp.getData()) {
                list.add(JadeConvert.toMatrixDO(jadeContentDO));
            }
            JadePage<JadeMatrixDO> jadePage = new JadePage<JadeMatrixDO>(temp, list);
            System.out.println("jadePage size: " + jadePage.getData().size());
            List<JadeMatrixDO> r = jadePage.getData();
            for (JadeMatrixDO j : r) {
                System.out.println(j.getAppName());
            }
        }

        assertEquals(basePageResult.isSuccess(), true);
    }

    @Test
    public void testPageQueryRule() {
        String appNamePattern = "MACENTER_APP";
        String groupNamePattern = "DEFAULT_GROUP";
        String environment = "1";

        appNamePattern = (appNamePattern == null ? "" : appNamePattern);
        String dataIdPattern = JadeAppRuleDO
                .getAppRuleVersionsDataIdPattern(appNamePattern);
        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, groupNamePattern, environment,
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
            }
            jadeRuleDOList.add(jadeAppRuleDO);
        }
        JadePage<JadeAppRuleDO> jadePage = new JadePage<JadeAppRuleDO>(temp,
                jadeRuleDOList);

        for (JadeAppRuleDO rule : jadeRuleDOList) {
//            System.out.println(rule.getRules());
            Map<String, JadeAppSingleRuleDO> jadeRule = rule.getRules();
            JadeAppSingleRuleDO j = jadeRule.get("1");
            String ruleStr = j.getRuleStr();
            System.out.println(ruleStr);
//            String path = "/Users/mitsui/Documents/workspace/code/pamirs/dbplus/dbplus-configure/src/main/resources/spring/test.xml";
//            FileUtil.saveAsFileOutputStream(path, ruleStr);
        }
        assertEquals(basePageResult.isSuccess(), true);
    }

    @Test
    public void testXml2Object() {

        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n" +
                "\n" +
                "<beans>\n" +
                "    <bean id=\"vtabroot\" class=\"com.pamirs.pddl.interact.rule.VirtualTableRoot\" init-method=\"init\">\n" +
                "        <property name=\"dbType\" value=\"MYSQL\" />\n" +
                "        <property name=\"defaultDbIndex\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tableRules\">\n" +
                "            <map>\n" +
                "                <entry key=\"my_favor_entity\" value-ref=\"my_favor_entity\" />\n" +
                "                <entry key=\"my_footprint_entity\" value-ref=\"my_footprint_entity\" />\n" +
                "                <entry key=\"tc_biz_order_buyer_entity\" value-ref=\"tc_biz_order_buyer_entity\" />\n" +
                "                <entry key=\"tc_order_buyer_relation_entity\" value-ref=\"tc_order_buyer_relation_entity\" />\n" +
                "                <entry key=\"tc_orderitem_snapshot_entity\" value-ref=\"tc_orderitem_snapshot_entity\" />\n" +
                "                <entry key=\"tc_pay_order_entity\" value-ref=\"tc_pay_order_entity\" />\n" +
                "            </map>\n" +
                "        </property>\n" +
                "    </bean>\n" +
                "    <bean id=\"my_favor_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"my_favor_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <value>Math.abs(#shop_user_id,1,64#.longValue() % 64)</value>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "    <bean id=\"my_footprint_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"my_footprint_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <value>Math.abs(#shop_user_id,1,256#.longValue() % 256)</value>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "    <bean id=\"tc_biz_order_buyer_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"tc_biz_order_buyer_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <list>\n" +
                "                <value>Math.abs(#city_code,1,256#.longValue() % 256)</value>\n" +
                "                <value>Math.abs(#biz_order_id,1,256#.longValue() % 256)</value>\n" +
                "            </list>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "    <bean id=\"tc_order_buyer_relation_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"tc_order_buyer_relation_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <value>Math.abs(#city_code,1,256#.longValue() % 256)</value>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "    <bean id=\"tc_orderitem_snapshot_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"tc_orderitem_snapshot_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <value>Math.abs(#city_code,1,256#.longValue() % 256)</value>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "    <bean id=\"tc_pay_order_entity\" class=\"com.pamirs.pddl.interact.rule.TableRule\">\n" +
                "        <property name=\"dbNamePattern\" value=\"TADA-MALL-GROUP\" />\n" +
                "        <property name=\"tbNamePattern\" value=\"tc_pay_order_entity_{0000}\" />\n" +
                "        <property name=\"tbRuleArray\">\n" +
                "            <value>Math.abs(#city_code,1,256#.longValue() % 256)</value>\n" +
                "        </property>\n" +
                "        <property name=\"allowFullTableScan\" value=\"false\" />\n" +
                "    </bean>\n" +
                "</beans>";
        Object o = str;
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");


        PddlRule pddlRule = new PddlRule();
        pddlRule.setAppRuleString(str);
        pddlRule.setAppName("PDDL_RULE");
        pddlRule.doInit();


//        PddlRule rule = (PddlRule) context.getBean("rule");


        List<TableRule> tableRules = pddlRule.getTables();
        for (TableRule t : tableRules) {

            System.out.println("dbName pattern:" + t.getDbNamePattern() +
                    "\ntb pattern:" + t.getTbNamePattern() + "\nvirt-tbName:" + t.getVirtualTbName() +
                    "\ntb rule:" +  t.getTbRulesStrs()[0] + "\nshard_key" + t.getShardColumns() +
                    "\ndbcount"+t.getDbCount() + "tbcount" + t.getTbCount());
            ;
            System.out.println("---------------------------------------------------------------------");
        }

    }

    public void setJadeMatrixAO(JadeMatrixAO jadeMatrixAO) {
        this.jadeMatrixAO = jadeMatrixAO;
    }

    public void setJadeConfigManager(JadeConfigManager jadeConfigManager) {
        this.jadeConfigManager = jadeConfigManager;
    }
}
