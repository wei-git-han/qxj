package com.css.app.qxjgl.dictionary.entity;

import java.io.Serializable;

/**
 * 休假类别字典表
 *
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-06-06 09:36:05
 */
public class DicVocationSortPlus implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String id;

	private String text;
	//是否需要审批
	//适用于交通工具，2是需要审批
	//适用于交通工具，3不需要审批
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
