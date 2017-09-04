package com.pamirs.dbplus.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.query.UserQuery;
import com.pamirs.dbplus.api.service.UserService;
import com.pamirs.dbplus.core.entity.DBResult;
import com.pamirs.dbplus.core.utils.TokenGenerator;
import com.pamirs.dbplus.web.utils.Captcha;
import com.pamirs.dbplus.web.utils.Constants;
import com.pamirs.dbplus.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * 用户控制类
 */
@Path("/account/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView showUserList(@Context HttpServletRequest request,
                                     @QueryParam("page") Integer page,
                                     @QueryParam("query_id") String query_id,
                                     @QueryParam("query_nick") String query_nick,
                                     @QueryParam("order_by_value") String orderByField,
                                     @QueryParam("order_by_type") String orderByType) {

        Map<String, Object> context = new HashMap<>();
        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, userDO);
        // 判断权限
//        @SuppressWarnings("unchecked")
//		Map<String, PlatformAuthDO> authMap = (Map<String, PlatformAuthDO>)request.getSession().getAttribute(UserDO.SESSION_AUTH_NAME);
//		context.put(UserDO.CONTEXT_AUTH_NAME, authMap);
//		if(!ViewUtils.auth(authMap, PlatformAuth.ACTION.WMS_ACCOUNT_MANAGER.getName(), PlatformAuth.OPERATION.SHOW.getName())){
//			return new ModelAndView("forbidden", context);
//		}

        context.put("query_id", query_id);
        context.put("query_nick", query_nick);
        context.put("order_by_value", orderByField);
        context.put("order_by_type", orderByType);

        //
        UserQuery userQuery = new UserQuery();
        // 查询属性
        try {
            userQuery.setId(query_id == null || "".equals(query_id) ? null : Long.parseLong(query_id));
        } catch (Exception e) {
            context.put("is_success", false);
            context.put("error_msg", "查询用户ID只允许填入数字.");
            context.put("nav_at", "account");
            return new ModelAndView("account/user/list", context);
        }

        try {
            String reg_start = "^[0-9_]";
            int len = query_nick.length();
            if (!(query_nick == null || "".equals(query_nick)) && len < 4) {
                context.put("is_success", false);
                context.put("error_msg", "昵称只能4~20位，每个中文字算2位字符");
                context.put("nav_at", "account");
                return new ModelAndView("account/user/list", context);
            } else if (!(query_nick == null || "".equals(query_nick)) && StringUtils.test(query_nick, reg_start)) {
                context.put("is_success", false);
                context.put("error_msg", "昵称不能用数字或下划线开头");
                context.put("nav_at", "account");
                return new ModelAndView("account/user/list", context);
            }
        } catch (Exception e) {

        }

        userQuery.setNick("".equals(query_nick) ? null : query_nick);
        userQuery.setStatus(Constants.STATUS_UNACTIVE);
        // 分页属性
        userQuery.setOrderBy("".equals(orderByField) || orderByField == null ? "id" : StringUtils.TransactSQLInjection(orderByField));

        Result<List<UserDO>> result = userService.getUserList(userQuery);

        Iterator<UserDO> it = result.isSuccess() ? result.getData().iterator() : new ArrayList<UserDO>().iterator();

//        result.getPaginator();

        context.put("userList", result.getData());
//        context.put("paginator", result.getPaginator());

        context.put("nav_at", "account");
        return new ModelAndView("account/user/list", context);
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@FormParam("nick") String nick,
                           @FormParam("name") String name,
                           @FormParam("email") String email,
                           @FormParam("password") String password) {
        Result<UserDO> result = new Result<>();
        String regStart = "^[\\d_]";
        String regChar = "^[\\u4e00-\\u9fa5_\\-\\w\\d]+$";
        if (name == null || "".equals(name)) {
            result.setErrorMessage("请填写用户名");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        }
        int length = StringUtils.getLength(nick);
        if (length > 20 || length < 4) {
            result.setErrorMessage("用户名只能4~20位，每个中文字算2位字符");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } else if (StringUtils.test(name, regStart)) {
            result.setErrorMessage("用户名不能用数字或下划线开头");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } else if (!StringUtils.test(name, regChar)) {
            result.setErrorMessage("用户名仅支持中文、数字、大小写字母、中杠、下划线");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        }

        String regEmail = "^\\w{3,}\\.?\\w*[@|#]\\w+(\\.\\w+)+$";
        if (email == null || "".equals(email)) {
            result.setErrorMessage("请填写邮箱");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } else if (!StringUtils.test(email, regEmail)) {
            result.setErrorMessage("邮件地址不正确");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        }

        if (password == null || "".equals(password)) {
            result.setErrorMessage("请填写密码");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } else if (password.length() < 6) {
            result.setErrorMessage("密码太短啦，至少要6位哦");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        } else if (password.length() > 36) {
            result.setErrorMessage("密码太长啦，不要超过36位哦");
            result.setSuccess(false);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        }

        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        try {
            result = userService.isExistEmail(userDO);
            if (result.isSuccess()) {
                result.setSuccess(false);
                result.setErrorMessage("邮箱已经使用，请更换一个邮箱");
                return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
            } else {
                userDO.setNick(nick);
                userDO.setPassword(password);
                userDO = userService.setPassword(userDO).getData();
                userDO.setStatus(UserDO.STATUS_UNACTIVE);
                result = userService.save(userDO);

                UserDO uid = result.getData();
                String userId = uid.getId().toString();
            }
        } catch (Exception e) {
            result = new Result<>();
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        }

        result.setErrorMessage("请等待管理员激活账号！");
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@Context HttpServletRequest request,
                        @Context HttpServletResponse response,
                        @FormParam("name") String name,
                        @FormParam("password") String password,
                        @FormParam("captcha") String captcha,
                        @FormParam("rememberme") Boolean rememberme,
                        @FormParam("next") String next) {
        DBResult<UserDO> result = new DBResult<UserDO>();
        Result<UserDO> result1 = new Result<>();
        Integer errorTimes = (Integer) request.getSession().getAttribute(DBResult.ERROR_TIMES);
        if (errorTimes == null) {
            result.setErrorTimes(0);
            errorTimes = 0;
        } else {
            result.setErrorTimes(errorTimes);
        }

        String sessionCaptcha = (String) request.getSession().getAttribute(Captcha.CAPTCHA);

        if (errorTimes > 2) {
            if (captcha == null) {
                result.setMsg("请输入验证码");
                result.setSuccess(false);
                return result.toJsonString();
            } else if (sessionCaptcha != null && !sessionCaptcha.toUpperCase().equals(captcha.toUpperCase())) {
                result.setMsg("验证码错误");
                result.setSuccess(false);
                return result.toJsonString();
            }
        }

        if (name == null || "".equals(name)) {
            result.setMsg("请填写用户名");
            result.setSuccess(false);
            return result.toJsonString();
        }

        if (password == null || "".equals(password)) {
            result.setMsg("请填写密码");
            result.setSuccess(false);
            return result.toJsonString();
        }

        UserDO userDO = new UserDO();
        userDO.setName(name);
        userDO.setPassword(password);
        try {
            result1 = userService.verifyPassword(userDO);
            if (result1.isSuccess()) {
                userDO = (UserDO) result1.getData();
                if (userDO.getIsActivate() == 0) {
                    result.setMsg("请等待管理员激活账号!");
                    result.setSuccess(false);
                    return result.toJsonString();
                }
                if (rememberme) {
                    // 两星期
                    request.getSession().setMaxInactiveInterval(1209600);
                    request.getSession().setAttribute(UserDO.SESSION_REMEMBERME, true);
                } else {
                    request.getSession().setMaxInactiveInterval(900);
                    request.getSession().setAttribute(UserDO.SESSION_REMEMBERME, false);
                }
                // 保存登陆状态
                request.getSession().setAttribute(UserDO.SESSION_LOGIN_NAME, userDO);
                // 刷新登录错误次数
                request.getSession().setAttribute(DBResult.ERROR_TIMES, 0);
                result.setUrl(next);
                result.setSuccess(result1.isSuccess());
            } else {
                errorTimes++;
                request.getSession().setAttribute(DBResult.ERROR_TIMES, errorTimes);
                result.setErrorTimes(errorTimes);
                result.setMsg("密码错误，请输入正确密码!");
            }
        } catch (Exception e) {
            result = new DBResult<>();
            result.setSuccess(false);
            result.setMsg("系统异常!请重新输入密码...");
        }

        return result.toJsonString();
    }

    @GET
    @Path("/modify/{id}")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView profile(@Context HttpServletRequest request,
                                @PathParam("id") Long query_id) {
        Map<String, Object> context = new HashMap<>();
        UserDO loginUserDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, loginUserDO);

        Long userid = query_id;

        DBResult<UserDO> result = new DBResult<>();
        Result<UserDO> result1 = new Result<>();
        try {
            result1 = userService.getUserById(userid);

            result.setSuccess(result1.isSuccess());
            result.setData(result1.getData());
            result.setMsg(result1.getErrorMessage());

            UserDO userDO = result1.getData();
//            int userStatus = userDO.getStatus() == null ? 0 : userDO.getStatus();
            context.put("op", "modify");
            context.put("user", userDO);
        } catch (Exception e) {
            logger.error(e.toString());
            context.put("is_success", false);
            context.put("msg", "更新用户信息出错.");
            context.put("nav_at", "account");
            return new ModelAndView("account/user/modify", context);
        }

        context.put("nav_at", "account");
        return new ModelAndView("account/user/modify", context);
    }

    @GET
    @Path("/auth/{id}")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView auth(@Context HttpServletRequest request,
                             @PathParam("id") Long query_id) {
        Map<String, Object> context = new HashMap<>();
        UserDO loginUserDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        context.put(UserDO.CONTEXT_LOGIN_NAME, loginUserDO);

        // 判断权限
//        @SuppressWarnings("unchecked")
//        Map<String, PlatformAuthDO> authMap = (Map<String, PlatformAuthDO>) request.getSession().getAttribute(UserDO.SESSION_AUTH_NAME);
//        context.put(UserDO.CONTEXT_AUTH_NAME, authMap);
//        if (!ViewUtils.auth(authMap, PlatformAuth.ACTION.WMS_ACCOUNT_ROLE_MANAGER_OPER.getName(), PlatformAuth.OPERATION.MODIFY.getName())) {
//            return new ModelAndView("forbidden", context);
//        }
//
//        Long userid = query_id;
//        try {
//            DBResult<UserDO> userResult = userService.getUserById(userid);
//            UserDO userDO = userResult.getData();
//            context.put("user", userDO);
//        } catch (Exception e) {
//            logger.error(e.toString());
//            context.put("is_success", false);
//            context.put("msg", "用户信息出错.");
//            context.put("nav_at", "account");
//            return new ModelAndView("account/user/auth", context);
//        }
//        context.put("roles", PlatformAuth.ROLE.values());

        context.put("nav_at", "account");
        return new ModelAndView("account/user/auth", context);
    }


    @POST
    @Path("/auth/do")
    @Produces(MediaType.APPLICATION_JSON)
    public String modifyUser(@Context HttpServletRequest request,
                             @FormParam("id") Long id,
                             @FormParam("role") Long authId) {

        DBResult<UserDO> result = new DBResult<>();
        Result<UserDO> result1 = new Result<>();

        if (authId == null || "".equals(authId)) {
            result.setMsg("请选择角色");
            result.setSuccess(false);
            return result.toJsonString();
        }

        try {
            UserDO userDO = new UserDO();
            userDO.setId(id);
            userDO.setAuthId(authId);

            result1 = userService.modify(userDO);
            result.setSuccess(result1.isSuccess());
            result.setData(result1.getData());
            result.setMsg(result1.getErrorMessage());
        } catch (Exception e) {
            result = new DBResult<UserDO>();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result.toJsonString();
    }

    @POST
    @Path("/add/do")
    @Produces(MediaType.APPLICATION_JSON)
    public String modifyUser(@Context HttpServletRequest request,
                             @FormParam("id") Long id,
                             @FormParam("nick") String nick,
                             @FormParam("name") String name,
                             @FormParam("oldpassword") String oldpassword,
                             @FormParam("newpassword") String newpassword,
                             @FormParam("confirm_newpassword") String confirmNewpassword,
                             @FormParam("email") String email,
                             @FormParam("password") String password) {
        DBResult<UserDO> result = new DBResult<UserDO>();
        Result<UserDO> result1 = new Result<>();
        UserDO loginUserDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        if (loginUserDO.getIsAdmin() != 1) {
            if (!loginUserDO.getId().equals(id)) {
                result.setMsg("你没有权限进行此操作");
                result.setSuccess(false);
                return result.toJsonString();
            }
        }
        String regStart = "^[\\d_]";
        String regChar = "^[\\u4e00-\\u9fa5_\\-\\w\\d]+$";
        if (name == null || "".equals(name)) {
            result.setMsg("请填写用户名");
            result.setSuccess(false);
            return result.toJsonString();
        }
        int length = StringUtils.getLength(name);
        if (length > 20 || length < 4) {
            result.setMsg("用户名只能4~20位，每个中文字算2位字符");
            result.setSuccess(false);
            return result.toJsonString();
        } else if (StringUtils.test(name, regStart)) {
            result.setMsg("用户名不能用数字或下划线开头");
            result.setSuccess(false);
            return result.toJsonString();
        } else if (!StringUtils.test(name, regChar)) {
            result.setMsg("用户名仅支持中文、数字、大小写字母、中杠、下划线");
            result.setSuccess(false);
            return result.toJsonString();
        }

        String regEmail = "^\\w{3,}\\.?\\w*[@|#]\\w+(\\.\\w+)+$";
        if (email == null || "".equals(email)) {
            result.setMsg("请填写邮箱");
            result.setSuccess(false);
            return result.toJsonString();
        } else if (!StringUtils.test(email, regEmail)) {
            result.setMsg("邮件地址不正确");
            result.setSuccess(false);
            return result.toJsonString();
        }

        if (password == null || "".equals(password)) {
            password = null;
        } else if (password.length() < 6) {
            result.setMsg("密码太短啦，至少要6位哦");
            result.setSuccess(false);
            return result.toJsonString();
        } else if (password.length() > 36) {
            result.setMsg("密码太长啦，不要超过36位哦");
            result.setSuccess(false);
            return result.toJsonString();
        }

        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setEmail(email);
        try {
            result1 = userService.isExistEmail(userDO);
            if (result1.isSuccess()) {
                result.setSuccess(false);
                result.setMsg("邮箱已经使用，请更换一个邮箱");
            } else {
                if (password != null) {
                    userDO.setPassword(password);
                    userDO = userService.resetPassword(userDO).getData();
                }
                userDO = userService.getUserById(id).getData();

                // 修改客户密码
                if (!("".equals(oldpassword) || "".equals(newpassword) || "".equals(confirmNewpassword))) {
                    if (newpassword.length() < 6 || confirmNewpassword.length() < 6) {
                        result.setMsg("密码太短啦，至少要6位哦");
                        result.setSuccess(false);
                        return result.toJsonString();
                    } else if (newpassword.length() > 36 || confirmNewpassword.length() > 36) {
                        result.setMsg("密码太长啦，不要超过36位哦");
                        result.setSuccess(false);
                        return result.toJsonString();
                    } else if (!newpassword.equals(confirmNewpassword)) {
                        result.setMsg("确认新密码错误，请检查！");
                        result.setSuccess(false);
                        return result.toJsonString();
                    }
                    String rawPassword = userDO.getPassword();
                    if (!TokenGenerator.checkPassword(oldpassword, rawPassword)) {
                        result.setMsg("原始密码不正确！");
                        result.setSuccess(false);
                        return result.toJsonString();
                    } else {
                        userDO.setPassword(newpassword);
                        userDO = userService.setPassword(userDO).getData();
                    }
                }

                userDO.setNick(nick);
                userDO.setName(name);
                userDO.setEmail(email);
                userDO.setStatus(UserDO.STATUS_ACTIVE);
                result1 = userService.modify(userDO);
                result.setSuccess(result1.isSuccess());
                result.setData(result1.getData());
                result.setMsg(result1.getErrorMessage());
            }
        } catch (Exception e) {
            result = new DBResult<>();
            result.setSuccess(false);
            result.setMsg(e.getMessage());

        }

        return result.toJsonString();
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
