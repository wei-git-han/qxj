package com.css.app.qxjgl.business.service.impl;

import com.css.app.qxjgl.business.dao.ProvinceCityDistrictDao;
import com.css.app.qxjgl.business.entity.ProvinceCityDistrict;
import com.css.app.qxjgl.business.service.ProvinceCityDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("provinceCityDistrictService")
public class ProvinceCityDistrictServiceImpl implements ProvinceCityDistrictService {
	@Autowired
	private ProvinceCityDistrictDao provinceCityDistrictDao;
	
	@Override
	public ProvinceCityDistrict queryObject(String id){
		return provinceCityDistrictDao.queryObject(id);
	}
	
	@Override
	public List<ProvinceCityDistrict> queryList(Map<String, Object> map){
		return provinceCityDistrictDao.queryList(map);
	}
	
	@Override
	public void save(ProvinceCityDistrict provinceCityDistrict){
		provinceCityDistrictDao.save(provinceCityDistrict);
	}
	
	@Override
	public void update(ProvinceCityDistrict provinceCityDistrict){
		provinceCityDistrictDao.update(provinceCityDistrict);
	}
	
	@Override
	public void delete(String id){
		provinceCityDistrictDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		provinceCityDistrictDao.deleteBatch(ids);
	}

	@Override
	public List<ProvinceCityDistrict> findByPid(String pid){
		return  provinceCityDistrictDao.findByPid(pid);
	}

	@Override
	public List<ProvinceCityDistrict> findAll(String pid){
		return  provinceCityDistrictDao.findAll(pid);
	}
	
}
