package com.css.app.dictionary.service;

import java.util.List;
import java.util.Map;

import com.css.app.dictionary.entity.DictionaryValue;

/**
 * 公文字典值表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
public interface DictionaryValueService {
	
	DictionaryValue queryObject(String id);
	
	List<DictionaryValue> queryList(Map<String, Object> map);
	
	List<DictionaryValue> queryListByTypeId(Map<String, Object> map);
	
	int queryTotal(Map<String,Object> map);
	
	int queryByValue(Map<String,Object> map);
	
	int queryMaxSort(String typeId);
	
	void save(DictionaryValue dictionaryValue);
	
	void update(DictionaryValue dictionaryValue);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	void deleteByTypeId(String typeId);
}
