package com.css.app.qxjgl.inputTemplate.service;

import com.css.app.qxjgl.inputTemplate.entity.DocumentSet;

public interface DocumentSetService {

	DocumentSet querySetByUserId(String userId);

	void update(DocumentSet documentSet);

	void save(DocumentSet documentSet);

}
