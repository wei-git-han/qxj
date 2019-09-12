package com.css.app.qxjgl.authorization.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.app.qxjgl.authorization.entity.TAuthorization;
import com.css.app.qxjgl.authorization.service.TAuthorizationService;
import com.css.base.utils.CurrentUser;

/**
 * 用户表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-13 16:54:26
 */
@Controller
@RequestMapping("app/qxjgl/orguser")
public class OrgUserContriller {
	
	@Autowired
	private TAuthorizationService tAuthorizationService;//授权表
	
	@Autowired
	private OrgService orgService;
	
	
	//显示人员信息
	@ResponseBody
	@RequestMapping("/showuser")//部门id
	public ResponseEntity<JSONObject> showuser(int page,int pagesize,String id){
		JSONObject result = new JSONObject(true);
		int start = (page-1) * pagesize;
		int end = page * pagesize;
		result.put("total", 0);
		result.put("page", page);
		JSONArray jsons = new JSONArray();
		if (StringUtils.isNotEmpty(id)) {
			UserInfo[] userInfos = orgService.getUserInfos(id);
			if (userInfos != null && userInfos.length > 0) {
				result.put("total", userInfos.length);
				if (userInfos.length < end) {
					for (int i = start;i<userInfos.length;i++) {
						JSONObject json = new JSONObject();
						json.put("id", userInfos[i].getUserid() == null ? "": userInfos[i].getUserid());//用户id
						json.put("name",  userInfos[i].getFullname() == null ? "":  userInfos[i].getFullname());
						json.put("tel", userInfos[i].getUserEmail() == null ? "": userInfos[i].getUserEmail());
						TAuthorization tAuthorization = tAuthorizationService.queryPerson(userInfos[i].getUserid());//根据人员id查询授权表
						if(tAuthorization != null){
							json.put("qx", tAuthorization.getUthorizationName() == null ? "": tAuthorization.getUthorizationName());//权限
							json.put("qxid", tAuthorization.getAuthorizationId() == null ? "": tAuthorization.getAuthorizationId());//权限
						}else{
							json.put("qx","");
							json.put("qxid","");
						}
						jsons.add(json);
					}
				} else {
                    for (int i = start;i < end;i++) {
                    	JSONObject json = new JSONObject();
						json.put("id", userInfos[i].getUserid() == null ? "": userInfos[i].getUserid());//用户id
						json.put("name",  userInfos[i].getFullname() == null ? "":  userInfos[i].getFullname());
						json.put("tel", userInfos[i].getUserEmail() == null ? "": userInfos[i].getUserEmail());
						TAuthorization tAuthorization = tAuthorizationService.queryPerson(userInfos[i].getUserid());//根据人员id查询授权表
						if(tAuthorization != null){
							json.put("qx", tAuthorization.getUthorizationName() == null ? "": tAuthorization.getUthorizationName());//权限
							json.put("qxid", tAuthorization.getAuthorizationId() == null ? "": tAuthorization.getAuthorizationId());//权限
						}else{
							json.put("qx","");
							json.put("qxid","");
						}
						jsons.add(json);
					}
				}
			}
		}
		result.put("rows", jsons);
		return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
		
	}
	
	/**
	 * 处室领导人员树
	 * @date 2017年6月18日
	 */
	@RequestMapping(value = "/leadertree")
	@ResponseBody
	public Object getLeaderTree() {
		String loginDepartmentId = "root";
		if (StringUtils.isNotEmpty(CurrentUser.getUserId())) {
			loginDepartmentId = CurrentUser.getDepartmentId();
		}
		String endOrgId = "";
		Organ org = orgService.getOrgan(loginDepartmentId);
		String[] port = org.getP().split(",");
		if("root".equals(loginDepartmentId)){
			endOrgId = "root";
		}else{
			endOrgId = port[2];
		}
		JSONObject list=  getLeaderUserTree2(endOrgId);
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	@Deprecated
	public JSONObject getLeaderUserTree(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		Organ department = orgService.getOrgan(id);
		result.put("id", department.getOrganId());
		result.put("text", department.getOrganName());
		result.put("type", "0");
		Organ[] depts = orgService.getSubOrg(id);
		for (Organ dept:depts) {
			JSONObject json = new JSONObject();
			UserInfo[] users =  orgService.getUserInfos(dept.getOrganId());
			if(users.length != 0){
				json.put("id", dept.getOrganId());
				json.put("text", dept.getOrganName());
				json.put("type", "0");
			    jsons.add(getLeaderUserTree(dept.getOrganId()));
			}
		}
		UserInfo[] sysUsers = orgService.getUserInfos(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("leaderType", "3");
		List<TAuthorization> authorList = tAuthorizationService.queryList(map);
		List<TAuthorization> leaderUserList = new ArrayList<TAuthorization>(); 
		for (UserInfo sysUser:sysUsers) {
			for(TAuthorization al : authorList){
				if(sysUser.getUserid().equals(al.getPersonId())){
					//若有处室领导审批权限才显示
					if(null!=al.getAuthorizationId() && !"".equals(al.getAuthorizationId())) {
						if((al.getAuthorizationId().indexOf("3")>=0)){
							leaderUserList.add(al);
						}
					}
				}
			}
		}
		for (TAuthorization leaderUser:leaderUserList) {
			String[] auts = leaderUser.getAuthorizationId().split(",");
			List<String> autsL = Arrays.asList(auts); 
			if(autsL.contains("3")){//是处室领导
				UserInfo user = orgService.getUserInfo(leaderUser.getPersonId());
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", leaderUser.getPersonId());
				if(user!=null) {					
					jsonUser.put("text",user.getFullname());
				}else {					
					jsonUser.put("text", leaderUser.getPersonName());
				}
				jsonUser.put("type", "1");
				jsons.add(jsonUser);
			}
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	
	public JSONObject getLeaderUserTree2(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		Organ department = orgService.getOrgan(id);
		result.put("id", department.getOrganId());
		result.put("text", department.getOrganName());
		result.put("type", "0");
		Organ[] depts = orgService.getSubOrg(id);
		for (Organ dept:depts) {
			JSONObject json = new JSONObject();
			UserInfo[] users =  orgService.getUserInfos(dept.getOrganId());
			if(users.length != 0){
				json.put("id", dept.getOrganId());
				json.put("text", dept.getOrganName());
				json.put("type", "0");
				jsons.add(getLeaderUserTree2(dept.getOrganId()));
			}
		}
		UserInfo[] sysUsers = orgService.getUserInfos(id);
		Map<String,Object> map = new HashMap<String, Object>();
		//map.put("leaderType", "3");
		List<TAuthorization> authorList = tAuthorizationService.queryList(map);
//		List<TAuthorization> leaderUserList = new ArrayList<TAuthorization>(); 
//		for (UserInfo sysUser:sysUsers) {
//			for(TAuthorization al : authorList){
//				if(sysUser.getUserid().equals(al.getPersonId())){
//					//若有处室领导审批权限才显示
//					if(null!=al.getAuthorizationId() && !"".equals(al.getAuthorizationId())) {
//						if((al.getAuthorizationId().indexOf("3")>=0)){
//							leaderUserList.add(al);
//						}
//					}
//				}
//			}
//		}
		for (TAuthorization leaderUser:authorList) {
			//String[] auts = leaderUser.getAuthorizationId().split(",");
			//List<String> autsL = Arrays.asList(auts); 
			//if(autsL.contains("3")){//是处室领导
				UserInfo user = orgService.getUserInfo(leaderUser.getPersonId());
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", leaderUser.getPersonId());
				if(user!=null) {					
					jsonUser.put("text",user.getFullname());
				}else {					
					jsonUser.put("text", leaderUser.getPersonName());
				}
				jsonUser.put("type", "1");
				jsons.add(jsonUser);
			//}
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
	/**
	 * 局领导人员树
	 * @date 2017年6月18日
	 */
	@RequestMapping(value = "/chairmantree")
	@ResponseBody
	public Object getChairmanTree() {
		String loginDepartmentId = "root";
		if (StringUtils.isNotEmpty(CurrentUser.getUserId())) {
			loginDepartmentId = CurrentUser.getDepartmentId();
		}
		String endOrgId = "";
		Organ org = orgService.getOrgan(loginDepartmentId);
		String[] port = org.getP().split(",");
		if("root".equals(loginDepartmentId)){
			endOrgId = "root";
		}else{
			endOrgId = port[2];
		}
		JSONObject list=  getChairmanUserTree(endOrgId);
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	public JSONObject getChairmanUserTree(String id){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		Organ department = orgService.getOrgan(id);
		result.put("id", department.getOrganId());
		result.put("text", department.getOrganName());
		result.put("type", "0");
		Organ[] depts = orgService.getSubOrg(id);
		for (Organ dept:depts) {
			JSONObject json = new JSONObject();
			json.put("id", dept.getOrganId());
			json.put("text", dept.getOrganName());
			json.put("type", "0");
		    jsons.add(getChairmanUserTree(dept.getOrganId()));
		}
		UserInfo[] sysUsers = orgService.getUserInfos(id);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("leaderType", "4");
		List<TAuthorization> authorList = tAuthorizationService.queryList(map);
		List<TAuthorization> leaderUserList = new ArrayList<TAuthorization>(); 
		for (UserInfo sysUser:sysUsers) {
			for(TAuthorization al : authorList){
				if(sysUser.getUserid().equals(al.getPersonId())){
					//若有局领导审批审批权限才显示
					if(null!=al.getAuthorizationId() && !"".equals(al.getAuthorizationId())) {
						if((al.getAuthorizationId().indexOf("4")>=0)){
							leaderUserList.add(al);
						}
					}
				}
			}
		}
		for (TAuthorization leaderUser:leaderUserList) {
			String[] auts = leaderUser.getAuthorizationId().split(",");
			List<String> autsL = Arrays.asList(auts); 
			if(autsL.contains("4")){//是局领导
				UserInfo user = orgService.getUserInfo(leaderUser.getPersonId());
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", leaderUser.getPersonId());
				if(user!=null) {					
					jsonUser.put("text",user.getFullname());
				}else {					
					jsonUser.put("text", leaderUser.getPersonName());
				}
				jsonUser.put("type", "1");
				jsons.add(jsonUser);
			}
		}
		if (jsons.size()>0) {
			result.put("children", jsons);
		}
		return result;
	}
}
