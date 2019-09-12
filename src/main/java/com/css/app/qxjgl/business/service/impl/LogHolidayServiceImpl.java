package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.LogHolidayDao;
import com.css.app.qxjgl.business.entity.LogHoliday;
import com.css.app.qxjgl.business.service.LogHolidayService;



@Service("qxjLogHolidayService")
public class LogHolidayServiceImpl implements LogHolidayService {
	@Autowired
	private LogHolidayDao qxjLogHolidayDao;
	
	@Override
	public LogHoliday queryObject(Integer id){
		return qxjLogHolidayDao.queryObject(id);
	}
	
	@Override
	public List<LogHoliday> queryList(Map<String, Object> map){
		return qxjLogHolidayDao.queryList(map);
	}
	
	@Override
	public void save(LogHoliday qxjLogHoliday){
		qxjLogHolidayDao.save(qxjLogHoliday);
	}
	
	@Override
	public void update(LogHoliday qxjLogHoliday){
		qxjLogHolidayDao.update(qxjLogHoliday);
	}
	
	@Override
	public void delete(Integer id){
		qxjLogHolidayDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		qxjLogHolidayDao.deleteBatch(ids);
	}
	
}
