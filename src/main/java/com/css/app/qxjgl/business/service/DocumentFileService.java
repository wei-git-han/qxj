package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.DocumentFile;

import java.util.List;
import java.util.Map;

/**
 * 请假单的相关文件表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-14 11:19:46
 */
public interface DocumentFileService {
	
	DocumentFile queryObject(String id);
	
	List<DocumentFile> queryList(Map<String, Object> map);
	
	void save(DocumentFile qxjDocumentFile);
	
	void update(DocumentFile qxjDocumentFile);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	int queryMinSort(String leaveId,String fileType);

	DocumentFile queryByLeaveId(String id,String fileType);

	void deleteByLeaveId(String leaveId);

}
