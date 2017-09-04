package com.pamirs.dbplus.api.service;

import com.pamirs.commons.dao.Result;

import java.util.Map;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/25
 */
public interface PddlSqlService {

    Result<String> fetchPddlQueryBySql(String logicTableName, String appName, String sqlStr);

    Result<String> fetchPddlQueryByColumn(String logicTableName, String appName, String columnName, String columnValue, String extraFileds);

    Result<Map<String,String>> fetchPddlTableFields(String logicTableName, String appName, String columnName);
}
