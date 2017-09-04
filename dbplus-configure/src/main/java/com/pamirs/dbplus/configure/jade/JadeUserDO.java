package com.pamirs.dbplus.configure.jade;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import com.pamirs.pddl.securety.PasswordCoderPamirsImpl;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 用户记录对象
 */
public class JadeUserDO extends JadeBaseDO {

	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(JadeConstants.JADE_USER_DBNAME, this.dbName);
			jsonObj.put(JadeConstants.JADE_USER_DBTYPE, this.dbType);
			jsonObj.put(JadeConstants.JADE_USER_USERNAME, this.userName);
			jsonObj.put(JadeConstants.JADE_USER_PASSWORD, this.password);
			jsonObj.put(JadeConstants.JADE_USER_KEY_ENCKEY, this.encKey);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	public JadeUserDO() {

	}

	public JadeUserDO(String dbName, String dbType, String userName) {
		this.dbName = dbName;
		this.dbType = dbType;
		this.userName = userName;

	}

	private String dbName;// 数据库名
	private String dbType;// 数据库类型
	private String userName;// 用户名

	private String password;// 明文
	private String encKey; // 密钥

	@Override
	public int hashCode() {
		String temp = dbName + dbType + userName;
		return temp.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof JadeUserDO))
			return false;
		JadeUserDO jadeUserDO = (JadeUserDO) obj;
		return StringUtils.equals(this.dbName, jadeUserDO.getDbName())
				&& StringUtils.equals(this.dbType, jadeUserDO.getDbType())
				&& StringUtils.equals(this.encKey, jadeUserDO.getEncKey())
				&& StringUtils.equals(this.groupName, jadeUserDO.getGroupName())
				&& StringUtils.equals(this.password, jadeUserDO.getPassword())
				&& StringUtils.equals(this.userName, jadeUserDO.getUserName());
	}

	public void setByKeyValue(String key, String value) {
		if (key == null || value == null)
			return;
		super.setByKeyValue(key, value);
		key = key.trim();
		value = value.trim();
		if (JadeConstants.JADE_USER_KEY_ENCKEY.equalsIgnoreCase(key)) {
			this.encKey = value;
		} else if (JadeConstants.JADE_USER_USERNAME.equalsIgnoreCase(key)) {
			this.userName = value;
		} else if (JadeConstants.JADE_USER_DBNAME.equalsIgnoreCase(key)) {
			this.dbName = value;
		} else if (JadeConstants.JADE_USER_DBTYPE.equalsIgnoreCase(key)) {
			this.dbType = value;
		} else if (JadeConstants.JADE_USER_PASSWORD.equalsIgnoreCase(key)) {
			this.password = value;
		}
	}

	/**
	 * 检查时候有空值
	 * 
	 * @return
	 */
	public boolean checkHasNull() {

		if (this.getDbName() == null || this.getDbName().trim().equals("")) {
			return true;
		}
		if (this.getDbType() == null || this.getDbType().trim().equals("")) {
			return true;
		}
		if (this.getUserName() == null || this.getUserName().trim().equals("")) {
			return true;
		}
		if (this.getPassword() == null || this.getPassword().trim().equals("")) {
			return true;
		}
		if (this.groupName == null || this.groupName.trim().equals("")) {
			return true;
		}
		return false;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public String getEncPasswd() {

		try {
			PasswordCoderPamirsImpl passwordCoder = JadeConvert.getPasswordCoder();
			if (StringUtils.isBlank(this.password)) {
				return "";
			} else if (StringUtils.isBlank(this.encKey)) {
				return passwordCoder.encode(this.password);
			} else
				return passwordCoder.encode(this.encKey,this.password);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * public void setEncPasswd(String encPasswd) { this.encPasswd = encPasswd;
	 * }
	 */
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return JadeConvert.toBaseDO(this).getContent();
	}

	@Override
	public String toString() {
		return "JadeUserDO [dbName=" + dbName + ", dbType=" + dbType
				+ ", userName=" + userName + ", encKey=" + encKey + "]";
	}

}
