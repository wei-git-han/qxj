package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.QxjLeaveCancel;

import java.util.List;
import java.util.Map;

/**
 * 销假天数配置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-03 10:26:36
 */
public interface QxjLeaveCancelService {
	
	void save(QxjLeaveCancel qxjLeaveCancel);
	
	void update(QxjLeaveCancel qxjLeaveCancel);
	
	QxjLeaveCancel findByDeptId(Map<String, String> map);
	
	List<QxjLeaveCancel> findAll();

}
