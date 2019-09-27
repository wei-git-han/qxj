package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 请假单的相关文件表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-14 11:19:46
 */
public class DocumentFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//唯一标识
	private String id;
	//主文件id
	private String leaveId;
	//文件名称
	private String fileName;
	//最新文件服务流式ID
	private String fileServerStreamId;
	//最新文件服务版式ID
	private String fileServerFormatId;
	//排序
	private Integer sort;
	//创建时间
	private Date createdTime;
	//文件类型(cpj主文件fj附件)
	private String fileType;
	//文件是否被编辑过;
	private String isEdit;

	/**
	 * 设置：唯一标识
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：唯一标识
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：主文件id
	 */
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	/**
	 * 获取：主文件id
	 */
	public String getLeaveId() {
		return leaveId;
	}
	/**
	 * 设置：文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：文件名称
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：最新文件服务流式ID
	 */
	public void setFileServerStreamId(String fileServerStreamId) {
		this.fileServerStreamId = fileServerStreamId;
	}
	/**
	 * 获取：最新文件服务流式ID
	 */
	public String getFileServerStreamId() {
		return fileServerStreamId;
	}
	/**
	 * 设置：最新文件服务版式ID
	 */
	public void setFileServerFormatId(String fileServerFormatId) {
		this.fileServerFormatId = fileServerFormatId;
	}
	/**
	 * 获取：最新文件服务版式ID
	 */
	public String getFileServerFormatId() {
		return fileServerFormatId;
	}
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
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
	/**
	 * 设置：文件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * 获取：文件类型
	 */
	public String getFileType() {
		return fileType;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
}
