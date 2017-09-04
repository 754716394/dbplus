package com.pamirs.dbplus.configure.jade;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import org.apache.commons.lang3.StringUtils;

public class JadeAppDO extends JadeBaseDO {
	private String appName;//应用名
	private String dbKey; //数据库的主键
	private String oracleConType;//Oracle的连接方式
	private String userName;
	private String minPoolSize;
	private String maxPoolSize;
	private String blockingTimeout;
	private String idleTimeout;
	private String preparedStatementCacheSize;
	private String connectionProperties;

	private String writeRestrictTimes;//写，次数限制
	private String readRestrictTimes;//读，次数限制
	private String threadCountRestrict;//thread count 次数限制
	private String timeSliceInMillis;//

	private String maxConcurrentReadRestrict; // 允许并发读的最大个数，0为不限制
	private String maxConcurrentWriteRestrict;//允许并发写的最大个数，0为不限制

	public JadeAppDO() {
		super();
	}

	public JadeAppDO(String appName, String dbKey) {
		super();
		this.appName = appName;
		this.dbKey = dbKey;
	}

	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(JadeConstants.JADE_GLOBAL_DBKEY, this.dbKey);
			jsonObj.put(JadeConstants.JADE_APP_APPNAME, this.appName);
			jsonObj.put(JadeConstants.JADE_APP_ORACLECONTYPE, this.oracleConType);
			jsonObj.put(JadeConstants.JADE_APP_USERNAME, this.userName);
			jsonObj.put(JadeConstants.JADE_APP_MINPOOLSIZE, this.minPoolSize);
			jsonObj.put(JadeConstants.JADE_APP_MAXPOOLSIZE, this.maxPoolSize);
			jsonObj.put(JadeConstants.JADE_APP_BLOCKINGTIMEOUT, this.blockingTimeout);
			jsonObj.put(JadeConstants.JADE_APP_IDLETIMEOUT, this.idleTimeout);
			jsonObj.put(JadeConstants.JADE_APP_PERPAREDSTATEMENTCACHESIZE, this.preparedStatementCacheSize);
			jsonObj.put(JadeConstants.JADE_APP_CONNECTIONPROPERTIES, this.connectionProperties);
			jsonObj.put(JadeConstants.JADE_APP_WRITERESTRICTTIMES, this.writeRestrictTimes);
			jsonObj.put(JadeConstants.JADE_APP_READRESTRICTTIMES, this.readRestrictTimes);
			jsonObj.put(JadeConstants.JADE_APP_THREADCOUNTRESTRICT, this.threadCountRestrict);
			jsonObj.put(JadeConstants.JADE_APP_TIMESLICEINMILLIS, this.timeSliceInMillis);
			jsonObj.put(JadeConstants.JADE_APP_MAXCONCURRENTREADRESTRICT, this.maxConcurrentReadRestrict);
			jsonObj.put(JadeConstants.JADE_APP_MAXCONCURRENTWRITERESTRICT, this.maxConcurrentWriteRestrict);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof JadeAppDO)) {
			return false;
		}
		JadeAppDO jadeAppDO = (JadeAppDO) obj;

		boolean ret = (groupName == null || groupName.equals(jadeAppDO.getGroupName()))
				&& (appName == null || appName.equals(jadeAppDO.getAppName()))
				&& (dbKey == null || dbKey.equals(jadeAppDO.getDbKey()))
				&& (userName == null || userName.equals(jadeAppDO.getUserName()))
				&& (minPoolSize == null || minPoolSize.equals(jadeAppDO.getMinPoolSize()))
				&& (maxPoolSize == null || maxPoolSize.equals(jadeAppDO.getMaxPoolSize()))
				&& (idleTimeout == null || idleTimeout.equals(jadeAppDO.getIdleTimeout()))
				&& (blockingTimeout == null || blockingTimeout.equals(jadeAppDO.getBlockingTimeout()))
				&& (preparedStatementCacheSize == null || preparedStatementCacheSize.equals(jadeAppDO
						.getPreparedStatementCacheSize()))
				&& (connectionProperties == null || connectionProperties.equals(jadeAppDO.getConnectionProperties()))
				&& StringUtils.equals(writeRestrictTimes, jadeAppDO.getWriteRestrictTimes())
				&& StringUtils.equals(readRestrictTimes, jadeAppDO.getReadRestrictTimes())
				&& StringUtils.equals(threadCountRestrict, jadeAppDO.getThreadCountRestrict())
				&& StringUtils.equals(timeSliceInMillis, jadeAppDO.getTimeSliceInMillis())
				&& StringUtils.equals(this.oracleConType, jadeAppDO.getOracleConType())
				&& StringUtils.equals(maxConcurrentReadRestrict, jadeAppDO.getMaxConcurrentReadRestrict())
				&& StringUtils.equals(this.maxConcurrentWriteRestrict, jadeAppDO.getMaxConcurrentWriteRestrict());
		return ret;
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null) {
			return;
		}
		key = key.trim();
		value = value.trim();
		super.setByKeyValue(key, value);
		if (JadeConstants.JADE_APP_USERNAME.equalsIgnoreCase(key)) {
			this.setUserName(value);
		} else if (JadeConstants.JADE_APP_MINPOOLSIZE.equalsIgnoreCase(key)) {
			this.setMinPoolSize(value);
		} else if (JadeConstants.JADE_APP_MAXPOOLSIZE.equalsIgnoreCase(key)) {
			this.setMaxPoolSize(value);
		} else if (JadeConstants.JADE_APP_IDLETIMEOUT.equalsIgnoreCase(key)) {
			this.setIdleTimeout(value);
		} else if (JadeConstants.JADE_APP_BLOCKINGTIMEOUT.equalsIgnoreCase(key)) {
			this.setBlockingTimeout(value);
		} else if (JadeConstants.JADE_APP_PERPAREDSTATEMENTCACHESIZE.equalsIgnoreCase(key)) {
			this.setPreparedStatementCacheSize(value);
		} else if (JadeConstants.JADE_APP_CONNECTIONPROPERTIES.equalsIgnoreCase(key)) {
			this.setConnectionProperties(value);
		} else if (JadeConstants.GROUP_NAME.equalsIgnoreCase(key)) {
			this.setGroupName(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_ORACLECONTYPE)) {
			this.setOracleConType(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_WRITERESTRICTTIMES)) {
			this.setWriteRestrictTimes(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_READRESTRICTTIMES)) {
			this.setReadRestrictTimes(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_THREADCOUNTRESTRICT)) {
			this.setThreadCountRestrict(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_TIMESLICEINMILLIS)) {
			this.setTimeSliceInMillis(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_APPNAME)) {
			this.setAppName(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_GLOBAL_DBKEY)) {
			this.setDbKey(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_MAXCONCURRENTREADRESTRICT)) {
			this.setMaxConcurrentReadRestrict(value);
		} else if (StringUtils.equalsIgnoreCase(key, JadeConstants.JADE_APP_MAXCONCURRENTWRITERESTRICT)) {
			this.setMaxConcurrentWriteRestrict(value);
		}
	}

	public boolean checkHasNull() {

		if (appName == null || this.getAppName().trim().equals("")) {
			return true;
		}
		if (dbKey == null || this.getDbKey().trim().equals("")) {
			return true;
		}
		if (userName == null || this.getUserName().trim().equals("")) {
			return true;
		}
		if (groupName == null || this.getGroupName().trim().equals("")) {
			return true;
		}

		if (StringUtils.isEmpty(this.getIdleTimeout())) {
			return true;
		}

		if (StringUtils.isNumeric(this.getMinPoolSize()) && StringUtils.isNumeric(this.getMaxPoolSize())) {
			long minpsize = Long.valueOf(this.getMinPoolSize());
			long maxpsize = Long.valueOf(this.getMaxPoolSize());
			if (minpsize <= 0) {
				//最小
				return true;
			}
			if (maxpsize < minpsize) {
				return true;
			}
		} else if (StringUtils.isNumeric(this.getMinPoolSize())) {
			long minpsize = Long.valueOf(this.getMinPoolSize());
			if (minpsize <= 0) {
				return true;
			}
		} else if (StringUtils.isNumeric(this.getMaxPoolSize())) {
			long maxpsize = Long.valueOf(this.getMaxPoolSize());
			if (maxpsize <= 0) {
				return true;
			}
		}
		return false;
	}

	public String getContent() {
		return JadeConvert.toBaseDO(this).getContent();
	}

	public String getDbKey() {
		return dbKey;
	}

	public void setDbKey(String dbKey) {
		this.dbKey = dbKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(String idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public String getBlockingTimeout() {
		return blockingTimeout;
	}

	public void setBlockingTimeout(String blockingTimeout) {
		this.blockingTimeout = blockingTimeout;
	}

	public String getPreparedStatementCacheSize() {
		return preparedStatementCacheSize;
	}

	public void setPreparedStatementCacheSize(String preparedStatementCacheSize) {
		this.preparedStatementCacheSize = preparedStatementCacheSize;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getOracleConType() {
		return oracleConType;
	}

	public void setOracleConType(String oracleConType) {
		this.oracleConType = oracleConType;
	}

	public String getWriteRestrictTimes() {
		return writeRestrictTimes;
	}

	public void setWriteRestrictTimes(String writeRestrictTimes) {
		this.writeRestrictTimes = writeRestrictTimes;
	}

	public String getReadRestrictTimes() {
		return readRestrictTimes;
	}

	public void setReadRestrictTimes(String readRestrictTimes) {
		this.readRestrictTimes = readRestrictTimes;
	}

	public String getThreadCountRestrict() {
		return threadCountRestrict;
	}

	public void setThreadCountRestrict(String threadCountRestrict) {
		this.threadCountRestrict = threadCountRestrict;
	}

	public String getTimeSliceInMillis() {
		return timeSliceInMillis;
	}

	public void setTimeSliceInMillis(String timeSliceInMillis) {
		this.timeSliceInMillis = timeSliceInMillis;
	}

	public String getMaxConcurrentReadRestrict() {
		return maxConcurrentReadRestrict;
	}

	public void setMaxConcurrentReadRestrict(String maxConcurrentReadRestrict) {
		this.maxConcurrentReadRestrict = maxConcurrentReadRestrict;
	}

	public String getMaxConcurrentWriteRestrict() {
		return maxConcurrentWriteRestrict;
	}

	public void setMaxConcurrentWriteRestrict(String maxConcurrentWriteRestrict) {
		this.maxConcurrentWriteRestrict = maxConcurrentWriteRestrict;
	}

	@Override
	public String toString() {
		return "JadeAppDO [appName=" + appName + ", dbKey=" + dbKey
				+ ", oracleConType=" + oracleConType + ", userName=" + userName
				+ ", minPoolSize=" + minPoolSize + ", maxPoolSize="
				+ maxPoolSize + ", blockingTimeout=" + blockingTimeout
				+ ", idleTimeout=" + idleTimeout
				+ ", preparedStatementCacheSize=" + preparedStatementCacheSize
				+ ", connectionProperties=" + connectionProperties
				+ ", writeRestrictTimes=" + writeRestrictTimes
				+ ", readRestrictTimes=" + readRestrictTimes
				+ ", threadCountRestrict=" + threadCountRestrict
				+ ", timeSliceInMillis=" + timeSliceInMillis
				+ ", maxConcurrentReadRestrict=" + maxConcurrentReadRestrict
				+ ", maxConcurrentWriteRestrict=" + maxConcurrentWriteRestrict
				+ "]";
	}
}