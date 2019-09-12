package com.css.app.qxjgl.business.entity;

import java.io.Serializable;



/**
 * 请销假申请人表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-26 16:11:48
 */
public class ApplyUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一标识
	private String id;
	//请假主id
	private String leaveId;
	//申请人id
	private String applyUserId;
	//申请人姓名
	private String applyUserName;
	//申请人部门id
	private String applyDeptId;
	//申请人部门名称
	private String applyDeptName;
	//申请人单位id
	private String applyOrgId;
	//申请人单位名称
	private String applyOrgName;

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
	 * 设置：请假主id
	 */
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	/**
	 * 获取：请假主id
	 */
	public String getLeaveId() {
		return leaveId;
	}
	/**
	 * 设置：申请人id
	 */
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取：申请人id
	 */
	public String getApplyUserId() {
		return applyUserId;
	}
	/**
	 * 设置：申请人姓名
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	/**
	 * 获取：申请人姓名
	 */
	public String getApplyUserName() {
		return applyUserName;
	}
	/**
	 * 设置：申请人部门id
	 */
	public void setApplyDeptId(String applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	/**
	 * 获取：申请人部门id
	 */
	public String getApplyDeptId() {
		return applyDeptId;
	}
	/**
	 * 设置：申请人部门名称
	 */
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	/**
	 * 获取：申请人部门名称
	 */
	public String getApplyDeptName() {
		return applyDeptName;
	}
	/**
	 * 设置：申请人单位id
	 */
	public void setApplyOrgId(String applyOrgId) {
		this.applyOrgId = applyOrgId;
	}
	/**
	 * 获取：申请人单位id
	 */
	public String getApplyOrgId() {
		return applyOrgId;
	}
	/**
	 * 设置：申请人单位名称
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	/**
	 * 获取：申请人单位名称
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
}
