package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.DicHoliday;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-27 18:34:56
 */
public interface DicHolidayService {
	
	DicHoliday queryObject(String id);
	
	List<DicHoliday> queryList(Map<String, Object> map);
	
	void save(DicHoliday qxjDicHoliday);
	
	void update(DicHoliday qxjDicHoliday);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	void updateByUserId(DicHoliday qxjDicHoliday);

	DicHoliday queryByUserId(String userId);
}
