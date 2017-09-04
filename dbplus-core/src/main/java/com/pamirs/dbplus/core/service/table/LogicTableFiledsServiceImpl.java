package com.pamirs.dbplus.core.service.table;

import com.alibaba.fastjson.JSON;
import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.query.LogicTableFiledsQuery;
import com.pamirs.dbplus.api.service.LogicTableFiledsService;
import com.pamirs.dbplus.core.dao.LogicTableFiledsDAO;
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
 * @since 2017/5/26
 */
public class LogicTableFiledsServiceImpl implements LogicTableFiledsService {

    private static final Logger logger = LoggerFactory.getLogger(LogicTableFiledsServiceImpl.class);

    @Autowired
    private LogicTableFiledsDAO logicTableFiledsDAO;

    @Override
    public Result<Void> add(LogicTableFiledsDO logicTableFiledsDO) {
        Result<Void> result = new Result<>();
        try {
            int count = logicTableFiledsDAO.insert(logicTableFiledsDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000041.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000042.toString(),
                    JSON.toJSONString(logicTableFiledsDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000042.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> modify(LogicTableFiledsDO logicTableFiledsDO) {
        Result<Void> result = new Result<>();
        if (null == logicTableFiledsDO || null == logicTableFiledsDO.getId()) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000043.setError(result);
            return result;
        }
        try {
            int count = logicTableFiledsDAO.update(logicTableFiledsDO);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000044.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},param:{1}",
                    DBPLUS_1000045.toString(),
                    JSON.toJSONString(logicTableFiledsDO)), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000045.setError(result);
        }
        return result;
    }

    @Override
    public Result<Void> deleteById(Long id) {
        Result<Void> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000046.setError(result);
            return result;
        }
        try {
            int count = logicTableFiledsDAO.delete(id);
            if (count != 1) {
                result.setSuccess(Boolean.FALSE);
                DBPLUS_1000047.setError(result);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000048.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000048.setError(result);
        }
        return result;
    }

    @Override
    public ResultList<LogicTableFiledsDO> queryListByQuery(LogicTableFiledsQuery logicTableFiledsQuery) {
        ResultList<LogicTableFiledsDO> resultList = new ResultList<>();
        try {
            List<LogicTableFiledsDO> logicTableFiledsDOList = logicTableFiledsDAO.selectList(logicTableFiledsQuery);
            long count = logicTableFiledsDAO.selectListCount(logicTableFiledsQuery);
            resultList.setDatalist(logicTableFiledsDOList);
            resultList.setTotal(count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},query:{1}",
                    DBPLUS_1000049.toString(),
                    JSON.toJSONString(logicTableFiledsQuery)), e);
            resultList.setSuccess(Boolean.FALSE);
            DBPLUS_1000049.setError(resultList);
        }
        return resultList;
    }

    @Override
    public Result<LogicTableFiledsDO> queryOneById(Long id) {
        Result<LogicTableFiledsDO> result = new Result<>();
        if (null == id) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000050.setError(result);
            return result;
        }
        try {
            LogicTableFiledsDO logicTableFiledsDO = logicTableFiledsDAO.selectOneById(id);
            result.setData(logicTableFiledsDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000051.toString(), id), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000051.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicTableFiledsDO> queryOneByRelationId(Long relationId) {
        Result<LogicTableFiledsDO> result = new Result<>();
        if (null == relationId) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000056.setError(result);
            return result;
        }
        try {
            LogicTableFiledsDO logicTableFiledsDO = logicTableFiledsDAO.selectOneByRelationId(relationId);
            result.setData(logicTableFiledsDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},id:{1}",
                    DBPLUS_1000057.toString(), relationId), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000057.setError(result);
        }
        return result;
    }

    @Override
    public ResultList<LogicTableFiledsDO> queryListByMatrixName(String matrixName) {
        ResultList<LogicTableFiledsDO> result = new ResultList<>();
        if (StringUtils.isBlank(matrixName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000052.setError(result);
            return result;
        }
        try {
            List<LogicTableFiledsDO> logicTableFiledsDOS = logicTableFiledsDAO.selectListByMatrixName(matrixName);
            result.setDatalist(logicTableFiledsDOS);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000053.toString(), matrixName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000053.setError(result);
        }
        return result;
    }

    @Override
    public Result<LogicTableFiledsDO> queryOneByTableName(String tableName) {
        ResultList<LogicTableFiledsDO> result = new ResultList<>();
        if (StringUtils.isBlank(tableName)) {
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000054.setError(result);
            return result;
        }
        try {
            LogicTableFiledsDO logicTableFiledsDO = logicTableFiledsDAO.selectOneByTableName(tableName);
            result.setData(logicTableFiledsDO);
        } catch (Exception e) {
            logger.error(MessageFormat.format("error:{0},code:{1}",
                    DBPLUS_1000055.toString(), tableName), e);
            result.setSuccess(Boolean.FALSE);
            DBPLUS_1000055.setError(result);
        }
        return result;
    }
}
