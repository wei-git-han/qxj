package com.css.app.qxjgl.authorization.entity;

import java.io.Serializable;

/**
 * 人员授权表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:28:19
 */
public class TAuthorization implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//人员姓名
	private String personName;
	//人员ID
	private String personId;
	//授权名称ID
	private String authorizationId;
	//授权名称
	private String uthorizationName;
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
	 * 设置：人员姓名
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * 获取：人员姓名
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * 设置：人员ID
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	/**
	 * 获取：人员ID
	 */
	public String getPersonId() {
		return personId;
	}
	/**
	 * 设置：授权名称ID
	 */
	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}
	/**
	 * 获取：授权名称ID
	 */
	public String getAuthorizationId() {
		return authorizationId;
	}
	/**
	 * 设置：授权名称
	 */
	public void setUthorizationName(String uthorizationName) {
		this.uthorizationName = uthorizationName;
	}
	/**
	 * 获取：授权名称
	 */
	public String getUthorizationName() {
		return uthorizationName;
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
