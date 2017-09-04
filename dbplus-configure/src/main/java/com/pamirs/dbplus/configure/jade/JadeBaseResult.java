package com.pamirs.dbplus.configure.jade;

/**
 * 基本处理结果类
 */
public class JadeBaseResult {

	public JadeBaseResult() {

	}

	public JadeBaseResult(String msg, boolean isSuccess) {
		super();
		this.msg = msg;
		this.isSuccess = isSuccess;
	}

	//消息
	private String msg;
	// 是否成功
	private boolean isSuccess = false;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 处理是否成功
	 * @return
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
