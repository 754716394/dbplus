package com.pamirs.dbplus.core.dao;

import com.pamirs.commons.dao.BaseDAO;
import com.pamirs.dbplus.api.model.LogicDbDO;
import com.pamirs.dbplus.api.query.LogicDbQuery;

import java.util.List;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/23
 */
public interface LogicDbDAO extends BaseDAO<LogicDbDO> {

    /**
     * 根据编码查询LogicDbDO
     *
     * @param matrixName
     * @return
     */
    LogicDbDO selectOneByMatrixName(String matrixName);

    /**
     * 根据编码查询LogicDbDO
     *
     * @param dbName
     * @return
     */
    LogicDbDO selectOneByDbName(String dbName);


    /**
     * 根据查询类查询LogicDbDO列表
     *
     * @param query
     * @return
     */
    List<LogicDbDO> selectList(LogicDbQuery query);

    /**
     * 根据查询类获取LogicDbDO总数
     *
     * @param query
     * @return
     */
    long selectListCount(LogicDbQuery query);

}
