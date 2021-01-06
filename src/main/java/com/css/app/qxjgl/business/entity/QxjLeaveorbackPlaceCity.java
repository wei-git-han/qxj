package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 保存请销假单-多个出差地点
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-06 16:01:57
 */
public class QxjLeaveorbackPlaceCity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String userId;
	//创建时间
	private Date craeateDate;
	//修改时间
	private Date updateDate;
	//
	private String userName;
	//省下面的城市
	private String city;
	//省/直辖市
	private String place;
	//QXJ_LEAVEORBACK表id
	private String leaveorbackId;
	//具体位置
	private String address;
	//风险等级
	private String level;
	//风险等级字段 0 :无，1：需要风险等级
	private String levelStatus;

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
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCraeateDate(Date craeateDate) {
		this.craeateDate = craeateDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCraeateDate() {
		return craeateDate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：省下面的城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：省下面的城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：省/直辖市
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * 获取：省/直辖市
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * 设置：QXJ_LEAVEORBACK表id
	 */
	public void setLeaveorbackId(String leaveorbackId) {
		this.leaveorbackId = leaveorbackId;
	}
	/**
	 * 获取：QXJ_LEAVEORBACK表id
	 */
	public String getLeaveorbackId() {
		return leaveorbackId;
	}
	/**
	 * 获取：具体位置
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：具体位置
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelStatus() {
		return levelStatus;
	}
	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}
	
	
}
