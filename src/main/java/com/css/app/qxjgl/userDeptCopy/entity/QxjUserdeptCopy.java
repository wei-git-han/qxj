package com.css.app.qxjgl.userDeptCopy.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户原来部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-13 11:19:52
 */
public class QxjUserdeptCopy implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//用户id
	private String userId;
	//原来部门id
	private String oldDeptId;
	//现在部门id
	private String newDeptId;

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
	 * 设置：原来部门id
	 */
	public void setOldDeptId(String oldDeptId) {
		this.oldDeptId = oldDeptId;
	}
	/**
	 * 获取：原来部门id
	 */
	public String getOldDeptId() {
		return oldDeptId;
	}
	/**
	 * 设置：现在部门id
	 */
	public void setNewDeptId(String newDeptId) {
		this.newDeptId = newDeptId;
	}
	/**
	 * 获取：现在部门id
	 */
	public String getNewDeptId() {
		return newDeptId;
	}
}
