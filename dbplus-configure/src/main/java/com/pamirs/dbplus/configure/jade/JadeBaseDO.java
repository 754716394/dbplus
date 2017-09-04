package com.pamirs.dbplus.configure.jade;

import com.pamirs.dbplus.configure.constant.JadeConstants;
import org.apache.commons.lang.StringUtils;

public class JadeBaseDO {
	protected long id;
	protected String groupName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null)
			return;
		key = key.trim();
		value = value.trim();
		if (StringUtils.equalsIgnoreCase(JadeConstants.GROUP_NAME, key)) {
			groupName = value;
		}
	}

}
