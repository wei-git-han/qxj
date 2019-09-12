package com.css.base.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 分页工具类
 * 
 * @author 中软信息系统工程有限公司
 * @email  
 * @date 2016年11月4日 下午12:59:00
 */
public class GwPageUtilsOfxlgl2 implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private int total;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//当前页数
	private int currPage;
	//列表数据
	private List<?> rows;
	
	private List<?> otherList;
	private long qyNum;
	private long tyNum;
	private long wyNum;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public GwPageUtilsOfxlgl2(List<?> list, int totalCount, int pageSize, int currPage) {
		this.rows = list;
		this.total = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}
	public GwPageUtilsOfxlgl2(List<?> list,long qyNum,long tyNum,long wyNum){
		if (list instanceof Page) {
			Page<?> page=(Page<?>) list;
			this.rows = page.getResult();
			this.total = (int) page.getTotal();
			this.pageSize = page.getPageSize();
			this.currPage = page.getPageNum();
			this.totalPage = page.getPageSize();
			this.qyNum=qyNum;
			this.tyNum=tyNum;
			this.setWyNum(wyNum);
		} else if (list instanceof Collection) {
			this.rows = list;
			this.total = list.size();
			this.pageSize = list.size();
			this.currPage = 1;
			this.totalPage = 1;
			this.qyNum=qyNum;
			this.tyNum=tyNum;
			this.setWyNum(wyNum);
		}
//			this.rows = list;
//			this.total = totalNum;
////			this.total = list.size();
//			this.pageSize = list.size();
//			this.currPage = 1;
//			this.totalPage = 1;
//			this.qyNum=qyNum;
//			this.tyNum=tyNum;
		
	}


	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public List<?> getOtherList() {
		return otherList;
	}
	public void setOtherList(List<?> otherList) {
		this.otherList = otherList;
	}
	public long getQyNum() {
		return qyNum;
	}
	public void setQyNum(long qyNum) {
		this.qyNum = qyNum;
	}
	public long getTyNum() {
		return tyNum;
	}
	public void setTyNum(long tyNum) {
		this.tyNum = tyNum;
	}
	public long getWyNum() {
		return wyNum;
	}
	public void setWyNum(long wyNum) {
		this.wyNum = wyNum;
	}

	
	
}
