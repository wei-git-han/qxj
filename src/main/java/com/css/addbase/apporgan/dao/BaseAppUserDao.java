package com.css.addbase.apporgan.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.app.qxjgl.business.dto.QxjUserAndOrganDays;
import com.css.base.dao.BaseDao;

/**
 * 人员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
@Mapper
public interface BaseAppUserDao extends BaseDao<BaseAppUser> {
	
	int queryTotal(Map<String,Object> map);
	
	/**
	 * 根据用户ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	@Select("select * from BASE_APP_USER where ISDELETE=0 and USER_ID = #{userId}")
	List<BaseAppUser> findByUserId(String userId);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	@Select("select * from BASE_APP_USER where ISDELETE=0 and ORGANID = #{organid} order by SORT")
	List<BaseAppUser> findByDepartmentId(String organid);
	
	/**
	 * 根据用户ID删除人员信息
	 * @author gengds
	 */
	@Delete("delete from BASE_APP_USER where USER_ID = #{userId}")
	List<BaseAppUser> deleteByUserId(String userId);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	@Select("select a.*,b.name as organid from BASE_APP_USER a,BASE_APP_ORGAN b where a.ORGANID = #{organid} and b.ID=a.ORGANID and b.ISDELETE=0 and a.ISDELETE=0 order by a.SORT")
	List<BaseAppUser> findByOrganid(String organid);
	
	/**
	 * 根据用户ID获取人员信息
	 * @param userIds
	 * @return
	 */
	List<BaseAppUser> queryObjectByUserIds(String[] userIds);
	
	/**
	 * 根据部门ID获取人员信息
	 * @param userIds
	 * @return
	 */
	List<BaseAppUser> queryObjectByDeptIds(String[] deptIds);
	
	/**
	 * 获取某个部门下的总人数
	 * @param deptId
	 * @return
	 */
	@Select("select count(uuid) from TASK_USER where user_dept_id in (select uuid from TASK_DEPT start with uuid= #{deptId} connect by prior uuid = dept_parent)")
	int queryCountUser(String deptId);
	
	/**
	 * 清空组织人员
	 */
	@Delete("delete from BASE_APP_USER")
	void clearUser();

	List<BaseAppUser> queryListBySet(Map<String, Object> map);	
	

	List<Map> queryUsers(@Param("id")String id,@Param("datetime")String datetime,@Param("txry")String txry,@Param("tableId")String tableId);

	int queryUserNum(@Param("id")String id,@Param("datetime")Date datetime);

	int queryNum(@Param("id")String id, @Param("grzt")Integer i,@Param("datetime")String datetime,@Param("txry")String txry,@Param("tableId")String tableId);

	@Select("select * from BASE_APP_USER where ISDELETE=0 and USER_ID = #{userId}")
	BaseAppUser queryByUserId(String userId);

//	@Select("select count(*) from BASE_APP_USER where isdelete='0' and organid=#{organ_id}")

	@Select("select count(*) from BASE_APP_USER where isdelete='0' and organid = #{orgId}")
	int queryNumByOrgId(String orgId);
	String queryUserDepartIdAndName(String userId);
	
	List<QxjUserAndOrganDays> queryListAndOrgan(Map<String, Object> map);
	
	int updateSFYXByOrganId(Map<String, Object> map);
	
	int queryTotalXLGL(Map<String,Object> map);
	
	
}
