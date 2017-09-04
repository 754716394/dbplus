package com.pamirs.dbplus.configure.jade;

import java.util.Arrays;
import java.util.List;


public class JadeAppRuleVersionDO extends JadeBaseDO {
	private String appName;
	private List<String> versions;
	private String versionStr;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<String> getVersions() {
		return versions;
	}

	public void setVersions(List<String> versions) {

		this.versions = versions;
		if (versions != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < versions.size(); i++) {
				sb.append(versions.get(i));
				if (i < versions.size() - 1) {
					sb.append(JadeAppRuleDO.PDDL_RULE_VERSION_SPLITOR);
				}
			}
		} else {
			this.versionStr = null;
		}
	}

	public String getVersionStr() {
		return versionStr;
	}

	public void setVersionStr(String versionStr) {
		this.versionStr = versionStr;
		if (versionStr != null) {
			String[] vers = versionStr.split(JadeAppRuleDO.PDDL_RULE_VERSION_SPLITOR);
			this.versions = Arrays.asList(vers);
		} else {
			this.versions = null;
		}
	}
	
	
	public boolean checkHasNull(){
		if(this.getAppName()==null || this.getAppName().equals(""))
			return true;
		if(this.getGroupName()==null || this.getGroupName().equals(""))
			return true;
		if(this.getVersionStr()==null||this.getVersionStr().equals(""))
		    return true;
		return false;
	}
}
