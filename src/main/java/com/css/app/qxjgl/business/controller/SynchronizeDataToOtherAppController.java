package com.css.app.qxjgl.business.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.css.addbase.apporgmapped.entity.BaseAppOrgMapped;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.addbase.constant.AppConstant;
import com.css.addbase.constant.AppInterfaceConstant;
import com.css.addbase.msg.MsgTipUtil;
import com.css.addbase.msg.entity.MsgTip;
import com.css.addbase.msg.service.MsgTipService;
import com.css.app.dictionary.entity.DictionaryValue;
import com.css.app.dictionary.service.DictionaryValueService;
import com.css.app.qxjgl.business.dto.DocumentFlowDto;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.service.ApprovalFlowService;
import com.css.app.qxjgl.business.service.DocumentFileService;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.OpinionService;
import com.css.base.utils.CrossDomainUtil;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;

import cn.com.css.filestore.impl.HTTPFile;

@Controller
@RequestMapping("/app/qxjgl/syncDataApi")
public class SynchronizeDataToOtherAppController {
	private final Logger logger = LoggerFactory.getLogger(SynchronizeDataToOtherAppController.class);
    @Autowired
    private MsgTipService msgService;
    @Autowired
    private MsgTipUtil msgUtils;
	@Autowired
	private BaseAppUserService baseAppUserService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private LeaveorbackService leaveorbackService;
	@Autowired
	private DocumentFileService documentFileService;
	@Autowired
	private ApprovalFlowService approvalFlowService;
	@Autowired
	private DictionaryValueService dictionaryValueService;
	@Autowired
	private OpinionService opinionService;
	/**
	 * @description:呈送办公厅弹框默认值
	 * @param id 主文件id
	 * @author:zhangyw
	 * @date:2019年8月19日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getCsbgtDefaultParam")
	public void getApplicationDefault(String id) {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(id)) {
			Leaveorback leave = leaveorbackService.queryObject(id);
			if(leave !=null) {
				Map<String, Object> map=new HashMap<>();
				map.put("leaveId", id);
				map.put("fileType", "cpj");
				List<DocumentFile> fileList = documentFileService.queryList(map);
				if(fileList !=null && fileList.size()>0) {
					result.put("documentTitle", fileList.get(0).getFileName());
				}
				if(StringUtils.isNotBlank(leave.getCreatorId())){
					result.put("undertakerId", leave.getCreatorId());
					result.put("undertakerName", leave.getCreator());
					if(leave.getCreatorId() !=null ) {
						BaseAppUser user = baseAppUserService.queryObject(leave.getCreatorId());
						//String orgId = baseAppOrgMappedService.getBareauByUserId(leave.getCreatorId());
						BaseAppOrgan org = baseAppOrganService.queryObject(user.getOrganid());
						if(org != null ) {
							result.put("undertakeDepartmentId", user.getOrganid());
							result.put("undertakeDepartmentName", org.getName());
						}
					}
				}
			}
		}
		Response.json(result);
	}
	/**
	 * @description:公文转办-->首秘-->首长-->首秘 同步流转数据
	 * @param documentFlowId 主文件id
	 * @param zhmsId 发送人id
	 * @param zhmsName 发送人
	 * @param leaderId 接收的首长id
	 * @param leaderName 接收的首长
	 * @param type 操作标识 ：0公文转办送首长1首长返回公文转办2首长退回公文转办3综合秘书返回公文处理 4综合秘书退回公文处理
	 * @author:zhangyw
	 * @date:2019年8月21日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/syncDataFromOtherApp")
	public void syncDataFromOtherApp(String documentFlowId,String zhmsId,String zhmsName,String leaderId,String leaderName,String type,String content,String drafts){
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(documentFlowId)) {
			Leaveorback leave = leaveorbackService.queryObject(documentFlowId);
			if(leave ==null) {
				result.put("result", "fail");
				result.put("msg", "找不到对应的请假主文件");
				Response.json(result);
				return;
			}
			if(StringUtils.isNotBlank(type)) {
				if(StringUtils.equals("0", type)) {// 0公文转办送首长
					//opinionService.saveOpinion(null, "0", "1", zhmsId, zhmsName, documentFlowId);
					approvalFlowService.updateLatestFlow(documentFlowId, zhmsId, zhmsName, 1);
					approvalFlowService.saveFlowValue(zhmsId, zhmsName, leaderId, leaderName, documentFlowId, "12",0);
				}else if(StringUtils.equals("1", type)) {//1首长返回公文转办
					opinionService.saveOpinion(content, "0", "1", leaderId, leaderName, documentFlowId);
					approvalFlowService.updateLatestFlow(documentFlowId, null, null, 1);
					approvalFlowService.saveFlowValue(zhmsId, zhmsName, "msz", leaderName, documentFlowId, "13",0);
				}else if(StringUtils.equals("2", type)) {//2首长退回公文转办
					opinionService.saveOpinion(content, "0", "1", leaderId, leaderName, documentFlowId);
					approvalFlowService.updateLatestFlow(documentFlowId, null, null, 1);
					approvalFlowService.saveFlowValue( zhmsId, zhmsName, "msz", leaderName, documentFlowId, "14",0);
				}else if(StringUtils.equals("3", type)) {//3综合秘书返回公文处理
					this.GwzbToGwclSyncData(leave, zhmsId, zhmsName, type, content, drafts);
				}else if(StringUtils.equals("4", type)) {// 4综合秘书退回公文处理
					this.GwzbToGwclSyncData(leave, zhmsId, zhmsName, type, content, drafts);
				}
			
				//同步版式文件
				if(StringUtils.isNotBlank(drafts)) {
					List<DocumentFile> fileList = JSONArray.parseArray(drafts, DocumentFile.class);
					if(fileList !=null && fileList.size()>0) {
						for (DocumentFile file : fileList) {
							DocumentFile docFile = documentFileService.queryObject(file.getId());
							docFile.setFileServerFormatId(file.getFileServerFormatId());
							documentFileService.update(docFile);
						}
					}
				}
			}else {
				result.put("result", "fail");
				result.put("msg", "传入的type为NULL");
				Response.json(result);
				return;
			}
			result.put("result", "success");
		}
		Response.json(result);
	}
	
	//公文转办返回数据到公文处理
	public void GwzbToGwclSyncData(Leaveorback leave,String zhmsId,String zhmsName,String type,String content,String drafts){
		String flowType="10";
		if(StringUtils.equals("4", type)) {
			flowType="20";
			leave.setStatus(20);
		}else if(StringUtils.equals("3", type)) {
			flowType="30";
			leave.setStatus(30);
		}
		//opinionService.saveOpinion(null, "0", "1", zhmsId, zhmsName, leave.getId());
		approvalFlowService.updateLatestFlow(leave.getId(),zhmsId,zhmsName, 1);
		//增加流转数据
		if(StringUtils.equals("4", type)) {
			approvalFlowService.saveFlowValue(zhmsId, zhmsName, leave.getCreatorId(), leave.getCreator(),leave.getId(), flowType,0);
		}
		//更改主文件状态
		leaveorbackService.update(leave);
		//发送消息
		this.sendTipMsg(leave.getId(), type, leave.getCreatorId());
	}

	

	/**
	 * @description:请销假呈送办公厅操作
	 * @param docFlow
	 * @author:zhangyw
	 * @date:2019年8月19日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/syncDataToGwzb")
	public void  syncDataToGwzb(String id,String opinionType,String opinionContent,DocumentFlowDto docFlow) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(id)) {
			logger.info("传入的主文件id为Null");
			result.put("result", "fail");
			Response.json(result);
			return;
		}
		Leaveorback leave = leaveorbackService.queryObject(id);
		if(leave ==null) {
			logger.info("QXJ_LEAVEORBACK表中找不到id为{}的记录",id);
			result.put("result", "fail");
			Response.json(result);
			return;
		}
		 docFlow = this.getApplicationDefault(id,docFlow);
		//主文件参数处理
		String orgId = baseAppOrgMappedService.getBareauByUserId(leave.getCreatorId());
		docFlow.setSendDepartmentFlag("root");
		docFlow.setLwdwid(orgId);
		BaseAppOrgan org = baseAppOrganService.queryObject(orgId);
		if(org != null ) {
			docFlow.setLwdw(org.getName());
		}
		String securityName = StringUtils.isBlank(docFlow.getSecurityClassification())?"":dictionaryValueService.queryObject(docFlow.getSecurityClassification()).getDictionaryValue();//密级
	    String jjcdName = StringUtils.isBlank(docFlow.getJjcdId())?"":dictionaryValueService.queryObject(docFlow.getJjcdId()).getDictionaryValue();//紧急程度
	    docFlow.setId(id);
	    docFlow.setJjcdName(jjcdName);
	    docFlow.setSecurityClassificationName(securityName);
	    docFlow.setDocumentDrafterId(docFlow.getUndertakerId());
	    docFlow.setDocumentDrafterName(docFlow.getUndertakerName());
	    //相关文件
		Map<String, Object> map=new HashMap<>();
		map.put("leaveId", id);
		List<DocumentFile> fileList = documentFileService.queryList(map);
		saveQxjOpinion(id, opinionContent, opinionType, "1");
		//发送数据去公文转办
		boolean ret = sendDataToGwzb(docFlow,fileList);
		if(ret) {
			//增加流转数据
			approvalFlowService.saveFlowValue(CurrentUser.getUserId(), CurrentUser.getUsername(), "msz", "办公厅", id, "11",0);
			//改变主文件状态
			leave.setStatus(10);
			leaveorbackService.update(leave);
			result.put("result", "success");
		}else {
			//保存意见
			result.put("result", "fail");
		}		
		Response.json(result);
	} 
	//呈送办公厅时意见处理
    private void saveQxjOpinion(String id, String opinionContent, String opinionType,String tempType) {
        Opinion opinion = opinionService.queryLatestOpinion(id);
        if (opinion != null) {
        	if(StringUtils.equals(CurrentUser.getUserId(), opinion.getUserId()) && StringUtils.equals("0", opinion.getTempType())) {
        		//存在临时意见，只做更新
        	}else {
        		//不存在临时意见，直接新增一条
        		opinion =new Opinion();
        		opinion.setLeaveId(id);
        		opinion.setUserId(CurrentUser.getUserId());
        		opinion.setUserName(CurrentUser.getUsername());
        		opinion.setDeptId(CurrentUser.getDepartmentId());
        		opinion.setDeptName(CurrentUser.getOrgName());       		       		
        	}
        	opinion.setTempType("1");
        	opinion.setOpinionDate(new Date());
            if (StringUtils.equals("1", opinionType) && StringUtils.contains(opinionContent, "http://")) {
            } else {
            	opinion.setOpinion(opinionContent);
            	opinion.setOpinionType(opinionType);
            }
        }else {
        	opinion =new Opinion();
    		opinion.setLeaveId(id);
    		opinion.setUserId(CurrentUser.getUserId());
    		opinion.setUserName(CurrentUser.getUsername());
    		opinion.setDeptId(CurrentUser.getDepartmentId());
    		opinion.setDeptName(CurrentUser.getOrgName());
    		opinion.setTempType("1");
        	opinion.setOpinionDate(new Date());
            if (StringUtils.equals("1", opinionType) && StringUtils.contains(opinionContent, "http://")) {
            } else {
            	opinion.setOpinion(opinionContent);
            	opinion.setOpinionType(opinionType);
            }
        }
        if(StringUtils.isNotBlank(opinion.getId())) {
    		opinionService.update(opinion);
    	}else {
    		opinion.setId(UUIDUtils.random());
    		opinionService.save(opinion);
    	}
    }
	
	//发送数据到公文转办
	public boolean sendDataToGwzb(DocumentFlowDto docFlow, List<DocumentFile> fileList) {		
		LinkedMultiValueMap<String,Object> infoMap = new LinkedMultiValueMap<String,Object>();		
		infoMap.add("docJson", JSON.toJSONString(docFlow));
		infoMap.add("fileListJson",JSON.toJSONString(fileList));
		BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMapped("", "", AppConstant.APP_GWZB);
		if(mapped != null){
			String url = mapped.getUrl() + AppInterfaceConstant.WEB_INTERFACE_SYNC_DATA_TO_GWZB_FROM_QXJGL;
			//String url = "http://172.16.3.134:11007/app/gwzb/api/saveSecretaryLeave";				
			JSONObject retInfo = CrossDomainUtil.getJsonData(url, infoMap);
			if(retInfo != null && !retInfo.get("result").equals("success")) {
				logger.info("公文转办未能成功接收请假数据数据");
				return false;
			}else {
				//调用文件服务拷贝扫描件到对应的文件服务器
				if(fileList != null && fileList.size()>0) {
					int failCount = this.copyFIlesToFileServer(fileList,mapped);
					if(failCount>0) {
						return false;
					}
				}
			}
		}else{
			logger.info("公文转办的配置数据错误");
			return false;
		}
		return true;
	}
		
	//调用文件服务拷贝扫描件到对应的文件服务器
	private int copyFIlesToFileServer(List<DocumentFile> fileList,BaseAppOrgMapped mapped) {
		int failCount=0;
		for(DocumentFile file : fileList) {
			String formatId = file.getFileServerFormatId();
			if(StringUtils.isNotBlank(formatId)){
				HTTPFile hf = new HTTPFile(formatId);
				boolean ret = hf.send(mapped.getFileServer());
				if(!ret){
					logger.info("拷贝DOCUNMENT_FILE表中Id为:{}的版式文件到公文转办失败",file.getId());
					failCount+=1; 
				}
			}else {
				logger.info("拷贝文件到公文转办时,DOCUNMENT_FILE表中Id为:{}的版式文件不存在",file.getId());
				failCount+=1;
			}
		}
		return failCount;
	}
	
	 /**
     * 消息推送
     * @param id 请假单ID
     * @param operateFlag 操作标识 4：退回3：完成返回
     * @param approvalId 消息接收人ID
     */
    private void sendTipMsg(String id, String operateFlag, String approvalId) {
        //消息推送      
        String appId = "";
		String appSecret = "";
		BaseAppOrgMapped mapped = (BaseAppOrgMapped)baseAppOrgMappedService.orgMappedByOrgId("", "root", AppConstant.APP_QXJGL);
		if(mapped != null){
			appId = mapped.getAppId();
			appSecret = mapped.getAppSecret();
		}
		String msgId="";
		if(StringUtils.equals("4", operateFlag)) {//退回
			msgId="qj_zhms_tuihui";
		}else if(StringUtils.equals("3", operateFlag)) {//通过
			msgId="qj_zhms_fanhui";
		}
        try {
        	 MsgTip msg = msgService.queryObject(msgId);
            if(msg!=null) {
                String msgRedirect = msg.getMsgRedirect();
                StringBuilder urlRedirect = new StringBuilder(msgRedirect).append("&id=").append(id);
                msgUtils.sendMsg(msg.getMsgTitle(), msg.getMsgContent(), urlRedirect.toString(), approvalId, appId, appSecret, msg.getGroupName(), msg.getGroupRedirect(),null,"true");
            }
        } catch (Exception e) {
            logger.info("调消息系统发消息给{}，当前用户ID：{}，异常：{}", approvalId, CurrentUser.getUserId(), e);
        }
    }
	private DocumentFlowDto getApplicationDefault(String id,DocumentFlowDto docFlow) {
		if (StringUtils.isNotEmpty(id)) {
			docFlow.setId(id);
			Leaveorback leave = leaveorbackService.queryObject(id);
			if(leave !=null) {
				Map<String, Object> map=new HashMap<>();
				map.put("leaveId", id);
				map.put("fileType", "cpj");
				List<DocumentFile> fileList = documentFileService.queryList(map);
				if(fileList !=null && fileList.size()>0) {
					docFlow.setDocumentTitle(fileList.get(0).getFileName());
				}
				if(StringUtils.isNotBlank(leave.getCreatorId())){
					docFlow.setUndertakerId(leave.getCreatorId());
					docFlow.setUndertakerName(leave.getCreator());
					if(leave.getCreatorId() !=null ) {
						BaseAppUser user = baseAppUserService.queryObject(leave.getCreatorId());
						BaseAppOrgan org = baseAppOrganService.queryObject(user.getOrganid());
						if(org != null ) {
							docFlow.setUndertakeDepartmentId(user.getOrganid());
							docFlow.setUndertakeDepartmentName(org.getName());
						}
					}
				}
			}
			List<DictionaryValue> list = dictionaryValueService.queryListByTypeIdList();
			for (DictionaryValue dictionaryValue : list) {
				if(dictionaryValue.getDictionaryTypeId().equals("security_classification")) {
					docFlow.setSecurityClassification(dictionaryValue.getId());
				}
				if(dictionaryValue.getDictionaryTypeId().equals("emergency_gegree")) {
					docFlow.setJjcdId(dictionaryValue.getId());
				}
			}
		}
		return docFlow;
	}

}
