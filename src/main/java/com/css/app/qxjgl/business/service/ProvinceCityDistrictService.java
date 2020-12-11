package com.css.app.qxjgl.business.service;


import com.css.app.qxjgl.business.entity.ProvinceCityDistrict;

import java.util.List;
import java.util.Map;

/**
 * 省市县数据表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-12-11 15:18:35
 */
public interface ProvinceCityDistrictService {
	
	ProvinceCityDistrict queryObject(String id);
	
	List<ProvinceCityDistrict> queryList(Map<String, Object> map);
	
	void save(ProvinceCityDistrict provinceCityDistrict);
	
	void update(ProvinceCityDistrict provinceCityDistrict);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<ProvinceCityDistrict> findByPid(String pid);

	List<ProvinceCityDistrict> findAll(String pid);
}
