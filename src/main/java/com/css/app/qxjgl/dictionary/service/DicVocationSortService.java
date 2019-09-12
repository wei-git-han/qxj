package com.css.app.qxjgl.dictionary.service;

import com.css.app.qxjgl.dictionary.entity.DicVocationSort;

import java.util.List;
import java.util.Map;

/**
 * 休假类别字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
public interface DicVocationSortService {
	
	DicVocationSort queryObject(String id);
	
	List<DicVocationSort> queryList(Map<String, Object> map);
	
	void save(DicVocationSort dicVocationSort);
	
	void update(DicVocationSort dicVocationSort);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	int queryTotal();
}
