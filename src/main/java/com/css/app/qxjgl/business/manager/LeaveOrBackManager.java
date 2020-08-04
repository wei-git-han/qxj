package com.css.app.qxjgl.business.manager;

import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.OpinionService;
import com.css.app.qxjgl.business.service.ApprovalFlowService;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.base.utils.StringUtils;
import com.css.base.utils.UUIDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请销假统一事务控制
 *
 * @author 中软信息系统工程有限公司
 * @date 2019-08-16 15:59:11
 */
@Component
public class LeaveOrBackManager {
    @Autowired
    private LeaveorbackService leaveorbackService;
    @Autowired
    private ApprovalFlowService approvalFlowService;
    @Autowired
	private OpinionService opinionService;


    @Transactional(rollbackFor = Exception.class)
    public void unifiedDealData(Opinion opinion,ApprovalFlow qxjApprovalFlow, Leaveorback tLeaveorback) {
        if(opinion !=null) {
        	if(StringUtils.isNotBlank(opinion.getId())) {
        		opinionService.update(opinion);
        	}else {
        		opinion.setId(UUIDUtils.random());
        		opinionService.save(opinion);
        	}
        }
    	if (qxjApprovalFlow != null) {
    		approvalFlowService.save(qxjApprovalFlow);
        }
    	leaveorbackService.update(tLeaveorback);
    }
	@Transactional(rollbackFor = Exception.class)
	public void unifiedDealData2(Opinion opinion,ApprovalFlow qxjApprovalFlow, Leaveorback tLeaveorback) {
		if(opinion !=null) {
			if(StringUtils.isNotBlank(opinion.getId())) {
				//opinionService.update(opinion);
			}else {
				opinion.setId(UUIDUtils.random());
				//opinionService.save(opinion);
			}
		}
		if (qxjApprovalFlow != null) {
			approvalFlowService.save(qxjApprovalFlow);
		}
		leaveorbackService.update(tLeaveorback);
	}

    @Transactional(rollbackFor = Exception.class)
    public void unifiedDealData1(ApprovalFlow qxjApprovalFlow, Leaveorback tLeaveorback,ApprovalFlow qxjApprovalFlow1,String deleteOpinion,Opinion latestOpinion) {
    	if(latestOpinion!= null) {
    		if(StringUtils.equals("1", deleteOpinion)) {
    			opinionService.delete(latestOpinion.getId());
    			latestOpinion=opinionService.queryLatestOpinion(tLeaveorback.getId());
    			latestOpinion.setTempType("0");
    		}
    		opinionService.update(latestOpinion);  	
    	}
    	approvalFlowService.delete(qxjApprovalFlow.getId());
        leaveorbackService.update(tLeaveorback);
        if (qxjApprovalFlow1 != null) {
        	approvalFlowService.updateById(qxjApprovalFlow1);
        }
    }
}
