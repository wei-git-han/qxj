package com.css.app.qxjgl.business.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-03-02 10:04:12
 */
public class DocumentRoleSet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一标识
	private String id;
	//创建人
	private String creator;
	//离岗人员
	private String leaveUser;
	//离岗人员ID
	private String leaveUserId;
	//状态标识
	private String status;
	//接替人员
	private String replaceUser;
	//接替人员ID
	private String replaceUserId;
	//结束时间
	private Date endDate;
	//开始时间
	private String startDate;
	//创建时间
	private Date createDate;
	//授权类型（0:授权全部文件 1：授权离岗期内文件）
	private String authorizationType;
	
	public String getAuthorizationType() {
		return authorizationType;
	}
	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}
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
	 * 设置：创建人
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * 设置：离岗人员
	 */
	public void setLeaveUser(String leaveUser) {
		this.leaveUser = leaveUser;
	}
	/**
	 * 获取：离岗人员
	 */
	public String getLeaveUser() {
		return leaveUser;
	}
	/**
	 * 设置：状态标识
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态标识
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：接替人员
	 */
	public void setReplaceUser(String replaceUser) {
		this.replaceUser = replaceUser;
	}
	/**
	 * 获取：接替人员
	 */
	public String getReplaceUser() {
		return replaceUser;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public String getLeaveUserId() {
		return leaveUserId;
	}
	public void setLeaveUserId(String leaveUserId) {
		this.leaveUserId = leaveUserId;
	}
	public String getReplaceUserId() {
		return replaceUserId;
	}
	public void setReplaceUserId(String replaceUserId) {
		this.replaceUserId = replaceUserId;
	}
	
}
