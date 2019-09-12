package com.css.app.qxjgl.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.dictionary.dao.DicLeaveDao;
import com.css.app.qxjgl.dictionary.entity.DicLeave;
import com.css.app.qxjgl.dictionary.service.DicLeaveService;

@Service("dicLeaveService")
public class DicLeaveServiceImpl implements DicLeaveService {
	@Autowired
	private DicLeaveDao dicLeaveDao;
	
	@Override
	public DicLeave queryObject(String id){
		return dicLeaveDao.queryObject(id);
	}
	
	@Override
	public List<DicLeave> queryList(Map<String, Object> map){
		return dicLeaveDao.queryList(map);
	}
	
	@Override
	public void save(DicLeave dicLeave){
		dicLeaveDao.save(dicLeave);
	}
	
	@Override
	public void update(DicLeave dicLeave){
		dicLeaveDao.update(dicLeave);
	}
	
	@Override
	public void delete(String id){
		dicLeaveDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dicLeaveDao.deleteBatch(ids);
	}
	
}
