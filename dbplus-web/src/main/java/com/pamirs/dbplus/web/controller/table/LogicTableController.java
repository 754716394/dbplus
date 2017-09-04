package com.pamirs.dbplus.web.controller.table;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.DisplayFiledDO;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.query.LogicTableQuery;
import com.pamirs.dbplus.api.service.JadeConfService;
import com.pamirs.dbplus.api.service.LogicTableFiledsService;
import com.pamirs.dbplus.api.service.LogicTableService;
import com.pamirs.dbplus.api.service.PddlSqlService;
import com.pamirs.dbplus.configure.constant.JadeConstants;
import com.pamirs.dbplus.configure.jade.JadeContentDO;
import com.pamirs.dbplus.configure.jade.JadeMatrixDO;
import com.pamirs.dbplus.configure.jade.JadePageResult;
import com.pamirs.dbplus.configure.manager.JadeConfigManager;
import com.pamirs.dbplus.configure.utils.JadeConvert;
import com.pamirs.dbplus.configure.utils.JadePage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.pamirs.dbplus.core.constant.ErrorCodeConstants.*;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/7/4
 */
@Path("/dbplus/logictable")
@Controller
public class LogicTableController {

    private static final Logger logger = LoggerFactory.getLogger(LogicTableController.class);

    private JadeConfigManager jadeConfigManager;

    private JadeConfService jadeConfService;

    private LogicTableService logicTableService;

    private PddlSqlService pddlSqlService;

    @Autowired
    private LogicTableFiledsService logicTableFiledsService;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView showAppNameList(@Context HttpServletRequest request,
                                        @QueryParam(value = "environment") String environment,
                                        @QueryParam(value = "app_name") String groupKeyPattern,
                                        @QueryParam(value = "current_page") Integer currentPage,
                                        @QueryParam(value = "page_size") Integer pageSize) {

        Map<String, Object> context = new HashMap();

        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, userDO);

        if (StringUtils.isBlank(environment)) {
            environment = "1";
        }
        if (null == currentPage) {
            currentPage = 1;
        }
        if (null == pageSize) {
            pageSize = 20;
        }
        ResultList<JadeMatrixDO> result = new ResultList<>();

        String groupNamePattern = "DEFAULT_GROUP";

        groupKeyPattern = (groupKeyPattern == null ? "" : groupKeyPattern);
        String dataIdPattern = JadeConstants.JADE_MATRIX_DATA_ID + "*"
                + groupKeyPattern + "*"
                + JadeConstants.JADE_MATRIX_DATA_ID_TAIL;   // 构建匹配dataId,
        JadePageResult<JadeContentDO> basePageResult = jadeConfigManager
                .queryBy(dataIdPattern, groupNamePattern, environment, currentPage, pageSize);

        JadePage<JadeContentDO> temp = basePageResult.getJadePage();// 取出原来的页对象

        if (null != temp) {
            List<JadeMatrixDO> list = new Vector<>();
            for (JadeContentDO jadeContentDO : temp.getData()) {
                list.add(JadeConvert.toMatrixDO(jadeContentDO));
            }
            JadePage<JadeMatrixDO> jadePage = new JadePage<>(temp, list);
            result.setDatalist(jadePage.getData());
            result.setTotal(jadePage.getTotalCounts());
        }

        context.put("appNames", result.getDatalist());
        context.put("environment", environment);
        context.put("app_name", groupKeyPattern);
        context.put("nav_at", "appTable");
        return new ModelAndView("table/app_table", context);
    }


    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView showLogicTableList(@Context HttpServletRequest request,
                                           @QueryParam(value = "environment") String environment,
                                           @QueryParam(value = "matrixName") String matrixName,
                                           @QueryParam(value = "tableName") String tableName) {

        Map<String, Object> context = new HashMap();

        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, userDO);


        ResultList<LogicTableDO> resultList = new ResultList<>();
        try {
            LogicTableQuery logicTableQuery = new LogicTableQuery();
            if (StringUtils.isNotBlank(matrixName)) logicTableQuery.setMatrixName(matrixName);
            if (StringUtils.isNotBlank(tableName)) logicTableQuery.setTableName(tableName);

            resultList = logicTableService.queryListByQuery(logicTableQuery);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_2000001.toString()), e);
            resultList.setSuccess(Boolean.FALSE);
            DBPLUS_2000001.setError(resultList);
        }

        context.put("logicTables", resultList.getDatalist());
        context.put("environment", environment);
        context.put("matrixName", matrixName);
        context.put("tableName", tableName);
        context.put("nav_at", "appTable");
        return new ModelAndView("table/app_inquiry", context);
    }


    @GET
    @Path("/fetch")
    @Produces(MediaType.TEXT_HTML)
    public String fetchData(@Context HttpServletRequest request,
                            @QueryParam(value = "tableName") String tableName,
                            @QueryParam(value = "appName") String appName,
                            @QueryParam(value = "columnName") String columnName,
                            @QueryParam(value = "columnValue") String columnValue,
                            @QueryParam(value = "extraFileds") String extraFileds) {

        Result<String> result = new Result<>();
        try {
            result = pddlSqlService.fetchPddlQueryByColumn(tableName, appName, columnName, columnValue, extraFileds);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_2000002.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_2000002.setError(result);
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }


    @GET
    @Path("/query_table")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView queryTableByValue(@Context HttpServletRequest request,
                                          @QueryParam(value = "tableName") String tableName,
                                          @QueryParam(value = "appName") String appName,
                                          @QueryParam(value = "columnName") String columnName,
                                          @QueryParam(value = "columnValue") String columnValue,
                                          @QueryParam(value = "extraFileds") String extraFileds) {

        Map<String, Object> context = new HashMap();

        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, userDO);


        Result<String> result = new Result<>();
        try {
            result = pddlSqlService.fetchPddlQueryByColumn(tableName, appName, columnName, columnValue, extraFileds);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_2000002.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_2000002.setError(result);
        }

        context.put("result", result.getData());
        context.put("tableName", tableName);
        context.put("appName", appName);
        context.put("columnName", columnName);
        context.put("columnValue", columnValue);
        context.put("extraFileds", extraFileds);
        context.put("nav_at", "appTable");
        return new ModelAndView("table/app_inquiry", context);
    }


    @GET
    @Path("/table_fields")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView showLogicTableFields(@Context HttpServletRequest request,
                                             @QueryParam(value = "relation_id") Long relationId,
                                             @QueryParam(value = "tableName") String tableName) {

        Map<String, Object> context = new HashMap();

        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, userDO);

        Result<LogicTableFiledsDO> result = new Result<>();
        try {
            result = logicTableFiledsService.queryOneByRelationId(relationId);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_2000010.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_2000010.setError(result);
        }

        context.put("fields", result.getData());
        context.put("tableName", tableName);
        context.put("relationId", relationId);
        context.put("nav_at", "appTable");
        return new ModelAndView("table/table_fields", context);
    }

    @GET
    @Path("/fields")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTableFields(@Context HttpServletRequest request,
                                 @QueryParam(value = "relation_id") Long relationId) {

        Result<LogicTableFiledsDO> result = new Result<>();
        try {
            result = logicTableFiledsService.queryOneByRelationId(relationId);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0}",
                    DBPLUS_2000010.toString()), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_2000010.setError(result);
        }

        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    @POST
    @Path("/rank_fields")
    @Produces(MediaType.APPLICATION_JSON)
    public String rankFields(@Context HttpServletRequest request,
                             @FormParam("relation_id") Long relationId,
                             @FormParam("json_data") String jsonData) {

        Result<Void> result = new Result<>();
        List<DisplayFiledDO> displayFiledDOS = JSONObject.parseArray(jsonData, DisplayFiledDO.class);
        for (int i = 0; i < displayFiledDOS.size(); i++) {
            displayFiledDOS.get(i).setRank(i);
        }
        Result<LogicTableFiledsDO> result1 = logicTableFiledsService.queryOneByRelationId(relationId);
        LogicTableFiledsDO logicTableFiledsDO = result1.getData();
        String fields = JSON.toJSONString(displayFiledDOS, SerializerFeature.WriteMapNullValue);
        logicTableFiledsDO.setFileds(fields);
        result = logicTableFiledsService.modify(logicTableFiledsDO);

        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }


    public void setJadeConfigManager(JadeConfigManager jadeConfigManager) {
        this.jadeConfigManager = jadeConfigManager;
    }

    public void setJadeConfService(JadeConfService jadeConfService) {
        this.jadeConfService = jadeConfService;
    }

    public void setLogicTableService(LogicTableService logicTableService) {
        this.logicTableService = logicTableService;
    }

    public void setPddlSqlService(PddlSqlService pddlSqlService) {
        this.pddlSqlService = pddlSqlService;
    }
}
