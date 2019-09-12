package com.css.app.qxjgl.authorization.dao;

import com.css.app.qxjgl.authorization.entity.TAuthorization;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 人员授权表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:28:19
 */
@Mapper
public interface TAuthorizationDao extends BaseDao<TAuthorization> {

	TAuthorization queryPerson(String personId);
}
