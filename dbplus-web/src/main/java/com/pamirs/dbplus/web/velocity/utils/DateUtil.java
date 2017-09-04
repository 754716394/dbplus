package com.pamirs.dbplus.web.velocity.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author by mitsui on 2016/9/9.
 */
public class DateUtil {

    /**
     * 生成日期字符串
     *
     * @return
     */
    public static String createDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getString(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date getDate(String date) throws ParseException {
        if (date == null || "".equals(date)) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    public static void main(String[] args) {
		String str ="1999-02-02 12:12:12";
		DateUtil du = new DateUtil();
		try {
			Date date = du.getDate(str);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    /**
     * 将 1970年1月1日到当前时刻的毫秒数 转化为Date
     *
     * @param gmt
     * @return
     */
    public static Date getDate(Long gmt) {
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

