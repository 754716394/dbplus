package com.pamirs.dbplus.configure.jade;

import com.pamirs.configure.domain.ConfigInfo;
import com.pamirs.configure.domain.PageContextResult;
import com.pamirs.dbplus.configure.utils.JadePage;

import java.util.List;
import java.util.Vector;

/**
 * 分页查询结果
 */
public class JadePageResult<T> extends JadeBaseResult {

	private String envId;

	private String envName;

	/**
	 * 通过ContentResult构建只有一个元素的PageResult
	 * @param jadeContentResult
	 */
	public JadePageResult(JadeContentResult<T> jadeContentResult) {
		this.setMsg(jadeContentResult.getMsg());
		this.setSuccess(jadeContentResult.isSuccess());

		if (!jadeContentResult.isSuccess()) {
			return;
		}
		List<T> list = new Vector<T>(1);
		list.add(jadeContentResult.getAbstractDO());
		this.jadePage = new JadePage<T>();
		this.jadePage.setCurrentPage(1);
		this.jadePage.setSizeOfPerPage(20);
		this.jadePage.setTotalCounts(1);
		this.jadePage.setTotalPages(1);
		this.jadePage.setData(list);

	}

	/**
	 * 构造方法
	 * @param pageContextResult 主要使用其原始的状态信息，分页信息
	 * @param list  新的数据列表
	 */
	public JadePageResult(PageContextResult<ConfigInfo> pageContextResult, List<T> list) {
		//设置状态
		this.setSuccess(pageContextResult.isSuccess());
		this.setMsg(pageContextResult.getStatusMsg());
		this.jadePage = new JadePage<T>();

		if (this.isSuccess())
			this.setMsg("查询成功" + pageContextResult.getStatusMsg());
		else
			this.setMsg("查询失败" + pageContextResult.getStatusMsg());

		this.jadePage.setCurrentPage(pageContextResult.getCurrentPage());
		this.jadePage.setSizeOfPerPage(pageContextResult.getSizeOfPerPage());
		this.jadePage.setTotalCounts(pageContextResult.getTotalCounts());
		this.jadePage.setTotalPages(pageContextResult.getTotalPages());
		this.jadePage.setData(list);

	}

	public JadePageResult(boolean success, String msg) {
		this.setSuccess(success);
		super.setMsg(msg);
	}

	public JadePageResult() {
	}

	/**
	 * 构造方法，构建T类型的JadePageResult
	 * @param msg 基本返回类型，包括是否成功和运行消息
	 * @param jadePage  具体的页对象
	 */

	public JadePageResult(boolean success, String msg, JadePage<T> jadePage) {
		this.setSuccess(success);
		this.setMsg(msg);
		this.setJadePage(jadePage);
	}

	public JadePageResult(JadeBaseResult jadeBaseResult, JadePage<T> jadePage) {
		this.setMsg(jadeBaseResult.getMsg());
		this.setSuccess(jadeBaseResult.isSuccess());
		this.jadePage = jadePage;

	}

	private JadePage<T> jadePage;

	public JadePage<T> getJadePage() {
		return jadePage;
	}

	public void setJadePage(JadePage<T> jadePage) {
		this.jadePage = jadePage;
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
