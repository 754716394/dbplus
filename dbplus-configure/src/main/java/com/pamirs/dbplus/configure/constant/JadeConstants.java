package com.pamirs.dbplus.configure.constant;

public class JadeConstants {
	
	public static final String JADE_ERROR_DATA_KEY="error data";
	
	public static final String JADE_ATOM="DB";
	public static final String JADE_USER="USER";
	public static final String JADE_APP="APP";
	public static final String JADE_GROUP="GROUP";
	public static final String JADE_MATRIX="MATRIX";
	
	public static final String GROUP_NAME="groupName";
	public static final String JADE_DEFAULT_GROUP_NAME="DEFAULT_GROUP";
	public static final String JADE_GLOBAL_DATA_ID="com.pamirs.pddl.atom.global.";
	
	public static final String JADE_CONFIGURE_ID="id";
	public static final String JADE_GLOBAL_DBKEY="dbKey";
	public static final String JADE_GLOBAL_IP="ip";
	public static final String JADE_GLOBAL_PORT="port";
	public static final String JADE_GLOBAL_DBNAME="dbName";
	public static final String JADE_GLOBAL_DBTYPE="dbType";
	public static final String JADE_GLOBAL_DBSTATUS="dbStatus";
	
	public static final String JADE_USER_DATA_ID="com.pamirs.pddl.atom.passwd.";
	public static final String JADE_USER_KEY_ENCPASSWD="encPasswd";
	public static final String JADE_USER_KEY_ENCKEY="encKey";
	public static final String JADE_USER_DEFALUT_ENCKEY="TAtomDefaultKey";
	public static final String JADE_USER_USERNAME="userName";
	public static final String JADE_USER_DBNAME="dbName";
	public static final String JADE_USER_DBTYPE="dbType";
	public static final String JADE_USER_PASSWORD="password";
	
	/** 默认加密*/
	public static final String JADE_DEFAULT_ENCKEY="defaultEncKey";
	
	public static final String JADE_APP_DATA_ID="com.pamirs.pddl.atom.app.";
	public static final String JADE_APP_USERNAME="userName";
	public static final String JADE_APP_MINPOOLSIZE="minPoolSize";
	public static final String JADE_APP_MAXPOOLSIZE="maxPoolSize";
	public static final String JADE_APP_BLOCKINGTIMEOUT="blockingTimeout";
	
	public static final String JADE_APP_IDLETIMEOUT="idleTimeout";
	public static final String JADE_APP_PERPAREDSTATEMENTCACHESIZE="preparedStatementCacheSize";
	public static final String JADE_APP_CONNECTIONPROPERTIES="connectionProperties";
	public static final String JADE_APP_ORACLECONTYPE="oracleConType";
	public static final String JADE_APP_WRITERESTRICTTIMES="writeRestrictTimes";
	public static final String JADE_APP_READRESTRICTTIMES="readRestrictTimes";
	public static final String JADE_APP_THREADCOUNTRESTRICT="threadCountRestrict";
	public static final String JADE_APP_TIMESLICEINMILLIS="timeSliceInMillis";
	public static final String JADE_APP_APPNAME="appName";
	public static final String JADE_APP_MAXCONCURRENTREADRESTRICT="maxConcurrentReadRestrict";
	public static final String JADE_APP_MAXCONCURRENTWRITERESTRICT="maxConcurrentWriteRestrict";
	
	public static final String JADE_APP_OPSFREENAME="opsfreeName";
	
	public static final String JADE_GROUP_DATA_ID="com.pamirs.pddl.jdbc.group_V2.4.1_";
	public static final String JADE_GROUP_KEY="groupKey";
	public static final String JADE_GROUP_FILTER_DATA_ID="com.pamirs.pddl.jdbc.extra_config.group_V2.4.1_";
	
	public static final String JADE_MATRIX_DATA_ID="com.pamirs.pddl.v1_";
	public static final String JADE_APPNAME_OPSFREENAME_DATA_ID="com.pamirs.pddl.mapping.";
	public static final String JADE_MATRIX_DATA_ID_TAIL="_dbgroups";
	
	public static final String JADE_OPERATE_PUB="pub";
	public static final String JADE_OPEARTE_REPUB="repub";
	
	public static final String JADE_DBTYPE_MYSQL="mysql";
	public static final String JADE_DBTYPE_ORACLE="oracle";
	public static final String JADE_ORACLE_CONN_THIN="thin";
	public static final String JADE_ORACLE_CONN_OCI="oci";
	
	public static final String JADE_PARALLEL_DATA_ID="com.pamirs.pddl.jdbc.client.sqlexecutor.";
	public static final String JADE_PARALLEL_USEPARALLELEXECUTE="useParallelExecute";
	public static final String JADE_PARALLEL_PARALLELTHREADCOUNT="parallelThreadCount";
	
	public static final String JADE_RULE_LE_VERSIONS_DATA_ID_FORMAT="com.pamirs.pddl.rule.le.{0}.versions";
	public static final String JADE_RULE_LE_VERSIONS_DATA_ID_PATTERN_FORMAT="com.pamirs.pddl.rule.le.*{0}*.versions";
	public static final String JADE_RULE_LE_DATA_ID_FORMAT="com.pamirs.pddl.rule.le.{0}.{1}";
	public static final String JADE_RULE_LE_DATA_ID_PATTERN_FORMAT="com.pamirs.pddl.rule.le.{0}.*";
}
