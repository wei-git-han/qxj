package com.css.app.qxjgl.business.dto;

import java.util.Date;

public class QXJPeopleManagementDto {
	//用户id
	private String userId;
	//用户名称
	private String userName;
	/**部门id
	 */
	private String orgId;
	/**部门名称
	 */
	private String orgName;
	// 已休假天数
	private String xiuJiaDays;

	// 应休天数
	private String totalDays;

	// 未请假天数
	private String weixiujiaDays;

	// 请假类别
	private String type;

	// 请假开始时间
	private Date startDate;
	// 请假结束时间
	private Date endDate;
	public String getXiuJiaDays() {
		return xiuJiaDays;
	}
	public void setXiuJiaDays(String xiuJiaDays) {
		this.xiuJiaDays = xiuJiaDays;
	}
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}
	public String getWeixiujiaDays() {
		return weixiujiaDays;
	}
	public void setWeixiujiaDays(String weixiujiaDays) {
		this.weixiujiaDays = weixiujiaDays;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	

}
