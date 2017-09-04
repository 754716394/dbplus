package com.pamirs.dbplus.core.dao;


import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.query.UserQuery;

import java.util.List;

/**
 * 用户数据库操作接口
 */

public interface UserDAO {

    /**
     * 通过id获取用户
     *
     * @param id
     * @return
     */
    public UserDO getUserById(Long id);

    /**
     * 通过email获取用户
     *
     * @param email
     * @return
     */
    public UserDO getUserByEmail(String email);

    /**
     * 通过name获取用户
     *
     * @param name
     * @return
     */
    public UserDO getUserByName(String name);

    /**
     * 获取所有用户
     *
     * @return
     */
    public List<UserDO> getUserList(UserQuery userQuery);

    /**
     * 添加用户
     *
     * @param userDO
     * @return
     */
    public Long addUser(UserDO userDO);

    /**
     * 修改用户信息
     *
     * @param userDO
     * @return
     * @throws Exception
     */
    public int modifyUser(UserDO userDO) throws Exception;

    /**
     * 通过id删除用户
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteUser(Long id) throws Exception;

    /**
     * 查询用户数量
     *
     * @param userQuery
     * @return
     */
    public Long selectListCount(UserQuery userQuery);

}