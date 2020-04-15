package com.css.app.qxjgl.business.service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.entity.DicCalender;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 15:14:15
 */
public interface DicCalenderService {
	
	DicCalender queryObject(String id);
	
	List<DicCalender> queryList(Map<String, Object> map);
	List<DicCalender> queryHoliday(Map<String, Object> map);
	
	void save(DicCalender qxjDicCalender);
	
	void update(DicCalender qxjDicCalender);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	void removeOneMouth(Map<String, Object> map);
	
	int queryHolidaySum(Map<String, Object> map);
}
