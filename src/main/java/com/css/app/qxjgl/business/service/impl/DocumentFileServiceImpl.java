package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.DocumentFileDao;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.service.DocumentFileService;



@Service("documentFileService")
public class DocumentFileServiceImpl implements DocumentFileService {
	@Autowired
	private DocumentFileDao documentFileDao;
	
	@Override
	public DocumentFile queryObject(String id){
		return documentFileDao.queryObject(id);
	}
	
	@Override
	public List<DocumentFile> queryList(Map<String, Object> map){
		return documentFileDao.queryList(map);
	}
	
	@Override
	public void save(DocumentFile qxjDocumentFile){
		documentFileDao.save(qxjDocumentFile);
	}
	
	@Override
	public void update(DocumentFile qxjDocumentFile){
		documentFileDao.update(qxjDocumentFile);
	}
	
	@Override
	public void delete(String id){
		documentFileDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		documentFileDao.deleteBatch(ids);
	}

	@Override
	public int queryMinSort(String leaveId, String fileType) {
		return documentFileDao.queryMinSort(leaveId, fileType);
	}

	@Override
	public DocumentFile queryByLeaveId(String id,String fileType) {
		return documentFileDao.queryByLeaveId(id,fileType);
	}

	@Override
	public void deleteByLeaveId(String leaveId) {
		documentFileDao.deleteByLeaveId(leaveId);
	}
	
}
