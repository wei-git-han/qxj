package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.QxjLeaveorbackPlaceCity;

import java.util.List;
import java.util.Map;

/**
 * 保存请销假单-多个出差地点
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-06 16:01:57
 */
public interface QxjLeaveorbackPlaceCityService {
	
	QxjLeaveorbackPlaceCity queryObject(String id);
	
	List<QxjLeaveorbackPlaceCity> queryList(Map<String, Object> map);
	
	void save(QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity);
	
	void update(QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	void savePlaces(Leaveorback leave); 
	
	List<QxjLeaveorbackPlaceCity> queryPlcaeList(String leaveorbackId);
	
	void deleteByLeaveorbackId(String id);
}
