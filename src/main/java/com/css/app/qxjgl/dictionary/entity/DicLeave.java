package com.css.app.qxjgl.dictionary.entity;

import java.io.Serializable;

/**
 * 请假状态字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
public class DicLeave implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//请假状态
	private String leaveStatus;
	//备注
	private String remark;

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
	 * 设置：请假状态
	 */
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	/**
	 * 获取：请假状态
	 */
	public String getLeaveStatus() {
		return leaveStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
