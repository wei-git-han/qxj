package com.css.app.dictionary.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 公文字典值表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
public class DictionaryValue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//字典类型ID
	private String dictionaryTypeId;
	//字典值
	private String dictionaryValue;
	//排序序号
	private Integer sort;
	//
	private Date createdTime;
	
	private Integer sysType;

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
	 * 设置：字典类型ID
	 */
	public void setDictionaryTypeId(String dictionaryTypeId) {
		this.dictionaryTypeId = dictionaryTypeId;
	}
	/**
	 * 获取：字典类型ID
	 */
	public String getDictionaryTypeId() {
		return dictionaryTypeId;
	}
	/**
	 * 设置：字典值
	 */
	public void setDictionaryValue(String dictionaryValue) {
		this.dictionaryValue = dictionaryValue;
	}
	/**
	 * 获取：字典值
	 */
	public String getDictionaryValue() {
		return dictionaryValue;
	}
	/**
	 * 设置：排序序号
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序序号
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	public Integer getSysType() {
		return sysType;
	}
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}
	
	
}
