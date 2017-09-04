package com.pamirs.dbplus.core.service.table;

import com.alibaba.fastjson.JSON;
import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicDbDO;
import com.pamirs.dbplus.api.query.LogicDbQuery;
import com.pamirs.dbplus.api.service.LogicDbService;
import com.pamirs.dbplus.core.dao.LogicDbDAO;
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
 * @since 2017/5/24
 */
public class LogicDbServiceImpl implements LogicDbService {

    private static final Logger logger = LoggerFactory.getLogger(LogicDbServiceImpl.class);

    @Autowired
    private LogicDbDAO logicDbDAO;

    @Override
    public Result<Void> add(LogicDbDO logicDbDO) {
        Result<Void> result = new Result<>();
        try {
            int count = logicDbDAO.insert(logicDbDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000016.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000017.toString(),
                    JSON.toJSONString(logicDbDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000017.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> modify(LogicDbDO logicDbDO) {
        Result<Void> result = new Result<>();
        if (null == logicDbDO || null == logicDbDO.getId()) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000018.setError(result);
            return result;
        }
        try {
            int count = logicDbDAO.update(logicDbDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000019.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000020.toString(),
                    JSON.toJSONString(logicDbDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000020.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> deleteById(Long id) {
        Result<Void> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000021.setError(result);
            return result;
        }
        try {
            int count = logicDbDAO.delete(id);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000022.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000023.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000023.setError(result);
        }
        return result;
    }

    @Override
    public ResultList<LogicDbDO> queryListByQuery(LogicDbQuery logicDbQuery) {
        ResultList<LogicDbDO> resultList = new ResultList<>();
        try {
            List<LogicDbDO> eventDOList = logicDbDAO.selectList(logicDbQuery);
            long count = logicDbDAO.selectListCount(logicDbQuery);
            resultList.setDatalist(eventDOList);
            resultList.setTotal(count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},query:{1}",
                    DBPLUS_1000024.toString(),
                    JSON.toJSONString(logicDbQuery)), e);
            resultList.setSuccess(Boolean.FALSE);
            DBPLUS_1000024.setError(resultList);
        }
        return resultList;
    }

    @Override
    public Result<LogicDbDO> queryOneById(Long id) {
        Result<LogicDbDO> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000025.setError(result);
            return result;
        }
        try {
            LogicDbDO logicDbDO = logicDbDAO.selectOneById(id);
            result.setData(logicDbDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000026.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000026.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicDbDO> queryOneByMatrixName(String matrixName) {
        Result<LogicDbDO> result = new Result<>();
        if (StringUtils.isBlank(matrixName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000027.setError(result);
            return result;
        }
        try {
            LogicDbDO logicDbDO = logicDbDAO.selectOneByMatrixName(matrixName);
            result.setData(logicDbDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000028.toString(), matrixName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000028.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicDbDO> queryOneByDbName(String dbName) {
        Result<LogicDbDO> result = new ResultList<>();
        if (StringUtils.isBlank(dbName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000029.setError(result);
            return result;
        }
        try {
            LogicDbDO logicDbDO = logicDbDAO.selectOneByDbName(dbName);
            result.setData(logicDbDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000030.toString(), dbName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000030.setError(result);
        }
        return result;
    }
}
