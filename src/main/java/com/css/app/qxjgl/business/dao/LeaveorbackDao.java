package com.css.app.qxjgl.business.dao;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.app.qxjgl.business.dto.LeaveorbackPlatDto;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.entity.Leaveorback;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 请销假表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:26:59
 */
@Mapper
public interface LeaveorbackDao extends BaseDao<Leaveorback> {

	int queryTotal(Map<String, Object> map);
	
	List<Leaveorback> queryNewList(Map<String, Object> map);

	List<Leaveorback> queryNewList1(Map<String, Object> map);

	List<Leaveorback> queryQjCompletedUser(Map<String, Object> users);

	int queryQjCompletedUserByOrgid(Map<String, Object> params);

	List<Leaveorback> queryQjCompletedUserByOrgid2(Map<String, Object> users);
	List<Leaveorback> queryQJList(Map<String, Object> map);

	List<BaseAppOrgan> queryBelongOrg(String deptid);

	List<Leaveorback> queryQXJList(Map<String, Object> paraterLeaderMap);

	/**
	 *  实际休假天数
	 * @param userId  去请假人ID
	 * @return 实际休假天数
	 */
    int queryRealRestDays(String userId);

	List<Leaveorback> queryCurrYearRestDays(Map<String, Object> map );
	
	Leaveorback getQXJDefaultParam(String userId);
	
	List<Leaveorback> queryDeducttonDays(Map<String, Object> map );

	@Update("update QXJ_LEAVEORBACK set preStatus = status where id =#{0}")
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

	@Select("select * from QXJ_APPROVAL_FLOW where leave_id = #{0} and approval_id = #{1} and isView = '0'")
	ApprovalFlow queryIsView(String id, String userId);

	@Select("delete from QXJ_APPROVAL_FLOW where leave_id = #{0} and approval_id = #{1} and isView = '0'")
	void deleteBubao(String id,String userId);
	
	
	/**
	 *  实际休假人数全局
	 * @param map 
	 * @return 实际休假人数
	 */
    int getHaveHolidayNumberXLGL(Map<String, Object> map);
	/**
	 *  当天请假人数全局
	 * @return 请假人数全局
	 */
    int getLeaveNumberXLGL(Map<String, Object> map);
    /**
     * 训练管理app-日常管理-人员管理下鼠标悬停显示功能
     * */
    List<Leaveorback> getWhetherRestByUserid(String userId);
    /**
     * 训练管理-人员管理-地图人数
     * */
    List<LeaveorbackPlatDto> getPlatNumber(Map<String, Object> map);
    
}
