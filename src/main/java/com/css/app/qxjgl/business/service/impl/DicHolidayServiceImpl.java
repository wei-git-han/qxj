package com.css.app.qxjgl.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.app.qxjgl.business.dao.DicHolidayDao;
import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.service.DicHolidayService;



@Service("qxjDicHolidayService")
public class DicHolidayServiceImpl implements DicHolidayService {
	@Autowired
	private DicHolidayDao qxjDicHolidayDao;
	
	@Override
	public DicHoliday queryObject(String id){
		return qxjDicHolidayDao.queryObject(id);
	}
	
	@Override
	public List<DicHoliday> queryList(Map<String, Object> map){
		return qxjDicHolidayDao.queryList(map);
	}
	
	@Override
	public void save(DicHoliday qxjDicHoliday){
		qxjDicHolidayDao.save(qxjDicHoliday);
	}
	
	
	@Override
	public void update(DicHoliday qxjDicHoliday){
		qxjDicHolidayDao.update(qxjDicHoliday);
	}
	
	@Override
	public void delete(String id){
		qxjDicHolidayDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjDicHolidayDao.deleteBatch(ids);
	}

	@Override
	public void updateByUserId(DicHoliday qxjDicHoliday) {
		qxjDicHolidayDao.updateByUserId(qxjDicHoliday);
		
	}

	@Override
	public DicHoliday queryByUserId(String userId) {
		return qxjDicHolidayDao.queryByUserId(userId);
	}
}
