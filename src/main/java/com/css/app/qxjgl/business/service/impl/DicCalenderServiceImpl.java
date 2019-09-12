package com.css.app.qxjgl.business.service.impl;

import com.css.app.qxjgl.business.dao.DicCalenderDao;
import com.css.app.qxjgl.business.entity.DicCalender;
import com.css.app.qxjgl.business.service.DicCalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("qxjDicCalenderService")
public class DicCalenderServiceImpl implements DicCalenderService {

	@Autowired
	private DicCalenderDao qxjDicCalenderDao;

	@Override
	public DicCalender queryObject(String id){
		return qxjDicCalenderDao.queryObject(id);
	}
	
	@Override
	public List<DicCalender> queryHoliday(Map<String, Object> map){
		return qxjDicCalenderDao.queryHoliday(map);
	}
	
	@Override
	public List<DicCalender> queryList(Map<String, Object> map){
		return qxjDicCalenderDao.queryList(map);
	}
	
	@Override
	public void save(DicCalender qxjDicCalender){
		qxjDicCalenderDao.save(qxjDicCalender);
	}
	
	@Override
	public void update(DicCalender qxjDicCalender){
		qxjDicCalenderDao.update(qxjDicCalender);
	}
	
	@Override
	public void delete(String id){
		qxjDicCalenderDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjDicCalenderDao.deleteBatch(ids);
	}

	@Override
	public void removeOneMouth(Map<String, Object> map) {
		 	qxjDicCalenderDao.removeOneMouth(map);

	}

}
