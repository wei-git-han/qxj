package com.css.app.dictionary.dao;

import com.css.app.dictionary.entity.DictionaryType;
import com.css.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


/**
 * 公文字典类型表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
@Mapper
public interface DictionaryTypeDao extends BaseDao<DictionaryType> {
	
	List<Map<String, Object>> queryDicList(Map<String, Object> map);
	
}
