package com.pamirs.dbplus.web.controller;

import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.core.utils.TokenGenerator;
import com.pamirs.dbplus.web.utils.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页君
 */

@Path("/")
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static String ERROR_TIMES = "error_times";

//	@Autowired
//	private UserService userService;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView index0(@Context HttpServletRequest request,
                               @Context HttpServletResponse response) {
        logger.debug("request /index");
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            logger.error("页面显示异常!" + e);
        }
        return null;
    }

    @GET
    @Path("/index")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView index(@Context HttpServletRequest request,
                              @Context HttpServletResponse response) {
        return index0(request, response);
    }

    @GET
    @Path("/403")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView forbidden(@Context HttpServletRequest request,
                                  @Context HttpServletResponse response,
                                  @QueryParam("next") String next) {

        logger.error("403");
        Map<String, Object> map = new HashMap<String, Object>();
        Integer errorTimes = (Integer) request.getSession().getAttribute(ERROR_TIMES);
        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        map.put(UserDO.CONTEXT_LOGIN_NAME, userDO);
        if (errorTimes == null) {
            map.put("login_error_times", 0);
        } else {
            map.put("login_error_times", errorTimes);
        }
        map.put("next", next);
        map.put("nav_at", "index");
        return new ModelAndView("403", map);
    }


    @GET
    @Path("/forbidden")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView forbidden(@Context HttpServletRequest request,
                                  @Context HttpServletResponse response) {
        logger.debug("request /forbidden");
        Map<String, Object> map = new HashMap<String, Object>();
        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        map.put(UserDO.CONTEXT_LOGIN_NAME, userDO);
        return new ModelAndView("forbidden", map);
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView login(@Context HttpServletRequest request,
                              @Context HttpServletResponse response,
                              @QueryParam("next") String next) {

        String requestUri = request.getRequestURI();
        if (request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME) != null) {
            //更好的实现方式的使用cookie
            if (requestUri.startsWith("/login") || requestUri.startsWith("/register")) {
                //重定向到登录页面
                try {
                    response.sendRedirect(request.getContextPath());
                } catch (IOException e) {
                    logger.error("登录页面显示异常!" + e);
                }
                return new ModelAndView();
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Integer errorTimes = (Integer) request.getSession().getAttribute(ERROR_TIMES);
        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        map.put(UserDO.CONTEXT_LOGIN_NAME, userDO);
        if (errorTimes == null) {
            map.put("login_error_times", 0);
        } else {
            map.put("login_error_times", errorTimes);
        }
        map.put("next", next);
        map.put("nav_at", "index");
        return new ModelAndView("login", map);
    }

    @GET
    @Path("/logout")
    public void logout(@Context HttpServletRequest request,
                       @Context HttpServletResponse response) throws IOException {
        request.getSession().setAttribute(UserDO.SESSION_LOGIN_NAME, null);
        Cookie cookie = new Cookie(UserDO.COOKIE_LOGIN_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect(request.getContextPath() + "/login");
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView register(@Context HttpServletRequest request,
                                 @Context HttpServletResponse response) {

        String requestUri = request.getRequestURI();
        if (request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME) != null) {
            //更好的实现方式的使用cookie
            if (requestUri.startsWith("/login") || requestUri.startsWith("/register")) {
                //重定向到登录页面
                try {
                    response.sendRedirect(request.getContextPath());
                } catch (IOException e) {
                    logger.error("注册页面显示异常!" + e);
                }
                return null;
            }
        }

        return new ModelAndView("register");
    }

    @GET
    @Path("/captcha")
    @Produces(MediaType.TEXT_HTML)
    public void getCaptcha(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        //设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //指定生成的相应图片  
        response.setContentType("image/jpeg");
        Captcha idCode = new Captcha();
        BufferedImage image = new BufferedImage(idCode.getWidth(), idCode.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        //定义字体样式  
        Font myFont = new Font("黑体", Font.BOLD, 18);
        //设置字体  
        g.setFont(myFont);

        g.setColor(idCode.getRandomColor(200, 250));
        //绘制背景  
        g.fillRect(0, 0, idCode.getWidth(), idCode.getHeight());

        g.setColor(idCode.getRandomColor(180, 200));
        idCode.drawRandomLines(g, 160);
        String captcha = idCode.drawRandomString(4, g);
        request.getSession().setAttribute(Captcha.CAPTCHA, captcha);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            logger.error("生成验证码错误");
        }
    }

    @GET
    @Path("/activate/user")
    @Produces(MediaType.TEXT_HTML)
    public ModelAndView activateUser(@Context HttpServletRequest request,
                                     @Context HttpServletResponse response,
                                     @QueryParam("email") String email,
                                     @QueryParam("validateCode") String validateCode) {
        Map<String, Object> context = new HashMap<String, Object>();
        String requestUri = request.getRequestURI();
        if (request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME) != null) {
            //更好的实现方式的使用cookie
            if (requestUri.startsWith("/login") || requestUri.startsWith("/testregister")) {
                //重定向到登录页面
                try {
                    response.sendRedirect(request.getContextPath());
                } catch (IOException e) {
                    logger.error("注册页面显示异常!" + e);
                }
                return null;
            }
        }
        Result<UserDO> userEmail = new Result<UserDO>();
//	    userEmail = userService.getUserByEmail(email);
        UserDO userDO1 = new UserDO();
        //验证用户是否存在
        if (userEmail.getData() != null) {
            //验证用户状态  
            if (userEmail.getData().getIsActivate() == 0) {
                Date currentTime = new Date();
                Date registerTime = userEmail.getData().getGmtCreate();
                int timeDifference = (int) ((currentTime.getTime() - registerTime.getTime()) / (60 * 1000));
                //验证链接是否过期，两天内有效期 
                if (timeDifference < 2880) {
                    //验证激活码是否正确  
                    if (TokenGenerator.checkEmail(email, validateCode)) {
                        //激活成功
                        userDO1.setId(userEmail.getData().getId());
                        userDO1.setIsActivate(1);
//                    	try {
//							userService.modify(userDO1);
//						} catch (CUException e) {
//							context.put("is_success", false);
//	            			context.put("error_msg", "用户激活失败");
//	            			return new ModelAndView("login_forbidden", context);
//						}
                    } else {
                        context.put("is_success", false);
                        context.put("error_msg", "激活码不正确");
                        return new ModelAndView("login_forbidden", context);
                    }
                } else {
                    context.put("is_success", false);
                    context.put("error_msg", "激活码已过期！");
                    return new ModelAndView("login_forbidden", context);
                }
            } else {
                context.put("is_success", false);
                context.put("error_msg", "邮箱已激活，请登录系统！");
                return new ModelAndView("login_forbidden", context);
            }
        } else {
            context.put("is_success", false);
            context.put("error_msg", "该邮箱未注册（邮箱地址不存在）！");
            return new ModelAndView("login_forbidden", context);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Integer errorTimes = (Integer) request.getSession().getAttribute(ERROR_TIMES);
        UserDO userDO = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        map.put(UserDO.CONTEXT_LOGIN_NAME, userDO);
        if (errorTimes == null) {
            map.put("login_error_times", 0);
        } else {
            map.put("login_error_times", errorTimes);
        }
        //map.put("next", next);
        map.put("nav_at", "index");
        return new ModelAndView("index", map);

    }

}
