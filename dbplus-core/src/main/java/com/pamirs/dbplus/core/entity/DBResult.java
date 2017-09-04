package com.pamirs.dbplus.core.entity;

import java.io.Serializable;

/**
 * 系统结果类
 */
public class DBResult<T> implements Serializable {

	private static final long serialVersionUID = 2216564031780985969L;

	public static String ERROR_TIMES = "error_times";
	
	private boolean isSuccess;
	
	private String msg = "";
	
	private T data;
	
	private int errorTimes;
	
	private String url;

	private Paginator paginator;

    private long total;

	private static int DEFAULT_PAGE_SIZE = 20;
	private int pageSize;
	private long start;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void appendMsg(String msg){
		this.msg = this.msg + msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Paginator getPaginator(Integer currentPage, Integer pageSize) {
		if(null == currentPage) {
			currentPage = Integer.valueOf(1);
		}

		if(null == pageSize) {
			pageSize = Integer.valueOf(20);
		}

		Paginator paginator = new Paginator(currentPage.intValue(), pageSize.intValue());
		paginator.setTotal(this.total);
		paginator.generateView();
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

	public String toJsonString(){
		return "{\"is_success\":" + (this.isSuccess?"true":"false") 
				+ ",\"msg\":\"" + this.msg 
				+ "\",\"error_times\":" + this.errorTimes
				+ ",\"url\":\"" + this.url
				+ "\"}";
	}

}
