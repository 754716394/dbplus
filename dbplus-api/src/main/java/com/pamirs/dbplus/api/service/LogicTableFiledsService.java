package com.pamirs.dbplus.api.service;

import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.query.LogicTableFiledsQuery;

/**
 * 逻辑表字段服务类
 *
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/26
 */
public interface LogicTableFiledsService {

    /**
     * 新增
     *
     * @param logicTableFiledsDO
     * @return
     */
    Result<Void> add(LogicTableFiledsDO logicTableFiledsDO);

    /**
     * 修改
     *
     * @param logicTableFiledsDO
     * @return
     */
    Result<Void> modify(LogicTableFiledsDO logicTableFiledsDO);

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
     * @param logicTableFiledsQuery
     * @return
     */
    ResultList<LogicTableFiledsDO> queryListByQuery(LogicTableFiledsQuery logicTableFiledsQuery);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Result<LogicTableFiledsDO> queryOneById(Long id);

    /**
     * 通过relationId查询
     *
     * @param relationId
     * @return
     */
    Result<LogicTableFiledsDO> queryOneByRelationId(Long relationId);

    /**
     * 通过matrixName查询
     *
     * @param matrixName
     * @return
     */
    ResultList<LogicTableFiledsDO> queryListByMatrixName(String matrixName);

    /**
     * 通过tableName查询
     *
     * @param tableName
     * @return
     */
    Result<LogicTableFiledsDO> queryOneByTableName(String tableName);
    
}
