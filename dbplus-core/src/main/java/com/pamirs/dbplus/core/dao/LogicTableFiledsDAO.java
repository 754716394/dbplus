package com.pamirs.dbplus.core.dao;

import com.pamirs.commons.dao.BaseDAO;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;
import com.pamirs.dbplus.api.query.LogicTableFiledsQuery;

import java.util.List;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/23
 */
public interface LogicTableFiledsDAO extends BaseDAO<LogicTableFiledsDO> {

    /**
     * 根据matrixName查询
     *
     * @param matrixName
     * @return
     */
    List<LogicTableFiledsDO> selectListByMatrixName(String matrixName);

    /**
     * 根据tableName查询
     *
     * @param tableName
     * @return
     */
    LogicTableFiledsDO selectOneByTableName(String tableName);

    /**
     * 根据relationId查询
     *
     * @param relationId
     * @return
     */
    LogicTableFiledsDO selectOneByRelationId(Long relationId);

    /**
     * 根据查询类查询列表
     *
     * @param query
     * @return
     */
    List<LogicTableFiledsDO> selectList(LogicTableFiledsQuery query);

    /**
     * 根据查询类获取总数
     *
     * @param query
     * @return
     */
    long selectListCount(LogicTableFiledsQuery query);

}
