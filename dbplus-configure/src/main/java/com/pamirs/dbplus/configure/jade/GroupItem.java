package com.pamirs.dbplus.configure.jade;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * group内部的每一项
 */
public class GroupItem {

	public String toJsonString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("db", db);
			jsonObj.put("r", r);
			jsonObj.put("w", w);
			jsonObj.put("p", p);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
	}

	@Override
	public String toString() {
		return this.db + ":r" + this.getR() + "w" + this.getW() + "p" + this.getP();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof GroupItem))
			return false;
		GroupItem groupItem = (GroupItem) obj;
		if (!StringUtils.equals(db, groupItem.getDb()))
			return false;
		if (w != groupItem.getW())
			return false;
		if (r != groupItem.getR())
			return false;
		return true;
	}

	public GroupItem() {

	}

	public GroupItem(long r, long w, String db) {
		super();
		this.r = r;
		this.w = w;
		this.db = db;
	}

	public GroupItem(long r, long w, long p, String db) {
		super();
		this.r = r;
		this.w = w;
		this.p = p;
		this.db = db;
	}

	private long r = 0;// 读优先,<0不可用
	private long w = 0;// 写优先,<0不可用
	private long p = 0;//读写优先级
	private String db;// 数据库的名

	private int index=-1;//所在位置

	public long getR() {
		return r;
	}

	public void setR(long r) {
		this.r = r;
	}

	public long getW() {
		return w;
	}

	public void setW(long w) {
		this.w = w;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
