package com.css.app.qxjgl.dictionary.service;

import com.css.app.qxjgl.dictionary.entity.DicAuthorization;

import java.util.List;
import java.util.Map;

/**
 * 授权名称字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
public interface DicAuthorizationService {
	
	DicAuthorization queryObject(String id);
	
	List<DicAuthorization> queryList(Map<String, Object> map);
	
	void save(DicAuthorization dicAuthorization);
	
	void update(DicAuthorization dicAuthorization);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
