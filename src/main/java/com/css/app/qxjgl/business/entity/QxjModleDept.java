package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 请销假模板部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-06-22 14:47:08
 */
public class QxjModleDept implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//模板名称
	private String modleName;
	//局id
	private String deptId;
	//局名称
	private String deptName;
	//
	private Date createdTime;
	private Date updateTime;
	//XXX审批
	private String modleValue;
	private String createdUserId;

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
	 * 设置：模板名称
	 */
	public void setModleName(String modleName) {
		this.modleName = modleName;
	}
	/**
	 * 获取：模板名称
	 */
	public String getModleName() {
		return modleName;
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
	 * 设置：局名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：局名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * 设置：XXX审批
	 */
	public void setModleValue(String modleValue) {
		this.modleValue = modleValue;
	}
	/**
	 * 获取：XXX审批
	 */
	public String getModleValue() {
		return modleValue;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	
}
