package com.pamirs.dbplus.api.query;

import com.pamirs.commons.dao.Query;
import com.pamirs.dbplus.api.model.LogicTableFiledsDO;

import java.util.Date;

/**
 * @version 1.0
 * @autho <a href="mailto:mitsui#pamirs.top">mitsui</a>
 * @since 2017/5/26
 */
public class LogicTableFiledsQuery extends Query<LogicTableFiledsDO> {

    private Long id;

    private Long relationId;

    private String matrixName;

    private String tableName;

    private Integer shardTbNum;

    private String dbName;

    private Integer shardDbNum;

    private String fileds;

    private String displayFileds;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
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

    public Integer getShardTbNum() {
        return shardTbNum;
    }

    public void setShardTbNum(Integer shardTbNum) {
        this.shardTbNum = shardTbNum;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Integer getShardDbNum() {
        return shardDbNum;
    }

    public void setShardDbNum(Integer shardDbNum) {
        this.shardDbNum = shardDbNum;
    }

    public String getFileds() {
        return fileds;
    }

    public void setFileds(String fileds) {
        this.fileds = fileds;
    }

    public String getDisplayFileds() {
        return displayFileds;
    }

    public void setDisplayFileds(String displayFileds) {
        this.displayFileds = displayFileds;
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
