package com.css.app.qxjgl.business.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.css.filestore.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.addbase.WordUtils;
import com.css.addbase.appconfig.entity.BaseAppConfig;
import com.css.addbase.appconfig.service.BaseAppConfigService;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.addbase.constant.AppConstant;
import com.css.addbase.suwell.OfdTransferUtil;
import com.css.app.qxjgl.business.entity.ApplyUser;
import com.css.app.qxjgl.business.entity.ApprovalFlow;
import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.entity.LeavebackSaveModel;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.app.qxjgl.business.manager.CountActualRestDaysManager;
import com.css.app.qxjgl.business.service.ApplyUserService;
import com.css.app.qxjgl.business.service.ApprovalFlowService;
import com.css.app.qxjgl.business.service.DicCalenderService;
import com.css.app.qxjgl.business.service.DicHolidayService;
import com.css.app.qxjgl.business.service.DocumentFileService;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.OpinionService;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;
import com.css.app.qxjgl.util.QxjStatusDefined;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.DateUtil;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;

import cn.com.css.filestore.impl.HTTPFile;
/**
 * 请假申请
 * @author zhangyw
 * @date 2019-08-14 10:56:12
 */
@Controller
@RequestMapping("/app/qxjgl/application")
public class LeaveApplicatonController {
	private final Logger logger = LoggerFactory.getLogger(LeaveApplicatonController.class);
	@Autowired
	private LeaveorbackService leaveorbackService;
	@Autowired
	private DocumentFileService documentFileService;	
	@Autowired
	private DicVocationSortService dicVocationSortService;//请假类别	
	@Autowired
	private ApprovalFlowService approvalFlowService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppUserService baseAppUserService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private BaseAppConfigService baseAppConfigService;
	@Autowired
	private DicHolidayService dicHolidayService;
	@Autowired
	private ApplyUserService applyUserService;
	@Autowired
	private OpinionService opinionService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	@Autowired
	private CountActualRestDaysManager countActualRestDaysManager;
	
	/**
	 * @description:新增请假单获取默认信息（当前人姓名及所在的单位）
	 * @author:zhangyw
	 * @date:2019年8月15日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getDefaultParam")
	public void getApplicationDefault() {
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(CurrentUser.getUserId())) {
			String loginUserId=CurrentUser.getUserId();
			String loginUserName=CurrentUser.getUsername();
			Leaveorback qxjDefaultParam = leaveorbackService.getQXJDefaultParam(loginUserId);
			if(qxjDefaultParam !=null) {
				result.put("deptDuty", qxjDefaultParam.getDeptDuty()==null?"":qxjDefaultParam.getDeptDuty());//部职别
				result.put("linkMan", qxjDefaultParam.getLinkMan()==null?"":qxjDefaultParam.getLinkMan());//联系人
				result.put("mobile", qxjDefaultParam.getMobile()==null?"":qxjDefaultParam.getMobile());//联系人电话
			}

			if(StringUtils.isNotBlank(loginUserId)){
				result.put("sqr", loginUserName);
				result.put("sqrId", loginUserId);
				result.put("undertakerId", loginUserId);
				result.put("undertaker", loginUserName);
				BaseAppUser user = baseAppUserService.queryObject(loginUserId);
				if(user !=null ) {
					result.put("undertakerMobile", user.getTelephone());
				}
				String orgId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
				BaseAppOrgan org = baseAppOrganService.queryObject(orgId);
				if(org != null ) {
					result.put("orgId",orgId);
					result.put("orgName", org.getName());
				}
			}
		}
		Response.json(result);
	}
	
	/**
	 * @description:获取某申请所有正式发布的意见
	 * @param id 主文件QXJ_LEAVEORBACK的id
	 * @author:zhangyw
	 * @date:2019年8月17日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getAllPublicOpinion")
	public void listAll(String id){
		List<Opinion> items = null;
		Leaveorback leave = leaveorbackService.queryObject(id);
		ApprovalFlow latestRecord = approvalFlowService.queryLatestFlowRecord(id);
		Map<String, Object> map =new HashMap<>();
		map.put("leaveId", id);
		if(leave.getStatus()!=null && QxjStatusDefined.DAI_TI_JIAO==leave.getStatus() && StringUtils.equals(CurrentUser.getUserId(), leave.getCreatorId())) {
			items = opinionService.queryList(map);
		}
		if(latestRecord !=null) {
			if(StringUtils.equals(CurrentUser.getUserId(), latestRecord.getApprovalId())) {
				items = opinionService.queryList(map);
			}else {
				map.put("tempType", "1");
				items = opinionService.queryList(map);
			}
		}
		if(items != null && items.size()>0) {
			for (Opinion item : items) {
				if (StringUtils.equals("1", item.getOpinionType())) {
					String comment = item.getOpinion();
					if(StringUtils.isNotBlank(comment)) {
						HTTPFile httpFile = new HTTPFile(comment);
						if(httpFile !=null ) {
							item.setOpinion(httpFile.getAssginDownloadURL());
						}
					}else {
						logger.info("{}意见标识为手写签批但并没有获取图片的id",item.getId());
					}
				}
			}
		}
		Response.json(items);
	}
	
	/**
	 * @description:获取当前要编辑的意见
	 * @author:zhangyw
	 * @date:2019年8月19日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getCurrentOpinion")
	public void editCurrentOpinion(String id){
		Opinion opinion = opinionService.queryObject(id);
		if(opinion !=null) {
			if (StringUtils.equals("1", opinion.getOpinionType())) {
				String comment = opinion.getOpinion();
				if(StringUtils.isNotBlank(comment)) {
					HTTPFile httpFile = new HTTPFile(comment);
					if(httpFile !=null ) {
						opinion.setOpinion(httpFile.getAssginDownloadURL());
					}
				}else {
					logger.info("{}意见标识为手写签批但并没有获取图片的id",opinion.getId());
				}
			}
		}
		Response.json(opinion);
	}
	
	/**
	 * @description:获取某申请详细信息
	 * @param id 主文件QXJ_LEAVEORBACK的id
	 * @author:zhangyw
	 * @date:2019年8月17日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getLeaveInfo")
	public void getLeaveInfo(String id,String receiverIsMe,String flowType,String sort){
		Leaveorback leave = leaveorbackService.queryObject(id);
		//请假类别
		if(StringUtils.isNotBlank(leave.getVacationSortId())) {
			DicVocationSort dicVocation =  dicVocationSortService.queryObject(leave.getVacationSortId());
			leave.setVacationSortName(dicVocation.getVacationSortId());
		}
		String loginUserId=CurrentUser.getUserId();
		Map<String,Object> map = new HashMap<>();
		map.put("loginUserId",loginUserId);
		map.put("flowPeople", "yes");
		//Leaveorback leave = leaveorbackService.queryObject(id);
		String preStatus = leave.getPreStatus();
		int status = leave.getStatus();
		//判断该字段是否有值，有值的话，说明操作过某个按钮，取操作按钮之前的状态
		if(StringUtils.isNotBlank(preStatus)){
			status = Integer.parseInt(preStatus);
		}
		if (com.css.base.utils.StringUtils.isNotBlank(String.valueOf(status))) {
			map.put("status", String.valueOf(status));
		}
		if (com.css.base.utils.StringUtils.isNotBlank(receiverIsMe)) {
			map.put("receiverIsMe", receiverIsMe);
			if (!"1".equals(receiverIsMe) && com.css.base.utils.StringUtils.isNotBlank(flowType)) {
				map.put("flowType", flowType);
			}
		}
		List<Leaveorback> leaveList = leaveorbackService.queryNewList1(map);
		String preId="";
		String sufId="";
		if (leaveList != null && leaveList.size() > 0) {
//			if (leaveList.size() == 1) {
//				preId = "noPredId";//上一页
//				sufId = "noSufId";//下一页
//			} else {
//				for (int i = 0; i < leaveList.size(); i++) {
//					if (StringUtils.equals(id, leaveList.get(i).getId())) {
//						if (i == 0) {
//							preId = "noPredId";
//							sufId = leaveList.get(i + 1).getId();
//							break;
//						} else if (i == leaveList.size() - 1) {
//							preId = leaveList.get(i - 1).getId();
//							sufId = "noSufId";
//							break;
//						} else {
//							preId = leaveList.get(i - 1).getId();
//							sufId = leaveList.get(i + 1).getId();
//							break;
//						}
//					}
//				}
//			}
			for (int i = 0; i < leaveList.size(); i++) {
				if (leaveList.size() == 1) {
					preId = "noPredId";
					sufId = leaveList.get(i).getId();
				} else {
					if ("1".equals(sort)) {
						preId = "noPredId";
					} else {
						preId = leaveList.get(i).getId();
					}
					int s = leaveList.size();
					if (StringUtils.equals(sort, String.valueOf(s))) {
						sufId = "noSufId";
					} else {
						int sum = i + 1;
						if (sum < leaveList.size()) {
							sufId = leaveList.get(i + 1).getId();
						} else {
							sufId = "noSufId";
						}
					}
				}
				break;
			}

		} else {
			preId = "noPredId";//上一页
			sufId = "noSufId";//下一页
		}
		leave.setPreId(preId);
		leave.setSufId(sufId);
		leave.setReceiverIsMe(Integer.parseInt(receiverIsMe));
		leave.setFlowType(flowType);
		Response.json(leave);
	}

	@ResponseBody
	@RequestMapping("/getNextPage")
	public void getNextPage(String id,String receiverIsMe,String flowType){
		String loginUserId=CurrentUser.getUserId();
		Map<String,Object> map = new HashMap<>();
		map.put("loginUserId",loginUserId);
		map.put("flowPeople", "yes");
		Leaveorback leave = leaveorbackService.queryObject(id);
		String preStatus = leave.getPreStatus();
		int status = leave.getStatus();
		//判断该字段是否有值，有值的话，说明操作过某个按钮，取操作按钮之前的状态
		if(StringUtils.isNotBlank(preStatus)){
			status = Integer.parseInt(preStatus);
		}
		if (com.css.base.utils.StringUtils.isNotBlank(String.valueOf(status))) {
			map.put("status", String.valueOf(status));
		}
		if (com.css.base.utils.StringUtils.isNotBlank(receiverIsMe)) {
			map.put("receiverIsMe", receiverIsMe);
			if (!"1".equals(receiverIsMe) && com.css.base.utils.StringUtils.isNotBlank(flowType)) {
				map.put("flowType", flowType);
			}
		}
		List<Leaveorback> leaveList = leaveorbackService.queryNewList1(map);
		String preId="";
		String sufId="";
		if (leaveList != null && leaveList.size() > 0) {
			if (leaveList.size() == 1) {
				preId = "noPredId";//上一页
				sufId = "noSufId";//下一页
			} else {
				for (int i = 0; i < leaveList.size(); i++) {
					if (StringUtils.equals(id, leaveList.get(i).getId())) {
						if (i == 0) {
							preId = "noPredId";
							sufId = leaveList.get(i + 1).getId();
							break;
						} else if (i == leaveList.size() - 1) {
							preId = leaveList.get(i - 1).getId();
							sufId = "noSufId";
							break;
						} else {
							preId = leaveList.get(i - 1).getId();
							sufId = leaveList.get(i + 1).getId();
							break;
						}
					}
				}
			}

		}
		leave.setPreId(preId);
		leave.setSufId(sufId);
		leave.setReceiverIsMe(Integer.parseInt(receiverIsMe));
		leave.setFlowType(flowType);
		Response.json(leave);
	}
	
	/**
	 * @description:删除主文件(即删除当前请销假申请)
	 * @param leaveId 主文件id
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/deleteInfo")
	public void deleteInfo(String leaveId){
		//删除请销假信息
		leaveorbackService.delete(leaveId);
		//删除请销假关联文件
		documentFileService.deleteByLeaveId(leaveId);
		//删除请销假相关流程信息
		approvalFlowService.deleteByLeaveId(leaveId);
		applyUserService.deleteByLeaveId(leaveId);
		opinionService.deleteByLeaveId(leaveId);
		Response.ok();
	}
	
	/**
	 * @description:提交意见草稿保存
	 * @param id 主文件id
	 * @param opinionContent 意见内容
	 * @param tempType 意见版本
	 * @param opinionType 是否为手写签批
	 * @author:zhangyw
	 * @date:2019年8月20日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/saveTempOpinion")
	public void saveTempOpinion(String id,String opinionContent,String tempType,String opinionType){
		Opinion opinion = opinionService.queryLatestOpinion(id);
		if (opinion != null) {
			if(StringUtils.equals("0", opinion.getTempType())) {
				opinion.setOpinion(opinionContent);
				opinion.setOpinionType(opinionType);
				opinion.setOpinionDate(new Date());
				opinionService.update(opinion);
			}else {
				opinionService.saveOpinion(opinionContent, opinionType, tempType, CurrentUser.getUserId(), CurrentUser.getUsername(), id);
			}
		}else {
			opinionService.saveOpinion(opinionContent, opinionType, tempType, CurrentUser.getUserId(), CurrentUser.getUsername(), id);
		}
		//请假类别
		Response.json("result","success");
	}
	
	/**
	 * 编辑时查看
	 */
	@ResponseBody
	@RequestMapping("/editInfo")
	public ResponseEntity<JSONObject> editInfo(String id){
		int day2=0;
		JSONObject result = new JSONObject(true);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotEmpty(id)){
			Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
			Calendar calendar = Calendar.getInstance();
			if(tLeaveorback.getPlanTimeStart() != null){
				calendar.setTime(tLeaveorback.getPlanTimeStart());
			}
			if(tLeaveorback!=null && tLeaveorback.getPlanTimeEnd() !=null && tLeaveorback.getPlanTimeStart() !=null) {
			calendar.setTime(tLeaveorback.getPlanTimeEnd());
			day2 = (int)((tLeaveorback.getPlanTimeEnd().getTime()-tLeaveorback.getPlanTimeStart().getTime())/(86400000));//1000*3600*24
			}
			//int day2 = calendar.get(Calendar.DAY_OF_YEAR);
//			String xjts = Integer.toString(day2+1);//计算休假天数
				result.put("sqr", tLeaveorback.getProposer() == null ? "" : tLeaveorback.getProposer());
				result.put("deptDuty", tLeaveorback.getDeptDuty() == null ? "" : tLeaveorback.getDeptDuty());
				result.put("sqrId", tLeaveorback.getDeleteMark() == null ? "" : tLeaveorback.getDeleteMark());//申请人id
//				result.put("xjts", xjts);
				result.put("xjts", tLeaveorback.getLeaveDays() == null ? "" : tLeaveorback.getLeaveDays());
				result.put("csld", tLeaveorback.getLeaderName() == null ? "" : tLeaveorback.getLeaderName());
				result.put("csldId", tLeaveorback.getLeaderId() == null ? "" : tLeaveorback.getLeaderId());
				result.put("csspyj", tLeaveorback.getLeaderLeaveView() == null ? "" : tLeaveorback.getLeaderLeaveView());
				result.put("jld", tLeaveorback.getChairmanName() == null ? "" : tLeaveorback.getChairmanName());
				result.put("jldId", tLeaveorback.getChairmanId() == null ? "" : tLeaveorback.getChairmanId());
				result.put("jspyj", tLeaveorback.getChairmanView() == null ? "" : tLeaveorback.getChairmanView());
				
				result.put("linkMan", tLeaveorback.getLinkMan() == null ? "" : tLeaveorback.getLinkMan());
				result.put("undertaker", tLeaveorback.getUndertaker() == null ? "" : tLeaveorback.getUndertaker());
				result.put("undertakerId", tLeaveorback.getUndertakerId() == null ? "" : tLeaveorback.getUndertakerId());
				result.put("undertakerMobile", tLeaveorback.getUndertakerMobile() == null ? "" : tLeaveorback.getUndertakerMobile());

				result.put("sqrq", tLeaveorback.getApplicationDate() == null ? "" : sdf.format(tLeaveorback.getApplicationDate()));
				result.put("xjsjFrom", tLeaveorback.getPlanTimeStart()== null ? "" : sdf.format(tLeaveorback.getPlanTimeStart()));
				result.put("xjsjTo", tLeaveorback.getPlanTimeEnd()== null ? "" : sdf.format(tLeaveorback.getPlanTimeEnd()));
				result.put("mobile", tLeaveorback.getMobile() == null ? "" :  tLeaveorback.getMobile());
				result.put("place", tLeaveorback.getPlace()== null ? "" :  tLeaveorback.getPlace());
				//result.put("origin", tLeaveorback.getPlanTimeEnd()== null ? "" :  tLeaveorback.getOrigin());
				result.put("origin", tLeaveorback.getOrigin()== null ? "" :  tLeaveorback.getOrigin());
				result.put("orgId", tLeaveorback.getOrgId()== null ? "" :  tLeaveorback.getOrgId());
				result.put("orgName", tLeaveorback.getOrgName()== null ? "" :  tLeaveorback.getOrgName());
				result.put("vehicle", tLeaveorback.getVehicle()== null ? "" :  tLeaveorback.getVehicle());
				result.put("turnOver", tLeaveorback.getTurnOver()== null ? "" :  tLeaveorback.getTurnOver());
				result.put("weekendNum", tLeaveorback.getWeekendNum()== null ? "0" : String.valueOf(tLeaveorback.getWeekendNum()));
				result.put("holidayNum", tLeaveorback.getHolidayNum()== null ? "0" :  String.valueOf(tLeaveorback.getHolidayNum()));
				result.put("status", tLeaveorback.getStatus()== null ? 0 : tLeaveorback.getStatus());
				result.put("parentOrgId", tLeaveorback.getParentOrgId()== null ? 0 : tLeaveorback.getParentOrgId());

				DicHoliday qxjDicHoliday=dicHolidayService.queryByUserId(tLeaveorback.getDeleteMark());
				if(qxjDicHoliday !=null){
				    result.put("shouldTakDays",qxjDicHoliday.getShouldtakdays());
                }
				else{
				    result.put("shouldTakDays","0");
                }
				String xjlbId = tLeaveorback.getVacationSortId();
				if(xjlbId != null){
					DicVocationSort dicVocationSort = dicVocationSortService.queryObject(xjlbId);
							if(dicVocationSort != null){
								String xjlb	= dicVocationSort.getVacationSortId();
								result.put("lb", xjlb );
							}
				}else{
					result.put("lb", "");
				}
		}
		
		return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
	}
	
	/**
	 * @description:新增或修改请假单
	 * @param id 主文件QXJ_LEAVEORBACK 的 id
	 * @param model 前后交互model
	 * @author:zhangyw
	 * @date:2019年8月15日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/saveLeaveApplication")
	public void saveLeaveApplication(String id ,LeavebackSaveModel model) {
		JSONObject json = new JSONObject();
		Leaveorback leave=null;
		if(StringUtils.isNotBlank(id)) {
			leave = leaveorbackService.queryObject(id);
			if(leave == null ) {
				logger.info("QXJ_LEAVEORBACK表中找不到对应id为{}的记录",id);
				json.put("result", "fail");
				Response.json(json);
				return;
			}
		}else {
			leave = new Leaveorback();
		}
		this.toLeave(leave,model);
		//生成请假报批单并返回对应文件服务id
		String ofdId = exprotOfd(leave);
		//20200113添加
		//如果返回的id为failed，说明生成文件或者生成文件后转办出了问题，开发环境无法复现，暂时跟不到原因。文件转版失败，尝试三次转办后退出
		if(StringUtils.isEmpty(ofdId)||(StringUtils.isNotEmpty(ofdId)&&ofdId.equals("failed"))){
			for(int i=0;i<3;i++) {
				String ofdIdN = exprotOfd(leave);
				if(i==2&&(StringUtils.isEmpty(ofdIdN)||(StringUtils.isNotEmpty(ofdIdN)&&ofdIdN.equals("failed")))) {
					json.put("result","文件转版失败,请联系管理员");
					Response.json(json);
					return;
				}else if(StringUtils.isNotEmpty(ofdIdN)&&!ofdIdN.equals("failed")) {
					ofdId=ofdIdN;
					break;
				}
			}
		}
		//新增或保存
		if(StringUtils.isNotBlank(leave.getId())) {
			leaveorbackService.update(leave);
		}else {
			leave.setStatus(QxjStatusDefined.DAI_TI_JIAO);//字典项：0=草稿，10=审批中，30=审批完毕，20=已退回
			leaveorbackService.save(leave);
		}
		json.put("id", leave.getId());
		json.put("result", "success");
		//保存申请人信息-请假申请人多个走这里
		String sqrId = model.getSqrId();
		if (StringUtils.isNotBlank(sqrId)) {
			if (StringUtils.isBlank(id)) {
				this.saveApplyUserInfo(json,leave,model);
			}
		}
		if(StringUtils.equals(json.getString("result"), "success") && StringUtils.isBlank(model.getSjqjts())) {
			//保存或更新对应关系表
			DocumentFile documentFile = documentFileService.queryByLeaveId(id,"cpj");
			if(documentFile == null) {
				documentFile= new DocumentFile();
			}
			DocumentFile docFile = organizeDocFile(documentFile, ofdId, leave);
			if(null!=docFile && StringUtils.isNotBlank(docFile.getId())) {
				documentFileService.update(documentFile);
			}else {
				docFile.setId(UUIDUtils.random());
				documentFileService.save(documentFile);
			}
			json.put("id", json.get("id"));
		}
		Response.json(json);
	}

	private DocumentFile organizeDocFile(DocumentFile docFile, String ofdId,Leaveorback leave) {
		docFile.setFileServerFormatId(ofdId);
		if(StringUtils.isBlank(docFile.getId())) {
			docFile.setLeaveId(leave.getId());
			docFile.setFileName(leave.getProposer()+"请假报批单.ofd");
			docFile.setCreatedTime(new Date());
			docFile.setFileServerFormatId(ofdId);
			docFile.setFileServerStreamId(null);
			docFile.setSort(1);
			docFile.setFileType("cpj");
		}
		return docFile;
	}

	/**
	 *  保存申请人信息
	 * @param json JSON
	 * @param leave 请假人主表信息
	 * @param model 入参
	 * @return JSONObject
	 */
	private JSONObject saveApplyUserInfo(JSONObject json, Leaveorback leave, LeavebackSaveModel model) {
		String sqrId = model.getSqrId();
		ApplyUser applyUser;
		if (StringUtils.isNotBlank(sqrId)) {
			String[] applyUserIds = sqrId.split(",");
			for (String applyUserId : applyUserIds) {
				applyUser = new ApplyUser();
				applyUser.setId(UUIDUtils.random());
				applyUser.setLeaveId(leave.getId());
				applyUser.setApplyUserId(applyUserId);
				String[] userOrgInfo = this.queryCurrUserOrgInfo(applyUserId, json);
				if (StringUtils.equals(json.getString("result"), "fail")) {
					return json;
				}
				applyUser.setApplyUserName(userOrgInfo[0]);
				applyUser.setApplyDeptId(userOrgInfo[1]);
				applyUser.setApplyDeptName(userOrgInfo[2]);
				applyUser.setApplyOrgId(model.getOrgId());
				applyUser.setApplyOrgName(model.getOrgName());
				applyUserService.save(applyUser);
			}
		} else {
			logger.info("传入申请人IDS：{}",  sqrId);
			json.put("result","fail");
		}
		return json;
	}

	/**
	 *  查询申请人机构信息
	 * @param userId 申请人
	 * @param json json
	 * @return  String[]
	 */
	private String[] queryCurrUserOrgInfo(String userId, JSONObject json){
		String [] userInfo = new String[3];
		BaseAppUser baseAppUser = baseAppUserService.queryByUserId(userId);
		if (baseAppUser != null) {
			userInfo[0] = baseAppUser.getTruename();
			userInfo[1] = baseAppUser.getOrganid();
		} else {
			logger.info("根据当前请假人ID：{}，查不到用户信息", userId);
			json.put("result","fail");
			return userInfo;
		}
		BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(baseAppUser.getOrganid());
		if (baseAppOrgan != null) {
			userInfo[2] = baseAppOrgan.getName();
		}else {
			logger.info("根据当前请假人ID：{}，当前机构ID：{}，查不到用户机构信息", userId, baseAppUser.getOrganid());
			json.put("result","fail");
			return userInfo;
		}
		json.put("result","success");
		return userInfo;
	}
	//统一的新增或修改请假单
	private void toLeave(Leaveorback tLeaveorback, LeavebackSaveModel model) {
		String loginUserId = CurrentUser.getUserId();
		String starday = model.getXjsjFrom();// 开始日期
		String endday = model.getXjsjTo();//结束日期
		/*Map<String,Object> map = new HashMap<String, Object>();
		map.put("man", loginUserId);
		map.put("starday", starday);
		map.put("endday", endday);
		List<TLeaveorback> DatesList = tLeaveorbackService.queryList(map);
		if(DatesList != null && DatesList.size() > 0){
			json.put("result", "deal");
			json.put("msg", "请假时间段重复！");
			return json;
		}*/
		if(StringUtils.isNotEmpty(model.getSqrId())) {
			tLeaveorback.setDeleteMark(model.getSqrId());//申请人ids
		}
		tLeaveorback.setRegistrationDate(new Date());//设置登记日期
		if(StringUtils.isNotEmpty(model.getSqr())) {
			tLeaveorback.setProposer(model.getSqr());//申请人
		}
		if(StringUtils.isNotEmpty(model.getXjlb())) {
			tLeaveorback.setVacationSortId(model.getXjlb());//类别
		}
		if(StringUtils.isNotEmpty(model.getDeptDuty())) {
			tLeaveorback.setDeptDuty(model.getDeptDuty());//部职别
		}
		if(StringUtils.isNotEmpty(model.getLinkMan())) {
			tLeaveorback.setLinkMan(model.getLinkMan());//联系人
		}
		if(StringUtils.isNotEmpty(model.getLinkMan())) {
			tLeaveorback.setMobile(model.getMobile());//联系人电话
		}
		if(StringUtils.isNotEmpty(model.getLinkMan())) {
			tLeaveorback.setUndertaker(model.getUndertaker());//承办人
		}
		if(StringUtils.isNotEmpty(model.getUndertakerId())) {
			tLeaveorback.setUndertakerId(model.getUndertakerId());//承办人id
		}
		if(StringUtils.isNotEmpty(model.getUndertakerMobile())) {
			tLeaveorback.setUndertakerMobile(model.getUndertakerMobile());//承办人电话
		}
		if(StringUtils.isNotEmpty(model.getPlace())) {
			tLeaveorback.setPlace(model.getPlace());//地点
		}
		if(StringUtils.isNotEmpty(model.getOrigin())) {
			tLeaveorback.setOrigin(model.getOrigin());//请假事由
		}
		String sqrId = model.getSqrId();
		if (StringUtils.isNotBlank(sqrId)) {
			String[] leaverIds = sqrId.split(",");
			List<String> orgIds = new ArrayList<>();
			for (String leaverId : leaverIds) {
				BaseAppUser baseAppUser = baseAppUserService.queryByUserId(leaverId);
				orgIds.add(baseAppUser.getOrganid());
			}
			//单位id
			tLeaveorback.setOrgId(orgIds.toString().replace("[","").replace("]",""));
		}
		if(StringUtils.isNotEmpty(model.getOrgName())) {
			tLeaveorback.setOrgName(model.getOrgName());//单位名称
		}
		if(StringUtils.isNotEmpty(model.getVehicle())) {
			tLeaveorback.setVehicle(model.getVehicle());//交通工具
		}
		if(StringUtils.isNotEmpty(model.getTurnOver())) {
			tLeaveorback.setTurnOver(model.getTurnOver());//移交事宜
		}
		if(StringUtils.isNotEmpty(model.getParentOrgId())) {
			tLeaveorback.setParentOrgId(model.getParentOrgId());
		}
		if(StringUtils.isNotBlank(model.getXjts())) {
			tLeaveorback.setActualVocationDate(Integer.valueOf(model.getXjts()));//实际休假天数()
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(StringUtils.isNotBlank(model.getSqrq())) {
				tLeaveorback.setApplicationDate(sdf.parse(model.getSqrq()));//申请日期
			}
			if(StringUtils.isNotBlank(model.getXjsjFrom())) {
				tLeaveorback.setPlanTimeStart(sdf.parse(model.getXjsjFrom()));// 开始日期			
			}
			if(StringUtils.isNotBlank(model.getXjsjTo())) {
				tLeaveorback.setPlanTimeEnd(sdf.parse(model.getXjsjTo()));//结束日期
			}			
			if(StringUtils.isNotBlank(model.getSjrqFrom())) {
				tLeaveorback.setActualTimeStart(sdf.parse(model.getSjrqFrom()));//销假实际开始日期
			}			
			if(StringUtils.isNotBlank(model.getSjrqTo())) {
				tLeaveorback.setActualTimeEnd(sdf.parse(model.getSjrqTo()));//销假实际结束日期
			}						
			if(StringUtils.isNotEmpty(model.getXjts())){
				tLeaveorback.setLeaveDays(Integer.valueOf(model.getXjts()));//休假天数
			}	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//销假状态
		if(StringUtils.isBlank(model.getSjqjts())) {
			tLeaveorback.setBackStatusId("0");
		}else {
			tLeaveorback.setBackStatusId("1");
		}
		if(StringUtils.isNotBlank(model.getSjqjts())) {
			tLeaveorback.setActualVocationDate(Integer.valueOf(model.getSjqjts()));//“实际请假天数”
		}
		if(model.getHolidayNum()!=null){
			tLeaveorback.setHolidayNum(model.getHolidayNum());//休假期间法定节假日天数
		}
		if(model.getWeekendNum()!=null){
			tLeaveorback.setWeekendNum(model.getWeekendNum());//休假期间周六日天数
		}
		
	}

	/**
	 *
	 * @param sqrId 请假人IDS
	 * @return String
	 */
	private String connectLeaverOrgId(String sqrId) {
		Set<String> orgIds = new HashSet<>();
		if (StringUtils.isNotBlank(sqrId)) {
			Arrays.asList(sqrId.split(",")).forEach(userId -> {
				BaseAppUser baseAppUser = baseAppUserService.queryByUserId(userId);
				orgIds.add(baseAppUser.getOrganid());
			});
		}
		return orgIds.toString().replace("[","").replace("]","");
	}

	/**
	 * @description:列表及状态栏数字统计
	 * @param page
	 * @param pagesize
	 * @param sqrqFrom
	 * @param sqrqTo
	 * @param documentStatus
	 * @param xjlb
	 * @param xjzt
	 * @param planTimeStart
	 * @param planTimeEnd
	 * @param username
	 * @param filefrom
	 * @author:zhangyw
	 * @date:2019年9月4日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/applicationList")
	public void list(Integer page, Integer pagesize,String sqrqFrom, String sqrqTo, String documentStatus,String xjlb,String xjzt,
					 String planTimeStart, String planTimeEnd,String username,String filefrom){
		String loginUserId=CurrentUser.getUserId();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> map1 = new HashMap<>();
		map.put("loginUserId", loginUserId);
		map1.put("loginUserId", loginUserId);
		if(StringUtils.equals("qxjsq", filefrom)) {
			map.put("creatorId", loginUserId);
			map1.put("creatorId", loginUserId);
		}else if(StringUtils.equals("qxjsp", filefrom)){
			map.put("flowPeople", "yes");
			map1.put("flowPeople", "yes");
		}		
		if(StringUtils.isNotBlank(documentStatus)){
			if(StringUtils.equals("11", documentStatus)) {
				map.put("status", QxjStatusDefined.DAI_SHEN_PI);
				map.put("receiverIsMe", "yes");
			}else {
				if(StringUtils.equals("qxjsp", filefrom)) {
					map.put("receiverIsMe", "no");
				}
				map.put("status", documentStatus);
			}
		}
		if(StringUtils.isNotBlank(sqrqFrom)){
			map.put("sqrqFrom", sqrqFrom);
			map1.put("sqrqFrom", sqrqFrom);
		}
		if(StringUtils.isNotBlank(sqrqTo)){
			map.put("sqrqTo", sqrqTo);
			map1.put("sqrqTo", sqrqTo);
		}
		if(StringUtils.isNotBlank(planTimeStart)){
			map.put("planTimeStart", planTimeStart);
			map1.put("planTimeStart", planTimeStart);
		}
		if(StringUtils.isNotBlank(planTimeEnd)){
			map.put("planTimeEnd", planTimeEnd);
			map1.put("planTimeEnd", planTimeEnd);
		}
		if(StringUtils.isNotBlank(xjlb)){
			map.put("xjlb", xjlb);
			map1.put("xjlb", xjlb);
		}
		if(StringUtils.isNotBlank(xjzt)){
			map.put("xjzt", xjzt);
			map1.put("xjzt", xjzt);
		}
		if(StringUtils.isNotBlank(username)) {
			map.put("sqrName", username);
			map1.put("sqrName", username);
		}
		PageHelper.startPage(page, pagesize);
		List<Leaveorback> leaveList = leaveorbackService.queryNewList(map);
		List<Leaveorback> allLeaveList = leaveorbackService.queryNewList(map1);
		int[] count = {0,0,0,0,0};
		count[0] = allLeaveList.size();
		for (Leaveorback leave : allLeaveList) {
			if(StringUtils.equals("qxjsq", filefrom)) {
				//状态栏统计
				if (leave.getStatus()==0) {//待提交
					count[1]+=1;
				} else if (leave.getStatus()==20) {//已退回
					count[2]+=1;
				} else if (leave.getStatus()==10) {//审批中
					count[3]+=1;
				} else if(leave.getStatus()==30){//已通过
					count[4]+=1;
				}
			}else {
				if (leave.getStatus()==20) {//已退回
					count[2]+=1;
				} else if (leave.getStatus()==10) {
					if(leave.getReceiverIsMe() !=null && leave.getReceiverIsMe()==1) {
						count[1]+=1;//待审批
					}else {//审批中
						count[3]+=1;
					}
				} else if(leave.getStatus()==30){//已通过
					count[4]+=1;
				}
			}
		}
		if(leaveList !=null && leaveList.size()>0) {
			for (Leaveorback leave : leaveList) {
				//请假类别
				if(StringUtils.isNotBlank(leave.getVacationSortId())) {
					DicVocationSort dicVocation =  dicVocationSortService.queryObject(leave.getVacationSortId());
					leave.setVacationSortName(dicVocation.getVacationSortId());
				}
				/*原系统里的：实际请假时间（//实际开始时间//实际结束时间//计算休假天数）
				if(leave.getActualTimeStart() !=null && leave.getActualTimeEnd() != null && !"0".equals(leave.getBackStatusId())){
					int sjxjts = leave.getActualVocationDate();
					String sjqjsj = DateUtil.format(leave.getActualTimeStart()) + "至" + DateUtil.format(leave.getActualTimeEnd())+"("+sjxjts+"天)";
					leave.setStartEndDateStr(sjxjts);
				}else{
					leave.setStartEndDateStr("");
				}*/
				//请假时间
				if(leave.getPlanTimeStart() != null && leave.getPlanTimeEnd() != null){
					Integer xjts = leave.getLeaveDays();//已改为手动输入；
					String qjsj = DateUtil.format(leave.getPlanTimeStart()) + "至" + DateUtil.format(leave.getPlanTimeEnd())+"("+xjts+"天)";
					leave.setPlanTimeStartEnd(qjsj);
				}else{
					leave.setPlanTimeStartEnd("");
				}
				//撤回按钮
				ApprovalFlow latestFlow = approvalFlowService.queryLatestFlowRecord(leave.getId());
				if (latestFlow != null) {
					if (StringUtils.equals(loginUserId, latestFlow.getCreatorId()) && (QxjStatusDefined.YI_TONG_GUO > leave.getStatus())) {
						//撤回按钮显示标志
						if(latestFlow.getIsView() !=null && 0==latestFlow.getIsView() && !StringUtils.equals("11",latestFlow.getFlowType())) {
							leave.setWithdrawFlag("1");
						}
					}
				}
				if(StringUtils.equals("qxjsp", filefrom)) {
					String[] qjrid = leave.getDeleteMark().split(",");
//					String[] qjrname = leave.getProposer().split(",");
					String offDays="";
					String leavedDays="";
					String noLeaveDays="";
					int noLeaveMinDays=0;
					for(int i=0;i<qjrid.length;i++) {
						DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(qjrid[i]);
						int shouldtakdays=0;
						if(qxjDicHoliday!=null) {
							shouldtakdays = qxjDicHoliday.getShouldtakdays().intValue();
						}
						int countActualRestDays = countActualRestDaysManager.countActualRestDays(qjrid[i],DateUtil.format(leave.getPlanTimeEnd()));
						int other= shouldtakdays- countActualRestDays;
						if(other<0) {
							other=0;
						}
//						offDays+=(qjrname[i]+"("+shouldtakdays+"天),");
//						leavedDays=(qjrname[i]+"("+countActualRestDays+"天),");
//						noLeaveDays+=(qjrname[i]+"("+other+"天),");
						if(i==0) {
							noLeaveMinDays=other;
						}else {
							if(noLeaveMinDays>other) {
								noLeaveMinDays=other;
							}
						}
						offDays+=(shouldtakdays+",");
						leavedDays+=(countActualRestDays+",");
						noLeaveDays+=(other+",");
					}
					if(StringUtils.isNotEmpty(offDays)) {
						offDays=offDays.substring(0,offDays.length()-1);
					}
					if(StringUtils.isNotEmpty(leavedDays)) {
						leavedDays=leavedDays.substring(0,leavedDays.length()-1);
					}
					if(StringUtils.isNotEmpty(noLeaveDays)) {
						noLeaveDays=noLeaveDays.substring(0,noLeaveDays.length()-1);
					}
					leave.setOffDays(offDays);
					leave.setLeavedDays(leavedDays);
					leave.setNoLeaveDays(noLeaveDays);
					leave.setNoLeaveMinDays(noLeaveMinDays);
				}
			}
		}
		GwPageUtils pageUtil = new GwPageUtils(leaveList);
		pageUtil.setClist(count);
		Response.json(pageUtil);
	}

	//生成word模板
	private String exprotOfd(Leaveorback item) {
		//模板中用的起止时间
		String  leaderName="局领导";
		Map<String, Object> params = new HashMap<String, Object>();
		if(item.getApplicationDate()!=null) {
			//控制word模板日志靠右对齐
			this.controllerWordDateFormat(params, item);
			params.put("applicationMonth", DateUtil.format(item.getApplicationDate(), "MM"));
			params.put("applicationDay", DateUtil.format(item.getApplicationDate(), "dd"));
		}
		if(item.getPlanTimeStart()!=null && item.getPlanTimeEnd()!=null) {
			params.put("startEndDateStr", DateUtil.format(item.getPlanTimeStart(), "MM月dd日") + "至"
					+ DateUtil.format(item.getPlanTimeEnd(), "MM月dd日"));
		}
		if(StringUtils.isNotBlank(item.getDeleteMark())) {
			String[] ids = item.getDeleteMark().split(",");
			for (String userId : ids) {
				boolean roleType = commonQueryManager.isJz(userId);
				if(roleType) {
					leaderName="首长";
					break;
				}
			}
		}
		params.put("leaderName", leaderName);
		params.put("item", item);
		//String templateName = "/com/css/app/qxjgl/leaveorback/dao/app.qxjgl.word.model.xml";
		String templateName = "/com/css/app/qxjgl/business/dao/app.qxjgl.word.qjspd.xml";
		String servicepath=baseAppConfigService.getValue("convertServer");
		String docName = item.getProposer()+DateUtil.format(new Date(), "yyyyMMdd-HHmmss")+".doc";
		String fileId=getFileId(params, docName, templateName,servicepath);
		return fileId;
	}

	/**
	 *  控制模板日期靠右对齐
	 * @param params params
	 * @param item item
	 */
	private void controllerWordDateFormat(Map<String, Object> params, Leaveorback item) {
		int length = item.getOrgName().length();
		int blankLength = 36;
		Date applicationDate = item.getApplicationDate();
		BaseAppConfig baseAppConfig = baseAppConfigService.queryObject("qxj_blank_length");
		if(baseAppConfig != null) {
			blankLength = Integer.parseInt(baseAppConfig.getValue());
		}
		int blankCounts = blankLength - ((length - 1) * 2);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < blankCounts; i++) {
			stringBuilder.append(" ");
		}
		params.put("applicationYear", stringBuilder.toString()+this.applicationYear(applicationDate));
	}

	/**
	 * 日期格式转化
	 * @param date 申请日期
	 * @return 格式化后的日期
	 */
	private String applicationYear(Date date){
		return DateUtil.format(date,"yyyy");
	}
	private String getFileId(Map<String, Object> params, String docName, String templateName, String ofdUrl) {
		String fileId = null;
		ByteArrayOutputStream wordOuts = WordUtils.createDoc(params, templateName);
		byte[] bytes=wordOuts.toByteArray();
		InputStream in=new  ByteArrayInputStream(bytes);
		HTTPFile httpFile=HTTPFile.save(in,docName);
		
		//待转版和合并文件的本地路径
		List<String> filePathList = new ArrayList<String>();

		//本地临时文件的路径
		String path = appConfig.getLocalFilePath() + UUIDUtils.random() + "." + httpFile.getSuffix();
		try {
			FileUtils.copyFile(new File(httpFile.getFilePath()) , new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(path)){
			filePathList.add(path);
		}
		if(filePathList != null && filePathList.size() > 0){
			fileId = OfdTransferUtil.mergeConvertLocalToOFD(filePathList);
			logger.info("转办后版式文件id:{}", fileId);
			if(StringUtils.isNotBlank(fileId)){
				//删除临时文件
				for(String delFilePath : filePathList){
					if(new File(delFilePath).exists()){
						new File(delFilePath).delete();
					}
				}
			}
			else{
				System.out.println("================");
				System.out.println("未获取到转版后的id");
				System.out.println("================");
				fileId = "failed";
			}
		}
		
		return fileId;
	}
	/**
	 * 检查数科转换是否ok
	 */
	@ResponseBody
	@RequestMapping("/checkSuwellServiceIsConnect")
	public void checkSuwellServiceIsConnect() {
		String convertServiceUrl = "";
		BaseAppConfig config = baseAppConfigService.queryObject(AppConstant.APP_CONVERT_SERVER);
		if(config != null){
			convertServiceUrl = config.getValue();
		}
		boolean result = false;
		if(StringUtils.isNotBlank(convertServiceUrl)) {
			String addr = convertServiceUrl.substring(convertServiceUrl.indexOf("http://") + 7,convertServiceUrl.lastIndexOf(":"));
			String num = convertServiceUrl.substring(convertServiceUrl.lastIndexOf(":") + 1,convertServiceUrl.lastIndexOf(":") + 5);
			int port = Integer.parseInt(num);
			Socket connect = new Socket();
			try {
				connect.connect(new InetSocketAddress(addr, port), 2000);
				result = connect.isConnected();
			}catch(IOException e) {
				e.printStackTrace();
			}finally{
				try {
					connect.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		Response.json("result",result);
	}
	
	
}
