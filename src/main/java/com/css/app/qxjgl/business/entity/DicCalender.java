package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 15:14:15
 */
public class DicCalender implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private Date calenderDate;
	//
	private String isweekend;
	//
	private String isholiday;
	//
	private String isworkingday;
	/**
	 *
	 */
	private String orgId;
	/**
	 *
	 */
	private String orgName;
	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setCalenderDate(Date calenderDate) {
		this.calenderDate = calenderDate;
	}
	/**
	 * 获取：
	 */
	public Date getCalenderDate() {
		return calenderDate;
	}
	/**
	 * 设置：
	 */
	public void setIsweekend(String isweekend) {
		this.isweekend = isweekend;
	}
	/**
	 * 获取：
	 */
	public String getIsweekend() {
		return isweekend;
	}
	/**
	 * 设置：
	 */
	public void setIsholiday(String isholiday) {
		this.isholiday = isholiday;
	}
	/**
	 * 获取：
	 */
	public String getIsholiday() {
		return isholiday;
	}
	/**
	 * 设置：
	 */
	public void setIsworkingday(String isworkingday) {
		this.isworkingday = isworkingday;
	}
	/**
	 * 获取：
	 */
	public String getIsworkingday() {
		return isworkingday;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
