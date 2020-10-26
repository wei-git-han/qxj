package com.css.app.qxjgl.business.service;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.entity.Leaveorback;

import java.util.List;
import java.util.Map;

/**
 * 请销假表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:26:59
 */
public interface LeaveorbackService {
	
	Leaveorback queryObject(String id);
	
	List<Leaveorback> queryQjCompletedUser(Map<String, Object> users);
	
	List<Leaveorback> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Leaveorback tLeaveorback);
	
	void update(Leaveorback tLeaveorback);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	int queryQjCompletedUserByOrgid(Map<String, Object> params);

	List<Leaveorback> queryQjCompletedUserByOrgid2(Map<String, Object> users);

	List<BaseAppOrgan> queryBelongOrg(String deptid);

	List<Leaveorback> queryQXJList(Map<String, Object> paraterLeaderMap);
	
	List<Leaveorback> queryNewList(Map<String, Object> map);

	List<Leaveorback> queryNewList1(Map<String, Object> map);

	int queryRealRestDays(String userId);

	List<Leaveorback> queryCurrYearRestDays(Map<String, Object> map );
	
	Leaveorback getQXJDefaultParam(String userId);
	
	List<Leaveorback> queryDeducttonDays(Map<String, Object> map );

	void updateStatus(String id);
	
	List<String> getIsJuGuanLi(String userId);

	void updateWeekendHolidayNum(Leaveorback leaveorback);

	String getBackStatusId(String id);

	String getStatus(String id);

	List<Leaveorback> selByBackAndTenday();

	void updateBackStatusId(Leaveorback leaveorbackk);

	int selcount(Map<String, Object> paraterLeaderMap);

	List<Leaveorback> queryQjUserIds(Map<String, Object> map);

	List<Leaveorback> getQjNum(Map<String, Object> map);

	ApprovalFlow queryIsView(String id, String userId);

	void deleteBubao(String id,String userId);
	/**
	 *  实际休假人数全局
	 * @return 实际休假人数
	 */
    int getHaveHolidayNumberXLGL(Map<String, Object> map);
	/**
	 *  当天请假人数全局
	 * @param map 
	 * @return 请假人数全局
	 */
    int getLeaveNumberXLGL(Map<String, Object> map);
    /**
     * 训练管理app-日常管理-人员管理下鼠标悬停显示功能
     * */
    List<Leaveorback> getWhetherRestByUserid(String userId);
}
