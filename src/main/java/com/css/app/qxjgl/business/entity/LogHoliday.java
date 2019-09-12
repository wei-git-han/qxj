package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-27 18:34:56
 */
public class LogHoliday implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String userid;
	//
	private String username;
	//
	private String deptcode;
	//
	private String deptname;
	//
	private Double beforedays;
	//
	private Double afterdays;
	//
	private Date logTime;
	//
	private String modifyUserid;
	//
	private String modifyUserName;

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
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	/**
	 * 获取：
	 */
	public String getDeptcode() {
		return deptcode;
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
	public void setBeforedays(Double beforedays) {
		this.beforedays = beforedays;
	}
	/**
	 * 获取：
	 */
	public Double getBeforedays() {
		return beforedays;
	}
	/**
	 * 设置：
	 */
	public void setAfterdays(Double afterdays) {
		this.afterdays = afterdays;
	}
	/**
	 * 获取：
	 */
	public Double getAfterdays() {
		return afterdays;
	}
	/**
	 * 设置：
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/**
	 * 获取：
	 */
	public Date getLogTime() {
		return logTime;
	}
	public String getModifyUserid() {
		return modifyUserid;
	}
	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	
	
}
