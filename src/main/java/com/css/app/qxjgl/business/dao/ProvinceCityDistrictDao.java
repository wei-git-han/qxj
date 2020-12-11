package com.css.app.qxjgl.business.dao;


import com.css.app.qxjgl.business.entity.ProvinceCityDistrict;
import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 省市县数据表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-12-11 15:18:35
 */
@Mapper
public interface ProvinceCityDistrictDao extends BaseDao<ProvinceCityDistrict> {

    @Select("select * from PROVINCE_CITY_DISTRICT where pid = #{0}")
	List<ProvinceCityDistrict> findByPid(String pid);

    @Select("select * from PROVINCE_CITY_DISTRICT where pid in ('11','12','13','14','15','21','22','23','31','32','33','34','35','36','37','41','42','43','44','45','46','50','51','52','53','54','61','62','63','64','65','71','81','91')")
	List<ProvinceCityDistrict> findAll(String pid);
}
