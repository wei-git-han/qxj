package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 请假单-随员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-19 16:27:19
 */
public class QxjLeaveorbackFollow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//随员id
	private String userid;
	//部职别
	private String post;
	//核酸检测结果
	private String check;
	//请假单id
	private String backid;
	//随员名称
	private String username;
	//职级
	private String level;
	//创建时间
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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
	 * 设置：随员id
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 获取：随员id
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置：部职别
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * 获取：部职别
	 */
	public String getPost() {
		return post;
	}
	/**
	 * 设置：核酸检测结果
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	/**
	 * 获取：核酸检测结果
	 */
	public String getCheck() {
		return check;
	}
	/**
	 * 设置：请假单id
	 */
	public void setBackid(String backid) {
		this.backid = backid;
	}
	/**
	 * 获取：请假单id
	 */
	public String getBackid() {
		return backid;
	}
	/**
	 * 设置：随员名称
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：随员名称
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：职级
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：职级
	 */
	public String getLevel() {
		return level;
	}
}
