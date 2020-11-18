package com.css.webservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.base.utils.DateUtil;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
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
}
