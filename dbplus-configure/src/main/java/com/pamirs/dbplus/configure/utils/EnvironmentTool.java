package com.pamirs.dbplus.configure.utils;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EnvironmentTool implements ToolFactory {
	
	static final String ENVIRONMENTTOOL = "environment-tool";

	private static Map<String,String> serverEnvironments;// 环境名称列表

	public static Map<String, String> getServerEnvironments() {
		return serverEnvironments;
	}
	
	/**
	 * 环境的KeySet
	 * @return
	 */
	public Set<String> environmentKeySet()
	{
		return serverEnvironments.keySet();
	}
	
	/**
	 * 环境的EntrySet
	 * @return
	 */
	public Set<Entry<String,String>> environmentEntrySet()
	{
		return serverEnvironments.entrySet();
	}
	
	
	public static void setServerEnvironments(Map<String, String> serverEnvironments) {
		EnvironmentTool.serverEnvironments = serverEnvironments;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object createTool() throws Exception {
		// TODO Auto-generated method stub
		return this;
	}

//	public static class DefinitionParser extends
//			AbstractNamedBeanDefinitionParser<EnvironmentTool> {
//
//		@Override
//		protected String getDefaultName() {
//			return EnvironmentTool.ENVIRONMENTTOOL;
//		}
//
//	}

}
