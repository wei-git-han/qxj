package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 审批流程表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-03-06 14:17:48
 */
public class ApprovalFlow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private String id;
	//请销假主键ID
		private String leaveId;
		//创建者ID
		private String creatorId;
		//创建者
		private String creator;
		//创建日期
		private Date createDate;
		//修改者
		private String modificator;
		//修改者ID
		private String modificatorId;
		//最后修改日期
		private Date modifyDate;
		//审批人ID
		private String approvalId;
		//审批人姓名
		private String approvalName;
		//0未读 1已读
		private Integer isView;
		//意见
		private String comment;
		//意见类型（1手写签批）
		private String commentType;
		//意见版本（0：临时 1：正式发布）
		private String tempType;
		//意见最终修改时间（临时版本的为创建时间，发布版本的为发布时间）
		private Date commentDate;
		//流转标识
		private String flowType;
		//备注
		private String remark;
		//字典项：1=当前审批人，0=非当前审批人（废弃）
		private Integer curNum;
		private String senderDepartId;
		private String senderDepartName;
		private String receiverDepartId;
		private String receiverDepartName;
		private Date isViewTime;

	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：审批内容
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 获取：审批内容
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * 设置：创建者ID
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * 获取：创建者ID
	 */
	public String getCreatorId() {
		return creatorId;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * 设置：修改者
	 */
	public void setModificator(String modificator) {
		this.modificator = modificator;
	}
	/**
	 * 获取：修改者
	 */
	public String getModificator() {
		return modificator;
	}
	/**
	 * 设置：审批人ID
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}
	/**
	 * 获取：审批人ID
	 */
	public String getApprovalId() {
		return approvalId;
	}
	/**
	 * 设置：审批人姓名
	 */
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	/**
	 * 获取：审批人姓名
	 */
	public String getApprovalName() {
		return approvalName;
	}
	/**
	 * 设置：修改者ID
	 */
	public void setModificatorId(String modificatorId) {
		this.modificatorId = modificatorId;
	}
	/**
	 * 获取：修改者ID
	 */
	public String getModificatorId() {
		return modificatorId;
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
	/**
	 * 设置：最后修改日期
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * 获取：最后修改日期
	 */
	public Date getModifyDate() {
		return modifyDate;
	}
	/**
	 * 设置：请销假ID
	 */
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	/**
	 * 获取：请销假ID
	 */
	public String getLeaveId() {
		return leaveId;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：字典项：1=当前审批人，0=非当前审批人
	 */
	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}
	/**
	 * 获取：字典项：1=当前审批人，0=非当前审批人
	 */
	public Integer getCurNum() {
		return curNum;
	}
	public Integer getIsView() {
		return isView;
	}
	public void setIsView(Integer isView) {
		this.isView = isView;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getCommentType() {
		return commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public String getTempType() {
		return tempType;
	}
	public void setTempType(String tempType) {
		this.tempType = tempType;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getSenderDepartId() {
		return senderDepartId;
	}

	public void setSenderDepartId(String senderDepartId) {
		this.senderDepartId = senderDepartId;
	}

	public String getSenderDepartName() {
		return senderDepartName;
	}

	public void setSenderDepartName(String senderDepartName) {
		this.senderDepartName = senderDepartName;
	}

	public String getReceiverDepartId() {
		return receiverDepartId;
	}

	public void setReceiverDepartId(String receiverDepartId) {
		this.receiverDepartId = receiverDepartId;
	}

	public String getReceiverDepartName() {
		return receiverDepartName;
	}

	public void setReceiverDepartName(String receiverDepartName) {
		this.receiverDepartName = receiverDepartName;
	}

	public Date getIsViewTime() {
		return isViewTime;
	}

	public void setIsViewTime(Date isViewTime) {
		this.isViewTime = isViewTime;
	}
}
