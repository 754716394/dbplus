package com.pamirs.dbplus.core.dao;

import com.pamirs.commons.dao.BaseDAO;
import com.pamirs.dbplus.api.model.LogicTableDO;
import com.pamirs.dbplus.api.query.LogicTableQuery;

import java.util.List;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/23
 */
public interface LogicTableDAO extends BaseDAO<LogicTableDO> {

    /**
     * 根据matrixName查询
     *
     * @param matrixName
     * @return
     */
    List<LogicTableDO> selectListByMatrixName(String matrixName);

    /**
     * 根据tableName查询查询
     *
     * @param tableName
     * @return
     */
    LogicTableDO selectOneByTableName(String tableName);

    /**
     * 根据查询类查询列表
     *
     * @param query
     * @return
     */
    List<LogicTableDO> selectList(LogicTableQuery query);

    /**
     * 根据查询类获取总数
     *
     * @param query
     * @return
     */
    long selectListCount(LogicTableQuery query);

}
