package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.QxjModleDept;

import java.util.List;
import java.util.Map;

/**
 * 请销假模板部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-06-22 14:47:08
 */
public interface QxjModleDeptService {
	
	QxjModleDept queryObject(String id);
	
	List<QxjModleDept> queryList(Map<String, Object> map);
	
	void save(QxjModleDept qxjModleDept);
	
	void update(QxjModleDept qxjModleDept);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	QxjModleDept findByDept(String deptId);
}
