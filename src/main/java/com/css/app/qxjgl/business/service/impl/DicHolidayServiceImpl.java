package com.css.app.qxjgl.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.css.base.utils.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.app.qxjgl.business.dao.DicHolidayDao;
import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.service.DicHolidayService;



@Service("qxjDicHolidayService")
public class DicHolidayServiceImpl implements DicHolidayService {
	private final static Logger logger = LoggerFactory.getLogger(DicHolidayServiceImpl.class);
	private SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		StringBuffer buffer = new StringBuffer();
		for(String id:ids){
			DicHoliday dicHoliday = qxjDicHolidayDao.queryObject(id);
			buffer.append(dicHoliday.getUsername());
			buffer.append(",");
		}
		qxjDicHolidayDao.deleteBatch(ids);
		logger.info(CurrentUser.getUsername()+"在"+format.format(new Date())+"应休假天数菜单中删除了姓名为:"+buffer.toString()+"的数据");
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
