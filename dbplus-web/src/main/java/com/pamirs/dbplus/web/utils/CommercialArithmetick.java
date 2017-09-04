package com.pamirs.dbplus.web.utils;

import java.math.BigDecimal;

/**
 * 商业计算
 *
 * @author deng<yihui,haloashen@gmail.com>
 * @version 创建时间：2013-10-29 上午10:26:32
 * 
 * 高精度商业计算
 */
public class CommercialArithmetick {
	
	/**
	 * 从接口或者页面，使用字符串接收参数，然后乘以1000转化为long类型
	 * 
	 * @param amount
	 * @return
	 */
	public static Long get1000Amount(String amount){
		BigDecimal bigAmount = new BigDecimal(amount.trim());
		return bigAmount.multiply(new BigDecimal(1000)).longValue();
	}
	
	/**
	 * 从应用中获得参数，然后除以1000转化为字符串类型
	 * 
	 * @param amount
	 * @return
	 */
	public static String getAmountString(Long amount){
		String result = new BigDecimal(amount).divide(new BigDecimal(1000)).toString();
		if(result.equals("9223372036854775.807")){
			return "∞";
		}else{
			return result;
		}
	}
	
	public static void main(String[] args){
		System.out.println(CommercialArithmetick.getAmountString(456L));
		System.out.println(CommercialArithmetick.get1000Amount("86.80"));
		System.out.println(CommercialArithmetick.getAmountString(CommercialArithmetick.get1000Amount("71.4")));
	}
	

}
