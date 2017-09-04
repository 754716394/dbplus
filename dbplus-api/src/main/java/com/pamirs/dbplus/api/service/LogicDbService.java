package com.pamirs.dbplus.api.service;

import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicDbDO;
import com.pamirs.dbplus.api.query.LogicDbQuery;

/**
 * 逻辑库服务类
 *
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/24
 */
public interface LogicDbService {


    /**
     * 新增
     *
     * @param logicDbDO
     * @return
     */
    Result<Void> add(LogicDbDO logicDbDO);

    /**
     * 修改
     *
     * @param logicDbDO
     * @return
     */
    Result<Void> modify(LogicDbDO logicDbDO);

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
     * @param logicDbQuery
     * @return
     */
    ResultList<LogicDbDO> queryListByQuery(LogicDbQuery logicDbQuery);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Result<LogicDbDO> queryOneById(Long id);

    /**
     * 通过matrixName查询
     *
     * @param matrixName
     * @return
     */
    Result<LogicDbDO> queryOneByMatrixName(String matrixName);

    /**
     * dbName
     *
     * @param dbName
     * @return
     */
    Result<LogicDbDO> queryOneByDbName(String dbName);


}
