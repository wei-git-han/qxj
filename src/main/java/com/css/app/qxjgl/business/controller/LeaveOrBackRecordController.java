package com.css.app.qxjgl.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgmapped.entity.BaseAppOrgMapped;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.addbase.constant.AppConstant;
import com.css.app.qxjgl.business.entity.DicCalender;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.app.qxjgl.business.manager.CountActualRestDaysManager;
import com.css.app.qxjgl.business.service.*;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;
import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.app.qxjgl.userDeptCopy.service.QxjUserdeptCopyService;
import com.css.base.entity.SSOUser;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.DateUtil;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.processor.ITextNodeProcessorMatcher;
import org.yaml.snakeyaml.events.Event;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * 请销假表
 *
 * @author 中软信息系统工程有限公司
 * @date 2019-09-04  13:33:33
 */
@RestController
@RequestMapping("/app/qxjgl/leaveOrBack")
public class LeaveOrBackRecordController {
    private final static Logger logger = LoggerFactory.getLogger(LeaveApplyFlowController.class);
    @Autowired
    private LeaveorbackService leaveorbackService;
    @Autowired
    private DicUsersService dicUsersService;
    @Autowired
    private DicVocationSortService dicVocationSortService;
    @Autowired
    private BaseAppOrgMappedService baseAppOrgMappedService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private DicHolidayService dicHolidayService;
    @Autowired
    private CommonQueryManager commonQueryManager;
    @Autowired
    private DicCalenderService dicCalenderService;
    @Autowired
	private CountActualRestDaysManager countActualRestDaysManager;
    @Autowired
    private ApplyUserService applyUserService;

    @Value("${filePath}")
    private String filePath;
    @Autowired
    private QxjUserdeptCopyService qxjUserdeptCopyService;

    private List<Leaveorback> queryQXJListForXls=null;
    
//    @Scheduled(cron = "0 0 10,14,16 * *")
//    @RequestMapping(value = "/finishXjTask" , method = RequestMethod.GET)
//    public void finishXjTask(String id) {
//    	String BackStatusId = leaveorbackService.getBackStatusId(id);
//    	String status = leaveorbackService.getStatus(id);
//    	if("1".equals(BackStatusId) && "30".equals(status)) {
//    		
//    	}
//    }
    
    /**
     * 判断该用户是否为该用户部门的局管理员
     * */
    @RequestMapping(value = "/getIsJuGuanLi" , method = RequestMethod.GET)
    public Map getIsJuGuanLi(){
    	String userId = CurrentUser.getUserId();
    	List<String> list = leaveorbackService.getIsJuGuanLi(userId);
    	Map map = new HashMap();
    	String flag = "false";
    	for(int i = 0; i<list.size(); i++) {
    		String selUserId = list.get(i);
    		if(userId.equals(selUserId)) {
    			flag = "true";
    		}
    	}
    	map.put("flag", flag);
    	return map;
    	
    }
    
    @RequestMapping(value = "/updateWeekendHolidayNum" , method = RequestMethod.GET)
    public Map updateWeekendHolidayNum(Integer weekendnum,Integer holidaynum,Integer actualVocationDate,String id) {
    	Map map = new HashMap();
    	try{
        	Leaveorback leaveorback = new Leaveorback();
        	leaveorback.setActualVocationDate(actualVocationDate);
        	leaveorback.setWeekendNum(weekendnum);
        	leaveorback.setHolidayNum(holidaynum);
        	leaveorback.setId(id);
        	leaveorbackService.updateWeekendHolidayNum(leaveorback);
        	map.put("result", "success");
        	return map;
    	}catch(Exception e){
    		map.put("result", "fail");
    		return map;
    	}
    }
    
    @RequestMapping("/getQXJlist")
    public void getQXJlist(String userid,String deptid,String planTimeStart,String planTimeEnd,Integer page, Integer rows,String[] documentStatus,
    		String operateFlag,String xjlb) {
        Map<String, Object> paraterLeaderMap = new HashMap<>();
        List<Leaveorback> queryQXJList = null;
        int count = 0;
        if (StringUtils.isNotBlank(deptid)) {
            List<BaseAppOrgan> auOrgLis = leaveorbackService.queryBelongOrg(deptid);
            paraterLeaderMap.put("deptId", this.getDeptIds(auOrgLis));
        }
        paraterLeaderMap.put("sqrId", userid);
        paraterLeaderMap.put("planTimeStart", planTimeStart);
        paraterLeaderMap.put("planTimeEnd", planTimeEnd);
        paraterLeaderMap.put("operateFlag", operateFlag);
        paraterLeaderMap.put("xjlb", xjlb);

        SSOUser loginUser = CurrentUser.getSSOUser();
        String userId = loginUser.getUserId();
        if (documentStatus != null&&1==documentStatus.length) {

            switch (documentStatus[0]) {
                case "1"://申请中
                    paraterLeaderMap.put("statusForQuery", "6");//申请中包括 审批中和已驳回
                    break;
                case "2"://执行中
                    paraterLeaderMap.put("backStatusId", "0");// 销假状态0 代表未销假
                    paraterLeaderMap.put("status", 30);//
                    break;
                case "3"://已销假
                    paraterLeaderMap.put("backStatusId", "1");// 销假状态1 代表已经销假
                    break;
            }
        }else if(documentStatus != null&&2==documentStatus.length){
            Integer para1=Integer.parseInt(documentStatus[0]);
            Integer para2=Integer.parseInt(documentStatus[1]);
            if (para1 != 0 && para2 != 0) {
                switch (para1+para2) {
                    case 3://申请中+执行中
                        paraterLeaderMap.put("statusForQuery", "3");
                        break;
                    case 4://申请中+已销假
                        paraterLeaderMap.put("statusForQuery", "4");
                        break;
                    case 5://已销假+执行中
                        paraterLeaderMap.put("statusForQuery", "5");
                        break;
                }
            }
        }
        // 非管理员的情况下走下面
        int roleType = commonQueryManager.roleType(userId);
//        if (!isAdministratior()) {
            if (roleType != 0) {
                // 局长 1 处长0
//                String roleCode = userEntity.getRolecode();
                //当前用户所在局ID
                if (roleType == 1) {
                    DicUsers userEntity = dicUsersService.queryByUserId(userId, "0");
                    String deptIds = userEntity.getDeptid();
                    List<BaseAppOrgan> auOrgLis = leaveorbackService.queryBelongOrg(deptIds);
                    String[] deptIds1 = this.getDeptIds(auOrgLis);
                    if (StringUtils.isBlank(deptid)) {
                        paraterLeaderMap.put("deptId", deptIds1);
                    } else {
                        if (Arrays.asList(deptIds1).contains(deptid)) {
                            //处长只能看处
                            paraterLeaderMap.put("deptIdOrg", deptid);
                        } else {
                            //其他禁止查看
                            paraterLeaderMap.put("deptIdOrg", "0");
                        }
                    }
                } else if (roleType == 2 || roleType == 3) {
                    String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
                    List<String> deptIdList = qxjUserdeptCopyService.queryDeptIds(orgId);//查询该部门的人是否有其他部门转过来的，查请假信息的时候都要查出来
                    List<BaseAppOrgan> deptIds = leaveorbackService.queryBelongOrg(orgId);
                    String[] deptIds1 = this.getDeptIds(deptIds);
                    deptIds1 = this.getOtherDeptIds(deptIds1,deptIdList);
                    if (StringUtils.isBlank(deptid)) {
                        paraterLeaderMap.put("deptId", deptIds1);
                    } else {
                        if (Arrays.asList(deptIds1).contains(deptid)) {
                            //局长看局内
                            List<BaseAppOrgan> deptIds11 = leaveorbackService.queryBelongOrg(deptid);
                            paraterLeaderMap.put("deptId", this.getDeptIds(deptIds11));
                        } else {
                            //其他局禁用
                            paraterLeaderMap.put("deptIdOrg", "1");
                        }
                    }
                } /*else if (roleType == 6 || roleType == 5 || roleType == 4){
                    logger.info("当前用户ID:{}的角色类型：{}，不正确。", userId, roleCode);
                }*/
                PageHelper.startPage(page, rows);
                queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
                queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
                dealData(queryQXJList);
            } else {
                PageHelper.startPage(page, rows);
                paraterLeaderMap.put("sqrId", userId);
                queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
                queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
                dealData(queryQXJList);
            }
       /*  }else {
            PageHelper.startPage(page, rows);
            queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
            queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
            dealData(queryQXJList);
        }*/
        String Scount = String.valueOf(count);
        PageUtils pageUtil = new PageUtils(queryQXJList,Scount);
        Response.json(pageUtil);
        /*int type = commonQueryManager.roleType(CurrentUser.getUserId());
        Response.json(String.valueOf(type),pageUtil);*/
    }
    
    @RequestMapping("/getQXJcount")
    public Map getQXJcount() {
    	String deptid = "";
    	Map map = new HashMap();
        Map<String, Object> paraterLeaderMap = new HashMap<>();
        List<Leaveorback> queryQXJList = null;
        int count = 0;

        SSOUser loginUser = CurrentUser.getSSOUser();
        String userId = loginUser.getUserId();
        
        // 非管理员的情况下走下面
        int roleType = commonQueryManager.roleType(userId);
//        if (!isAdministratior()) {
        if (roleType != 0) {
            // 局长 1 处长0
//            String roleCode = userEntity.getRolecode();
            //当前用户所在局ID
            if (roleType == 1) {
                DicUsers userEntity = dicUsersService.queryByUserId(userId, "0");
                String deptIds = userEntity.getDeptid();
                List<BaseAppOrgan> auOrgLis = leaveorbackService.queryBelongOrg(deptIds);
                String[] deptIds1 = this.getDeptIds(auOrgLis);
                if (StringUtils.isBlank(deptid)) {
                    paraterLeaderMap.put("deptId", deptIds1);
                } else {
                    if (Arrays.asList(deptIds1).contains(deptid)) {
                        //处长只能看处
                        paraterLeaderMap.put("deptIdOrg", deptid);
                    } else {
                        //其他禁止查看
                        paraterLeaderMap.put("deptIdOrg", "0");
                    }
                }
            } else if (roleType == 2 || roleType == 3) {
                String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
                List<String> deptIdList = qxjUserdeptCopyService.queryDeptIds(orgId);//查询该部门的人是否有其他部门转过来的，查请假信息的时候都要查出来
                List<BaseAppOrgan> deptIds = leaveorbackService.queryBelongOrg(orgId);
                String[] deptIds1 = this.getDeptIds(deptIds);
                deptIds1 = this.getOtherDeptIds(deptIds1,deptIdList);
                if (StringUtils.isBlank(deptid)) {
                    paraterLeaderMap.put("deptId", deptIds1);
                } else {
                    if (Arrays.asList(deptIds1).contains(deptid)) {
                        //局长看局内
                        List<BaseAppOrgan> deptIds11 = leaveorbackService.queryBelongOrg(deptid);
                        paraterLeaderMap.put("deptId", this.getDeptIds(deptIds11));
                    } else {
                        //其他局禁用
                        paraterLeaderMap.put("deptIdOrg", "1");
                    }
                }
            } /*else if (roleType == 6 || roleType == 5 || roleType == 4){
                logger.info("当前用户ID:{}的角色类型：{}，不正确。", userId, roleCode);
            }*/
//            PageHelper.startPage(page, rows);
            queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
            queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
            count = leaveorbackService.selcount(paraterLeaderMap);
//            dealData(queryQXJList);
        } else {
//                PageHelper.startPage(page, rows);
                paraterLeaderMap.put("sqrId", userId);
                queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
                queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
                count = leaveorbackService.selcount(paraterLeaderMap);
//                for(int i = 0;i<queryQXJList.size();i++) {
//                	String status = queryQXJList.get(i).getStatus().toString();
//                	String backStatusId = queryQXJList.get(i).getBackStatusId();
//                	if("30".equals(status) && "0".equals(backStatusId)) {
//                		count++;
//                	}
//                }
//                dealData(queryQXJList);
            }
       /*  }else {
            PageHelper.startPage(page, rows);
            queryQXJList = leaveorbackService.queryQXJList(paraterLeaderMap);
            queryQXJListForXls = leaveorbackService.queryQXJList(paraterLeaderMap);
            dealData(queryQXJList);
        }*/
        map.put("count", count);
        return map;
    }
    
    
    private String[] getDeptIds(List<BaseAppOrgan> auOrgLis){
        String[] deptIds = new String[auOrgLis.size()];
        for (int i = 0; i < auOrgLis.size(); i++) {
            String id = auOrgLis.get(i).getId();
            deptIds[i] = id;
        }
        return deptIds;
    }

    public String[] getOtherDeptIds(String[] deptIds,List<String> deptIdList){
        List list = new ArrayList();
        for(int i=0;i<deptIds.length;i++){
            list.add(deptIds[i]);
        }
        for(int j=0;j<deptIdList.size();j++){
            list.add(deptIdList.get(j));
        }

        String[] deptIds1 =new String[list.size()];
        for(int m=0;m<list.size();m++){
            deptIds1[m] = (String)list.get(m);
        }
        return deptIds1;
    }
    private void dealData(List<Leaveorback> queryQXJList) {
        for (int i = 0; i < queryQXJList.size(); i++) {
            Leaveorback Tleaveorback = queryQXJList.get(i);
            String backStatusId = Tleaveorback.getBackStatusId()== null ?"0" :Tleaveorback.getBackStatusId();
            if(StringUtils.isNotEmpty(Tleaveorback.getVacationSortId())){
                DicVocationSort dicVocationSort =  dicVocationSortService.queryObject(Tleaveorback.getVacationSortId());
                if(dicVocationSort != null){
                    String qjlb = dicVocationSort.getVacationSortId();
                    Tleaveorback.setVacationSortName( qjlb == null ? "" : qjlb);//请假类别
                }
            }else{
                Tleaveorback.setVacationSortName("");
            }

            //未销假显示请假的起止日期，已销假显示销假的起止日期
            if("1".equals(backStatusId)) {//1 代表已销假
                if(Tleaveorback.getActualTimeStart()==null || Tleaveorback.getActualTimeEnd()==null) {
                    Tleaveorback.setPlanTimeStartEnd("");//起止日期
                }else {
                    String actualTimeStart = new SimpleDateFormat("yyyy-MM-dd").format(Tleaveorback.getActualTimeStart());
                    String actualTimeEnd = new SimpleDateFormat("yyyy-MM-dd").format(Tleaveorback.getActualTimeEnd());
                    Tleaveorback.setPlanTimeStartEnd(actualTimeStart+"~"+actualTimeEnd);//起止日期
                }
                if(Tleaveorback.getStatus().equals(30)) {
                	Tleaveorback.setStatus(32);
                }
            }else {
                if(Tleaveorback.getPlanTimeStart()==null || Tleaveorback.getPlanTimeEnd()==null ) {
                    Tleaveorback.setPlanTimeStartEnd("");//起止日期
                }else {
                    String planTimeStart = new SimpleDateFormat("yyyy-MM-dd").format(Tleaveorback.getPlanTimeStart());
                    String planTimeEnd = new SimpleDateFormat("yyyy-MM-dd").format(Tleaveorback.getPlanTimeEnd());
                    Tleaveorback.setPlanTimeStartEnd(planTimeStart+"~"+planTimeEnd);//起止日期
                }
                if(Tleaveorback.getPlanTimeEnd().before(new Date())&&Tleaveorback.getStatus().equals(30)) {
                	Tleaveorback.setStatus(31);
                }
            }
            Tleaveorback.setShouldTakDays(Tleaveorback.getShouldTakDays()==null?0:Tleaveorback.getShouldTakDays());//应休天数
            Tleaveorback.setBackStatusId(backStatusId);//销假状态
            String[] qjrid = Tleaveorback.getDeleteMark().split(",");
			String offDays="";
			String leavedDays="";
			String noLeaveDays="";
			int noLeaveMinDays=0;
			for(int j=0;j<qjrid.length;j++) {
				DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(qjrid[j]);
				int shouldtakdays=0;
				if(qxjDicHoliday!=null) {
					shouldtakdays = qxjDicHoliday.getShouldtakdays().intValue();
				}
				int countActualRestDays = countActualRestDaysManager.countActualRestDays(qjrid[j],DateUtil.format(Tleaveorback.getPlanTimeEnd()));
				int other= shouldtakdays- countActualRestDays;
				if(other<0) {
					other=0;
				}
				if(j==0) {
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
			Tleaveorback.setOffDays(offDays);
			Tleaveorback.setLeavedDays(leavedDays);
			Tleaveorback.setNoLeaveDays(noLeaveDays);
			Tleaveorback.setNoLeaveMinDays(noLeaveMinDays);
        }
    }
    private Boolean isAdministratior() {
        BaseAppOrgMapped mapped = (BaseAppOrgMapped) baseAppOrgMappedService.orgMappedByOrgId(null, "root",
                AppConstant.APP_QXJGL);
        if (mapped !=null && CurrentUser.getIsManager(mapped.getAppId(), mapped.getAppSecret())==false) {
            return false;
        } else {
            return true;
        }
    }
    @RequestMapping("exportQXJList")
    public void getQXJlist() {
        InputStream is = null;
        Map<String,Object> resultMap =new HashMap<String,Object>();
//		String time = new SimpleDateFormat("yyyy-MM-dd.HH:mm:SS").format(new Date());
        File tempFile=new File(filePath, "请假情况详情.xls");
        if(tempFile.exists()){
            tempFile.delete();
        }else{
            tempFile.getParentFile().mkdirs();
        }
        try {
            dealData(queryQXJListForXls);
            is = fileInfoService.createQXJExcel(queryQXJListForXls,tempFile.getAbsolutePath());
            resultMap.put("fileUrl", tempFile.getAbsoluteFile());
            resultMap.put("fileName",tempFile.getName());
            resultMap.put("result","success");
        } catch (Exception e) {
            Response.error(500,"间隔时间太久，请重新刷新页面后再导出Excle！");
            e.printStackTrace();
        }
        Response.download("请假情况详情.xls", is);
    }
    /*sqrq	 申请日期
	sqr		 申请人
	sqrID	申请人ID
	xjlb	 休假类别
	xjsjFrom 开始时间
	xjsjTo	 结束时间
	xjts     休假天数
	csld	 处室领导
	csldID	 处室领导ID
	csspyj	处室领导审批意见
	jld		局领导
	jldID	局领导ID
	jspyj   局领导审批意见
	*/
    /**
     * 编辑时查看
     */
    @RequestMapping("/info")
    public ResponseEntity<JSONObject> info(String id){
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
            result.put("sqrID", tLeaveorback.getDeleteMark() == null ? "" : tLeaveorback.getDeleteMark());//申请人id
//				result.put("xjts", xjts);
            result.put("xjts", tLeaveorback.getLeaveDays() == null ? "" : tLeaveorback.getLeaveDays());
            result.put("csld", tLeaveorback.getLeaderName() == null ? "" : tLeaveorback.getLeaderName());
            result.put("csldId", tLeaveorback.getLeaderId() == null ? "" : tLeaveorback.getLeaderId());
            result.put("csspyj", tLeaveorback.getLeaderLeaveView() == null ? "" : tLeaveorback.getLeaderLeaveView());
            result.put("jld", tLeaveorback.getChairmanName() == null ? "" : tLeaveorback.getChairmanName());
            result.put("jldId", tLeaveorback.getChairmanId() == null ? "" : tLeaveorback.getChairmanId());
            result.put("jspyj", tLeaveorback.getChairmanView() == null ? "" : tLeaveorback.getChairmanView());

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

            DicHoliday qxjDicHoliday=dicHolidayService.queryByUserId(tLeaveorback.getDeleteMark());
            if(qxjDicHoliday !=null) {
                result.put("shouldTakDays", qxjDicHoliday.getShouldtakdays());
            } else {
                result.put("shouldTakDays", "0");
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
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    /**
     * 销假编辑
	 */
    @RequestMapping("/xjinfo")
    public ResponseEntity<JSONObject> xjinfo(String id){
        JSONObject result = new JSONObject(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotEmpty(id)){
            Leaveorback tLeaveorback = leaveorbackService.queryObject(id);
            Calendar calendar = Calendar.getInstance();
            if(tLeaveorback.getPlanTimeStart() != null){
                calendar.setTime(tLeaveorback.getActualTimeStart());
            }
            int day1 = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(tLeaveorback.getActualTimeEnd());
            int day2 = calendar.get(Calendar.DAY_OF_YEAR);
//			String sjxjts = Integer.toString(day2-day1+1);//计算休假天数
            result.put("csld", tLeaveorback.getLeaderName() == null ? "" : tLeaveorback.getLeaderName());
            result.put("csldId", tLeaveorback.getLeaderId() == null ? "" : tLeaveorback.getLeaderId());
            result.put("csspyj", tLeaveorback.getLeaderBackView() == null ? "" : tLeaveorback.getLeaderBackView());
            result.put("sjrqFrom",sdf.format(tLeaveorback.getActualTimeStart())  == null ? "" : sdf.format(tLeaveorback.getActualTimeStart()));
            result.put("sjrqTo", sdf.format(tLeaveorback.getActualTimeEnd()) == null ? "" : sdf.format(tLeaveorback.getActualTimeEnd()));
            result.put("mobile", tLeaveorback.getMobile() == null ? "" :  tLeaveorback.getMobile());
            result.put("place", tLeaveorback.getPlace()== null ? "" :  tLeaveorback.getPlace());
            result.put("origin", tLeaveorback.getPlanTimeEnd()== null ? "" :  tLeaveorback.getOrigin());
            result.put("sjqjts", tLeaveorback.getActualVocationDate() == null ? "" : tLeaveorback.getActualVocationDate());//改为手动输入“实际请假天数”
            result.put("bz", tLeaveorback.getLeaveRemark() == null ? "" : tLeaveorback.getLeaveRemark());
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
     *  获取两个日期之间的日期（包括边界日期）
     * @param startDateStr 起始日期
     * @param endDateStr 截止日期
     * @return List<Date>
     */
    private List<Date > getBetweenDates(String startDateStr, String endDateStr){
        List<Date> dates = new ArrayList<>();
        List<String> datesStr = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = LocalDate.parse(startDateStr, dateTimeFormatter);
        LocalDate endLocalDate = LocalDate.parse(endDateStr, dateTimeFormatter);
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        while (startDate.getTime() <= endDate.getTime()){
            dates.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            startDate = tempStart.getTime();
        }
//        dates.forEach(date -> datesStr.add(new SimpleDateFormat("yyyy-MM-dd").format(date)));
        return dates;
    }
    @RequestMapping("calculateHolidays")
    public void calculateHolidays(String startDateStr, String toDateStr) {
        Date startDate = null;
        Date toDate = null;
        Date betweedDate = null;
        int dayNum = 0;
        int holidayNum = 0;
        int weekendNum = 0;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> betweedDateLis = this.getBetweedDate(startDateStr, toDateStr);
        try {
            startDate = simpleDateFormat.parse(startDateStr);
            toDate = simpleDateFormat.parse(toDateStr);
            dayNum = (int) ((toDate.getTime() - startDate.getTime()) /86400000);//1000 * 60 * 60 * 24
            for (int i = 0; i < betweedDateLis.size(); i++) {
                String betweedDateStr = betweedDateLis.get(i);
                betweedDate = simpleDateFormat.parse(betweedDateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(betweedDate);
                int week = calendar.get(Calendar.DAY_OF_WEEK);
                if (week == 1 || week == 7) { // 1代表周日 7代表周六
                    weekendNum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        paraMap.put("startDate", startDate);
        paraMap.put("toDate", toDate);
        paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
        List<DicCalender> queryHolidayLis = dicCalenderService.queryHoliday(paraMap);
        for (DicCalender qxjDicCalender : queryHolidayLis) {
            String isholiday = qxjDicCalender.getIsholiday();
            if ("1".equals(isholiday)) {
                holidayNum++;
            }
        }
        resultMap.put("xjts", (dayNum - holidayNum+1)<0?0:dayNum - holidayNum+1);
        resultMap.put("holidayNum", holidayNum);
        resultMap.put("weekendNum", weekendNum);
        Response.json(resultMap);
    }

    /**
     * 局管理员删除请销假信息
     * @param id
     */
    @RequestMapping("deleteQxj")
    public void deleteQxj(String id){
        String[] ids = id.split(",");
        //删除请销假表信息
        leaveorbackService.deleteBatch(ids);
        //删除请销假申请人信息
        applyUserService.deleteBatchByLeaveId(ids);
        Response.ok();
    }

    private List<String> getBetweedDate(String start,String end){
        List<String> list=new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long instance = ChronoUnit.DAYS.between(startDate, endDate);
//		if(instance<1)
//		return list;
        Stream<LocalDate> iterate = Stream.iterate(startDate, d -> d.plusDays(1));
        Stream<LocalDate> limit = iterate.limit(instance+1);
        limit.forEach(f -> list.add(f.toString()));
        return list;
    }
}
