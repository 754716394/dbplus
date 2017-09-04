package com.pamirs.dbplus.configure.jade;

/**
 * 发布处理结果类
 */
public class JadeContentResult<T> extends JadeBaseResult {

	private String envId;

	private String envName;

	public JadeContentResult() {

	}

	public JadeContentResult(boolean success, String msg) {
		this.setSuccess(success);
		super.setMsg(msg);
	}

	public JadeContentResult(boolean success, String msg, T abstractDO) {
		super.setSuccess(success);
		super.setMsg(msg);
		this.abstractDO = abstractDO;
	}

	public JadeContentResult(JadeBaseResult jadeBaseResult, T abstractDO) {
		this.setSuccess(jadeBaseResult.isSuccess());
		this.setMsg(jadeBaseResult.getMsg());
		this.abstractDO = abstractDO;
	}
	
	public JadeContentResult(boolean success, String msg, String envId, String envName, T abstractDO) {
		super.setSuccess(success);
		super.setMsg(msg);
		this.envId = envId;
		this.envName = envName;
		this.abstractDO = abstractDO;
	}


	private T abstractDO;

	public T getAbstractDO() {
		return abstractDO;
	}

	public void setAbstractDO(T abstractDO) {
		this.abstractDO = abstractDO;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

}
