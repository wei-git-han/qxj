package com.css.app.qxjgl.vocationdate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.app.qxjgl.vocationdate.dao.TVocationDateDao;
import com.css.app.qxjgl.vocationdate.entity.TVocationDate;
import com.css.app.qxjgl.vocationdate.service.TVocationDateService;



@Service("tVocationDateService")
public class TVocationDateServiceImpl implements TVocationDateService {
	@Autowired
	private TVocationDateDao tVocationDateDao;
	
	@Override
	public TVocationDate queryObject(String id){
		return tVocationDateDao.queryObject(id);
	}
	
	@Override
	public List<TVocationDate> queryList(Map<String, Object> map){
		return tVocationDateDao.queryList(map);
	}
	
	@Override
	public void save(TVocationDate tVocationDate){
		tVocationDateDao.save(tVocationDate);
	}
	
	@Override
	public void update(TVocationDate tVocationDate){
		tVocationDateDao.update(tVocationDate);
	}
	
	@Override
	public void delete(String id){
		tVocationDateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tVocationDateDao.deleteBatch(ids);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tVocationDateDao.queryTotal(map);
	}

	@Override
	public TVocationDate queryByUserId(String userId) {
		// TODO Auto-generated method stub
		return tVocationDateDao.queryByUserId(userId);
	}

	
}
