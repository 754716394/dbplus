package com.pamirs.dbplus.api.query;

import com.pamirs.commons.dao.Query;
import com.pamirs.dbplus.api.model.LogicTableDO;

import java.util.Date;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/22
 */
public class LogicTableQuery extends Query<LogicTableDO> {


    private Long id;

    private String matrixName;

    private String tableName;

    private String shardColumn;

    private String shardRule;

    private String shardKeys;

    private String dbGroup;

    private Integer dbCount;

    private Integer tbCount;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName = matrixName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardColumn() {
        return shardColumn;
    }

    public void setShardColumn(String shardColumn) {
        this.shardColumn = shardColumn;
    }

    public String getShardRule() {
        return shardRule;
    }

    public void setShardRule(String shardRule) {
        this.shardRule = shardRule;
    }

    public String getShardKeys() {
        return shardKeys;
    }

    public void setShardKeys(String shardKeys) {
        this.shardKeys = shardKeys;
    }

    public String getDbGroup() {
        return dbGroup;
    }

    public void setDbGroup(String dbGroup) {
        this.dbGroup = dbGroup;
    }

    public Integer getDbCount() {
        return dbCount;
    }

    public void setDbCount(Integer dbCount) {
        this.dbCount = dbCount;
    }

    public Integer getTbCount() {
        return tbCount;
    }

    public void setTbCount(Integer tbCount) {
        this.tbCount = tbCount;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}
