package com.pamirs.dbplus.configure.utils;

/**
 *
 */
public class Constants {

	public static String GEMINI_DATUM_ID = "GeminiRedundantManager";

	public static final String PAGE_CACHE_DATA_ID = "com.pamirs.pagecache.config";

	public static final String R_JDBC_DATA_ID = "com.pamirs.rjdbc.datasource.config";

	public static final String R_TFS_DATA_ID = "com.pamirs.tfs.server.nameserver.config";

	public static final String R_SEARCH_DATA_ID = "com.pamirs.searchengine.server.mergeserverconfig";

	public static final String R_FEED_DATA_ID = "com.pamirs.searchengine.server.feedserver.config";

	public static final String R_CHECK_CODE_DATA_ID = "com.pamirs.rcheckcode.config";
	
	public static final String R_FLC_DATA_ID = "com.pamirs.gemini.flc.config";
	
	public static String[] R_DATAIDS = new String[]{
		PAGE_CACHE_DATA_ID,
		R_JDBC_DATA_ID,
		R_TFS_DATA_ID,
		R_SEARCH_DATA_ID,
		R_FEED_DATA_ID,
		R_CHECK_CODE_DATA_ID,
		R_FLC_DATA_ID};
	
	public static final String TOP_CACHE_SWITCHER_DATA_ID = "com.pamirs.top.cache.switcher";
	
	public static final String CLIENT_SERVER_IP_KEY="!Server";
	
	public static final String DEFAULT_GROUP_NAME="DEFAULT_GROUP";
	
	public static final String DEFALUT_CLIENT_SERVER_PORT="9600";
	
	public static final String PDDL_DATA_ID_FIX="com.pamirs.pddl.v1_";

	public static String EQUAL_MARK = "=";
	
	public static String SPLIT_MARK = "�I";
 
	public static String COMMA_MARK = ",";
	
	public static String POINT_MARK = ".";

	public static String EMPTY_MARK = " ";

	public static String SEMICOLON_MARK = ";";
	
	public static String COLON_MARK = ":";

	public static String STICK_MARK = "|";

	public static String STR_LINE_MARK = "-";
	
	public static String UNDER_LINE_MARK = "_";
	
	public static String AND_MARK = "&";
	
	public static String UP_TR_MARK = "��";
	
	public static String DOW_TR_MARK = "��";
	
	public static String START_MARK = "��";
	
	public static String ALL_MARK = "*";

	public static String FILE_ARGS = STR_LINE_MARK + "f";

	public static String GROUP_ARGS = STR_LINE_MARK + "g";

	public static String OVER_ARGS = STR_LINE_MARK + "o";

	public static String TYPE_ARGS = STR_LINE_MARK + "t";

	public static String ACTION_ARGS = STR_LINE_MARK + "a";

	public static String VALUE_ARGS = STR_LINE_MARK + "v";

	public static String SWITCH_ARGS = STR_LINE_MARK + "d";
	
	public static final String ADDR_ARGS=STR_LINE_MARK+"addr";

	public static int READ_TIME_OUT = 20000;
	
	public static final String CONFIGURE_ADDR_ARGS=STR_LINE_MARK+"configure";

}
