package com.pamirs.dbplus.configure.jade;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JadeGroupDO extends JadeBaseDO {

	public String toJsonString() {
		List<String> list = new ArrayList<String>();
		if (groupItems != null) {
			for (GroupItem groupItem : groupItems) {
				list.add(groupItem.toJsonString());
			}
		}
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("groupKey", groupKey);
			jsonObj.put("groupName", groupName);
			jsonObj.put("groupItems", list);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null) {
			return;
		}
		key = key.trim();
		value = value.trim();
		super.setByKeyValue(key, value);
		if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_GROUP_KEY)) {
			this.setGroupKey(value);
		}
	}

	public JadeGroupDO() {

	}

	@Override
	public int hashCode() {
		return this.groupKey.hashCode();
	}

	public JadeGroupDO(String groupKey) {
		this.groupKey = groupKey;
	}

	private String groupKey;
	private List<GroupItem> groupItems;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof JadeGroupDO))
			return false;
		JadeGroupDO jadeGroupDO = (JadeGroupDO) obj;
		if ((groupItems == null || groupItems.isEmpty())
				&& (jadeGroupDO.getGroupItems() == null || jadeGroupDO
						.getGroupItems().isEmpty())) {
			return StringUtils.equals(groupName, jadeGroupDO.getGroupName())
					&& StringUtils.equals(groupKey, jadeGroupDO.getGroupKey());
		}
		if ((groupItems == null || groupItems.isEmpty())
				|| (jadeGroupDO.getGroupItems() == null || jadeGroupDO
						.getGroupItems().isEmpty())) {
			return false;
		}

		if (groupItems.size() != jadeGroupDO.getGroupItems().size())
			return false;

		for (int i = 0; i < groupItems.size(); i++) {
			if (!groupItems.get(i).equals(jadeGroupDO.getGroupItems().get(i)))
				return false;
		}
		return StringUtils.equals(groupName, jadeGroupDO.getGroupName())
				&& StringUtils.equals(groupKey, jadeGroupDO.getGroupKey());

	}

	/**
	 * 把当前对象构建成一个Content对象
	 * 
	 * @return
	 */
	public String getContent() {
		return JadeConvert.toBaseDO(this).getContent();
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public List<GroupItem> getGroupItems() {
		return groupItems;
	}

	public void setGroupItems(List<GroupItem> groupItems) {
		this.groupItems = groupItems;
	}

	/**
	 * 检查是否有空值
	 * 
	 * @return true 含有空值，false不含有空值
	 */
	public boolean checkHasNull() {
		if (StringUtils.isBlank(this.groupKey)) {
			return true;
		}
		if (StringUtils.isBlank(this.groupName)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
