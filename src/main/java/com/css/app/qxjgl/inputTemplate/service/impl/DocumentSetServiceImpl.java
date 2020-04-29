package com.css.app.qxjgl.inputTemplate.service.impl;

import com.css.app.qxjgl.inputTemplate.dao.DocumentSetDao;
import com.css.app.qxjgl.inputTemplate.entity.DocumentSet;
import com.css.app.qxjgl.inputTemplate.service.DocumentSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("documentSetService")
public class DocumentSetServiceImpl implements DocumentSetService {
	@Autowired
	private DocumentSetDao documentSetDao;

	@Override
	public DocumentSet querySetByUserId(String userId) {
		DocumentSet documentSet = documentSetDao.querySetByUserId(userId);
		return documentSet;
	}

	@Override
	public void update(DocumentSet documentSet) {
		documentSetDao.update(documentSet);
	}

	@Override
	public void save(DocumentSet documentSet) {
		documentSetDao.save(documentSet);
	}
	
	
}
