package com.css.app.qxjgl.business.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgan.util.OrgUtil;
import com.css.addbase.apporgmapped.entity.BaseAppOrgMapped;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.addbase.constant.AppConstant;
import com.css.addbase.constant.AppInterfaceConstant;
import com.css.addbase.msg.MsgTipUtil;
import com.css.addbase.msg.entity.MsgTip;
import com.css.addbase.msg.service.MsgTipService;
import com.css.app.qxjgl.business.dto.DocumentRoleSet;
import com.css.app.qxjgl.business.dto.QXJPeopleManagementDto;
import com.css.app.qxjgl.business.dto.QxjUserAndOrganDays;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.app.qxjgl.business.manager.LeaveOrBackManager;
import com.css.app.qxjgl.business.service.ApprovalFlowService;
import com.css.app.qxjgl.business.service.DicCalenderService;
import com.css.app.qxjgl.business.service.DicHolidayService;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.OpinionService;
import com.css.app.qxjgl.qxjbubao.entity.QxjFlowBubao;
import com.css.app.qxjgl.qxjbubao.service.QxjFlowBubaoService;
import com.css.app.qxjgl.util.QxjStatusDefined;
import com.css.base.utils.CrossDomainUtil;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;


/**
 * 请销假流程控制
 *
 * @author 中软信息系统工程有限公司
 * @date 2019-08-16 15:59:11
 */
@Controller
@RequestMapping("/leave/apply")
public class LeaveApplyFlowController {
    private final Logger logger = LoggerFactory.getLogger(LeaveApplyFlowController.class);
    @Autowired
    private LeaveorbackService leaveorbackService;
    @Autowired
    private ApprovalFlowService approvalFlowService;
    @Autowired
    private MsgTipService msgService;
    @Autowired
    private MsgTipUtil msgUtils;
    @Autowired
    private LeaveOrBackManager leaveOrBackManager;
    @Autowired
    private BaseAppUserService baseAppUserService;
    @Autowired
    private BaseAppOrgMappedService baseAppOrgMappedService;
    @Autowired
    private BaseAppOrganService  baseAppOrganService;
    @Autowired
    private DicHolidayService dicHolidayService;
    @Autowired
	private OpinionService opinionService;
    @Autowired
    private DicCalenderService dicCalenderService;
    @Autowired
	private CommonQueryManager commonQueryManager;
    @Value("#{'00'}")
    private String sendApproveFlag;
    @Value("#{'01'}")
    private String finishApproveFlag;
    @Value("#{'03'}")
    private String isGoBackFlag;
    @Autowired
    private QxjFlowBubaoService qxjFlowBubaoService;
    
    
    /**
	 * 获取送审的人员树
	 * @param id 主文件id
	 * @return
	 */
	@RequestMapping("/sendUserTree")
	@ResponseBody
	public Object getUserTree(String id, String leaverId) {
		Leaveorback leaveorback = leaveorbackService.queryObject(id);
		String creatorId="";
        if (leaveorback != null) {
            creatorId = leaveorback.getCreatorId();
        }
		String[] hideIds= {creatorId};
		String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<BaseAppUser> users = baseAppUserService.queryList(null);
		if (StringUtils.isNotEmpty(organId)) {
			JSONObject list=  OrgUtil.getUserTree(organs, users, organId, null, null, hideIds, null);
			return list;
		} else {
			JSONObject list=  OrgUtil.getUserTree(organs, users, null, null, null, hideIds, null);
			return list;
		}
	}
    /**
     * @description:请销假申请、请销假审批详情页按钮及意见框显示控制
     * @param id 主文件id
     * @date:2019年8月28日
     * @Version v1.0
     */
    @ResponseBody
    @RequestMapping("/detailButtonIsShow")
    public void detailButtonIsShow(String id){
        //请假人，请假列表点击进入详情后，底部按钮控制；
        JSONObject jsonObject = new JSONObject();
        try {
            String userId = CurrentUser.getUserId();
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            if (tLeaveorback != null) {
                String creatorId = tLeaveorback.getCreatorId();
                Integer status = tLeaveorback.getStatus();
                //是否销假
                String backStatusId = tLeaveorback.getBackStatusId();
                if (QxjStatusDefined.DAI_TI_JIAO.equals(status)) {
                    if(StringUtils.equals(creatorId,userId)){
                        if(StringUtils.equals(tLeaveorback.getDeleteMark(),userId)){
                            jsonObject.put("authrityShow", "1");
                        }
                        jsonObject.put("sendShow", "1");
                        jsonObject.put("editShow", "1");
                        jsonObject.put("sendBgtShow", "1");
                        jsonObject.put("opinionShow", "1");
                    }
                } else if (status > QxjStatusDefined.YI_TUI_HUI){
                    jsonObject.put("sendAgainShow", "0");
                    jsonObject.put("returnShow", "0");
                    jsonObject.put("sendBgtShow", "0");
                    jsonObject.put("finishShow", "0");
                    jsonObject.put("editShow", "0");
                    jsonObject.put("opinionShow", "0");
                    if (StringUtils.equals("0", backStatusId)) {
                        jsonObject.put("xjapply", "1");
                    }
                }else {
                    ApprovalFlow qxjApprovalFlow = approvalFlowService.queryLatestFlowRecord(id);
                    String approvalId = qxjApprovalFlow.getApprovalId();
                    String flowType = qxjApprovalFlow.getFlowType();
                    if (StringUtils.isNotBlank(flowType) && Integer.parseInt(flowType) < QxjStatusDefined.YI_TONG_GUO) {
                        if (StringUtils.equals(userId, approvalId)) {
                            if (!StringUtils.equals(tLeaveorback.getCreatorId(), approvalId)) {
                                jsonObject.put("returnShow", "1");
                            }
                            //继续送审、审批完成、呈送办公厅
                            jsonObject.put("sendAgainShow", "1");
                            jsonObject.put("sendBgtShow", "1");
                            jsonObject.put("finishShow", "1");
                            jsonObject.put("opinionShow", "1");
                            if (StringUtils.equals(userId, creatorId) && QxjStatusDefined.YI_TUI_HUI.equals(status)) {
                                jsonObject.put("editShow", "1");
                                jsonObject.put("finishShow", "0");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("调用请假详情页底部按钮控制是否显示异常：{}", StringUtils.isBlank(e.getMessage()) ? "请看后台日志："+e : e.getMessage());
        } finally {
            Response.json(jsonObject);
        }
    }
    /**
     * @description:获取当前处理人id
     * @param id 主文件id
     * @author:zhangyw
     * @date:2019年9月6日
     * @Version v1.0
     */
    @ResponseBody
    @RequestMapping("/getCurrentDealUser")
    public void getCurrentDealUser(String id){
    	String curDealUser="";
    	String isDealUser="0";
		try {
		    Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
		    if (tLeaveorback != null) {
		        Integer status = tLeaveorback.getStatus();
		        if (QxjStatusDefined.DAI_TI_JIAO.equals(status)) {
		        	curDealUser=tLeaveorback.getCreatorId();
		        } else if (QxjStatusDefined.YI_TONG_GUO.equals(status)){
		        	curDealUser="";
		        }else {
		            ApprovalFlow qxjApprovalFlow = approvalFlowService.queryLatestFlowRecord(id);
		            curDealUser = qxjApprovalFlow.getApprovalId();
		        }
		    }
		    if(StringUtils.equals(CurrentUser.getUserId(), curDealUser)) {
		    	isDealUser="1";
		    }
		} catch (Exception e) {
		    logger.info("请假详情页获取当前处理人异常：{}", StringUtils.isBlank(e.getMessage()) ? "请看后台日志："+e : e.getMessage());
		} finally {
		    Response.json("isDealUser",isDealUser);
		}
    }
        
    /**
     * @description:送审或审批完成操作
     * @param id 主文件id
     * @param approvalId 接收人id
     * @param approvalName 接收人
     * @param approveContent 一家内容
     * @param opinionType 意见类型
     * @param operateFlag 操作标识
     */
    @ResponseBody
    @RequestMapping("/sendOrFinshApprove")
    public void sendOrFinshApprove(String id, String approvalId, String approvalName,String approveContent, String opinionType,String operateFlag){
        JSONObject jsonObject = new JSONObject();
        try {
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            ApprovalFlow qxjApprovalFlow = null;
            if (tLeaveorback != null) {
                String creatorId = tLeaveorback.getCreatorId();
                //组织意见相关信息
                Opinion qxjOpinion = this.organizeQxjOpinion(tLeaveorback,approveContent, opinionType);
                //组织请假流转数据
                if (StringUtils.equals(sendApproveFlag, operateFlag)) {//送审或继续审批
                    qxjApprovalFlow = this.organizeQxjApprovalFlow(tLeaveorback, approvalId, approvalName,"1");
                } else if (StringUtils.equals(finishApproveFlag, operateFlag)) {//完成审批
                    //审批通过通知请假申请人
                    approvalId = creatorId;

                } else {
                    logger.info("流程操作传入状态：{}与约定不符", operateFlag);
                    return;
                }
                //组织更新请假状态
                this.updateTLeaveOrBack(tLeaveorback,operateFlag,approvalId);
                //统一进行库操作
                leaveOrBackManager.unifiedDealData(qxjOpinion,qxjApprovalFlow, tLeaveorback);
                jsonObject.put("result","success");
                //消息发送
                this.sendTipMsg(id, operateFlag, approvalId, creatorId);
                if(StringUtils.equals(finishApproveFlag,operateFlag)){
                    List<QxjFlowBubao> list = qxjFlowBubaoService.queryLasterUser(id);
                    if(list != null && list.size()>0){
                        buBaoSend(id,"", "0","00");
                    }
                }
            }
        } catch (Exception e) {
           logger.info("调用请销假管理送审批，当前用户ID：{}，异常：{}", CurrentUser.getUserId(), e);
           jsonObject.put("result","fail");
        } finally {
           Response.json(jsonObject);
        }
    }

    public void buBaoSend(String id,String approveContent, String opinionType,String operateFlag){
        JSONObject jsonObject = new JSONObject();
        try {
            QxjFlowBubao qxjFlowBubao = qxjFlowBubaoService.queryLastBuBaoUser(id);
            String approvalId = qxjFlowBubao.getReceiveId();
            String approvalName = qxjFlowBubao.getUserName();
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            ApprovalFlow qxjApprovalFlow = null;
            if (tLeaveorback != null) {
                String creatorId = tLeaveorback.getCreatorId();
                //组织意见相关信息
                Opinion qxjOpinion = this.organizeQxjOpinion(tLeaveorback,approveContent, opinionType);
                //组织请假流转数据
                qxjApprovalFlow = this.organizeQxjApprovalFlow(tLeaveorback, approvalId, approvalName,"1");

                //组织更新请假状态
                this.updateTLeaveOrBack(tLeaveorback,operateFlag,approvalId);
                //统一进行库操作
                leaveOrBackManager.unifiedDealData2(qxjOpinion,qxjApprovalFlow, tLeaveorback);
                jsonObject.put("result","success");
                //消息发送
                this.sendTipMsg(id, operateFlag, approvalId, creatorId);
            }
        } catch (Exception e) {
            logger.info("调用请销假管理送审批，当前用户ID：{}，异常：{}", CurrentUser.getUserId(), e);
            jsonObject.put("result","fail");
        }
    }

    /**
     *
     * @param id 主文件id
     * @param approvalIds  补报选的人Id+名字，，id,name;id,name;id,name
     * @param approveContent
     * @param opinionType
     * @param operateFlag
     */
    @ResponseBody
    @RequestMapping("/qxjBuBao")
    public void qxjBuBao(String id, String approvalIds,String approveContent, String opinionType,String operateFlag) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(approvalIds)) {
            String[] idAndNames = approvalIds.split(";");
            String[] idAndName = idAndNames[0].split(",");
            String userId = idAndName[0];
            String name = idAndName[1];
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            ApprovalFlow qxjApprovalFlow = null;
            if (tLeaveorback != null) {
                String creatorId = tLeaveorback.getCreatorId();
                //组织意见相关信息
                Opinion qxjOpinion = this.bubaoQxjOpinion(tLeaveorback, approveContent, opinionType);
                //组织请假流转数据
                if (StringUtils.equals(sendApproveFlag, operateFlag)) {//送审或继续审批
                    qxjApprovalFlow = this.organizeQxjApprovalFlow(tLeaveorback, userId, name, "2");
                } else {
                    logger.info("流程操作传入状态：{}与约定不符", operateFlag);
                    return;
                }
                //组织更新请假状态
                this.updateTLeaveOrBack(tLeaveorback, operateFlag, userId);
                //统一进行库操作
                leaveOrBackManager.unifiedDealData(qxjOpinion, qxjApprovalFlow, tLeaveorback);
                this.sendTipMsg(id, operateFlag, userId, creatorId);
            }
            for (int i = 0; i < idAndNames.length; i++) {
                String[] ids = idAndNames[i].split(",");
                String user = ids[0];
                String userName = ids[1];
                QxjFlowBubao qxjFlowBubao = new QxjFlowBubao();
                qxjFlowBubao.setId(UUIDUtils.random());
                qxjFlowBubao.setFileId(id);
                qxjFlowBubao.setCreatedTime(new Date());
                qxjFlowBubao.setReceiveId(user);
                qxjFlowBubao.setUserName(userName);
                qxjFlowBubao.setCompleteFlag("0");
                qxjFlowBubao.setSenderId(CurrentUser.getUserId());
                qxjFlowBubaoService.save(qxjFlowBubao);
            }
            //把最新一条数据显示出来
            approvalFlowService.updateFlag(id);
        }
        jsonObject.put("result", "success");
        Response.json(jsonObject);
    }

    /**
     * 补报查询
     * @param id
     */
    @ResponseBody
    @RequestMapping("/queryFlag")
    public void queryFlag(String id){
        QxjFlowBubao qxjFlowBubao = qxjFlowBubaoService.queryLastBuBaoUser(id);
        List<QxjFlowBubao> list = qxjFlowBubaoService.queryAllBuBaoUser(id);
        if(list != null && list.size() > 0){
            for(QxjFlowBubao qxjFlowBubao1:list){
                if(qxjFlowBubao != null){
                    if(StringUtils.equals(qxjFlowBubao.getReceiveId(),qxjFlowBubao1.getReceiveId())){
                        qxjFlowBubao1.setStatus("待审批");
                    }
                }
            }
        }
        Response.json("result",list);
    }

    /**
     * 审批人未读可以删除
     * @param id
     * @param userId
     */
    @ResponseBody
    @RequestMapping("/deleteBuBao")
    public void deleteBuBao(String id,String userId) {
        ApprovalFlow approvalFlow = leaveorbackService.queryIsView(id, userId);
        if ((approvalFlow != null && "0".equals(approvalFlow.getIsView())) || approvalFlow == null) {
            leaveorbackService.deleteBubao(id, userId);
            qxjFlowBubaoService.deleteBubao(id, userId);
            Response.json("result", "已删除");
        } else if (approvalFlow != null && "1".equals(approvalFlow.getIsView())) {
            Response.json("result", "该审批人已读，不能删除");
        }
    }

    /**
     * 补报人员顺序调整
     * @param id  文id
     * @param receiveId   补报人一id
     * @param otherReceiveId   补报人二id
     */
    @ResponseBody
    @RequestMapping("/changePeople")
    public void changePeople(String id,String receiveId,String otherReceiveId) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(receiveId) && StringUtils.isNotBlank(otherReceiveId)) {
            QxjFlowBubao qxjFlowBubao = qxjFlowBubaoService.queryLastBuBaoUser(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fileId", id);
            map.put("receiveId", receiveId);
            List<QxjFlowBubao> receiveIdList = qxjFlowBubaoService.queryList(map);
            map.put("fileId", id);
            map.put("receiveId", otherReceiveId);
            List<QxjFlowBubao> otherReceiveIdList = qxjFlowBubaoService.queryList(map);
            if (receiveIdList.size() > 0 && otherReceiveIdList.size() > 0) {
                if (StringUtils.equals(receiveIdList.get(0).getId(), qxjFlowBubao.getId())
                        || StringUtils.equals(otherReceiveIdList.get(0).getId(), qxjFlowBubao.getId())
                        || StringUtils.equals(receiveIdList.get(0).getCompleteFlag(), "1")
                        || StringUtils.equals(otherReceiveIdList.get(0).getCompleteFlag(), "1")) {
                    jsonObject.put("result", "fail");
                    jsonObject.put("msg", "数据异常");
                    Response.json(jsonObject);
                }
                QxjFlowBubao qxjFlowBubao1 = new QxjFlowBubao();
                qxjFlowBubao1.setId(receiveIdList.get(0).getId());
                qxjFlowBubao1.setCreatedTime(otherReceiveIdList.get(0).getCreatedTime());
                qxjFlowBubaoService.update(qxjFlowBubao1);
                qxjFlowBubao1.setId(otherReceiveIdList.get(0).getId());
                qxjFlowBubao1.setCreatedTime(receiveIdList.get(0).getCreatedTime());
                qxjFlowBubaoService.update(qxjFlowBubao1);
            }
            jsonObject.put("result", "success");
            jsonObject.put("msg", "替换成功");
            Response.json(jsonObject);
        }
    }

    /**
     * 消息推送
     * @param id 请假单ID
     * @param operateFlag 审批、审批完成、退回操作标志
     * @param approvalId 消息接收人ID
     */
    private void sendTipMsg(String id, String operateFlag, String approvalId, String creatorId) {
        //消息推送
        MsgTip msg;
        String operateType;
        String signFlag = null;
        try {
            BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMappedByOrgId("", "root", AppConstant.APP_QXJGL);
            switch (operateFlag){
                case "00":
                    operateType = "qj_qjsp";
                    signFlag = "qxjsp";
                    break;
                case "01":
                    operateType = "qj_sptg";
                    break;
                case "04":
                    operateType = "qj_chehui";
                    break;
                default:
                    operateType = "qj_tuihui";
                    if (!StringUtils.equals(creatorId, approvalId)) {
                        signFlag = "qxjsp";
                    }
                    break;
            }
            msg = msgService.queryObject(operateType);
            if(msg!=null && mapped!= null) {
                String msgRedirect = msg.getMsgRedirect();
                StringBuilder urlRedirect = new StringBuilder(msgRedirect).append("&id=").append(id).append("&fileFrom=").append(signFlag);
                msgUtils.sendMsg(msg.getMsgTitle(), msg.getMsgContent(), urlRedirect.toString(), approvalId, mapped.getAppId(), mapped.getAppSecret(), msg.getGroupName(), msg.getGroupRedirect(),null,"true");
            }
        } catch (Exception e) {
            logger.info("掉消息系统发消息给{}，当前用户ID：{}，异常：{}", approvalId, CurrentUser.getUserId(), e);
        }
    }

    /**
     * 组织更新意见
     * @param approveContent 审批内容
     * @param opinionType 意见类型
     */
    private Opinion organizeQxjOpinion( Leaveorback leave, String approveContent, String opinionType) {
        Date currDate = new Date();
        Opinion latestOpinion = opinionService.queryLatestOpinion(leave.getId());
        if (latestOpinion != null) {
        	if(StringUtils.equals(CurrentUser.getUserId(), latestOpinion.getUserId()) && StringUtils.equals("0", latestOpinion.getTempType())) {
        		//存在临时意见，只做更新
        	}else {
        		//不存在临时意见，直接新增一条
                latestOpinion = this.dealDuplicateCode(leave);
        	}
        	latestOpinion.setTempType("1");
        	latestOpinion.setOpinionDate(currDate);
            this.dealDuplicateCode(opinionType, approveContent, latestOpinion);
        }else {
                latestOpinion = this.dealDuplicateCode(leave);
        		latestOpinion.setTempType("1");
        		latestOpinion.setOpinionDate(currDate);
                this.dealDuplicateCode(opinionType, approveContent, latestOpinion);
        }
        return latestOpinion;
    }

    private Opinion bubaoQxjOpinion(Leaveorback leaveorback,String approveContent,String opinionType){
        Opinion opinion = opinionService.queryLatestOpinion(leaveorback.getId());
        return opinion;
    }

    /**
     *  处理重复代码
     * @param leave 请假信息
     */
    private Opinion dealDuplicateCode(Leaveorback leave){
        Opinion  latestOpinion =new Opinion();
        latestOpinion.setLeaveId(leave.getId());
        latestOpinion.setUserId(CurrentUser.getUserId());
        latestOpinion.setUserName(CurrentUser.getUsername());
        latestOpinion.setDeptId(CurrentUser.getDepartmentId());
        latestOpinion.setDeptName(CurrentUser.getOrgName());
        return latestOpinion;
    }
    /**
     *  处理重复代码
     * @param opinionType 意见类型
     * @param approveContent 意见内容
     * @param latestOpinion 最新意见
     */
    private void dealDuplicateCode(String opinionType, String approveContent, Opinion latestOpinion){
        if (StringUtils.equals("1", opinionType) && StringUtils.contains(approveContent, "http://")) {
        } else {
            latestOpinion.setOpinion(approveContent);
            latestOpinion.setOpinionType(opinionType);
        }
    }
    /**
     * @description:退回操作
     * @param id 主文佳id
     * @param receiverId 接收人id
     * @param receiverName 接收人
     * @param approveContent 意见内容
     * @param opinionType 意见类型
     */
    @ResponseBody
    @RequestMapping("/goBackToLastApply")
    public void goBackToLastApply(String id, String receiverId, String receiverName, String approveContent, String opinionType){
        JSONObject jsonObject = new JSONObject();
        try {
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            String creatorId = tLeaveorback.getCreatorId();
            Opinion qxjOpinion = this.organizeQxjOpinion(tLeaveorback,approveContent, opinionType);
            ApprovalFlow qxjApprovalFlow1 = this.organizeQxjApprovalFlow(tLeaveorback, receiverId, receiverName,"3");
            //退回状态
            tLeaveorback.setStatus(QxjStatusDefined.YI_TUI_HUI);
            leaveOrBackManager.unifiedDealData(qxjOpinion,qxjApprovalFlow1, tLeaveorback);
            jsonObject.put("result","success");
            //发消息
            this.sendTipMsg(id, "03", receiverId, creatorId);

        } catch (Exception e) {
            logger.info("调用请销假管理调用退回功能，当前用户ID：{}，退回给用户：{}，异常：{}", CurrentUser.getUserId(), receiverId, e);
            jsonObject.put("result","fail");
        } finally {
            Response.json(jsonObject);
        }
    }

    @ResponseBody
    @RequestMapping("/flowAllPersons")
    public void flowAllPersons(String id){
        JSONArray jsonArray = new JSONArray();
        String organName;
        String userName;
        String userId;
        String creatorId = null;
        try {
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            creatorId = tLeaveorback.getCreatorId();
            String creator = tLeaveorback.getCreator();
            String orgName = tLeaveorback.getOrgName();
            jsonArray.add(this.organizeJSONObject(orgName, creator, creatorId));
            List<ApprovalFlow> qxjApprovalFlows = distictqxjApprovalFlows(id);
            if (qxjApprovalFlows.size() > 0) {
                for (ApprovalFlow qxjApprovalFlow: qxjApprovalFlows) {
                    organName = qxjApprovalFlow.getReceiverDepartName();
                    userName = qxjApprovalFlow.getApprovalName();
                    userId = qxjApprovalFlow.getApprovalId();
                    jsonArray.add(this.organizeJSONObject(organName, userName, userId));
                }
            }
        } catch (Exception e) {
            logger.info("调用请销假管理退回，当前用户ID：{}，异常：{}",  CurrentUser.getUserId(), e);
        } finally {
            Response.json(distictqxjJSONArray(jsonArray, creatorId));
        }
    }

    /**
     * 去重请假流程数据
     * @param id 请假ID
     * @return List<QxjApprovalFlow>
     */
    private List<ApprovalFlow> distictqxjApprovalFlows(String id){
        return approvalFlowService.queryQxjApprovalFlow(id)
                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(t -> t.getApprovalId()))), ArrayList::new));
    }
    /**
     * 再次去重请假流程数据，返回不重复流经人
     * @param jsonArray 重排序
     * @param creatorId 拟稿人ID
     * @return JSONArray
     */
    private JSONArray distictqxjJSONArray(JSONArray jsonArray, String creatorId){
        List<JSONObject> jsonObjectListNew = new ArrayList<>();
        List<JSONObject> jsonObjectList = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class)
                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(t -> t.getString("userId")))), ArrayList::new));
        jsonObjectList.forEach(jsonObject -> {
            String userId = jsonObject.getString("userId");
            if (!StringUtils.equals(CurrentUser.getUserId(), userId)) {
                if (StringUtils.equals(creatorId, userId)) {
                    jsonObjectListNew.add(0, jsonObject);
                } else {
                    jsonObjectListNew.add(jsonObject);
                }
            }
        });
        return JSONArray.parseArray(JSON.toJSONString(jsonObjectListNew));
    }
    /**
     * 组织退回返回人员
     * @param organName 机构名
     * @param userName 用户名
     * @param userId 用户id
     * @return JSONObject
     */
    private JSONObject organizeJSONObject(String organName, String userName, String userId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("organName", organName);
        jsonObject.put("userName", userName);
        jsonObject.put("userId", userId);
        return jsonObject;
    }
	/**
	 * @description:撤回操作
	 * @param id 主文佳id
	 */
    @ResponseBody
    @RequestMapping("/withdrawToLastApply")
    public void withdrawToLastApply(String id){
        JSONObject jsonObject = new JSONObject();
        try {
            List<ApprovalFlow> qxjApprovalFlows = approvalFlowService.queryLatestTwoFlowRecord(id);
            ApprovalFlow qxjApprovalFlow1;
            ApprovalFlow qxjApprovalFlow2 = null;
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            if (qxjApprovalFlows.size() > 0) {
                qxjApprovalFlow1 = qxjApprovalFlows.get(0);
                //主文件状态回滚
                if (qxjApprovalFlows.size() == 1) {//说明是起草后第一次提交，状态回到【待提交】
                    this.updateTLeaveOrBackStatus(tLeaveorback, 0);
                } else {//状态回到上一步的状态
                    qxjApprovalFlow2 = qxjApprovalFlows.get(1);
                    this.updateTLeaveOrBackStatus(tLeaveorback, Integer.parseInt(qxjApprovalFlow2.getFlowType()));
                }
                String userId = CurrentUser.getUserId();
                String creatorId = qxjApprovalFlow1.getCreatorId();
                String approvalId = qxjApprovalFlow1.getApprovalId();
                Integer isView = qxjApprovalFlow1.getIsView();
                //意见回滚：存在当前接收人的临时意见则删除，同时将上一个人的一家置为临时的
                String deleteOpinion ="0";
                Opinion latestOpinion = opinionService.queryLatestOpinion(id);
                if (latestOpinion != null) {
                	String opinionUserId = latestOpinion.getUserId();
                	if(StringUtils.equals(opinionUserId, qxjApprovalFlow1.getApprovalId()) && StringUtils.equals("0", latestOpinion.getTempType())) {
                		deleteOpinion="1";
                	}else {
                		if(StringUtils.equals(opinionUserId, creatorId)) {
                			latestOpinion.setTempType("0");
                		}
                	}
                }
                if (StringUtils.equals(creatorId, userId) && !StringUtils.equals("1", isView+"")) {
                    //撤回操作
                    //删除流转表最新数据   更新主表请假状态，上一条记录置为临时意见
                    leaveOrBackManager.unifiedDealData1(qxjApprovalFlow1, tLeaveorback, qxjApprovalFlow2,deleteOpinion,latestOpinion);
                }
                this.sendTipMsg(id,"04",approvalId,creatorId);
            }
            //this.sendTipMsg(id,"04","","");
            jsonObject.put("result", "success");
        } catch (NumberFormatException e) {
            logger.info("调用请销假管理撤回，当前用户ID：{}，异常：{}", CurrentUser.getUserId(), e);
            jsonObject.put("result", "fail");
        } finally {
            Response.json(jsonObject);
        }
    }

    /**
     *
     * @param tLeaveorback 主表数据
     * @param withdrawLastStatus 撤回前状态
     */
    private void updateTLeaveOrBackStatus(Leaveorback tLeaveorback, Integer withdrawLastStatus) {
            tLeaveorback.setStatus(withdrawLastStatus);
    }
    /**
     * 更新请假状态
     * @param tLeaveorback 请假申请数据
     * @param buttonOperateFlag 点击送审按钮还是审批完成按钮
     */
    private void updateTLeaveOrBack(Leaveorback tLeaveorback, String buttonOperateFlag,String approvalId) {
        if (StringUtils.equals(sendApproveFlag, buttonOperateFlag)) {
            //审批中
            tLeaveorback.setStatus(QxjStatusDefined.DAI_SHEN_PI);
        } else if (StringUtils.equals(finishApproveFlag, buttonOperateFlag)){
            //审批完成
            tLeaveorback.setStatus(QxjStatusDefined.YI_TONG_GUO);
        }
        String currentUser = CurrentUser.getUserId();
        List<QxjFlowBubao> qxjFlowBubao = qxjFlowBubaoService.queryUserId(tLeaveorback.getId(),currentUser);
        if(qxjFlowBubao != null && qxjFlowBubao.size() > 0){
            approvalId = currentUser;
            //补报表当前审批人更新为已审批
            Date date = new Date();
            qxjFlowBubaoService.updateBubao(tLeaveorback.getId(),approvalId,date);
        }

    }

    /**
     *
     * @param tLeaveorback 请假申请数据
     * @param approvalId 审批人ID
     * @return QxjApprovalFlow
     */
    private ApprovalFlow organizeQxjApprovalFlow(Leaveorback tLeaveorback, String approvalId, String approvalName,String type) {
        String userId = CurrentUser.getUserId();
        String username = CurrentUser.getUsername();
        Date currDate = new Date();
        ApprovalFlow qxjApprovalFlow = new ApprovalFlow();
        qxjApprovalFlow.setId(UUID.randomUUID().toString());
        qxjApprovalFlow.setCreatorId(userId);
        qxjApprovalFlow.setCreator(username);
        qxjApprovalFlow.setModificator(username);
        qxjApprovalFlow.setApprovalId(approvalId);
        String[] userIds = {approvalId};
        if (StringUtils.isBlank(approvalName)) {
            List<BaseAppUser> baseAppUsers = baseAppUserService.queryObjectByUserIds(userIds);
            if (baseAppUsers.size() > 0) {
                qxjApprovalFlow.setApprovalName(baseAppUsers.get(0).getTruename());
            } else {
                logger.info("根据approvalId:{}，查不到BaseAppUser数据", approvalId);
            }
        } else {
            qxjApprovalFlow.setApprovalName(approvalName);
        }
        qxjApprovalFlow.setModificatorId(userId);
        qxjApprovalFlow.setModifyDate(currDate);
        qxjApprovalFlow.setLeaveId(tLeaveorback.getId());
        qxjApprovalFlow.setCreateDate(currDate);
        //暂定未读
        qxjApprovalFlow.setIsView(0);
        //审批中
        qxjApprovalFlow.setFlowType(QxjStatusDefined.DAI_SHEN_PI+"");
        //默认不显示
        if("2".equals(type)){
            qxjApprovalFlow.setFlag("1");
        }else{
            qxjApprovalFlow.setFlag("0");
        }
        String sendUserDepartIdAndNames = this.queryUserDepartIdAndName(userId);
        if (StringUtils.isNotBlank(sendUserDepartIdAndNames)) {
            String[] sendUserDepartIdAndName = sendUserDepartIdAndNames.split(",");
            qxjApprovalFlow.setSenderDepartId(sendUserDepartIdAndName[0]);
            qxjApprovalFlow.setSenderDepartName(sendUserDepartIdAndName[1]);
        }
        String receiverUserDepartIdAndNames = this.queryUserDepartIdAndName(approvalId);
        if (StringUtils.isNotBlank(receiverUserDepartIdAndNames)) {
            String[] receiverUserDepartIdAndName = receiverUserDepartIdAndNames.split(",");
            qxjApprovalFlow.setReceiverDepartId(receiverUserDepartIdAndName[0]);
            qxjApprovalFlow.setReceiverDepartName(receiverUserDepartIdAndName[1]);
        }
        return qxjApprovalFlow;
    }

    /**
     *
     * @param userId 用户ID
     * @return 部门ID和部门名
     */
    private String queryUserDepartIdAndName(String userId){
        return baseAppUserService.queryUserDepartIdAndName(userId);
    }


    @ResponseBody
    @RequestMapping("/isView")
    public void isView(String id){
        String approvalId = CurrentUser.getUserId();
        ApprovalFlow qxjApprovalFlow = approvalFlowService.queryLatestFlowRecord(id);
        if (qxjApprovalFlow != null && StringUtils.equals(approvalId, qxjApprovalFlow.getApprovalId())) {
            qxjApprovalFlow.setIsView(1);
            qxjApprovalFlow.setIsViewTime(new Date());
            approvalFlowService.updateById(qxjApprovalFlow);
        }
        Response.json("result", "success");
    }
    @ResponseBody
    @RequestMapping("/chooseOneJuPersons")
    public void chooseOneJuPersons(String userIds){
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(userIds)) {
            List<String> userIdsList = Arrays.asList(userIds.split(","));
            List<String> hashSet = new ArrayList<>();
            for (String userId : userIdsList) {
                String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
                hashSet.add(orgId);
//                if (StringUtils.isNotBlank(orgId)) {
//                    if (hashSet.size() > 1) {
//                        jsonObject.put("result", "fail");
//                        break;
//                    } else {
//                        jsonObject.put("result", "success");
//                    }
//                }
            }
            jsonObject.put("result","success");
            //if (StringUtils.equals("success", jsonObject.getString("result"))) {
                for (String orgId : hashSet) {
                    jsonObject.put("orgId", orgId);
                    BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(orgId);
                    jsonObject.put("orgName", baseAppOrgan.getName());
                }
           // }
        }

        Response.json(jsonObject);
    }
    /**
     * 检查是否可以授权
     * @param documentRoleSet documentRoleSet
     */
    @ResponseBody
    @RequestMapping("/authorizeCheck")
    public void authorizeCheck(DocumentRoleSet documentRoleSet){
    	String leaveUserId = documentRoleSet.getLeaveUserId();
    	String orgId = baseAppUserService.getBareauByUserId(CurrentUser.getUserId());
		// 获取公文开放的授权接口
    	BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMappedByOrgId("",orgId,AppConstant.APP_GWCL);
		String url = mapped.getUrl() + mapped.getWebUri()+ AppInterfaceConstant.WEB_INTERFACE_GWCL_GW_AUTHORIZE_CHECK;
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
		map.add("leaveUserId", leaveUserId);
		JSONObject jsonData = CrossDomainUtil.getJsonData(url,map);
        Response.json(jsonData);
    }
    /**
     * 授权给他人权限
     * @param documentRoleSet
     * @param startDate
     */
    @ResponseBody
    @RequestMapping("/authorize")
    public void authorize(DocumentRoleSet documentRoleSet,String startDate){
    	String leaveUser = "";
    	String leaveUserId = documentRoleSet.getLeaveUserId();
    	String orgId = baseAppUserService.getBareauByUserId(CurrentUser.getUserId());
    	if(StringUtils.isNotBlank(leaveUserId)) {
    		BaseAppUser user = baseAppUserService.queryObject(leaveUserId);
    		leaveUser = user.getTruename();
    	}
		// 获取公文开放的授权接口
    	BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMappedByOrgId("",orgId,AppConstant.APP_GWCL);
		String url = mapped.getUrl() + mapped.getWebUri()+ AppInterfaceConstant.WEB_INTERFACE_GWCL_GW_AUTHORIZE;
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
		map.add("startDate", startDate);
		map.add("leaveUser", leaveUser);
		map.add("leaveUserId", leaveUserId);
		map.add("replaceUser", documentRoleSet.getReplaceUser());
		map.add("replaceUserId", documentRoleSet.getReplaceUserId());
		map.add("authorizationType", documentRoleSet.getAuthorizationType());
		JSONObject jsonData = CrossDomainUtil.getJsonData(url,map);
        Response.json(jsonData);
    }

    /**
     * 销假撤回授权
     * @param recallUserId 销假人的ID
     */
    @ResponseBody
    @RequestMapping("/recallAuthorize")
    public void recallAuthorize(String recallUserId){
		// 获取公文开放的取消授权接口
    	String orgId = baseAppUserService.getBareauByUserId(CurrentUser.getUserId());
    	BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMappedByOrgId("",orgId,AppConstant.APP_GWCL);
		String url = mapped.getUrl() + mapped.getWebUri()+ AppInterfaceConstant.WEB_INTERFACE_GWCL_GW_RECALLAUTHORIZE;
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
		map.add("recallUserId", recallUserId);
		CrossDomainUtil.getJsonData(url,map);
    }

    @ResponseBody
    @RequestMapping("/bubbleCountStatistics")
    public void bubbleCountStatistics(){
        JSONObject jsonObject = new JSONObject();
        String userId = CurrentUser.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("loginUserId", userId);
        map.put("creatorId", userId);
        map.put("isBubbleStatistics","yes");
        List<Leaveorback> leaveLists = leaveorbackService.queryNewList(map);
        jsonObject.put("qxjsq",this.distictTLeaveorbackSQ(leaveLists));
        map.clear();
        map.put("loginUserId", userId);
        map.put("flowPeople", "yes");
        map.put("nostatus", QxjStatusDefined.DAI_TI_JIAO);
        map.put("isBubbleStatistics","yes");
        List<Leaveorback> leaveLists1= leaveorbackService.queryNewList(map);
        jsonObject.put("qxjsp",this.distictTLeaveorbackSP(leaveLists1));
        Response.json(jsonObject);
    }

    
    /**
     * 过滤数据
     * @param leaveLists1 请假数据
     * @return 数据量
     */
    private  int distictTLeaveorbackSQ(List<Leaveorback> leaveLists1){
    	System.out.println(leaveLists1.stream().filter(leaveList -> (leaveList.getStatus() == 30&&leaveList.getBackStatusId().equals("0"))).count());
        return (int) leaveLists1.stream().filter(leaveList -> ((leaveList.getStatus() == 10 || leaveList.getStatus() == 20) && (leaveList.getReceiverIsMe() == null ? 0 : leaveList.getReceiverIsMe()) == 1)||(leaveList.getStatus() == 30&&leaveList.getBackStatusId().equals("0"))&&(leaveList.getPlanTimeEnd().before(new Date()))).count();
    }
    private  int distictTLeaveorbackSP(List<Leaveorback> leaveLists1){
        return (int) leaveLists1.stream().filter(leaveList -> ((leaveList.getStatus() == 10 || leaveList.getStatus() == 20) && (leaveList.getReceiverIsMe() == null ? 0 : leaveList.getReceiverIsMe()) == 1)).count();
    }
  
    /**
     *  计算本年总共休假天数
     * @return int
     */
    private int countActualRestDays() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        LocalDate newYearStartDate = LocalDate.parse(year + "-01" + "-01");
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        LocalDate newYearEndDate = LocalDate.parse(year + "-12" + "-31");
        map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        map.put("userId", CurrentUser.getUserId());
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        List<Leaveorback> leaveOrBacks = leaveorbackService.queryCurrYearRestDays(map);
        int actualRestDays = 0;
        Date lastYearFirstDay = Date.from(currYearLastYearFirstDay.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Date currYearLastDay = Date.from(newYearEndDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        
        Date startDate = Date.from(newYearStartDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        for (Leaveorback leaveOrBack : leaveOrBacks) {
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime()-startDate.getTime())/(24*60*60*1000)+1-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                //计算起始日期到年末的天数差
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        int queryDeducttonDays = queryDeducttonDays(newYearStartDate,newYearEndDate,lastYearFirstDay,currYearLastDay,startDate);
        actualRestDays = actualRestDays -queryDeducttonDays;
        return actualRestDays;
    }
    
    /**
     *  计算本年总共休假天数
     * @return int
     */
    private int countActualRestDaysXLGL(String userid) {
    	String orgind = "";
    	BaseAppUser queryObject = baseAppUserService.queryObject(userid);
    	if(queryObject !=null) {
    		orgind = queryObject.getOrganid();
    	}
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        LocalDate newYearStartDate = LocalDate.parse(year + "-01" + "-01");
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        LocalDate newYearEndDate = LocalDate.parse(year + "-12" + "-31");
        map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        map.put("userId",userid);
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        List<Leaveorback> leaveOrBacks = leaveorbackService.queryCurrYearRestDays(map);
        int actualRestDays = 0;
        Date lastYearFirstDay = Date.from(currYearLastYearFirstDay.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Date currYearLastDay = Date.from(newYearEndDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        
        Date startDate = Date.from(newYearStartDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        for (Leaveorback leaveOrBack : leaveOrBacks) {
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", orgind);
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime()-startDate.getTime())/(24*60*60*1000)+1-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                //计算起始日期到年末的天数差
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", orgind);
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        int queryDeducttonDays = queryDeducttonDaysXLGL(newYearStartDate,newYearEndDate,lastYearFirstDay,currYearLastDay,startDate,userid);
        actualRestDays = actualRestDays -queryDeducttonDays;
        return actualRestDays;
    }

    @ResponseBody
    @RequestMapping("/isAdministratiorUrl")
    public void isAdministratiorUrl(){
        BaseAppOrgMapped mapped = (BaseAppOrgMapped) baseAppOrgMappedService.orgMappedByOrgId(null, "root",
                AppConstant.APP_QXJGL);
        Response.json("isAdministratior", mapped !=null && !CurrentUser.getIsManager(mapped.getAppId(), mapped.getAppSecret()));
        }
    @ResponseBody
    @RequestMapping("/acquireLoginPersonRole")
    public void acquireLoginPersonRole() {
        Response.json("loginPersonRole",commonQueryManager.roleType(CurrentUser.getUserId()));
    }
    
    private int queryDeducttonDays(LocalDate newYearStartDate,LocalDate newYearEndDate,
    		Date lastYearFirstDay,	Date currYearLastDay ,Date startDate) {
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        map.put("userId", CurrentUser.getUserId());
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
        List<Leaveorback> queryDeducttonDays = leaveorbackService.queryDeducttonDays(map);
        int actualRestDays = 0;
        for (Leaveorback leaveOrBack : queryDeducttonDays) {
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime() - startDate.getTime())/(24*60*60*1000)-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                //计算起始日期到年末的天数差
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        return actualRestDays ;
    }
    
    private int queryDeducttonDaysXLGL(LocalDate newYearStartDate,LocalDate newYearEndDate,
    		Date lastYearFirstDay,	Date currYearLastDay ,Date startDate,String userid) {
    	String orgId = "";
    	BaseAppUser queryObject = baseAppUserService.queryObject(userid);
    	if(queryObject !=null) {
    		orgId = queryObject.getOrganid();
    	}
    	
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        map.put("userId", userid);
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        map.put("orgId", orgId);
        List<Leaveorback> queryDeducttonDays = leaveorbackService.queryDeducttonDays(map);
        int actualRestDays = 0;
        for (Leaveorback leaveOrBack : queryDeducttonDays) {
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", orgId);
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime() - startDate.getTime())/(24*60*60*1000)-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", orgId);
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                //计算起始日期到年末的天数差
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        return actualRestDays ;
    }
    
    @ResponseBody
    @RequestMapping("/countXiuJiaDays")
    public void countXiuJiaDays(){
        String userId = CurrentUser.getUserId();
        JSONObject jsonObject = new JSONObject();
        //获取应休假天数
        DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(userId);
        jsonObject.put("xiuJiaDays", this.countActualRestDays());
        if (qxjDicHoliday != null) {
            jsonObject.put("totalDays", qxjDicHoliday.getShouldtakdays());
        } else {
            jsonObject.put("totalDays", 0);
        }
        Response.json(jsonObject);
    }
    
    
    @ResponseBody
    @RequestMapping("/countXiuJiaDaysXLGL")
    public void countXiuJiaDaysXLGL(){
    	ArrayList<QXJPeopleManagementDto> arrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        //获取应休假天数
        List<DicHoliday> queryList = dicHolidayService.queryList(null);
        int countActualRestDays = this.countActualRestDays();//已休假天数
        for (DicHoliday dicHoliday : queryList) {
        	QXJPeopleManagementDto qxjPeopleManagementDto = new QXJPeopleManagementDto();
        	Double shouldtakdays = dicHoliday.getShouldtakdays();
        	if(shouldtakdays ==null) {
        		shouldtakdays = 0.0;
        	}
        	int intValue = new Double(shouldtakdays).intValue();//应休天数
        	int weixiujiaDays =intValue -countActualRestDays;//未请假天数
        	  if(weixiujiaDays < countActualRestDays) {
              	List<Leaveorback> whetherRestByUserid = leaveorbackService.getWhetherRestByUserid(null);
              	if(whetherRestByUserid.size()>0) {
              		for (Leaveorback leaveorback : whetherRestByUserid) {
						if(dicHoliday.getUserid().equals(leaveorback.getDeleteMark())) {
		              		qxjPeopleManagementDto.setType(leaveorback.getVacationSortId());
		              		qxjPeopleManagementDto.setStartDate(leaveorback.getActualTimeStart());
		              		qxjPeopleManagementDto.setEndDate(leaveorback.getActualTimeEnd());
						}
					}	
              	}
              }
        	  qxjPeopleManagementDto.setUserId(dicHoliday.getUserid());
        	  qxjPeopleManagementDto.setUserName(dicHoliday.getUsername());
        	  qxjPeopleManagementDto.setOrgId(dicHoliday.getOrgId());
        	  qxjPeopleManagementDto.setOrgName(dicHoliday.getOrgName());
        	  qxjPeopleManagementDto.setXiuJiaDays(Integer.toString(countActualRestDays));
        	  qxjPeopleManagementDto.setTotalDays(Integer.toString(intValue));
        	  qxjPeopleManagementDto.setWeixiujiaDays(Integer.toString(weixiujiaDays));
        	  arrayList.add(qxjPeopleManagementDto);
        }
        jsonObject.put("list", arrayList);
        Response.json(jsonObject);
    }
    
    
    @ResponseBody
    @RequestMapping("/countXLGL")
    public void countXLGL(){
    	ArrayList<QXJPeopleManagementDto> arrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        //获取应休假天数
        List<DicHoliday> queryList = dicHolidayService.queryList(null);
        for (DicHoliday dicHoliday : queryList) {
        	 int countActualRestDays = this.countActualRestDaysXLGL(dicHoliday.getUserid());//已休假天数
        	QXJPeopleManagementDto qxjPeopleManagementDto = new QXJPeopleManagementDto();
        	Double shouldtakdays = dicHoliday.getShouldtakdays();
        	if(shouldtakdays ==null) {
        		shouldtakdays = 0.0;
        	}
        	int intValue = new Double(shouldtakdays).intValue();//应休天数
        	int weixiujiaDays =intValue -countActualRestDays;//未请假天数
              	List<Leaveorback> whetherRestByUserid = leaveorbackService.getWhetherRestByUserid(null);
              	if(whetherRestByUserid.size()>0) {
              		for (Leaveorback leaveorback : whetherRestByUserid) {
						if(dicHoliday.getUserid().equals(leaveorback.getDeleteMark())) {
							if(StringUtils.isNotBlank(leaveorback.getType())) {
								if(leaveorback.getType().equals("0")) {
									qxjPeopleManagementDto.setType("请假");
								}else {
									qxjPeopleManagementDto.setType("出差");
								}
							}
		              		qxjPeopleManagementDto.setStartDate(leaveorback.getActualTimeStart());
		              		qxjPeopleManagementDto.setEndDate(leaveorback.getActualTimeEnd());
		              		qxjPeopleManagementDto.setAddress(leaveorback.getAddress());
		              		qxjPeopleManagementDto.setOrigin(leaveorback.getOrigin());
		              		qxjPeopleManagementDto.setPlace(leaveorback.getPlace());
						}
					}	
              	}
              
        	  qxjPeopleManagementDto.setUserId(dicHoliday.getUserid());
        	  qxjPeopleManagementDto.setUserName(dicHoliday.getUsername());
        	  qxjPeopleManagementDto.setOrgId(dicHoliday.getOrgId());
        	  qxjPeopleManagementDto.setOrgName(dicHoliday.getOrgName());
        	  qxjPeopleManagementDto.setXiuJiaDays(Integer.toString(countActualRestDays));
        	  qxjPeopleManagementDto.setTotalDays(Integer.toString(intValue));
        	  qxjPeopleManagementDto.setWeixiujiaDays(Integer.toString(weixiujiaDays));
        	  arrayList.add(qxjPeopleManagementDto);
        }
        jsonObject.put("list", arrayList);
        Response.json(jsonObject);
    }

    
    @ResponseBody
    @RequestMapping("/getPreStatus")
    public void getPreStatus(String id){
        //此功能暂时搁置不用，暂且注释掉，前端仍保留该请求
        //leaveorbackService.updateStatus(id);
        Response.json("result","success");

    }
    /**
     * 训练管理 - 日常管理 - 人员管理  - 单位人员请销假情况列表 训练管理app调用
     * @param organId 组织的id
     * */
    @ResponseBody
    @RequestMapping("/qxjUserInfoList")
    public void qxjUserInfoList (Integer page, Integer limit,String organId) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("treePath", organId);
		PageHelper.startPage(page, limit);
		List<QxjUserAndOrganDays> queryList = baseAppUserService.queryListAndOrgan(map);
    	PageUtils pageUtil = new PageUtils(queryList);
		for (QxjUserAndOrganDays qxjUserAndOrganDays : queryList) {
			Double daysRate = this.getDaysRate(qxjUserAndOrganDays.getId());
			qxjUserAndOrganDays.setRate(daysRate);
		}

    	Response.json("page",pageUtil);
    }
    
    private  Double getDaysRate(String userId){
        //获取应休假天数
        DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(userId);
        Double totalDays =0.0;
        if (qxjDicHoliday != null) {
        	totalDays=  qxjDicHoliday.getShouldtakdays();
        } 
        double xiuJiaDays = (double)this.countActualRestDays();
    	double rate =(xiuJiaDays/ totalDays)*100;

        return rate;
    }
}
