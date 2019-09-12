package com.css.app.qxjgl.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.addbase.apporgan.dao.BaseAppOrganDao;
import com.css.addbase.apporgan.dao.BaseAppUserDao;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.app.qxjgl.business.dao.ApprovalFlowDao;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.service.ApprovalFlowService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.UUIDUtils;


@Service("approvalFlowService")
public class ApprovalFlowServiceImpl implements ApprovalFlowService {
	@Resource
	private ApprovalFlowDao approvalFlowDao;
	@Autowired
	private BaseAppUserDao baseAppUserDao;
	@Autowired
	private BaseAppOrganDao baseAppOrganDao;

	@Override
	public ApprovalFlow queryObject(String id){
		return approvalFlowDao.queryObject(id);
	}
	
	@Override
	public List<ApprovalFlow> queryList(Map<String, Object> map){
		return approvalFlowDao.queryList(map);
	}
	
	@Override
	public void save(ApprovalFlow item){
//		Date date=new Date();
//		String userId=CurrentUser.getUserId();
//		String userName=CurrentUser.getSSOUser().getFullname();
//		String id=StringUtils.isNotBlank(item.getId())?item.getId():UUIDUtils.random();
//		item.setId(id);
//		item.setCreateDate(date);
//		item.setCreatorId(userId);
//		item.setCreator(userName);
//		item.setModifyDate(date);
//		item.setModificatorId(userId);
//		item.setModificator(userName);
		approvalFlowDao.save(item);
	}
	
	@Override
	public void update(ApprovalFlow item){
		Date date=new Date();
		String userId=CurrentUser.getUserId();
		String userName=CurrentUser.getSSOUser().getFullname();
		item.setModifyDate(date);
		item.setModificatorId(userId);
		item.setModificator(userName);
		approvalFlowDao.update(item);
	}
	
	@Override
	public void delete(String id){
		approvalFlowDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		approvalFlowDao.deleteBatch(ids);
	}

	@Override
	public String[] getLeaveIds(String approvalId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("approvalId", approvalId);
		List<ApprovalFlow> items=approvalFlowDao.queryList(map);
		List<String> leaveIds=new ArrayList<String>();
		if(items!=null&&items.size()>0){
			for(ApprovalFlow item:items){
				if(!leaveIds.contains(item.getLeaveId())){
					leaveIds.add(item.getLeaveId());
				}
			}
		}
		return leaveIds!=null&&leaveIds.size()>0?leaveIds.toArray(new String[]{}):new String[]{""};
	}

	@Override
	public List<ApprovalFlow> queryListAll(Map<String, Object> map) {
		return approvalFlowDao.queryList(map);
	}

	@Override
	public ApprovalFlow queryObjectByLeaveId(String leaveId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaveId", leaveId);
		map.put("curNum", 1);
		List<ApprovalFlow> items = approvalFlowDao.queryList(map);
		if (items != null && items.size() > 0) {
			return items.get(0);
		}
		return new ApprovalFlow();
	}

	@Override
	public void deleteByLeaveId(String id) {
		approvalFlowDao.deleteByLeaveId(id);
	}

	@Override
	public void updateIsViewToOne(ApprovalFlow qxjApprovalFlow) {
		approvalFlowDao.updateIsViewToOne(qxjApprovalFlow);
		
	}

	@Override
	public List<ApprovalFlow> queryByLeaveId(Map<String, Object> map) {
		return 	approvalFlowDao.queryByLeaveId( map);

	}

	@Override
	public void updateIsView(ApprovalFlow qxjApprovalFlow) {
		approvalFlowDao.updateIsView( qxjApprovalFlow);
		
	}

	@Override
	public void updateForWithdraw(ApprovalFlow item) {
		approvalFlowDao.updateForWithdraw(item);
		
	}

	@Override
	public void deleteForWithdraw(ApprovalFlow item) {
		approvalFlowDao.deleteForWithdraw(item);
		
	}

	@Override
	public ApprovalFlow queryLatestFlowRecord(String leaveId) {
		return approvalFlowDao.queryLatestFlowRecord(leaveId);
	}

	@Override
	public List<ApprovalFlow> queryLatestTwoFlowRecord(String id) {
		return approvalFlowDao.queryLatestTwoFlowRecord(id);
	}

	@Override
	public List<ApprovalFlow> queryQxjApprovalFlow(String id) {
		return approvalFlowDao.queryQxjApprovalFlow(id);
	}

	@Override
	public void updateById(ApprovalFlow qxjApprovalFlow) {
		approvalFlowDao.updateById(qxjApprovalFlow);
	}

	@Override
	public ApprovalFlow queryLeaveIdAndApprovalIdLastNew(String id, String approvalId) {
		return approvalFlowDao.queryLeaveIdAndApprovalIdLastNew(id, approvalId);
	}

	@Override
	public void saveFlowValue(String creatorId, String creator,String approvalId, String approvalName, String leaveId, String flowType,Integer isView) {
		 ApprovalFlow  flow = new ApprovalFlow();
	        flow.setId(UUIDUtils.random());	       
	        flow.setCreatorId(creatorId);
	        flow.setCreator(creator);
	        flow.setModificator(creator);
	        flow.setApprovalId(approvalId);
	        flow.setApprovalName(approvalName);
	        flow.setModificatorId(creatorId);
	        flow.setModifyDate(new Date());
	        flow.setLeaveId(leaveId);
	        flow.setCreateDate(new Date());
	        if(isView !=null) {
	        	flow.setIsView(isView);
	        	if(1==isView) {
	        		flow.setIsViewTime(new Date());
				}
			}
	        flow.setFlowType(flowType);
	        if (StringUtils.isNotBlank(creatorId)) {
	        	BaseAppUser user = baseAppUserDao.queryObject(creatorId);
	        	if(user != null) {
	        		flow.setSenderDepartId(user.getOrganid());
	        		BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
	        		if(org != null) {
	        			flow.setSenderDepartName(org.getName());
	        		}
	        	}
	        }
	        if (StringUtils.isNotBlank(approvalId)) {
	        	BaseAppUser user = baseAppUserDao.queryObject(approvalId);
	        	if(user != null) {
	        		flow.setReceiverDepartId(user.getOrganid());
	        		BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
	        		if(org != null) {
	        			flow.setReceiverDepartName(org.getName());
	        		}
	        	}
	        }
	        approvalFlowDao.save(flow);
	}

	@Override
	public void updateLatestFlow(String id, String approvalId, String approvalName, Integer isview) {
		ApprovalFlow record = approvalFlowDao.queryLatestFlowRecord(id);
		if(record !=null) {
			if(isview !=null) {
				record.setIsView(isview);
				if(1==isview) {
					record.setIsViewTime(new Date());
				}
			}
			if(StringUtils.isNotBlank(approvalId)) {
				record.setApprovalId(approvalId);
				record.setApprovalName(approvalName);
			}
			approvalFlowDao.updateById(record);
		}		
	}
}
