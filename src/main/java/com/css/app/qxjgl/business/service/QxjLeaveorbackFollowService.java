package com.css.app.qxjgl.business.service;


import com.css.app.qxjgl.business.entity.QxjLeaveorbackFollow;

import java.util.List;
import java.util.Map;

/**
 * 请假单-随员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-19 16:27:19
 */
public interface QxjLeaveorbackFollowService {
	
	QxjLeaveorbackFollow queryObject(String id);
	
	List<QxjLeaveorbackFollow> queryList(Map<String, Object> map);
	
	void save(QxjLeaveorbackFollow qxjLeaveorbackFollow);
	
	void update(QxjLeaveorbackFollow qxjLeaveorbackFollow);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	QxjLeaveorbackFollow queryTop1(String userId);
}
