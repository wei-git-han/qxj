package com.css.app.qxjgl.dictionary.dao;

import com.css.app.qxjgl.dictionary.entity.DicVocationSort;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

	@Select("select VACATION_SORT_ID from QXJ_DIC_VOCATION_SORT where type = #{0} and ORG_ID = #{1}")
	List<String> queryByType(String type,String orgId);

	@Select("select * from QXJ_DIC_VOCATION_SORT where VACATION_SORT_ID like '%'||#{0}||'%' and ORG_ID = #{1}")
	DicVocationSort queryByvacationSortId(String vacationSortId,String orgId);
	
}
