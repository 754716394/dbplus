package com.pamirs.dbplus.configure.jade;

public class JadeConfigureServerConfig {
	private String configureIp;
	private String configurePort;
	private String configureUsername;
	private String configurePassword;
	
	@Override
	public String toString() {
		return "["+configureIp+","+configurePort+","+configureUsername+","+configurePassword+"]";
	}
	public JadeConfigureServerConfig() {
		super();
	}
	public JadeConfigureServerConfig(String configureIp, String configurePort,
                                     String configureUsername, String configurePassword) {
		super();
		this.configureIp = configureIp;
		this.configurePort = configurePort;
		this.configureUsername = configureUsername;
		this.configurePassword = configurePassword;
	}
	public String getConfigureIp() {
		return configureIp;
	}
	public void setConfigureIp(String configureIp) {
		this.configureIp = configureIp;
	}
	public String getConfigurePort() {
		return configurePort;
	}
	public void setConfigurePort(String configurePort) {
		this.configurePort = configurePort;
	}
	public String getConfigureUsername() {
		return configureUsername;
	}
	public void setConfigureUsername(String configureUsername) {
		this.configureUsername = configureUsername;
	}
	public String getConfigurePassword() {
		return configurePassword;
	}
	public void setConfigurePassword(String configurePassword) {
		this.configurePassword = configurePassword;
	}
	

}
