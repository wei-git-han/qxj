package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.QxjModleDept;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 请销假模板部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-06-22 14:47:08
 */
@Mapper
public interface QxjModleDeptDao extends BaseDao<QxjModleDept> {

	QxjModleDept findByDept(String deptId);
	
}
