package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.LogHoliday;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-27 18:34:56
 */
public interface LogHolidayService {
	
	LogHoliday queryObject(Integer id);
	
	List<LogHoliday> queryList(Map<String, Object> map);
	
	void save(LogHoliday qxjLogHoliday);
	
	void update(LogHoliday qxjLogHoliday);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
