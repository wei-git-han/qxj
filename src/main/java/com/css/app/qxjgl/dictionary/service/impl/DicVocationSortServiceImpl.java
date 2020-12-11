package com.css.app.qxjgl.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.dictionary.dao.DicVocationSortDao;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;



@Service("dicVocationSortService")
public class DicVocationSortServiceImpl implements DicVocationSortService {
	@Autowired
	private DicVocationSortDao dicVocationSortDao;
	
	@Override
	public DicVocationSort queryObject(String id){
		return dicVocationSortDao.queryObject(id);
	}
	
	@Override
	public List<DicVocationSort> queryList(Map<String, Object> map){
		return dicVocationSortDao.queryList(map);
	}
	
	@Override
	public void save(DicVocationSort dicVocationSort){
		dicVocationSortDao.save(dicVocationSort);
	}
	
	@Override
	public void update(DicVocationSort dicVocationSort){
		dicVocationSortDao.update(dicVocationSort);
	}
	
	@Override
	public void delete(String id){
		dicVocationSortDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dicVocationSortDao.deleteBatch(ids);
	}

	@Override
	public int queryTotal() {
		// TODO Auto-generated method stub
		return dicVocationSortDao.queryTotal();
	}

	@Override
	public List<String> queryByType(String type){
		return dicVocationSortDao.queryByType(type);
	}
	
}
