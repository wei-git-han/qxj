package com.css.app.qxjgl.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.dictionary.dao.DicAuthorizationDao;
import com.css.app.qxjgl.dictionary.entity.DicAuthorization;
import com.css.app.qxjgl.dictionary.service.DicAuthorizationService;



@Service("dicAuthorizationService")
public class DicAuthorizationServiceImpl implements DicAuthorizationService {
	@Autowired
	private DicAuthorizationDao dicAuthorizationDao;
	
	@Override
	public DicAuthorization queryObject(String id){
		return dicAuthorizationDao.queryObject(id);
	}
	
	@Override
	public List<DicAuthorization> queryList(Map<String, Object> map){
		return dicAuthorizationDao.queryList(map);
	}
	
	@Override
	public void save(DicAuthorization dicAuthorization){
		dicAuthorizationDao.save(dicAuthorization);
	}
	
	@Override
	public void update(DicAuthorization dicAuthorization){
		dicAuthorizationDao.update(dicAuthorization);
	}
	
	@Override
	public void delete(String id){
		dicAuthorizationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dicAuthorizationDao.deleteBatch(ids);
	}
	
}
