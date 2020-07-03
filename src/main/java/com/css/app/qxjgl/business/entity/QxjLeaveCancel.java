package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 销假天数配置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-03 10:26:36
 */
public class QxjLeaveCancel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//局id
	private String deptId;
	//
	private String deptName;
	//开关:0开，1关
	private String type;
	//天数
	private String days;

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
	 * 设置：局id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：局id
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：开关:0开，1关
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：开关:0开，1关
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：天数
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * 获取：天数
	 */
	public String getDays() {
		return days;
	}
}
