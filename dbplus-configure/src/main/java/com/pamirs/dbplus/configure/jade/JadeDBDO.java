package com.pamirs.dbplus.configure.jade;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.utils.JadeConvert;

/**
 * 数据库对象
 */
public class JadeDBDO extends JadeBaseDO {

	private String dbKey = "";// 数据库主键
	private String ip = "";// ip地址
	private String port = ""; // 端口号
	private String dbName = "";// 数据库名
	private String dbType = "";// 数据库类型
	private String dbStatus = "";// 数据库状态

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof JadeDBDO))
			return false;
		JadeDBDO jadeDBDO = (JadeDBDO) obj;

		return this.dbKey.equals(jadeDBDO.getDbKey()) && this.ip.equals(jadeDBDO.getIp())
				&& this.port.equals(jadeDBDO.getPort()) && this.dbName.equals(jadeDBDO.getDbName())
				&& this.dbType.equals(jadeDBDO.getDbType()) && this.dbStatus.equals(jadeDBDO.getDbStatus())
				&& this.groupName.equals(jadeDBDO.getGroupName());

	}

	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(JadeConstants.JADE_GLOBAL_DBKEY, this.dbKey);
			jsonObj.put(JadeConstants.JADE_GLOBAL_IP, this.ip);
			jsonObj.put(JadeConstants.JADE_GLOBAL_PORT, this.port);
			jsonObj.put(JadeConstants.JADE_GLOBAL_DBNAME, this.dbName);
			jsonObj.put(JadeConstants.JADE_GLOBAL_DBTYPE, this.dbType);
			jsonObj.put(JadeConstants.JADE_GLOBAL_DBSTATUS, this.dbStatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null)
			return;
		super.setByKeyValue(key, value);
		key = key.trim();
		value = value.trim();
		if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_DBKEY)) {
			this.setDbKey(value);
		} else if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_IP)) {
			this.setIp(value);
		} else if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_PORT)) {
			this.setPort(value);
		} else if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_DBNAME)) {
			this.setDbName(value);
		} else if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_DBTYPE)) {
			this.setDbType(value);
		} else if (key.equalsIgnoreCase(JadeConstants.JADE_GLOBAL_DBSTATUS)) {
			this.setDbStatus(value);
		}
	}

	/**
	 * 检查是否有空值
	 * 
	 * @return
	 */
	public boolean checkHasNull() {
		if (dbKey == null || dbKey.trim().equals("")) {
			return true;
		}
		if (ip == null || ip.trim().equals("")) {
			return true;
		}
		if (port == null || port.trim().equals("")) {
			return true;
		}
		if (dbName == null || dbName.trim().equals("")) {
			return true;
		}
		if (dbType == null || dbType.trim().equals("")) {
			return true;
		}
		if (dbStatus == null || dbStatus.trim().equals("")) {
			return true;
		}
		if (groupName == null || groupName.trim().equals("")) {
			return true;
		}
		return false;

	}

	public String getDbKey() {
		return dbKey;
	}

	public void setDbKey(String dbKey) {
		this.dbKey = dbKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
	}

	public String getContent() {
		return JadeConvert.toBaseDO(this).getContent();
	}

	@Override
	public String toString() {
		return toJsonString();
	}
	
}
