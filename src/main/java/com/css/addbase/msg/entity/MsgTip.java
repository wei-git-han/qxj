package com.css.addbase.msg.entity;

import java.io.Serializable;

/**
 * 消息信息表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-08-22 10:24:24
 */
public class MsgTip implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//消息ID
	private String msgId;
	//消息标题
	private String msgTitle;
	//消息内容
	private String msgContent;
	//消息转发地址
	private String msgRedirect;
	
	private String status;
	
	private String statusName;
	// 消息分组的名称
	private String groupName;
	// 消息分组的地址
	private String groupRedirect;

	/**
	 * 设置：消息ID
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	/**
	 * 获取：消息ID
	 */
	public String getMsgId() {
		return msgId;
	}
	/**
	 * 设置：消息标题
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	/**
	 * 获取：消息标题
	 */
	public String getMsgTitle() {
		return msgTitle;
	}
	/**
	 * 设置：消息内容
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	/**
	 * 获取：消息内容
	 */
	public String getMsgContent() {
		return msgContent;
	}
	/**
	 * 设置：消息转发地址
	 */
	public void setMsgRedirect(String msgRedirect) {
		this.msgRedirect = msgRedirect;
	}
	/**
	 * 获取：消息转发地址
	 */
	public String getMsgRedirect() {
		return msgRedirect;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupRedirect() {
		return groupRedirect;
	}
	public void setGroupRedirect(String groupRedirect) {
		this.groupRedirect = groupRedirect;
	}
	
}
