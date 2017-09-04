package com.pamirs.dbplus.web.filters;

import com.pamirs.commons.dao.Result;
import com.pamirs.dbplus.api.model.UserDO;
import com.pamirs.dbplus.api.service.UserService;
import com.pamirs.dbplus.core.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 登录拦截器
 */
public class LoginFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    private List<String> excludedUrls;

    private String loginUrl;

    private UserService userService;

    private String DBPLUS_CODE;

    static private String ERROR_TIMES = "error_times";

    private String defaultNamesrvAddr;

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request0, ServletResponse response0,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request0;
        HttpServletResponse response = (HttpServletResponse) response0;
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString == null || "null".equals(queryString)) {
            queryString = "";
        } else {
            queryString = "?" + queryString;
        }
        String requestUrl = requestUri + queryString;
        requestUrl = URLEncoder.encode(requestUrl, "UTF-8");
        boolean isPass = false;
        for (String url : excludedUrls) {
            if (requestUri.startsWith(url)) {
                isPass = true;
            }
        }

        request.getSession().setMaxInactiveInterval(-1);

        UserDO loginUser = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
        if (loginUser != null) {
            isPass = true;
            String userString = userService.getUserString(loginUser);
            Cookie cookie = new Cookie(UserDO.COOKIE_LOGIN_NAME, userString);
            Boolean rememberme = (Boolean) request.getSession().getAttribute(UserDO.SESSION_REMEMBERME);
            rememberme = rememberme == null ? Boolean.TRUE.toString().equals(CookieUtils.getCookie(UserDO.SESSION_REMEMBERME, request)) : rememberme;
            request.getSession().setAttribute(UserDO.SESSION_REMEMBERME, null);
            Cookie remembermeCookie = new Cookie(UserDO.SESSION_REMEMBERME, rememberme + "");
            if (rememberme != null && rememberme.booleanValue()) {
                cookie.setMaxAge(14 * 24 * 3600);
                remembermeCookie.setMaxAge(14 * 24 * 3600);
            } else {
                cookie.setMaxAge(-1);
                remembermeCookie.setMaxAge(-1);
            }
            remembermeCookie.setPath("/");
            cookie.setPath("/");
            response.addCookie(remembermeCookie);
            response.addCookie(cookie);
        } else {
            String userString = CookieUtils.getCookie(UserDO.COOKIE_LOGIN_NAME, request);
            UserDO cookieUser = userService.getUserFromString(userString);
            if (cookieUser != null) {
                Result<UserDO> result;
                try {
                    result = userService.verifyToken(cookieUser);
                } catch (Exception e) {
                    result = new Result<UserDO>();
                    result.setSuccess(false);
                }
                if (result.isSuccess()) {
                    cookieUser = (UserDO) result.getData();
                    cookieUser.setPassword(null);
                    request.getSession().setAttribute(UserDO.SESSION_LOGIN_NAME, cookieUser);
                    request.getSession().setAttribute(ERROR_TIMES, 0);
                    isPass = true;
                }
            }
        }

        if (isPass) {
            // 保存权限
            UserDO authUser = (UserDO) request.getSession().getAttribute(UserDO.SESSION_LOGIN_NAME);
            if (authUser != null) {
            }
            // 跳转
            chain.doFilter(request, response);
        } else {
            //重定向到登录页面
            if (requestUrl == null || "null".equals(requestUrl)) {
                requestUrl = "";
            }
            response.sendRedirect(request.getContextPath() + loginUrl + "?next=" + requestUrl);
        }
    }


    @Override
    public void destroy() {

    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getDBPLUS_CODE() {
        return DBPLUS_CODE;
    }

    public void setDBPLUS_CODE(String DBPLUS_CODE) {
        this.DBPLUS_CODE = DBPLUS_CODE;
    }

    public void setDefaultNamesrvAddr(String defaultNamesrvAddr) {
        this.defaultNamesrvAddr = defaultNamesrvAddr;
    }
}
