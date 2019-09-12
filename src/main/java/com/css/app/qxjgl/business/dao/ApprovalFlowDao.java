package com.css.app.qxjgl.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.base.dao.BaseDao;

/**
 * 审批流程表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-03-06 14:17:48
 */
@Mapper
public interface ApprovalFlowDao extends BaseDao<ApprovalFlow> {

	void deleteByLeaveId(String id);
	public void updateIsViewToOne(ApprovalFlow qxjApprovalFlow);
	List<ApprovalFlow> queryByLeaveId(Map<String, Object> map);
	void updateIsView(ApprovalFlow qxjApprovalFlow);
	void updateForWithdraw(ApprovalFlow item);
	void deleteForWithdraw(ApprovalFlow item);
	void updateById(ApprovalFlow qxjApprovalFlow);
	//获取某申请的最新一条流转记录
	ApprovalFlow queryLatestFlowRecord(String leaveId);
	
    List<ApprovalFlow> queryLatestTwoFlowRecord(String leaveId);
    
	List<ApprovalFlow> queryQxjApprovalFlow(String leaveId);

    ApprovalFlow queryLeaveIdAndApprovalIdLastNew(String id, String approvalId);
}
