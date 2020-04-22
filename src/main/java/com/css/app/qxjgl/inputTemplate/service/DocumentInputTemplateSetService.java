package com.css.app.qxjgl.inputTemplate.service;


import com.css.app.qxjgl.inputTemplate.entity.DocumentInputTemplateSet;

import java.util.List;
import java.util.Map;

/**
 * 签批输入模板设置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-10-22 16:52:18
 */
public interface DocumentInputTemplateSetService {
	
	DocumentInputTemplateSet queryObject(String id);
	
	List<DocumentInputTemplateSet> queryList(Map<String, Object> map);
	
	void save(DocumentInputTemplateSet documentInputTemplateSet);
	
	void update(DocumentInputTemplateSet documentInputTemplateSet);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	DocumentInputTemplateSet getSetByUserId(String userId);
	
	void delSetByUserId(String userId);
}
