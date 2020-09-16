package com.css.addbase.apporgan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.app.qxjgl.business.dto.QxjUserAndOrganDays;
/**
 * 人员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppUserService {
	
	int queryTotal(Map<String,Object> map);
	
	BaseAppUser queryObject(String id);
	
	List<BaseAppUser> queryList(Map<String, Object> map);
	
	void save(BaseAppUser baseAppUser);
	
	void update(BaseAppUser baseAppUser);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	/**
	 * 根据用户ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	List<BaseAppUser> findByUserId(String userId);
	
	/**
	 * 根据用户ID删除人员信息
	 * @author gengds
	 */
	List<BaseAppUser> deleteByUserId(String userId);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
	List<BaseAppUser> findByDepartmentId(String organid);
	
	/**
	 * 根据部门ID获取人员信息
	 * @author gengds
	 * @date 2017年6月17日
	 */
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
	 * 根据人员ID获取局级部门ID
	 * @param userIds
	 * @return
	 */
	String getBareauByUserId(String userId);
	
	/**
	 * 判断某个部门下是否存在人员
	 * @param deptId
	 * @return
	 */
	boolean queryCountUser(String deptId);
	/**
	 * 清空组织人员
	 */
	void clearUser();

	/**
	 * 根据组织id获取该组织人员总数（只限最小组织机构）
	 * @param orgId
	 * @return
	 */
	int queryNumByOrgId(String orgId);
	
	List<BaseAppUser> queryListBySet(Map<String, Object> map);

	int queryUserNum(String id,Date datetime);

	int queryNum(String id, Integer i,String datetime,String txry, String tableId);

	List<Map> queryForUser(String id, String datetime, String txry, String tableId);
	/**
	 * 根据用户ID查询该用户信息
	 * @param userId
	 * @return
	 */
	BaseAppUser queryByUserId(String userId);
	/**
	 *
	 * @param userId 用户ID
	 * @return 用户部门ID和部门名
	 */
	String queryUserDepartIdAndName(String userId);
	
	List<QxjUserAndOrganDays> queryListAndOrgan(Map<String, Object> map);
}
