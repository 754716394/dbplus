package com.pamirs.dbplus.core.service.table;

import com.alibaba.fastjson.JSON;
import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.query.LogicTableQuery;
import com.pamirs.dbplus.api.service.LogicTableService;
import com.pamirs.dbplus.core.dao.LogicTableDAO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;

import static com.pamirs.dbplus.core.constant.ErrorCodeConstants.*;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/22
 */
public class LogicTableServiceImpl implements LogicTableService {

    private static final Logger logger = LoggerFactory.getLogger(LogicTableServiceImpl.class);

    @Autowired
    private LogicTableDAO logicTableDAO;

    @Override
    public Result<Void> add(LogicTableDO logicTableDO) {
        Result<Void> result = new Result<>();
        try {
            int count = logicTableDAO.insert(logicTableDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000001.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000002.toString(),
                    JSON.toJSONString(logicTableDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000002.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> modify(LogicTableDO logicTableDO) {
        Result<Void> result = new Result<>();
        if (null == logicTableDO || null == logicTableDO.getId()) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000003.setError(result);
            return result;
        }
        try {
            int count = logicTableDAO.update(logicTableDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000004.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000005.toString(),
                    JSON.toJSONString(logicTableDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000005.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> deleteById(Long id) {
        Result<Void> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000006.setError(result);
            return result;
        }
        try {
            int count = logicTableDAO.delete(id);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000007.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000008.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000008.setError(result);
        }
        return result;
    }

    @Override
    public ResultList<LogicTableDO> queryListByQuery(LogicTableQuery logicTableQuery) {
        ResultList<LogicTableDO> resultList = new ResultList<>();
        try {
            List<LogicTableDO> logicTableDOList = logicTableDAO.selectList(logicTableQuery);
            long count = logicTableDAO.selectListCount(logicTableQuery);
            resultList.setDatalist(logicTableDOList);
            resultList.setTotal(count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},query:{1}",
                    DBPLUS_1000009.toString(),
                    JSON.toJSONString(logicTableQuery)), e);
            resultList.setSuccess(Boolean.FALSE);
            DBPLUS_1000009.setError(resultList);
        }
        return resultList;
    }

    @Override
    public Result<LogicTableDO> queryOneById(Long id) {
        Result<LogicTableDO> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000010.setError(result);
            return result;
        }
        try {
            LogicTableDO logicTableDO = logicTableDAO.selectOneById(id);
            result.setData(logicTableDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000011.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000011.setError(result);
        }
        return result;
    }

    @Override
    public ResultList<LogicTableDO> queryListByMatrixName(String matrixName) {
        ResultList<LogicTableDO> result = new ResultList<>();
        if (StringUtils.isBlank(matrixName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000012.setError(result);
            return result;
        }
        try {
            List<LogicTableDO> logicTableDOS = logicTableDAO.selectListByMatrixName(matrixName);
            result.setDatalist(logicTableDOS);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000013.toString(), matrixName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000013.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicTableDO> queryOneByTableName(String tableName) {
        ResultList<LogicTableDO> result = new ResultList<>();
        if (StringUtils.isBlank(tableName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000014.setError(result);
            return result;
        }
        try {
            LogicTableDO logicTableDO = logicTableDAO.selectOneByTableName(tableName);
            result.setData(logicTableDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000015.toString(), tableName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000015.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicTableDO> queryByMatrixNameAndTableName(String matrixName, String tableName) {
        ResultList<LogicTableDO> result = new ResultList<>();
        if (StringUtils.isBlank(matrixName) || StringUtils.isBlank(tableName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000031.setError(result);
            return result;
        }
        LogicTableDO logicTableDO = new LogicTableDO();
        logicTableDO.setMatrixName(matrixName);
        logicTableDO.setTableName(tableName);
        try {
            LogicTableDO logicTableDO1 = logicTableDAO.selectOne(logicTableDO);
            result.setData(logicTableDO1);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000032.toString(), tableName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000032.setError(result);
        }
        return result;
    }
}
