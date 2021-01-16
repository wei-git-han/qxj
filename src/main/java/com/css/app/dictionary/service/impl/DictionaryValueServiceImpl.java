package com.css.app.dictionary.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.app.dictionary.dao.DictionaryValueDao;
import com.css.app.dictionary.entity.DictionaryValue;
import com.css.app.dictionary.service.DictionaryValueService;



@Service("dictionaryValueService")
public class DictionaryValueServiceImpl implements DictionaryValueService {
	@Autowired
	private DictionaryValueDao dictionaryValueDao;
	
	@Override
	public DictionaryValue queryObject(String id){
		return dictionaryValueDao.queryObject(id);
	}
	
	@Override
	public List<DictionaryValue> queryList(Map<String, Object> map){
		return dictionaryValueDao.queryList(map);
	}
	
	@Override
	public List<DictionaryValue> queryListByTypeId(Map<String, Object> map){
		return dictionaryValueDao.queryListByTypeId(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return dictionaryValueDao.queryTotal(map);
	}
	
	@Override
	public int queryByValue(Map<String, Object> map) {
		return dictionaryValueDao.queryByValue(map);
	}
	
	@Override
	public int queryMaxSort(String typeId) {
		return dictionaryValueDao.queryMaxSort(typeId);
	}
	
	@Override
	public void save(DictionaryValue dictionaryValue){
		dictionaryValueDao.save(dictionaryValue);
	}
	
	@Override
	public void update(DictionaryValue dictionaryValue){
		dictionaryValueDao.update(dictionaryValue);
	}
	
	@Override
	public void delete(String id){
		dictionaryValueDao.delete(id);
	}
	
	@Override
	public void deleteByTypeId(String typeId){
		dictionaryValueDao.deleteByTypeId(typeId);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dictionaryValueDao.deleteBatch(ids);
	}

	@Override
	public List<DictionaryValue> queryListByTypeIdList() {
		return dictionaryValueDao.queryListByTypeIdList();
	}
	
}
