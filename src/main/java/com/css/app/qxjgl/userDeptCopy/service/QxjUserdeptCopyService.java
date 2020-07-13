package com.css.app.qxjgl.userDeptCopy.service;


import com.css.app.qxjgl.userDeptCopy.entity.QxjUserdeptCopy;

import java.util.List;
import java.util.Map;

/**
 * 用户原来部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-13 11:19:52
 */
public interface QxjUserdeptCopyService {
	
	QxjUserdeptCopy queryObject(String id);
	
	List<QxjUserdeptCopy> queryList(Map<String, Object> map);
	
	void save(QxjUserdeptCopy qxjUserdeptCopy);
	
	void update(QxjUserdeptCopy qxjUserdeptCopy);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<String> queryDeptIds(String orgId);
}
