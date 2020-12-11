package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 省市县数据表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-12-11 15:18:35
 */
public class ProvinceCityDistrict implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//地区代码
	private String id;
	//地区名称
	private String name;
	//当前地区的上一级地区代码
	private String pid;

	/**
	 * 设置：地区代码
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：地区代码
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：地区名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：地区名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：当前地区的上一级地区代码
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取：当前地区的上一级地区代码
	 */
	public String getPid() {
		return pid;
	}
}
