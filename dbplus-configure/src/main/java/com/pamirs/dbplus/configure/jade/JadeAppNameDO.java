package com.pamirs.dbplus.configure.jade;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JadeAppNameDO extends JadeBaseDO {
	private String appName;
	private String opsfreeName;
	private List<String> groupList;

	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("appName", appName);
			jsonObj.put("groupName", groupName);
			jsonObj.put("opsfreeName", opsfreeName);
			jsonObj.put("groupList", groupList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	public JadeAppNameDO() {

	}

	public JadeAppNameDO(String appName, String opsfreeName) {
		this.appName = appName;
		this.opsfreeName=opsfreeName;
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null)
			return;
		key = key.trim();
		value = value.trim();
		super.setByKeyValue(key, value);

		if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_APPNAME)) {
			this.setAppName(value);
		}
		
		if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_OPSFREENAME)) {
			this.setOpsfreeName(value);
		}
	}

	/**
	 * 得到JadeMatrixDO的content对象
	 * 
	 * @return
	 */
	public String getContent() {
		if (null != groupList) {
			StringBuffer sb = new StringBuffer();
			for (String s : groupList) {
				sb.append(s).append(",");
			}
			if (sb.length() > 0)
				return sb.substring(0, sb.length() - 1);
		}
		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof JadeAppNameDO))
			return false;
		JadeAppNameDO jadeAppNameDO = (JadeAppNameDO) obj;
		if ((groupList == null || groupList.isEmpty())
				&& (jadeAppNameDO.getGroupList() == null || jadeAppNameDO.groupList.isEmpty())) {
			return StringUtils.equals(appName, jadeAppNameDO.getAppName())
			        && StringUtils.equals(opsfreeName, jadeAppNameDO.getOpsfreeName())
					&& StringUtils.equals(groupName, jadeAppNameDO.getGroupName());
		}
		if ((groupList == null || groupList.isEmpty())
				|| (jadeAppNameDO.getGroupList() == null || jadeAppNameDO.groupList.isEmpty())) {
			return false;
		}
		if (groupList.size() != jadeAppNameDO.groupList.size()) {
			return false;
		}

		for (int i = 0; i < groupList.size(); i++) {
			if (!StringUtils.equals(groupList.get(i), jadeAppNameDO.getGroupList().get(i)))
				return false;
		}
		
		return StringUtils.equals(appName, jadeAppNameDO.getAppName())
		        && StringUtils.equals(opsfreeName, jadeAppNameDO.getOpsfreeName())
				&& StringUtils.equals(groupName, jadeAppNameDO.getGroupName());
	}

	/**
	 * 检查是否有空值
	 * 
	 * @return true 含有空值，false不含有空值
	 */
	public boolean checkHasNull() {
		if (StringUtils.isBlank(appName)) {
			return true;
		}
		if (StringUtils.isBlank(opsfreeName)) {
			return true;
		}
		if (StringUtils.isBlank(groupName)) {
			return true;
		}
		return false;

	}

	public String getOpsfreeName() {
		return opsfreeName;
	}

	public void setOpsfreeName(String opsfreeName) {
		this.opsfreeName = opsfreeName;
	}

	public List<String> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
