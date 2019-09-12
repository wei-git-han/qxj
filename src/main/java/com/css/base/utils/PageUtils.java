package com.css.base.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

/**
 * 分页工具类
 * 
 * @author 中软信息系统工程有限公司
 * @email  
 * @date 2016年11月4日 下午12:59:00
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private int totalCount;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//当前页数
	private int currPage;
	//列表数据
	private List<?> rows;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> rows, int totalCount, int pageSize, int currPage) {
		this.rows = rows;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}
	public PageUtils(List<?> rows){
		Page<?> page=(Page<?>) rows;
		this.rows = page.getResult();
		this.totalCount = (int) page.getTotal();
		this.pageSize = page.getPageSize();
		this.currPage = page.getPageNum();
		this.totalPage = page.getPageSize();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
	
	public Map<String,Object> getPageResult(){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("page", this.getCurrPage());
		dataMap.put("total", this.getTotalCount());
		dataMap.put("rows", this.getRows());
		return dataMap;
	}
}
