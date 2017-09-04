package com.pamirs.dbplus.api.service;



import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.query.UserQuery;

import java.util.List;

/**
 * 用户服务类
 */
public interface UserService {
	
	/**
	 * 新增用户
	 * 
	 * @param userDO
	 * @return
	 */
	public Result<UserDO> save(UserDO userDO);
	
	/**
	 * 校验密码
	 * 
	 * @param userDO
	 * @return
	 * @throws Exception
	 */
	public Result<UserDO> verifyPassword(UserDO userDO) throws Exception;

	/**
	 * 校验暗码
	 * 
	 * @param userDO
	 * @return
	 * @throws Exception
	 */
	public Result<UserDO> verifyToken(UserDO userDO) throws Exception;
	
	/**
	 * 重设密码，并存储到数据库
	 * 
	 * @param userDO
	 * @return 
	 */
	public Result<UserDO> resetPassword(UserDO userDO);
	
	/**
	 * 设置密码
	 * 
	 * @param userDO
	 * @return
	 */
	public Result<UserDO> setPassword(UserDO userDO);
	
	/**
	 * 重设邮箱
	 * 
	 * @param userDO
	 * @return 
	 * @throws Exception
	 */
	public Result<UserDO> resetEmail(UserDO userDO) throws Exception;
	
	/**
	 * 判断用户已经存在
	 * 
	 * @param userDO
	 * @return
	 * @throws Exception
	 */
	public Result<UserDO> isExistEmail(UserDO userDO) throws Exception;
	
	/**
	 * 通过id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Result<UserDO> getUserById(Long id);

	/**
	 * 通过email获取用户
	 * 
	 * @param email
	 * @return
	 */
	public Result<UserDO> getUserByEmail(String email);
	

	/**
	 * 获取用户列表
	 * 
	 * @param userQuery
	 * @return
	 */
	public Result<List<UserDO>> getUserList(UserQuery userQuery);
	
	/**
	 * 删除用户
	 * 
	 * @param userDO
	 * @return
	 */
	public Result<UserDO> deleteUser(UserDO userDO);

	/**
	 * 修改用户信息
	 * 
	 * @param userDO
	 * @return
	 * @throws Exception
	 */
	public Result<UserDO> modify(UserDO userDO) throws Exception;

	/**
	 * 获取用户数据json字符串经过RSA加密生成的字符串
	 * 
	 * @param userDO
	 * @return
	 */
	public String getUserString(UserDO userDO);

	/**
	 * 根据用户数据json字符串经过RSA加密生成的字符串生成用户对象
	 * 
	 * @param source
	 * @return
	 */
	public UserDO getUserFromString(String source);

}
