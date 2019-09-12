package com.css.app.qxjgl.vocationdate.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 定义休假天数表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:38:15
 */
public class TVocationDate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//人员姓名
	private String personName;
	//人员ID
	private String personId;
	//入伍时间
	private String enrolTime;
	//参加工作时间
	private Date joinWorkTime;
	//应休假天数
	private Integer shouldVocationDate;
	//请销假ID
	private String leaveBackId;
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
	 * 设置：入伍时间
	 */
	public void setEnrolTime(String enrolTime) {
		this.enrolTime = enrolTime;
	}
	/**
	 * 获取：入伍时间
	 */
	public String getEnrolTime() {
		return enrolTime;
	}
	/**
	 * 设置：参加工作时间
	 */
	public void setJoinWorkTime(Date joinWorkTime) {
		this.joinWorkTime = joinWorkTime;
	}
	/**
	 * 获取：参加工作时间
	 */
	public Date getJoinWorkTime() {
		return joinWorkTime;
	}
	/**
	 * 设置：应休假天数
	 */
	public void setShouldVocationDate(Integer shouldVocationDate) {
		this.shouldVocationDate = shouldVocationDate;
	}
	/**
	 * 获取：应休假天数
	 */
	public Integer getShouldVocationDate() {
		return shouldVocationDate;
	}
	/**
	 * 设置：请销假ID
	 */
	public void setLeaveBackId(String leaveBackId) {
		this.leaveBackId = leaveBackId;
	}
	/**
	 * 获取：请销假ID
	 */
	public String getLeaveBackId() {
		return leaveBackId;
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
