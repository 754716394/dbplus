package com.pamirs.dbplus.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie操作
 *
 * @author deng<yihui,haloashen@gmail.com>
 * @version 创建时间：2013-8-21 上午10:00:34
 * 
 * 操作cookie
 */
public class CookieUtils {

	/**
	 * 获取cookie
	 * 
	 * @param name
	 * @param request
	 * @return
	 */
	public static String getCookie(String name, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
	    if(cookies != null){
		    for(int i=0;i<cookies.length;i++){
		    	Cookie cookie = cookies[i];
		    	if(name.equals(cookie.getName())){
		    		return cookie.getValue();
		    	}
		    }
	    }
	    return null;
	}
	
	
}
