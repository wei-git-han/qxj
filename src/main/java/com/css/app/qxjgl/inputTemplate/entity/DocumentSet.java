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
public class DocumentSet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//人员id
	private String userId;
	//笔迹编号
	private String pen;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPen() {
		return pen;
	}
	public void setPen(String pen) {
		this.pen = pen;
	}

}
