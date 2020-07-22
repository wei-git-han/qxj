package com.css.app.qxjgl.dictionary.entity;

import java.io.Serializable;

/**
 * 休假类别字典表
 *
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-06-06 09:36:05
 */
public class DicVocationSort implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String id;
	//休假类别
	private String vacationSortId;
	//删除标志
	private Integer deleteMark;
	//备注
	private String remark;
	/**
	 */
	private String orgId;
	/**
	 *
	 */
	private String orgName;
	/**
	 * 是否抵扣应休假天数
	 * 0： 是 
	 * 1：否
	 * */
	private String deductionVacationDay;

	/**
	 * 是否删除
	 */
	private Integer sfsc;

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
	 * 设置：休假类别
	 */
	public void setVacationSortId(String vacationSortId) {
		this.vacationSortId = vacationSortId;
	}
	/**
	 * 获取：休假类别
	 */
	public String getVacationSortId() {
		return vacationSortId;
	}
	/**
	 * 设置：删除标志
	 */
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	/**
	 * 获取：删除标志
	 */
	public Integer getDeleteMark() {
		return deleteMark;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDeductionVacationDay() {
		return deductionVacationDay;
	}
	public void setDeductionVacationDay(String deductionVacationDay) {
		this.deductionVacationDay = deductionVacationDay;
	}


	public Integer getSfsc() {
		return sfsc;
	}

	public void setSfsc(Integer sfsc) {
		this.sfsc = sfsc;
	}
}
