package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.ApplyUser;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 请销假申请人表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-26 16:11:48
 */
@Mapper
public interface ApplyUserDao extends BaseDao<ApplyUser> {
	void deleteByLeaveId(String id);

	void deleteBatchByLeaveId(String[] ids);
}
