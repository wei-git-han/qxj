package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.QxjLeaveCancel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.css.base.dao.BaseDao;

/**
 * 销假天数配置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-03 10:26:36
 */
@Mapper
public interface QxjLeaveCancelDao extends BaseDao<QxjLeaveCancel> {

	QxjLeaveCancel findByDept(Map<String, String> map);

	List<QxjLeaveCancel> findAll();
	
}
