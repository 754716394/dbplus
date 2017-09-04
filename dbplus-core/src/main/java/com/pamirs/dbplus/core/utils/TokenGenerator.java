package com.pamirs.dbplus.core.utils;


import com.pamirs.dbplus.api.constants.Constants;
import com.pamirs.dbplus.core.utils.base64.Base64;

import java.util.Map;
import java.util.Random;

/**
 * 序列号生成器
 * 
 * @author deng<yihui,haloashen@gmail.com>
 * @version 创建时间：2013-8-5 上午11:23:18
 * 
 */
public class TokenGenerator implements Constants {

	/**
	 * 生成随机验证码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/**
	 * 生成密码
	 * @param password
	 * @return
	 */
	public static String genPassword(String password) {
		String algorithm = "SHA";
		String salt = genRandomNum(6);
		password = getSHA(salt + password);
		String token = algorithm + PWD_SPLIT + salt + PWD_SPLIT + password;
		return token;
	}
	
	/** 
	 * 校验密码
	 * 
	 * @param input
	 * @param rawPassword
	 * @return
	 */
	public static boolean checkPassword(String input, String rawPassword){
		String[] pwds = rawPassword.split("\\" + PWD_SPLIT);
		String algorithm = pwds[0];
		String salt = pwds[1];
		String password = pwds[2];
		if(password.equals(getALgorithm(salt + input, algorithm))){
			return true;
		}
		return false;
	}
	
	/**
	 * 生成邮箱激活码
	 * 
	 * @param email
	 * @return
	 */
	public static String genEmail(String email) {
		String algorithm = "SHA";
		String salt = genRandomNum(6);
		email = getSHA(salt + email);
		String token = algorithm + PWD_SPLIT + salt + PWD_SPLIT + email;
		return token;
	}
	
	/** 
	 * 校验邮箱激活码
	 * 
	 * @param input
	 * @param rawEmail
	 * @return
	 */
	public static boolean checkEmail(String input, String rawEmail){
		String[] ema = rawEmail.split("\\" + PWD_SPLIT);
		String algorithm = ema[0];
		String salt = ema[1];
		String email = ema[2];
		if(email.equals(getALgorithm(salt + input, algorithm))){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据算法加密
	 * 
	 * @param source
	 * @param algorithm
	 * @return
	 */
	public static String getALgorithm(String source, String algorithm){
		if("MD5".equals(algorithm)){
			return getMD5(source);
		}else{
			return getSHA(source);
		}
	}

	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {
		return Encryption.encryMD5(source);
	}

	/**
	 * sha加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getSHA(String source) {
		return Encryption.encrySHA(source);
	}
	
	private static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJ0VXclZoC9YbIJ4JEbe6E6OOMa2oh2Zkv2WKBXl0CN5essdDPQa+m8Lnt/Q/ztnUrVdbuYZqpvOo8NJ86N7+qe+bHA9SDkPtJrBYB0URHKIdSFVQVKYWm/uS/QK54sFl5vc2uGrJkWvCoupL5rLyHr5cIP/v922YIu+eSM50X1LAgMBAAECgYBoRfar4kLjKt9c/EOYh//NpbsqrbGP1JIuo66YWqV/5vQMQIyIvh/f9ubPy/yhgkT0/8DOHduS8ejjigOhUntgLWtNwyuNqCJpSMg2FlSMWQeRoKLnAFus98DnJpkNby9JoMm35cmX8QDrD6u3A0zMwkdM6vAQnTJEGLaiQR82AQJBAPvYDvgfE1fWVoeo6MhCaY0LWCh12pch1wciNqWi07wb0Ni2o6QErHNHiCDcbjUjp+wGiKhLrIPU2BueNPwu1ZECQQCfrPtoCVGqZ3Ag8d4n+qzqWT8Zd4i+o8IfGh3zoB00md6xjON0z1KmqqbKANiEKuWbFtmfNzUq/x/w2CK7agcbAkEA0bJIXI87gvLkX9bj5QNvcdKVWDMxjI/PxI/XYrmehc2/awNAZGzDzsUr8vi4ByUmSB/0n/VKswaB3xUvVyAEEQJBAI1mbiwvNHamqxFBnu3gewf8iHhQ9nN8KQfJ5QQ9oh9Ws3KfxT1H63VUYec44IQKSNISZh0skpTE4NAzVm6I8kkCQQDlgFrevylNKzTMCFteb8STAK5hPgU+Bv3I41TEKLsap85Zo1so76UCpVPCoonZnvOah0VtJNnqp/d0/lBjtws/";
	/**
	 * RSA加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getRSA(String source) {
		byte[] data = source.getBytes();
        try {
			byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
			
			return new String(Base64.encode(encodedData));
		} catch (Exception e) {
			return null;
		}
	}
	
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdFV3JWaAvWGyCeCRG3uhOjjjGtqIdmZL9ligV5dAjeXrLHQz0GvpvC57f0P87Z1K1XW7mGaqbzqPDSfOje/qnvmxwPUg5D7SawWAdFERyiHUhVUFSmFpv7kv0CueLBZeb3NrhqyZFrwqLqS+ay8h6+XCD/7/dtmCLvnkjOdF9SwIDAQAB";
	/**
	 * RSA解密
	 * 
	 * @param source
	 * @return
	 */
	public static String getASR(String source) {
		if(source == null){
			return null;
		}
		byte[] data = Base64.decode(source.getBytes());
        byte[] decodedData;
		try {
			decodedData = RSAUtils.decryptByPublicKey(data, publicKey);
		} catch (Exception e) {
			return null;
		}
        return new String(decodedData);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*
		System.out.println(genRandomNum(128));
		System.out.println(getMD5("123"));
		System.out.println(getSHA("123"));
		System.out.println(genPassword("123456"));
		System.out.println(checkPassword("123456", "SHA$xxfhdo$eaa398275062050946e52897d6cf31c7dc96fac4"));
		System.out.println(checkPassword("1234567", "SHA$xxfhdo$eaa398275062050946e52897d6cf31c7dc96fac4"));
		*/
		testSign();
	}
	
	public static void getKeyPair(){
		try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(Base64.encode(encodedData)));
        byte[] decodedData = RSAUtils.decryptByPublicKey(Base64.decode(new String(Base64.encode(encodedData)).getBytes()), publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }


}
