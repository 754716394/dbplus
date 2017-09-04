package com.pamirs.dbplus.core.service.table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.model.DisplayFiledDO;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.service.LogicTableFiledsService;
import com.pamirs.dbplus.api.service.LogicTableService;
import com.pamirs.dbplus.api.service.PddlSqlService;
import com.pamirs.pddl.common.exception.PddlException;
import com.pamirs.pddl.matrix.jdbc.TDataSource;
import com.pamirs.pddl.rule.PddlRule;
import com.pamirs.pddl.rule.TableRule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pamirs.dbplus.core.constant.ErrorCodeConstants.*;


/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/25
 */
public class PddlSqlServiceImpl implements PddlSqlService {

    private static final Logger logger = LoggerFactory.getLogger(PddlSqlServiceImpl.class);

    @Autowired
    private LogicTableService logicTableService;
    @Autowired
    private LogicTableFiledsService logicTableFiledsService;
    //http://www.cnblogs.com/henryhappier/archive/2010/07/05/1771295.html
    //DDL（Data Definition Language）数据库定义语言
    //static String DDL= "CREATE,ALTER,DROP,TRUNCATE,COMMENT,RENAME";
    static List<String> DDLs = Lists.newArrayList("CREATE", "ALTER", "DROP", "TRUNCATE", "COMMENT", "RENAME");
    //DML（Data Manipulation Language）数据操纵语言
    //static String DML = "SELECT,INSERT,UPDATE,DELETE,MERGE,CALL,EXPLAIN PLAN,LOCK TABLE";
    //允许执行SELECT 和 EXPLAIN PLAN
    static List<String> DMLs = Lists.newArrayList("INSERT", "UPDATE", "DELETE", "MERGE", "CALL", "LOCK", "TABLE");
    //DCL（Data Control Language）数据库控制语言  授权，角色控制等
    //static String DCL = "GRANT,REVOKE";
    static List<String> DCLs = Lists.newArrayList("GRANT", "REVOKE");
    //TCL（Transaction Control Language）事务控制语言
    //static String TCL = "SAVEPOINT,ROLLBACK,SET TRANSACTION";
    static List<String> TCLs = Lists.newArrayList("SAVEPOINT", "ROLLBACK", "SET", "TRANSACTION");

    Map<String/**appName*/, TDataSource /**dataSource*/> dsMap = Maps.newConcurrentMap();

    @Override
    public Result<String> fetchPddlQueryBySql(String logicTableName, String appName, String sqlStr) {
        Result<String> result = new Result<>();

        //Step1.只允许执行SELECT 和 EXPLAIN PLAN
        for (String ddl : DDLs) {
            if (sqlStr.toUpperCase().contains(ddl)) {
                result.setSuccess(Boolean.FALSE);
                result.setErrorMessage("语法错误：仅支持查询(SELECT)");
                return result;
            }
        }
        for (String dml : DMLs) {
            if (sqlStr.toUpperCase().contains(dml)) {
                result.setSuccess(Boolean.FALSE);
                result.setErrorMessage("语法错误：仅支持查询(SELECT)");
                return result;
            }
        }
        for (String dcl : DCLs) {
            if (sqlStr.toUpperCase().contains(dcl)) {
                result.setSuccess(Boolean.FALSE);
                result.setErrorMessage("语法错误：仅支持查询(SELECT)");
                return result;
            }
        }
        for (String tcl : TCLs) {
            if (sqlStr.toUpperCase().contains(tcl)) {
                result.setSuccess(Boolean.FALSE);
                result.setErrorMessage("语法错误：仅支持查询(SELECT)");
                return result;
            }
        }
        //step2. 判断sql的where是否包含分表字段
        Connection conn = null;
        Statement stmt = null;
        TDataSource dataSource = dsMap.get(appName);
        try {
            if (dataSource == null) {
                dataSource = new TDataSource();
                dataSource.setAppName(appName);
                dataSource.setDynamicRule(true);
                dataSource.init();
                dsMap.put(appName, dataSource);
            }
            PddlRule pddlRule = new PddlRule();
            pddlRule.setAppRuleString(dataSource.getAppRuleString());
            pddlRule.setAppName(appName);
            pddlRule.doInit();
            List<TableRule> tableRules = pddlRule.getTables();
            List<String> shards = Lists.newArrayList();
            for (TableRule t : tableRules) {
                if (!Strings.isNullOrEmpty(t.getVirtualTbName())) {
                    if (t.getVirtualTbName().equalsIgnoreCase(logicTableName)) {
                        shards = t.getShardColumns();
                        break;
                    }
                }
            }
            //如果配置了分表规则，则where条件中必须要求包含分表字段
            if (shards.size() > 0) {
                boolean hasShareCloumn = false;
                String subWhereCause = sqlStr.substring(sqlStr.toUpperCase().indexOf("WHERE"));
                if (!Strings.isNullOrEmpty(subWhereCause)) {
                    for (String shard : shards) {
                        if (subWhereCause.toUpperCase().contains(shard.toUpperCase())) {
                            hasShareCloumn = true;
                            break;
                        }
                    }
                }
                if (!hasShareCloumn) {
                    result.setSuccess(Boolean.FALSE);
                    result.setErrorMessage("语法错误：WHERE语句中必须包含分表字段[" + Joiner.on(",").join(shards) + "]");
                    return result;
                }
            }

            Result<LogicTableDO> logicTableDOResult = logicTableService.queryByMatrixNameAndTableName(appName, logicTableName);
            LogicTableDO logicTableDO = logicTableDOResult.getData();
            Result<LogicTableFiledsDO> logicTableFiledsDOResult = logicTableFiledsService.queryOneByRelationId(logicTableDO.getId());
            LogicTableFiledsDO logicTableFiledsDO = logicTableFiledsDOResult.getData();
            String fileds = logicTableFiledsDO.getFileds();
            List<DisplayFiledDO> filedList = JSONObject.parseArray(fileds, DisplayFiledDO.class);
            List<String> filedDOS = new ArrayList<>();
            for (DisplayFiledDO displayFiledDO : filedList) {
                if (!displayFiledDO.getDisplay()) {
                    continue;
                }
                filedDOS.add(displayFiledDO.getName());
            }
            String fetchFileds = filedDOS.toString().replace("[", "").replace("]", "");

            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            String sql = sqlStr;
            if (!sqlStr.toLowerCase().contains("limit")) {
                sql = sqlStr + " limit 0,200";
            }
            ResultSet r = stmt.executeQuery(sql);

            Map<String, List<String>> resultMap = new HashMap<>();
            List<String> cs = new ArrayList<>();
            List<String> ls = new ArrayList<>();

            ResultSetMetaData rsmd = r.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String fetchFiledUpper = fetchFileds.toUpperCase();
            // 输出列名
            List<String> outPutFields = Lists.newArrayList();
            for (int i = 1; i <= columnCount; i++) {
                if (fetchFiledUpper.contains(rsmd.getColumnName(i).toUpperCase())) {
                    cs.add(rsmd.getColumnName(i));
                    outPutFields.add(rsmd.getColumnName(i));
                }
            }
            // 输出数据
            while (r.next()) {
                List<String> line = new ArrayList<>();
                for (String field : outPutFields) {
                    line.add(r.getString(field));
                }
                String l = JSON.toJSONString(line, SerializerFeature.WriteMapNullValue);
                ls.add(l);
            }
            resultMap.put("column", cs);
            resultMap.put("data", ls);
            result.setData(JSON.toJSONString(resultMap, SerializerFeature.WriteMapNullValue));
        } catch (PddlException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000070.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000070.setError(result);
        } catch (SQLException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000071.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000071.setError(result);
        } finally {
            try {
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                logger.error(MessageFormat.format("error:{0}",
                        DBPLUS_1000073.toString()), e);
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000073.setError(result);
            }
        }

        return result;
    }

    @Override
    public Result<String> fetchPddlQueryByColumn(String logicTableName, String appName, String columnName, String columnValue, String extraFileds) {
        Result<String> result = new Result<>();
        Result<LogicTableDO> logicTableDOResult = logicTableService.queryByMatrixNameAndTableName(appName, logicTableName);
        LogicTableDO logicTableDO = logicTableDOResult.getData();
        if (null == logicTableDO) {
            logger.error("逻辑表获取为空. appName:" + appName + "|logicTableName:" + logicTableName);
            result.setSuccess(Boolean.FALSE);
            result.setErrorMessage("逻辑表获取为空");
            return result;
        }
        Result<LogicTableFiledsDO> logicTableFiledsDOResult = logicTableFiledsService.queryOneByRelationId(logicTableDO.getId());
        LogicTableFiledsDO logicTableFiledsDO = logicTableFiledsDOResult.getData();
        String fileds = logicTableFiledsDO.getFileds();
        List<DisplayFiledDO> filedList = JSONObject.parseArray(fileds, DisplayFiledDO.class);
        List<String> filedDOS = new ArrayList<>();
        for (DisplayFiledDO displayFiledDO : filedList) {
            if (!displayFiledDO.getDisplay()) {
                continue;
            }
            filedDOS.add(displayFiledDO.getName());
        }
        String fetchFileds = filedDOS.toString().replace("[", "").replace("]", "");

        Connection conn = null;
        Statement stmt = null;
        TDataSource dataSource = new TDataSource();
        dataSource.setAppName(appName);
        dataSource.setDynamicRule(true);
        try {
            dataSource.init();
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            String sql = "select " + fetchFileds + "  from " + logicTableName + " where " + columnName + " = " + columnValue
                    + " " + StringUtils.trimToEmpty(extraFileds);
            if (!sql.toUpperCase().contains("LIMIT")) {
                sql = sql + " limit 0, 500";
            }
            logger.error("execute-SQL:" + sql);
            ResultSet r = stmt.executeQuery(sql);

            Map<String, List<String>> resultMap = new HashMap<>();
            List<String> cs = new ArrayList<>();
            List<String> ls = new ArrayList<>();

            ResultSetMetaData rsmd = r.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // 输出列名
            for (int i = 1; i <= columnCount; i++) {
                cs.add(rsmd.getColumnName(i));
            }
            // 输出数据
            while (r.next()) {
                List<String> line = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    line.add(r.getString(i));
                }
                String l = JSON.toJSONString(line, SerializerFeature.WriteMapNullValue);
                ls.add(l);
            }
            resultMap.put("column", cs);
            resultMap.put("data", ls);
            result.setData(JSON.toJSONString(resultMap, SerializerFeature.WriteMapNullValue));
        } catch (PddlException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000070.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000070.setError(result);
        } catch (SQLException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000071.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000071.setError(result);
        } finally {
            try {
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                logger.error(MessageFormat.format("error:{0}",
                        DBPLUS_1000073.toString()), e);
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000073.setError(result);
            }
        }

        return result;
    }

    @Override
    public Result<Map<String, String>> fetchPddlTableFields(String logicTableName, String appName, String columnName) {
        Result<Map<String, String>> result = new Result<>();
        Connection conn = null;
        Statement stmt = null;

        TDataSource dataSource = new TDataSource();
        dataSource.setAppName(appName);
        dataSource.setDynamicRule(true);
        try {
            dataSource.init();
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            ResultSet r = stmt.executeQuery("select *  from " + logicTableName + " where " + columnName + " = 0 limit 1");

            Map<String, String> filedsMap = new HashMap<>();
            ResultSetMetaData rsmd = r.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // 输出列名
            for (int i = 1; i <= columnCount; i++) {
                filedsMap.put(rsmd.getColumnName(i), rsmd.getColumnTypeName(i).replace("Type", ""));
            }
            result.setData(filedsMap);
        } catch (PddlException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000070.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000070.setError(result);
        } catch (SQLException e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_1000071.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000071.setError(result);
        } finally {
            try {
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(MessageFormat.format("error:{0}",
                        DBPLUS_1000073.toString()), e);
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000073.setError(result);
            }
        }
        return result;
    }

}
