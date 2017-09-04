package com.pamirs.dbplus.configure.jade;

public class JadeConfigureDBConfig {
	
	private String dbIp;  //数据库ip
	
	private String dbPort; //数据库端口
	
	private String dbName;  //数据库名
	
	private String dbUserName;  //数据库用户名
	
	private String dbPasswd;  //数据库密码
	
	@Override
	public String toString() {
		return "["+dbIp+","+dbPort+","+dbName+","+dbUserName+","+dbPasswd+"]";
	}
	public JadeConfigureDBConfig() {
		super();
	}
	public JadeConfigureDBConfig(String dbIp, String dbPort, String dbName,
                                 String dbUserName, String dbPasswd) {
		super();
		this.dbIp = dbIp;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUserName = dbUserName;
		this.dbPasswd = dbPasswd;
	}
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPasswd() {
		return dbPasswd;
	}
	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}
	

}
