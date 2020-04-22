package com.css.app.qxjgl.inputTemplate.service.impl;

import com.css.app.qxjgl.inputTemplate.dao.DocumentInputTemplateSetDao;
import com.css.app.qxjgl.inputTemplate.entity.DocumentInputTemplateSet;
import com.css.app.qxjgl.inputTemplate.service.DocumentInputTemplateSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("documentInputTemplateSetService")
public class DocumentInputTemplateSetServiceImpl implements DocumentInputTemplateSetService {
	@Autowired
	private DocumentInputTemplateSetDao documentInputTemplateSetDao;
	
	@Override
	public DocumentInputTemplateSet queryObject(String id){
		return documentInputTemplateSetDao.queryObject(id);
	}
	
	@Override
	public List<DocumentInputTemplateSet> queryList(Map<String, Object> map){
		return documentInputTemplateSetDao.queryList(map);
	}
	
	@Override
	public void save(DocumentInputTemplateSet documentInputTemplateSet){
		documentInputTemplateSetDao.save(documentInputTemplateSet);
	}
	
	@Override
	public void update(DocumentInputTemplateSet documentInputTemplateSet){
		documentInputTemplateSetDao.update(documentInputTemplateSet);
	}
	
	@Override
	public void delete(String id){
		documentInputTemplateSetDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		documentInputTemplateSetDao.deleteBatch(ids);
	}

	@Override
	public DocumentInputTemplateSet getSetByUserId(String userId) {
		return documentInputTemplateSetDao.getSetByUserId(userId);
	}

	@Override
	public void delSetByUserId(String userId) {
		documentInputTemplateSetDao.delSetByUserId(userId);
	}
	
}
