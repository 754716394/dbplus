package com.pamirs.dbplus.api.query;


import com.pamirs.commons.dao.Query;
import com.pamirs.dbplus.api.model.UserDO;

import java.util.Date;

/**
 * 用户对象查询
 */
public class UserQuery extends Query<UserDO> {

    private Long id;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 真名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 加入时间
     */
    private Date gmtJoin;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 角色ID
     */
    private Long authId;

    /**
     * 描述
     */
    private String remark;

    /**
     * 是否是后台管理员
     */
    private Integer isAdmin;

    /**
     * 是否是超级管理员
     */
    private Integer isSuper;

    /**
     * 是否邮箱验证码激活
     */
    private Integer isActivate;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGmtJoin() {
        return gmtJoin;
    }

    public void setGmtJoin(Date gmtJoin) {
        this.gmtJoin = gmtJoin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Integer getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(Integer isActivate) {
        this.isActivate = isActivate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
