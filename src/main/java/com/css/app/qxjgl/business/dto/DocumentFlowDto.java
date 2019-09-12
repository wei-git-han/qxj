package com.css.app.qxjgl.business.dto;

import java.io.Serializable;

public class DocumentFlowDto implements Serializable{

	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//标题
	private String documentTitle;	
	//紧急程度
	private String jjcdId;
	//紧急程度名称
	private String jjcdName;
	//密级
	private String securityClassification;
	//密级NAME
	private String securityClassificationName;
	//来文单位id
	private String lwdwid;
	//来文单位
	private String lwdw;
	//承办人ID
	private String undertakerId;
	//承办人名称
	private String undertakerName;
	//拟稿人ID
	private String documentDrafterId;
	//拟稿人名称
	private String documentDrafterName;
	//承办单位ID
	private String undertakeDepartmentId;
	//承办单位名称（处室的）
	private String undertakeDepartmentName;
	//发送单位的唯一标示
	private String sendDepartmentFlag;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getJjcdId() {
		return jjcdId;
	}
	public void setJjcdId(String jjcdId) {
		this.jjcdId = jjcdId;
	}
	public String getJjcdName() {
		return jjcdName;
	}
	public void setJjcdName(String jjcdName) {
		this.jjcdName = jjcdName;
	}
	public String getSecurityClassification() {
		return securityClassification;
	}
	public void setSecurityClassification(String securityClassification) {
		this.securityClassification = securityClassification;
	}
	public String getSecurityClassificationName() {
		return securityClassificationName;
	}
	public void setSecurityClassificationName(String securityClassificationName) {
		this.securityClassificationName = securityClassificationName;
	}
	public String getLwdwid() {
		return lwdwid;
	}
	public void setLwdwid(String lwdwid) {
		this.lwdwid = lwdwid;
	}
	public String getLwdw() {
		return lwdw;
	}
	public void setLwdw(String lwdw) {
		this.lwdw = lwdw;
	}
	public String getUndertakerId() {
		return undertakerId;
	}
	public void setUndertakerId(String undertakerId) {
		this.undertakerId = undertakerId;
	}
	public String getUndertakerName() {
		return undertakerName;
	}
	public void setUndertakerName(String undertakerName) {
		this.undertakerName = undertakerName;
	}
	public String getDocumentDrafterId() {
		return documentDrafterId;
	}
	public void setDocumentDrafterId(String documentDrafterId) {
		this.documentDrafterId = documentDrafterId;
	}
	public String getDocumentDrafterName() {
		return documentDrafterName;
	}
	public void setDocumentDrafterName(String documentDrafterName) {
		this.documentDrafterName = documentDrafterName;
	}
	public String getUndertakeDepartmentId() {
		return undertakeDepartmentId;
	}
	public void setUndertakeDepartmentId(String undertakeDepartmentId) {
		this.undertakeDepartmentId = undertakeDepartmentId;
	}
	public String getUndertakeDepartmentName() {
		return undertakeDepartmentName;
	}
	public void setUndertakeDepartmentName(String undertakeDepartmentName) {
		this.undertakeDepartmentName = undertakeDepartmentName;
	}
	public String getSendDepartmentFlag() {
		return sendDepartmentFlag;
	}
	public void setSendDepartmentFlag(String sendDepartmentFlag) {
		this.sendDepartmentFlag = sendDepartmentFlag;
	}
}
