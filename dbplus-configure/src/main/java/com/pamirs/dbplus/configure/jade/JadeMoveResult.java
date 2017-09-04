package com.pamirs.dbplus.configure.jade;

public class JadeMoveResult {
	
	private String key;//被操作的Key
	
	private boolean success;//是否成功
	
	private boolean overrite;//是否被重写
	
	private String msg;//消息
	
	public JadeMoveResult(String key, boolean success, boolean overrite,
                          String msg) {
		super();
		this.key = key;
		this.success = success;
		this.overrite = overrite;
		this.msg = msg;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isOverrite() {
		return overrite;
	}
	public void setOverrite(boolean overrite) {
		this.overrite = overrite;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
