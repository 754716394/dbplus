package com.pamirs.dbplus.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间方法
 * 
 * @author deng
 * 
 */
public class DateUtil {

	/**
	 * 生成日期字符串
	 * 
	 * @return
	 */
	public static String createDateString(Date date) {
		// Calendar c = Calendar.getInstance(Locale.CHINA);
		// Calendar f=Calendar.getInstance(Locale.CHINA);
		// Date d = c.getTime();
		// StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	
	public static String getString(Date date){
		if(date == null){
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static Date getDate(String date) throws ParseException{
		if(date == null || "".equals(date)){
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
	}

	/**
	 * 将 1970年1月1日到当前时刻的毫秒数 转化为Date
	 * 
	 * @param gmt
	 * @return
	 */
	public static Date getDate(Long gmt){
		return new Date(gmt);
	}
	
	/**
	 * 根据regx生成日期
	 * 
	 * @param regx
	 * @return
	 */
	public static String createDateString(String regx) {
		Calendar c = Calendar.getInstance(Locale.CHINA);

		// 这里是用当前的系统日期来生成年月日如当前是20081028
		Date d = c.getTime();
		// StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat(regx);
		return sdf.format(d);

	}
}
