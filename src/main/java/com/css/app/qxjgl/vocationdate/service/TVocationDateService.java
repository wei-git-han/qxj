package com.css.app.qxjgl.vocationdate.service;

import com.css.app.qxjgl.vocationdate.entity.TVocationDate;

import java.util.List;
import java.util.Map;

/**
 * 定义休假天数表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:38:15
 */
public interface TVocationDateService {
	
	TVocationDate queryObject(String id);
	
	List<TVocationDate> queryList(Map<String, Object> map);
	
	void save(TVocationDate tVocationDate);
	
	void update(TVocationDate tVocationDate);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	int queryTotal(Map<String, Object> map);

	TVocationDate queryByUserId(String userId);

}
