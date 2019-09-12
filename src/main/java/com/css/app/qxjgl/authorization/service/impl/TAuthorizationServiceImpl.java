package com.css.app.qxjgl.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.authorization.dao.TAuthorizationDao;
import com.css.app.qxjgl.authorization.entity.TAuthorization;
import com.css.app.qxjgl.authorization.service.TAuthorizationService;

@Service("tAuthorizationService")
public class TAuthorizationServiceImpl implements TAuthorizationService {
	@Autowired
	private TAuthorizationDao tAuthorizationDao;
	
	@Override
	public TAuthorization queryObject(String id){
		return tAuthorizationDao.queryObject(id);
	}
	
	@Override
	public List<TAuthorization> queryList(Map<String, Object> map){
		return tAuthorizationDao.queryList(map);
	}
	
	@Override
	public void save(TAuthorization tAuthorization){
		tAuthorizationDao.save(tAuthorization);
	}
	
	@Override
	public void update(TAuthorization tAuthorization){
		tAuthorizationDao.update(tAuthorization);
	}
	
	@Override
	public void delete(String id){
		tAuthorizationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tAuthorizationDao.deleteBatch(ids);
	}

	@Override
	public TAuthorization queryPerson(String personId) {
		return tAuthorizationDao.queryPerson(personId);
	}
}
