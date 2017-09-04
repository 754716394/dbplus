package com.pamirs.dbplus.api.service;

import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.query.LogicTableQuery;

/**
 * 逻辑表服务类
 *
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/22
 */
public interface LogicTableService {

    /**
     * 新增
     *
     * @param logicTableDO
     * @return
     */
    Result<Void> add(LogicTableDO logicTableDO);

    /**
     * 修改
     *
     * @param logicTableDO
     * @return
     */
    Result<Void> modify(LogicTableDO logicTableDO);

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    Result<Void> deleteById(Long id);

    /**
     * 通过query查询列表
     *
     * @param logicTableQuery
     * @return
     */
    ResultList<LogicTableDO> queryListByQuery(LogicTableQuery logicTableQuery);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Result<LogicTableDO> queryOneById(Long id);

    /**
     * 通过matrixName查询
     *
     * @param matrixName
     * @return
     */
    ResultList<LogicTableDO> queryListByMatrixName(String matrixName);

    /**
     * 通过tableName查询
     *
     * @param tableName
     * @return
     */
    Result<LogicTableDO> queryOneByTableName(String tableName);

    Result<LogicTableDO> queryByMatrixNameAndTableName(String matrixName, String tableName);

}
