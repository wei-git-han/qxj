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
