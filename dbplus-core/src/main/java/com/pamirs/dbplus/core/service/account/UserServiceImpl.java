package com.pamirs.dbplus.core.service.account;

import com.alibaba.fastjson.JSON;
import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.constants.Constants;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.query.UserQuery;
import com.pamirs.dbplus.api.service.UserService;
import com.pamirs.dbplus.core.dao.UserDAO;
import com.pamirs.dbplus.core.utils.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService, Constants {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDAO;

    @Override
    public Result<UserDO> save(UserDO userDO) {
        Result<UserDO> result = new Result<UserDO>();
        Long id = 0L;
        try {
            id = userDAO.addUser(userDO);
        } catch (RuntimeException e) {
            logger.error("添加用户!" + e);
        }
        if (id != null && id.intValue() > 0) {
            try {
                userDAO.getUserById(id);
            } catch (Exception e) {
                logger.error("查询用户Id失败!" + e);
            }
            result.setData(userDO);
            result.setSuccess(true);
            result.setErrorMessage("添加用户成功");
        } else {
            result.setSuccess(false);
            result.setErrorMessage("添加用户失败");
        }
        return result;
    }

    @Override
    public Result<UserDO> verifyPassword(UserDO userDO) throws Exception {
        Result<UserDO> result = new Result<UserDO>();
        String inputPassword = userDO.getPassword();

        try {
            UserDO rawUserDO = userDAO.getUserByName(userDO.getName());
            if (rawUserDO == null) {
                result.setSuccess(false);
                result.setErrorMessage("用户不存在");
                return result;
            }
            String rawPassword = rawUserDO.getPassword();
            if (TokenGenerator.checkPassword(inputPassword, rawPassword)) {
                result.setSuccess(true);
                result.setData(rawUserDO);
            } else {
                result.setSuccess(false);
                result.setErrorMessage("密码错误");
            }

        } catch (RuntimeException e) {
            logger.error("用户验证密码错误!" + e);
            throw new Exception("用户注册的密码格式错误");
        }

        return result;
    }

    @Override
    public Result<UserDO> verifyToken(UserDO userDO) throws Exception {
        Result<UserDO> result = new Result<UserDO>();
        String inputPassword = userDO.getPassword();

        try {
            UserDO rawUserDO = userDAO.getUserByEmail(userDO.getEmail());
            if (rawUserDO == null) {
                result.setSuccess(false);
                result.setErrorMessage("用户不存在");
                return result;
            }
            String rawPassword = rawUserDO.getPassword();
            if (rawPassword.equals(inputPassword)) {
                result.setSuccess(true);
                result.setData(rawUserDO);
            } else {
                result.setSuccess(false);
                result.setErrorMessage("密码错误");
            }

        } catch (RuntimeException e) {
            logger.error("用户验证密码错误!" + e);
            throw new Exception("用户注册的密码格式错误");
        }

        return result;
    }

    @Override
    public Result<UserDO> setPassword(UserDO userDO) {
        Result<UserDO> result = new Result<UserDO>();
        String inputPassword = userDO.getPassword();

        try {
            String encPassword = TokenGenerator.genPassword(inputPassword);
            userDO.setPassword(encPassword);
        } catch (Exception e) {
            logger.error(e.getMessage() + "!" + e.getCause());
            result.setSuccess(false);
            result.setErrorMessage("设置密码错误");
            return result;
        }
        result.setSuccess(true);
        result.setData(userDO);
        return result;
    }

    @Override
    public Result<UserDO> resetPassword(UserDO userDO) {
        Result<UserDO> result = new Result<UserDO>();

        try {
            this.setPassword(userDO);
            userDAO.modifyUser(userDO);
            result.setSuccess(true);
            result.setErrorMessage("重设密码成功");
        } catch (RuntimeException e) {
            logger.error("重设密码!" + e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("重设密码错误");
        }
        return result;
    }

    @Override
    public Result<UserDO> resetEmail(UserDO userDO) throws Exception {
        Result<UserDO> result = new Result<UserDO>();

        // 判断该邮箱是否已经使用
        result = this.isExistEmail(userDO);
        if (!result.isSuccess()) {
            return result;
        }
        String email = userDO.getEmail();
        if (email == null) {
            result.setSuccess(false);
            result.setErrorMessage("重设邮箱错误,未设置需要修改邮箱的用户对象的email");
            return result;
        }
        try {
            userDAO.modifyUser(userDO);
            result.setSuccess(true);
            result.setErrorMessage("重设邮箱成功");
        } catch (RuntimeException e) {
            logger.error("重设邮箱!" + e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("重设邮箱错误");
        }
        return result;
    }

    @Override
    public Result<UserDO> modify(UserDO userDO) throws Exception {
        Result<UserDO> result = new Result<UserDO>();

        try {
            userDAO.modifyUser(userDO);
            result.setSuccess(true);
            result.setErrorMessage("修改用户信息成功");
        } catch (RuntimeException e) {
            logger.error("修改用户信息!" + e);
        } catch (Exception e) {
            logger.error("修改用户信息!" + e);
            result.setSuccess(false);
            result.setErrorMessage("修改用户信息错误");
        }
        return result;
    }

    @Override
    public Result<UserDO> isExistEmail(UserDO userDO) throws Exception {
        Result<UserDO> result = new Result<UserDO>();

        String email = userDO.getEmail();
        if (email == null) {
            throw new Exception("判断用户是否存在失败,未设置待查询用户对象的email");
        }

        Long id = userDO.getId();
        try {
            if (id != null) {
                UserDO rawUserDO = userDAO.getUserById(id);
                if (rawUserDO != null && userDO.getEmail().equals(rawUserDO.getEmail())) {
                    result.setSuccess(false);
                    return result;
                }
            }
            // 根据邮箱查询
            userDO = userDAO.getUserByEmail(email);
            if (userDO == null) {
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
            }
        } catch (RuntimeException e) {
            logger.error("判断用户是否存在失败!" + e.getMessage());
            throw new Exception("判断用户是否存在失败,用户注册信息错误");
        }

        return result;
    }

    @Override
    public Result<UserDO> getUserById(Long id) {
        Result<UserDO> result = new Result<UserDO>();
        UserDO userDO = null;
        try {
            userDO = userDAO.getUserById(id);
        } catch (RuntimeException e) {
            logger.error("通过id获取用户!" + e);
            result.setSuccess(false);
            result.setErrorMessage("获取用户信息错误");
            return result;
        }
        result.setSuccess(true);
        result.setData(userDO);
        result.setErrorMessage("获取用户信息成功");
        return result;
    }

    @Override
    public Result<UserDO> getUserByEmail(String email) {
        Result<UserDO> result = new Result<>();
        UserDO userDO = null;
        try {
            userDO = userDAO.getUserByEmail(email);
        } catch (RuntimeException e) {
            logger.error("通过email获取用户!" + e);
            result.setSuccess(false);
            result.setErrorMessage("获取用户信息错误");
            return result;
        }
        result.setSuccess(true);
        result.setData(userDO);
        result.setErrorMessage("获取用户信息成功");
        return result;
    }



    @Override
    public Result<List<UserDO>> getUserList(UserQuery userQuery) {
        Result<List<UserDO>> result = new Result<>();
        try {
            List<UserDO> listResult = userDAO.getUserList(userQuery);
            result.setData(listResult);
            // todo 分页
//            result.setPaginator(listResult.getPaginator());
        } catch (Exception e) {
            logger.error("查询用户列表失败!" + e);
            result.setSuccess(false);
            result.setErrorMessage("查询用户列表失败");
            return result;
        }
        result.setSuccess(true);
        result.setErrorMessage("查询用户列表成功");
        return result;
    }

    @Override
    public Result<UserDO> deleteUser(UserDO userDO) {
        Result<UserDO> result = new Result<>();
        int num = 0;
        try {
            num = userDAO.deleteUser(userDO.getId());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("删除用户失败");
            return result;
        }
        if (num > 0) {
            result.setSuccess(false);
            result.setErrorMessage("删除用户失败");
        } else {
            result.setSuccess(true);
            result.setErrorMessage("删除用户成功");
        }
        return result;
    }

    @Override
    public String getUserString(UserDO userDO) {
        String source = JSON.toJSONString(userDO);
        return TokenGenerator.getRSA(source);
    }

    @Override
    public UserDO getUserFromString(String source) {
        source = TokenGenerator.getASR(source);
        if ("".equals(source)) {
            return null;
        }
        UserDO userDO = JSON.parseObject(source, UserDO.class);
        return userDO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
