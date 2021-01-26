package com.css.webservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgan.util.OrgUtil;
import com.css.app.qxjgl.business.dto.LeaveorbackPlatDto;
import com.css.app.qxjgl.business.dto.LeaveorbackUserPlatDto;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.base.utils.DateUtil;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/app/qxjgl/api")
public class XlglApiController {
    @Autowired
    private LeaveorbackService leaveorbackService;
    @Autowired
    private BaseAppUserService baseAppUserService;
    @Autowired
    private BaseAppOrganService baseAppOrganService;

    /**
     * 训练管理获取请假人员
     * @param fromFlag ：来自首页还是列表的请求：listPage-来自列表页；其他为来自首页
     * */
    @ResponseBody
    @RequestMapping("/getQjUserIds")
    public void getQjUserIds(String fromFlag,String deptId) { // 返回整个部门下所有请假通过且在期限内的用户登录名
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        List<Leaveorback> qjUserIdsList = leaveorbackService.queryQjUserIds(map);

        if(qjUserIdsList == null || qjUserIdsList.size() == 0) {
            Response.json(null);
            return;
        }

        String qjUserIds = "";
        for(Leaveorback leaveorback : qjUserIdsList) {
            qjUserIds += "," + leaveorback.getDeleteMark();
        }

        List<BaseAppUser> baseAppUserList = baseAppUserService.queryObjectByUserIds(qjUserIds.split(","));
        Map<String, Object> returnMap = new HashMap<>();
        if(baseAppUserList != null) {
            for(int i = 0; i < baseAppUserList.size(); i++) {
                for(Leaveorback leaveorback : qjUserIdsList) {
                    if("listPage".equals(fromFlag) && StringUtils.contains(leaveorback.getDeleteMark(), baseAppUserList.get(i).getUserId())) {
                        returnMap.put(baseAppUserList.get(i).getAccount(), new SimpleDateFormat("yyyy-MM-dd").format(leaveorback.getRegistrationDate()));
                        returnMap.put("userId",baseAppUserList.get(i).getUserId());
                        break;
                    }
                }
            }
        }

        Response.json(returnMap);
    }

    @ResponseBody
    @RequestMapping("/getQjNum")
    public void getQjNum(String fromFlag,String deptId) { // 返回整个部门下所有请假通过且在期限内的用户登录名
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObj = new JSONObject();
        JSONArray jsons = new JSONArray();
        JSONArray jsons1 = new JSONArray();
        String startTime = DateUtil.getDate().substring(0,10)+" 00:00:00";
        String endTime = DateUtil.getDate().substring(0,10)+" 23:59:59";
        map.put("deptId", deptId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        List<Leaveorback> qjUserIdsList = leaveorbackService.getQjNum(map);
        long num = 0;
//        if(qjUserIdsList != null && qjUserIdsList.size() > 0){
//            num = qjUserIdsList.size();
//        }
        
        String userIds = "";
        for(Leaveorback q : qjUserIdsList) {
        	userIds+=","+q.getDeleteMark();
        }
        if(!StringUtils.equals("", userIds)) {
        	String[] userId = userIds.substring(1, userIds.length()).split(",");
        	List<String> collect2 = Arrays.asList(userId).stream().distinct().collect(Collectors.toList());
        	String collect = collect2.stream().collect(Collectors.joining(","));
        	num = collect2.size();
        	userIds = collect;
        }
        
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("userId", userIds);
        jsonUser.put("num", num);
        jsons1.add(jsonUser);
        Map<String,Integer> orgMap = new HashMap<>();
        for(String s : userIds.split(",")) {
        	String userDepartIdAndNames = baseAppUserService.queryUserDepartIdAndName(s);
        	if (StringUtils.isNotBlank(userDepartIdAndNames)) {
        		String orgId = userDepartIdAndNames.split(",")[0];
        		BaseAppOrgan orgBean = baseAppOrganService.queryObject(orgId);
                String treepath = orgBean.getTreePath();
                String []orgIds = treepath.split(",");
                for(String org : orgIds) {
                	Object countObj = orgMap.get(org);
                    Integer count =0;
                    if(countObj!=null) {
                        count=Integer.parseInt(countObj.toString());
                    }
                    orgMap.put(org,(count+1));
                }
                
        	}
        }
        
        for(String key : orgMap.keySet()) {
        	JSONObject jsonOrg = new JSONObject();
        	jsonOrg.put("orgId", key);
        	jsonOrg.put("count", orgMap.get(key));
        	jsons.add(jsonOrg);
        }
        
        jsonObj.put("jsons", jsons1);
        jsonObj.put("detps", jsons);
        Response.json(jsonObj);
    }
    
    /**
     * 训练管理-人员管理-地图人数接口
     * @author 李振楠 2020-12-16
     * @param organId 查询条件 各局id
     * @param timeStr 查询条件 时间 默认当前时间
     * */
    @ResponseBody
    @RequestMapping("/getPlatNumber")
    public void getPlatNumber(String parentId,String organId,String timeStr) {
    	String orgId = "";
    	if(StringUtils.isNotBlank(parentId)) {
    		if(StringUtils.isNotBlank(organId)) {
    			orgId = organId;
    		}else {
        		orgId = parentId;
    		}
    	}else {
    		if(StringUtils.isNotBlank(organId)) {
    			orgId = organId;
    		}else {
    			orgId = "root";
    		}
    	}
    	JSONObject jsonObj = new JSONObject();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<String> arrayList = new ArrayList<String>();
		
		arrayList = OrgUtil.getOrganTreeList(organs, orgId, true, true, arrayList);
		String[] arr = arrayList.toArray(new String[arrayList.size()]);
		
		if(StringUtils.isNotBlank(timeStr)) {
			try {
				Date parse = format.parse(timeStr);
				map.put("time", parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			map.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		map.put("orgIds", arr);
		List<LeaveorbackPlatDto> platNumber = leaveorbackService.getPlatNumber(map);
		jsonObj.put("list", platNumber);
		Response.json(jsonObj);
    }
    
    /**
     * 训练管理-人员管理-地图人员详情接口
     * @author 李振楠 2020-12-16
     * @param province 省份 为必填 不能为空
     * @param organId 查询条件各局id
     * @param timeStr 查询条件时间
     * @param userName 查询人员名称
     * */
    @ResponseBody
    @RequestMapping("/platList")
    public void platList(String province,String parentId,String organId,String timeStr,String userName) {
    	String orgId = "";
    	if(StringUtils.isNotBlank(parentId)) {
    		if(StringUtils.isNotBlank(organId)) {
    			orgId = organId;
    		}else {
        		orgId = parentId;
    		}
    	}else {
    		if(StringUtils.isNotBlank(organId)) {
    			orgId = organId;
    		}else {
    			orgId = "root";
    		}
    	}
    	JSONObject jsonObj = new JSONObject();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<String> arrayList = new ArrayList<String>();
		arrayList = OrgUtil.getOrganTreeList(organs, orgId, true, true, arrayList);
		String[] arr = arrayList.toArray(new String[arrayList.size()]);
		if(StringUtils.isNotBlank(province)) {
			map.put("province", province);
		}
		if(StringUtils.isNotBlank(timeStr)) {
			try {
				Date parse = format.parse(timeStr);
				map.put("time", parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			map.put("time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()) );
		}
		if(StringUtils.isNotBlank(userName)) {
			map.put("userName", userName);
		}
		map.put("orgIds", arr);
		List<LeaveorbackUserPlatDto> platList = leaveorbackService.platList(map);
		for (LeaveorbackUserPlatDto leaveorbackUserPlatDto : platList) {
			if(StringUtils.isNotBlank(leaveorbackUserPlatDto.getStatus()) &&
					leaveorbackUserPlatDto.getStatus().equals("1")) {
				leaveorbackUserPlatDto.setStatus("出差");
			}else if(StringUtils.isNotBlank(leaveorbackUserPlatDto.getStatus()) &&
					leaveorbackUserPlatDto.getStatus().equals("0")) {
				leaveorbackUserPlatDto.setStatus("请假");
			}
			if(StringUtils.isBlank(leaveorbackUserPlatDto.getDeptId())) {
				leaveorbackUserPlatDto.setDeptId(leaveorbackUserPlatDto.getOrganId());
			}
			if(StringUtils.isBlank(leaveorbackUserPlatDto.getDeptName())) {
				leaveorbackUserPlatDto.setDeptName(leaveorbackUserPlatDto.getOrganName());
			}
			
		}
		jsonObj.put("list", platList);
		Response.json(jsonObj);
    }
    /**
     * 训练管理-人员管理-地图人员详情接口
     * @author 李振楠 2020-12-16
     * @param province 省份 为必填 不能为空
     * @param organId 查询条件各局id
     * @param timeStr 查询条件时间
     * @param userName 查询人员名称
     * */
    @ResponseBody
    @RequestMapping("/updateOrganIsInvalId")
    public void updateOrganIsInvalId(String organId,String isInvalId) {
     	Map<String,Object> map = new HashMap<String,Object>();
     	map.put("organId", organId);
     	map.put("IsInvalId", isInvalId);
    	baseAppOrganService.updateOrganIsInvalId(map);
    	map.put("sfyx", isInvalId);
    	baseAppUserService.updateSFYXByOrganId(map);
    }
    
	@ResponseBody
	@RequestMapping("/updateUserSfyx")
	  public void updateUserSfyx(String id,String sfyx) {
		BaseAppUser baseAppUser = new BaseAppUser();
		baseAppUser.setId(id);
		baseAppUser.setSfyx(sfyx);
		baseAppUserService.update(baseAppUser);
		
	}
}
