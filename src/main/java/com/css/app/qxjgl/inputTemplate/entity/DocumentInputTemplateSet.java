package com.css.app.qxjgl.inputTemplate.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 签批输入模板设置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-10-22 16:52:18
 */
public class DocumentInputTemplateSet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//用户ID
	private String userId;
	//签批模板：1手写输入2键盘输入
	private String inputTemplate;
	//笔的粗细
	private String penWidth;
	//创建时间
	private Date createdDate;

	
	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：签批模板：1手写输入2键盘输入
	 */
	public void setInputTemplate(String inputTemplate) {
		this.inputTemplate = inputTemplate;
	}
	/**
	 * 获取：签批模板：1手写输入2键盘输入
	 */
	public String getInputTemplate() {
		return inputTemplate;
	}
	/**
	 * 获取：笔的粗细
	 */
	public String getPenWidth() {
		return penWidth;
	}
	/**
	 * 设置：笔的粗细
	 */
	public void setPenWidth(String penWidth) {
		this.penWidth = penWidth;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
}
