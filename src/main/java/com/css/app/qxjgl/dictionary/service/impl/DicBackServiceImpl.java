package com.css.app.qxjgl.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.dictionary.dao.DicBackDao;
import com.css.app.qxjgl.dictionary.entity.DicBack;
import com.css.app.qxjgl.dictionary.service.DicBackService;



@Service("dicBackService")
public class DicBackServiceImpl implements DicBackService {
	@Autowired
	private DicBackDao dicBackDao;
	
	@Override
	public DicBack queryObject(String id){
		return dicBackDao.queryObject(id);
	}
	
	@Override
	public List<DicBack> queryList(Map<String, Object> map){
		return dicBackDao.queryList(map);
	}
	
	@Override
	public void save(DicBack dicBack){
		dicBackDao.save(dicBack);
	}
	
	@Override
	public void update(DicBack dicBack){
		dicBackDao.update(dicBack);
	}
	
	@Override
	public void delete(String id){
		dicBackDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dicBackDao.deleteBatch(ids);
	}
	
}
