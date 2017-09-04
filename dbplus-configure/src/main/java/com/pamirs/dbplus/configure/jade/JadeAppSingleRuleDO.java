package com.pamirs.dbplus.configure.jade;


public class JadeAppSingleRuleDO extends JadeBaseDO {
	private String appName;
	private String version;
	private String ruleStr;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRuleStr() {
		return ruleStr;
	}

	public void setRuleStr(String ruleStr) {
		this.ruleStr = ruleStr;
	}
	
	public boolean checkHasNull(){
		if(this.getAppName()==null || this.getAppName().equals(""))
			return true;
		if(this.getGroupName()==null || this.getGroupName().equals(""))
			return true;
		if(this.getVersion()==null||this.getVersion().equals(""))
			return true;
		if(this.getRuleStr()==null||this.getRuleStr().equals(""))
		    return true;
		return false;
	}
}
