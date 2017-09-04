package com.pamirs.dbplus.configure.jade;

import java.util.List;
import java.util.Vector;

public class JadeConfigureConfig {
	private String environment; //环境名称
	private List<JadeConfigureServerConfig> configures; //configure的配置对象
	public JadeConfigureConfig() {
		super();
		configures=new Vector<JadeConfigureServerConfig>();
	}
	
	public String getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public List<JadeConfigureServerConfig> getConfigures() {
		return configures;
	}
	public void setConfigures(List<JadeConfigureServerConfig> configures) {
		this.configures = configures;
	}
	
	

	
}
