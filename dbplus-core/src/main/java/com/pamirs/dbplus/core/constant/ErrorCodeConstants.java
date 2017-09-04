package com.pamirs.dbplus.core.constant;

import com.pamirs.commons.dao.Result;
import com.pamirs.commons.dao.ResultList;

/**
 * 错误码
 *
 * @author <a href="mailto:yihui@alibaba-inc.com">yihui</a>
 * @version 1.0
 * @since 2017年5月25日
 */
public enum ErrorCodeConstants {

    /**
     * 错误码在工程中出现次数必须全局唯一
     */
    DBPLUS_1000001(1000001, "LogicTableServiceImpl.add.Failed", "新增logicTable失败"),
    DBPLUS_1000002(1000002, "LogicTableServiceImpl.add.Exception", "新增logicTable系统异常"),
    DBPLUS_1000003(1000003, "LogicTableServiceImpl.modify.IdIsNull", "修改logicTable,必填参数ID为空"),
    DBPLUS_1000004(1000004, "LogicTableServiceImpl.modify.Failed", "修改logicTable失败"),
    DBPLUS_1000005(1000005, "LogicTableServiceImpl.modify.Exception", "修改logicTable系统异常"),
    DBPLUS_1000006(1000006, "LogicTableServiceImpl.delete.IdIsNull", "删除logicTable,必填参数ID为空"),
    DBPLUS_1000007(1000007, "LogicTableServiceImpl.delete.Failed", "删除logicTable失败"),
    DBPLUS_1000008(1000008, "LogicTableServiceImpl.delete.Exception", "删除logicTable系统异常"),
    DBPLUS_1000009(1000009, "LogicTableServiceImpl.queryListByQuery.Exception", "根据query对象查询logicTable列表异常"),
    DBPLUS_1000010(1000010, "LogicTableServiceImpl.queryOneById.IdIsNull", "根据ID查询logicTable,ID为空"),
    DBPLUS_1000011(1000011, "LogicTableServiceImpl.queryOneById.Exception", "根据ID查询logicTable异常"),
    DBPLUS_1000012(1000012, "LogicTableServiceImpl.queryListByMatrixName.MatrixNameIsNull", "根据matrixName查询logicTable,matrixName为空"),
    DBPLUS_1000013(1000013, "LogicTableServiceImpl.queryListByMatrixName.Exception", "根据matrixName查询logicTable异常"),
    DBPLUS_1000014(1000014, "LogicTableServiceImpl.queryOneByTableName.TableNameIsNull", "根据tableName查询logicTable,tableName为空"),
    DBPLUS_1000015(1000015, "LogicTableServiceImpl.queryOneByTableName.Exception", "根据tableName查询logicTable异常"),
    DBPLUS_1000016(1000016, "LogicDbServiceImpl.add.Failed", "新增logicDb失败"),
    DBPLUS_1000017(1000017, "LogicDbServiceImpl.add.Exception", "新增logicDb异常"),
    DBPLUS_1000018(1000018, "LogicDbServiceImpl.modify.IdIsNull", "修改logicDb,必填参数ID为空"),
    DBPLUS_1000019(1000019, "LogicDbServiceImpl.modify.Failed", "修改logicDb失败"),
    DBPLUS_1000020(1000020, "LogicDbServiceImpl.modify.Exception", "修改logicDb异常"),
    DBPLUS_1000021(1000021, "LogicDbServiceImpl.delete.IdIsNull", "删除logicDb,必填参数ID为空"),
    DBPLUS_1000022(1000022, "LogicDbServiceImpl.delete.Failed", "删除logicDb失败"),
    DBPLUS_1000023(1000023, "LogicDbServiceImpl.delete.Exception", "删除logicDb异常"),
    DBPLUS_1000024(1000024, "LogicDbServiceImpl.queryListByQuery.Exception", "根据query对象查询logicDb列表异常"),
    DBPLUS_1000025(1000025, "LogicDbServiceImpl.queryOneById.IdIsNull", "根据ID查询logicDb,ID为空"),
    DBPLUS_1000026(1000026, "LogicDbServiceImpl.queryOneById.Exception", "根据ID查询logicDb异常"),
    DBPLUS_1000027(1000027, "LogicDbServiceImpl.queryOneByMatrixName.MatrixNameIsNull", "根据matrixName查询logicDb,matrixName为空"),
    DBPLUS_1000028(1000028, "LogicDbServiceImpl.queryOneByMatrixName.Exception", "根据matrixName查询logicDb异常"),
    DBPLUS_1000029(1000029, "LogicDbServiceImpl.queryOneByDbName.DbNameIsNull", "根据dbName查询logicDb,dbname为空"),
    DBPLUS_1000030(1000030, "LogicDbServiceImpl.queryOneByDbName.Exception", "根据dbName查询logicDb异常"),
    DBPLUS_1000031(1000031, "LogicTableServiceImpl.queryByMatrixNameAndTableName.IsNull", "根据matrixName,tableName查询,必传字段为空"),
    DBPLUS_1000032(1000032, "LogicTableServiceImpl.queryByMatrixNameAndTableName.Exception", "根据matrixName,tableName查询异常"),
    DBPLUS_1000041(1000041, "LogicTableFiledsServiceImpl.add.Failed", "新增logicTableFileds失败"),
    DBPLUS_1000042(1000042, "LogicTableFiledsServiceImpl.add.Exception", "新增logicTableFileds系统异常"),
    DBPLUS_1000043(1000043, "LogicTableFiledsServiceImpl.modify.IdIsNull", "修改logicTableFileds,必填参数ID为空"),
    DBPLUS_1000044(1000044, "LogicTableFiledsServiceImpl.modify.Failed", "修改logicTableFileds失败"),
    DBPLUS_1000045(1000045, "LogicTableFiledsServiceImpl.modify.Exception", "修改logicTableFileds系统异常"),
    DBPLUS_1000046(1000046, "LogicTableFiledsServiceImpl.delete.IdIsNull", "删除logicTableFileds,必填参数ID为空"),
    DBPLUS_1000047(1000047, "LogicTableFiledsServiceImpl.delete.Failed", "删除logicTableFileds失败"),
    DBPLUS_1000048(1000048, "LogicTableFiledsServiceImpl.delete.Exception", "删除logicTableFileds系统异常"),
    DBPLUS_1000049(1000049, "LogicTableFiledsServiceImpl.queryListByQuery.Exception", "根据query对象查询logicTableFileds列表异常"),
    DBPLUS_1000050(1000050, "LogicTableFiledsServiceImpl.queryOneById.IdIsNull", "根据ID查询logicTableFileds,ID为空"),
    DBPLUS_1000051(1000051, "LogicTableFiledsServiceImpl.queryOneById.Exception", "根据ID查询logicTableFileds异常"),
    DBPLUS_1000052(1000052, "LogicTableFiledsServiceImpl.queryListByMatrixName.MatrixNameIsNull", "根据matrixName查询logicTableFileds,matrixName为空"),
    DBPLUS_1000053(1000053, "LogicTableFiledsServiceImpl.queryListByMatrixName.Exception", "根据matrixName查询logicTableFileds异常"),
    DBPLUS_1000054(1000054, "LogicTableFiledsServiceImpl.queryOneByTableName.TableNameIsNull", "根据tableName查询logicTableFileds,tableName为空"),
    DBPLUS_1000055(1000055, "LogicTableFiledsServiceImpl.queryOneByTableName.Exception", "根据tableName查询logicTableFileds异常"),
    DBPLUS_1000056(1000056, "LogicTableFiledsServiceImpl.queryOneByRelationId.RelationIdIsNull", "根据RelationId查询logicTableFileds,RelationId为空"),
    DBPLUS_1000057(1000057, "LogicTableFiledsServiceImpl.queryOneByRelationId.Exception", "根据RelationId查询logicTableFileds异常"),

    DBPLUS_1000070(1000070, "PddlSqlServiceImpl.fetchPddlQueryByColumn.PddlException", "通过pddl查询数据时,pddl报错"),
    DBPLUS_1000071(1000071, "PddlSqlServiceImpl.fetchPddlQueryByColumn.SQLException", "通过pddl查询数据时,SQL异常"),
    DBPLUS_1000073(1000073, "PddlSqlServiceImpl.Connection.closeException", "数据库连接关闭异常"),

    DBPLUS_1000080(1000080, "JadeConfServiceImpl.syncAppNameRule.addLogicTable.Exception", "同步appName时，添加logicTable异常"),
    DBPLUS_1000081(1000081, "JadeConfServiceImpl.syncAppNameRule.modifyLogicTable.Exception", "同步appName时，修改logicTable异常"),
    DBPLUS_1000082(1000082, "JadeConfServiceImpl.getAppNameRuleFromConfigServer.ParseException", "config-server获取rule异常"),

    DBPLUS_2000001(2000001, "LogicTableController.list.Exception", "页面获取逻辑表异常"),
    DBPLUS_2000002(2000002, "LogicTableController.fetchData.Exception", "页面获取逻辑表数据异常"),

    DBPLUS_2000010(2000010, "LogicTableFieldsController.queryOneTableFields.Exception", "页面获取逻辑表字段异常"),
    DBPLUS_2000011(2000011, "LogicTableFieldsController.syncFileds.Exception", "页面同步逻辑表字段异常"),
    DBPLUS_2000012(2000012, "LogicTableFieldsController.syncFileds.addLogicTableFiledsDO.Failed", "同步逻辑表字段写数据库失败"),
    DBPLUS_2000013(2000013, "LogicTableFieldsController.fetchPddlTableFields.Failed", "同步过程中获取pddl逻辑表字段失败"),

    DBPLUS_3000001(3000001, "UserServiceImpl.save.Failed", "添加用户失败"),
    DBPLUS_3000002(3000002, "UserServiceImpl.verifyPassword.UserNotExist", "验证用户密码时，用户不存在"),
    DBPLUS_3000003(3000003, "UserServiceImpl.verifyPassword.CheckPasswordError", "验证用户密码时，密码校验错误！"),
    DBPLUS_3000004(3000004, "UserServiceImpl.verifyPassword.PasswordError", "用户注册的密码格式错误"),
    DBPLUS_3000005(3000005, "UserServiceImpl.verifyToken.UserNotExist", "用户不存在"),
    DBPLUS_3000006(3000006, "UserServiceImpl.verifyToken.CheckPasswordError", "密码校验错误"),
    DBPLUS_3000007(3000007, "UserServiceImpl.verifyToken.PasswordError", "用户注册的密码格式错误"),
    DBPLUS_3000008(3000008, "UserServiceImpl.setPassword.PasswordError", "用户注册设置密码错误"),
    DBPLUS_3000009(3000009, "UserServiceImpl.resetPassword.PasswordError", "用户注册重置密码错误"),
    DBPLUS_3000010(3000010, "UserServiceImpl.modify.Exception", "用户修改失败"),
    ;

    /**
     * 错误code
     */
    private int errorCode;

    /**
     * 错误名称
     */
    private String errorName;

    /**
     * 错误信息
     */
    private String errorMessage;

    ErrorCodeConstants(int errorCode, String errorName, String errorMessage) {

        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMessage = errorMessage;

    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return String.format(errorMessage, args);
    }

    public <T> void setError(Result<T> result) {
        result.setError(this.getErrorCode(),
                this.getErrorName(),
                this.getErrorMessage());
    }

    public <T> void setError(ResultList<T> result) {
        result.setError(this.getErrorCode(),
                this.getErrorName(),
                this.getErrorMessage());
    }

    @Override
    public String toString() {
        return "{\"errorCode\":\"" + errorCode + "\",\"errorName\":\"" + errorName + "\",\"errorMessage\":\"" + errorMessage + "\"}";
    }
}