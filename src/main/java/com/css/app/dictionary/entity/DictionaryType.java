package com.css.app.dictionary.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 公文字典类型表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
public class DictionaryType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//字典名称
	private String dictionaryName;
	//排序序号
	private Integer sort;
	//创建时间
	private Date createdTime;

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
	 * 设置：字典名称
	 */
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}
	/**
	 * 获取：字典名称
	 */
	public String getDictionaryName() {
		return dictionaryName;
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
	 * 设置：创建时间
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
}
