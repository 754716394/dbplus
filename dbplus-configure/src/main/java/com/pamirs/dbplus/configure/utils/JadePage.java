package com.pamirs.dbplus.configure.utils;


import com.pamirs.dbplus.configure.jade.JadeContentDO;

import java.util.ArrayList;
import java.util.List;

public class JadePage<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JadePage(List list) {
		if (list == null) {
			this.totalCounts = 0;
			this.totalPages = 0;
			this.currentPage = 0;
			this.sizeOfPerPage = 0;
			this.data = new ArrayList<T>();
		} else {
			this.totalCounts = list.size();
			this.totalPages = 1;
			this.currentPage = 1;
			this.sizeOfPerPage = list.size();
			this.data = list;
		}
	}

	public JadePage() {

	}

	/**
	 * 构造方法
	 * @param jadeBasePage  原来的分页对象，主要使用其页号，记录总数等等数据
	 * @param data  新的类型的分页数据对象
	 */
	public JadePage(JadePage<JadeContentDO> jadeBasePage, List<T> data) {
		this.totalCounts = jadeBasePage.getTotalCounts();
		this.totalPages = jadeBasePage.getTotalPages();
		this.currentPage = jadeBasePage.getCurrentPage();
		this.sizeOfPerPage = jadeBasePage.getSizeOfPerPage();
		this.data = data;
	}

	public long getPerPageNo() {
		if (this.getCurrentPage() > 1)
			return this.getCurrentPage() - 1;
		return this.getCurrentPage();
	}

	public long getNextPageNo() {
		if (this.getCurrentPage() < this.getTotalPages())
			return this.getCurrentPage() + 1;
		return this.getCurrentPage();
	}

	public long getCurrentPageItemCount() {
		if (totalCounts == 0)
			return 0;
		return totalCounts % sizeOfPerPage == 0 ? sizeOfPerPage : totalCounts % sizeOfPerPage;
	}

	public static final int DefaultPageSize = 20;//默认页大小
	// 总记录数
	private long totalCounts = 0;
	// 总页数
	private long totalPages = 1;
	// 当前是哪页
	private long currentPage = 1;
	// 每页记录数
	private long sizeOfPerPage = 20;

	//数据对象
	private List<T> data;

	public long getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(long totalCounts) {
		this.totalCounts = totalCounts;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getSizeOfPerPage() {
		return sizeOfPerPage;
	}

	public void setSizeOfPerPage(long sizeOfPerPage) {
		this.sizeOfPerPage = sizeOfPerPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
