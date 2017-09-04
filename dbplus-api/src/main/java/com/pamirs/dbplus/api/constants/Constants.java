package com.pamirs.dbplus.api.constants;


/**
 * 常量
 */
public interface Constants {
	
	// 密码分隔符
	public static String PWD_SPLIT = "$";
	// 权限分隔符
	public static String AUTH_SPLIT = "-";
	
	// 状态标志
	public static int STATUS_ACTIVE = 1;
	public static int STATUS_UNACTIVE = 0;
	
	// session中user的name
	public static String SESSION_LOGIN_NAME = "dbplus-user";
	public static String SESSION_AUTH_NAME = "dbplus-auth";
	public static String SESSION_REMEMBERME = "rememberme";
	
	// cookie中user的name
	public static String COOKIE_LOGIN_NAME = "SESSION_ID";
	
	// context上下文中的user的name
	public static String CONTEXT_LOGIN_NAME = "login_user";
	public static String CONTEXT_AUTH_NAME = "login_auth";
	

}
