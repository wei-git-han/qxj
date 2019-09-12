package com.css.app.dictionary.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.app.dictionary.dao.DictionaryTypeDao;
import com.css.app.dictionary.entity.DictionaryType;
import com.css.app.dictionary.service.DictionaryTypeService;



@Service("dictionaryTypeService")
public class DictionaryTypeServiceImpl implements DictionaryTypeService {
	@Autowired
	private DictionaryTypeDao dictionaryTypeDao;
	
	@Override
	public DictionaryType queryObject(String id){
		return dictionaryTypeDao.queryObject(id);
	}
	
	@Override
	public List<DictionaryType> queryList(Map<String, Object> map){
		return dictionaryTypeDao.queryList(map);
	}
	
	@Override
	public List<Map<String, Object>> queryDicList(Map<String, Object> map){
		return dictionaryTypeDao.queryDicList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return dictionaryTypeDao.queryTotal(map);
	}
	
	@Override
	public void save(DictionaryType dictionaryType){
		dictionaryTypeDao.save(dictionaryType);
	}
	
	@Override
	public void update(DictionaryType dictionaryType){
		dictionaryTypeDao.update(dictionaryType);
	}
	
	@Override
	public void delete(String id){
		dictionaryTypeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dictionaryTypeDao.deleteBatch(ids);
	}
	
}
