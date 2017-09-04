package com.pamirs.dbplus.web.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	/**
	 * 获取字符串在数据库中存储的真实位数，中文字符算两个字符
	 * 
	 * @param str
	 * @return
	 */
	public static int getLength(String str){
        str = StringUtils.replaceReg(str, "[^\u0000-\u007f]", "**");
        str = StringUtils.replaceReg(str, "[\r\n,\n]", "**");
		return str.length();
	}
	
	/**
	 * 使用正则表达式替换字符串
	 * 
	 * @param src 源字符串
	 * @param reg 正则表达式
	 * @param rep 用来替换的字符串
	 * @return
	 */
	public static String replaceReg(String src, String reg, String rep){
		Pattern pat = Pattern.compile(reg);
		Matcher matcher = pat.matcher(src);
        if (matcher.find()) {
            return matcher.replaceAll(rep);
        } else {
            return src;
        }
	}
	
	/**
	 * 判断字符串是否匹配正则表达式
	 * 
	 * @param src 源字符串
	 * @param reg 正则表达式
	 * @return
	 */
	public static boolean test(String src, String reg){
		Pattern pat = Pattern.compile(reg);
		Matcher matcher = pat.matcher(src);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
	}
	
	/** 
	 * 防止sql注入 
	 * 
	 * @param sql 
	 * @return 
	 */  
	public static String TransactSQLInjection(String sql) { 
		if(sql == null){
			return sql;
		}
		return sql.replaceAll(".*([';]+|(--)+).*", " ");  
	} 
	
	/**
	 * 通过ID生成流水号
	 * 
	 * @param id
	 * @return
	 */
	public static String formatCarton(long id){     
		if(id <= 99999){
			id += 100000L;
		}
		String temp = "" + id;
		if(temp.length() > 5){
			temp = temp.substring(temp.length() - 5, temp.length());
		}
		return temp;
	}
	
	/**
	 * String2InputStream
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream String2InputStream(String str){
	    ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
	    return stream;
	}
	 
	/**
	 * inputStream2String
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException{
	    BufferedReader in = new BufferedReader(new InputStreamReader(is));
	    StringBuffer buffer = new StringBuffer();
	    String line = "";
	    while ((line = in.readLine()) != null){
	      buffer.append(line);
	    }
	    return buffer.toString();
	}

}
