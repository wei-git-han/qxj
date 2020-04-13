package com.css.addbase.apporgan.dao;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.css.base.dao.BaseDao;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
@Mapper
public interface BaseAppOrganDao extends BaseDao<BaseAppOrgan> {
	
	int queryTotal(Map<String,Object> map);
	
	/**
	 * 根据父Id获取部门信息
	 * @author gengds
	 */
	@Select("select * from BASE_APP_ORGAN where ISDELETE = 0 and PARENT_ID = #{parentId} order by SORT")
	List<BaseAppOrgan> findByParentId(String parentId);
	
	/**
	 * 根据ID获取该部门及其全部子部门信息
	 * @author gengds
	 */
	@Select("select * from BASE_APP_ORGAN where ISDELETE = 0 and TREE_PATH like '%'||#{deptId}||'%' ")
	List<BaseAppOrgan> findAllDeptById(String deptId);
	
	/**
	 * 根据ID获取该部门及其全部子部门信息
	 * @author gengds
	 */
	@Select("select * from BASE_APP_ORGAN start with ID= #{id} and ISDELETE=0 connect by prior ID = PARENT_ID ORDER BY ID")
	List<BaseAppOrgan> findAllByparentId(String id);
	
	/**
	 * 根据ID获取该部门及其全部子部门信息(有序
	 */
	@Select("select * from BASE_APP_ORGAN start with ID= #{id} and ISDELETE=0 connect by prior ID = PARENT_ID ORDER BY SORT ASC")
	List<BaseAppOrgan> findAllByparentIdWithOrder(String id);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	@Delete("delete from BASE_APP_ORGAN")
	void clearOrgan();
	
	List<BaseAppOrgan> queryListByIds(String[] ids);
	
}
