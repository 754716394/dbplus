package com.pamirs.dbplus.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * Title:String 加密工具包 *
 * <p>
 * * *
 * <p>
 * Description: 加密工具包，有MD5，SHA加密算法 *
 * </p>
 * * *
 * <p>
 * Copyright: Copyright (c) 2010 *
 * </p>
 * *
 * 
 * @see MessageDigest * @version 1.0 * @author oyf
 */
public class Encryption {
	
	public static final String ENCRY_MD5 = "MD5";
	public static final String ENCRY_SHA = "SHA";
	private static Logger logger = LoggerFactory.getLogger(Encryption.class);

	/**
	 * *
	 * <p>
	 * 根据指定的模式，返回加密器
	 * <p>
	 * * @param option 模式 MD5 SHA * @return
	 */
	private static MessageDigest messageDigest(String option) {
		try {
			return MessageDigest.getInstance(option);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>
	 * 用户指定的加密器，加密字符串，返回16位数组
	 * <p>
	 * * @param digest 加密器 * @see MessageDigest
	 * 
	 * @param str
	 *            待加密的字符串 * @return 16位byte数组
	 */
	private static byte[] encryptionToByte(MessageDigest digest, String str) {
		digest.update(str.getBytes());
		return digest.digest();
	}

	private static String byteToString(byte[] bytes) {
		byte[] arrayOfByte;
		StringBuffer buffer = new StringBuffer();
		int j = (arrayOfByte = bytes).length;
		for (int i = 0; i < j; ++i) {
			byte aByte = arrayOfByte[i];
			if ((aByte & 0xFF) < 16)
				buffer.append("0");
			buffer.append(Integer.toString(aByte & 0xFF, 16));
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * MD5 32位加密
	 * <p>
	 * 
	 * @param str
	 *            待加密的字符串 * @return 已加密的字符串
	 */
	public static String encryMD5(String str) {
		return byteToString(encryptionToByte(messageDigest(ENCRY_MD5), str));
	}

	/**
	 * <p>
	 * MD5 32位批量加密
	 * <p>
	 * 
	 * @param strs
	 *            待比较的字符串数组 * @return 已加密的字符串数组
	 */
	public static String[] encryMD5(String[] strs) {
		String[] retStr = new String[strs.length];
		for (int i = 0; i < retStr.length; ++i)
			retStr[i] = encryMD5(strs[i]);

		return retStr;
	}

	/**
	 * <p>
	 * MD5 32位加密和给定字符比较
	 * <p>
	 * * @param str 待比较的字符串 * @param obj 已加密的字符串 * @return 比较结果
	 */
	public static boolean encryMD5(String str, String obj) {
		return encryMD5(str).equalsIgnoreCase(obj);
	}

	/**
	 * <p>
	 * MD5 16位加密
	 * <p>
	 * 
	 * @param str
	 *            待比较的字符串 * @return 已加密的字符串
	 */
	public static String encryMD5Short(String str) {
		return encryMD5(str).substring(8, 24);
	}

	/**
	 * <p>
	 * MD5 16位批量加密
	 * <p>
	 * 
	 * @param strs
	 *            待比较的字符串数组 * @return 已加密的字符串数组
	 */
	public static String[] encryMD5Short(String[] strs) {
		String[] reStrings = new String[strs.length];
		for (int i = 0; i < strs.length; ++i)
			reStrings[i] = encryMD5(strs)[i];
		return reStrings;
	}

	/**
	 * <p>
	 * MD5 16位加密和给定字符比较
	 * <p>
	 * * @param str 待加密的字符串 * @param obj 待比较的字符串 * @return 比较结果
	 */
	public static boolean encryMD5Short(String str, String obj) {
		return encryMD5Short(str).equalsIgnoreCase(obj);
	}

	/**
	 * <p>
	 * SHA 加密
	 * <p>
	 * 
	 * @param str
	 *            待加密的字符串 * @return 已加密字符串
	 */
	public static String encrySHA(String str) {
		return byteToString(encryptionToByte(messageDigest(ENCRY_SHA), str));
	}

	/**
	 * <p>
	 * SHA 批量加密
	 * <p>
	 * 
	 * @param strs
	 *            待加密的字符串数组 * @return 已加密字符串数组
	 */
	public static String[] encrySHA(String[] strs) {
		String[] reStrings = new String[strs.length];
		for (int i = 0; i < strs.length; ++i)
			reStrings[i] = encrySHA(strs[i]);
		return reStrings;
	}

	/**
	 * <p>
	 * SHA 加密和给定字符比较
	 * <p>
	 * * @param str 待加密的字符串 * @param obj 已待比较字符串 * @return 如果是true，否则false
	 */
	public static boolean encrySHA(String str, String obj) {
		return encrySHA(str).equalsIgnoreCase(obj);
	}
	
}
