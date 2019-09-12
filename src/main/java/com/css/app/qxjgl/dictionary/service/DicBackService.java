package com.css.app.qxjgl.dictionary.service;

import com.css.app.qxjgl.dictionary.entity.DicBack;

import java.util.List;
import java.util.Map;

/**
 * 销假状态字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
public interface DicBackService {
	
	DicBack queryObject(String id);
	
	List<DicBack> queryList(Map<String, Object> map);
	
	void save(DicBack dicBack);
	
	void update(DicBack dicBack);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
