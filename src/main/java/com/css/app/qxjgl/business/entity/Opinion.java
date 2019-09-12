package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 请销假意见表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-28 10:36:48
 */
public class Opinion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一标识
	private String id;
	//主文件id
	private String leaveId;
	//用户id
	private String userId;
	//用户姓名
	private String userName;
	//用户所在部门id
	private String deptId;
	//用户所在部门名称
	private String deptName;
	//意见内容
	private String opinion;
	//意见版本（0：临时 1：正式发布
	private String tempType;
	//意见类型（1手写签批）
	private String opinionType;
	//意见最终修改时间（临时版本的为创建时间，发布版本的为发布时间）
	private Date opinionDate;

	/**
	 * 设置：唯一标识
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：唯一标识
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：主文件id
	 */
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	/**
	 * 获取：主文件id
	 */
	public String getLeaveId() {
		return leaveId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：用户所在部门id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：用户所在部门id
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：用户所在部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：用户所在部门名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：意见内容
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**
	 * 获取：意见内容
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 * 设置：意见版本（0：临时 1：正式发布
	 */
	public void setTempType(String tempType) {
		this.tempType = tempType;
	}
	/**
	 * 获取：意见版本（0：临时 1：正式发布
	 */
	public String getTempType() {
		return tempType;
	}
	/**
	 * 设置：意见类型（1手写签批）
	 */
	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}
	/**
	 * 获取：意见类型（1手写签批）
	 */
	public String getOpinionType() {
		return opinionType;
	}
	/**
	 * 设置：意见最终修改时间（临时版本的为创建时间，发布版本的为发布时间）
	 */
	public void setOpinionDate(Date opinionDate) {
		this.opinionDate = opinionDate;
	}
	/**
	 * 获取：意见最终修改时间（临时版本的为创建时间，发布版本的为发布时间）
	 */
	public Date getOpinionDate() {
		return opinionDate;
	}
}
