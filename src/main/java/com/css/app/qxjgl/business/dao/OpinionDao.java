package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.Opinion;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 请销假意见表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-28 10:36:48
 */
@Mapper
public interface OpinionDao extends BaseDao<Opinion> {
	
	Opinion queryLatestOpinion(String leaveId);
	
}
