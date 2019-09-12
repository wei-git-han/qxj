package com.css.app.dictionary.dao;

import com.css.app.dictionary.entity.DictionaryValue;
import com.css.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;



/**
 * 公文字典值表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
@Mapper
public interface DictionaryValueDao extends BaseDao<DictionaryValue> {
	
	List<DictionaryValue> queryListByTypeId(Map<String, Object> map);
	
	int deleteByTypeId(String typeId);
	
	int queryByValue(Map<String,Object> map);
	
	int queryMaxSort(String typeId);
}
