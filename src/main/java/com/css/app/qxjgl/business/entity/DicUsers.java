package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 11:05:13
 */
public class DicUsers implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String userid;
	//
	private String username;
	//
	private String deptid;
	//
	private String deptname;
	//
	private String rolecode;
	//
	private String rolename;
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
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	/**
	 * 获取：
	 */
	public String getDeptid() {
		return deptid;
	}
	/**
	 * 设置：
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	/**
	 * 获取：
	 */
	public String getDeptname() {
		return deptname;
	}
	/**
	 * 设置：
	 */
	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}
	/**
	 * 获取：
	 */
	public String getRolecode() {
		return rolecode;
	}
	/**
	 * 设置：
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	/**
	 * 获取：
	 */
	public String getRolename() {
		return rolename;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
