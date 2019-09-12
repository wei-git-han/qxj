package com.css.app.qxjgl.dictionary.dao;

import com.css.app.qxjgl.dictionary.entity.DicVocationSort;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;

/**
 * 休假类别字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
@Mapper
public interface DicVocationSortDao extends BaseDao<DicVocationSort> {

	int queryTotal();
	
}
