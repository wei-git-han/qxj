package com.css.app.qxjgl.business.service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.entity.ApprovalFlow;

/**
 * 审批流程表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-03-06 14:17:48
 */
public interface ApprovalFlowService {
	
	ApprovalFlow queryObject(String id);
	
	List<ApprovalFlow> queryList(Map<String, Object> map);
	
	void save(ApprovalFlow qxjApprovalFlow);
	
	void update(ApprovalFlow qxjApprovalFlow);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	String[] getLeaveIds(String approvalId);

	List<ApprovalFlow> queryListAll(Map<String, Object> map);

	ApprovalFlow queryObjectByLeaveId(String approvalId);

	void deleteByLeaveId(String id);

	void updateIsViewToOne(ApprovalFlow qxjApprovalFlow);

	List<ApprovalFlow> queryByLeaveId(Map<String, Object> map);

	void updateIsView(ApprovalFlow qxjApprovalFlow);

	void updateForWithdraw(ApprovalFlow item);

	void deleteForWithdraw(ApprovalFlow item);
	//获取某申请的最新一条流转记录
	ApprovalFlow queryLatestFlowRecord(String leaveId);

    List<ApprovalFlow> queryLatestTwoFlowRecord(String id);

	List<ApprovalFlow> queryQxjApprovalFlow(String id);

	void updateById(ApprovalFlow qxjApprovalFlow);

	ApprovalFlow queryLeaveIdAndApprovalIdLastNew(String id, String approvalId);
	void saveFlowValue(String creatorId,String creator,String approvalId,String approvalName,String leaveId,String flowType,Integer isView);
	
	//更新最后一条流转记录
	void updateLatestFlow(String id,String approvalId,String approvalName,Integer isview);

	void updateFlag(String id);
}
