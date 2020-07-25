package com.css.app.qxjgl.qxjbubao.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 补报表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-25 14:38:45
 */
public class QxjFlowBubao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//创建时间
	private Date createdTime;
	//审批完成标识：0未审批，1已审批
	private String completeFlag;
	//请假单id
	private String fileId;
	//审批完成时间
	private Date finishId;
	//发送人id
	private String senderId;
	//接收人id
	private String receiveId;

	private String status;

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
	/**
	 * 设置：审批完成标识：0未审批，1已审批
	 */
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	/**
	 * 获取：审批完成标识：0未审批，1已审批
	 */
	public String getCompleteFlag() {
		return completeFlag;
	}
	/**
	 * 设置：请假单id
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * 获取：请假单id
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * 设置：审批完成时间
	 */
	public void setFinishId(Date finishId) {
		this.finishId = finishId;
	}
	/**
	 * 获取：审批完成时间
	 */
	public Date getFinishId() {
		return finishId;
	}
	/**
	 * 设置：发送人id
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * 获取：发送人id
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * 设置：接收人id
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	/**
	 * 获取：接收人id
	 */
	public String getReceiveId() {
		return receiveId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
