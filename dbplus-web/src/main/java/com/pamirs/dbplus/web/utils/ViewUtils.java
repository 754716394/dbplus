package com.pamirs.dbplus.web.utils;

/**
 * velocity需要使用的一些方法
 *
 * @author deng(yihui) E-mail:haloashen@gmail.com
 * @version 创建时间：2013-4-22 下午4:21:24
 */
public class ViewUtils implements Constants {

    public double random() {
        return Math.random();
    }

    public String subString(String str, int count, String suffix) {
        if (str == null) {
            return null;
        } else {
            if (str.length() > count) {
                str = str.substring(0, count) + suffix;
            }
            return str;
        }
    }

    public String getAmountString(Long amount) {
        if (amount == null) {
            return "";
        }
        return CommercialArithmetick.getAmountString(amount);
    }

    public boolean isShowOrderButton(Integer currentStatus, Integer status) {
        if (status == null) {
            return false;
        }
//		// 如果已经退货则不显示按钮
//		if(status == OrderStatus.STATUS.WMS_RETURN.getNo()){
//			return false;
//		}
        // 如果状态大于或者等于当前节点状态，则不显示按钮
        else if (status > currentStatus) {
            return false;
        }
        return true;
    }

    /**
     * 判断用户是否具有权限
     *
     * @param authMap
     * @param action
     * @param operation
     * @return
     */
//	public static boolean auth(Map<String, PlatformAuthDO> authMap, String action, String operation){
//		if(authMap == null){
//			return false;
//		}
//		String authName = action + Constants.AUTH_SPLIT + operation;
//		PlatformAuthDO platformAuthDO = authMap.get(authName);
//		if(platformAuthDO == null){
//			return false;
//		}
//		if(platformAuthDO.getAuth() == PlatformAuth.AUTH.UNSET.getAuth()){
//			return false;
//		}else if(platformAuthDO.getAuth() == PlatformAuth.AUTH.ALLOW.getAuth()){
//			return true;
//		}else if(platformAuthDO.getAuth() == PlatformAuth.AUTH.FORBIDDEN.getAuth()){
//			return false;
//		}else{
//			return false;
//		}
//	}

}
