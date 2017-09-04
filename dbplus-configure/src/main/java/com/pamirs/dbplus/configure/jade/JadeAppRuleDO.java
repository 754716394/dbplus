package com.pamirs.dbplus.configure.jade;


import com.pamirs.dbplus.configure.constant.JadeConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Map;


public class JadeAppRuleDO {
	public static final String PDDL_RULE_VERSION_SPLITOR = ",";
	
	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用的所有规则
	 */
	private Map<String/* version */, JadeAppSingleRuleDO> rules;
	private String rulesStr;

	/**
	 * 使用的版本,有顺序
	 */
	private JadeAppRuleVersionDO jadeAppRuleVersionDO;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Map<String, JadeAppSingleRuleDO> getRules() {
		return rules;
	}

	public void setRules(Map<String, JadeAppSingleRuleDO> rules) {
		this.rules = rules;
		int i=0;
		int mapSize=rules.size();
		StringBuilder temp=new StringBuilder();
		for(String key:rules.keySet()){
			temp.append(key);
			if(i<(mapSize-1)){
				temp.append(PDDL_RULE_VERSION_SPLITOR);
			}
			i++;
		}
		this.rulesStr=temp.toString();
	}
	
	public String getRulesStr() {
		return rulesStr;
	}

	public JadeAppRuleVersionDO getJadeAppRuleVersionDO() {
		return jadeAppRuleVersionDO;
	}

	public void setJadeAppRuleVersionDO(
			JadeAppRuleVersionDO jadeAppRuleVersionDO) {
		this.jadeAppRuleVersionDO = jadeAppRuleVersionDO;
	}

	public static String getAppRuleVersionsDataId(String appName) {
		String versionsDataId = new MessageFormat(
				JadeConstants.JADE_RULE_LE_VERSIONS_DATA_ID_FORMAT)
				.format(new Object[] { appName });
		return versionsDataId;
	}

	public static String getAppRuleDataId(String appName, String version) {
		String ruleDataId = new MessageFormat(
				JadeConstants.JADE_RULE_LE_DATA_ID_FORMAT).format(new Object[] {
				appName, version });
		return ruleDataId;
	}

	public static String getAppRuleVersionsDataIdPattern(String appName) {
		String versionsDataIdPattern = new MessageFormat(
				JadeConstants.JADE_RULE_LE_VERSIONS_DATA_ID_PATTERN_FORMAT)
				.format(new Object[] { appName });
		return versionsDataIdPattern;
	}

	public static String getAppRuleDataIdPattern(String appName) {
		String ruleDataIdPattern = new MessageFormat(
				JadeConstants.JADE_RULE_LE_DATA_ID_PATTERN_FORMAT)
				.format(new Object[] { appName });
		return ruleDataIdPattern;
	}

	public static boolean isControllDataIdOrNoVersion(String versionStr) {
		if (null == versionStr || "".equals(versionStr.trim())
				|| versionStr.equals("versions")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String[] splitRuleVersionStr(String versionStr){
		return StringUtils.split(versionStr,PDDL_RULE_VERSION_SPLITOR);
	}
}
