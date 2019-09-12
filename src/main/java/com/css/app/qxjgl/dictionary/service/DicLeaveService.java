package com.css.app.qxjgl.dictionary.service;

import com.css.app.qxjgl.dictionary.entity.DicLeave;

import java.util.List;
import java.util.Map;

/**
 * 请假状态字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
public interface DicLeaveService {
	
	DicLeave queryObject(String id);
	
	List<DicLeave> queryList(Map<String, Object> map);
	
	void save(DicLeave dicLeave);
	
	void update(DicLeave dicLeave);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
