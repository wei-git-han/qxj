package com.css.app.qxjgl.vocationdate.dao;

import com.css.app.qxjgl.vocationdate.entity.TVocationDate;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 定义休假天数表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:38:15
 */
@Mapper
public interface TVocationDateDao extends BaseDao<TVocationDate> {

	int queryTotal(Map<String, Object> map);

	TVocationDate queryByUserId(String userId);

}
