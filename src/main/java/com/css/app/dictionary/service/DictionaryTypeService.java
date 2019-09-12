package com.css.app.dictionary.service;

import java.util.List;
import java.util.Map;

import com.css.app.dictionary.entity.DictionaryType;

/**
 * 公文字典类型表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
public interface DictionaryTypeService {
	
	DictionaryType queryObject(String id);
	
	List<DictionaryType> queryList(Map<String, Object> map);
	
	List<Map<String, Object>> queryDicList(Map<String, Object> map);
	
	int queryTotal(Map<String,Object> map);
	
	void save(DictionaryType dictionaryType);
	
	void update(DictionaryType dictionaryType);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
