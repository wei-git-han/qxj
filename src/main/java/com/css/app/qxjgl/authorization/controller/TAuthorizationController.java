package com.css.app.qxjgl.authorization.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.orgservice.OrgService;
import com.css.addbase.orgservice.UserInfo;
import com.css.app.qxjgl.authorization.entity.TAuthorization;
import com.css.app.qxjgl.authorization.service.TAuthorizationService;
import com.css.base.utils.StringUtils;
import com.css.base.utils.UUIDUtils;

/**
 * 人员授权页面
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:28:19
 */
@Controller
@RequestMapping("app/qxjgl/authority")
public class TAuthorizationController {
	
	@Autowired
	private TAuthorizationService tAuthorizationService;
	
	@Autowired
	private OrgService orgService;
	
	/**
	 * 菜单配置，人员授权
	 */
	@ResponseBody
	@RequestMapping("/authorization") // ywpz是0，qxj是1，csldsp是2，jldsp是3
	public ResponseEntity<JSONObject> authorization(String id, String QXJ, String CSLDSP, String JLDSP, String YWPZ) {
		String qxjId = "";
		String csldspId = "";
		String jldspId = "";
		String ywpzId = "";
		String qxjName = "";
		String csldspName = "";
		String jldspName = "";
		String ywpzName = "";
		String authorizationId = "";
		String authorizationName = "";
		if (StringUtils.isNotEmpty(QXJ)) {
			qxjId = "2";
			qxjName = "请销假";
			authorizationId = authorizationId + qxjId + ",";
			authorizationName = authorizationName + qxjName + ",";
		}
		if (StringUtils.isNotEmpty(CSLDSP)) {
			csldspId = "3";
			//csldspName = "处室领导审批";
			csldspName = "请销假审批";
			authorizationId = authorizationId + csldspId + ",";
			authorizationName = authorizationName + csldspName + ",";
		}
		if (StringUtils.isNotEmpty(JLDSP)) {
			jldspId = "4";
			jldspName = "局领导审批";
			authorizationId = authorizationId + jldspId + ",";
			authorizationName = authorizationName + jldspName + ",";
		}
		if (StringUtils.isNotEmpty(YWPZ)) {
			ywpzId = "1";
			ywpzName = "业务配置";
			authorizationId = authorizationId + ywpzId;
			authorizationName = authorizationName + ywpzName;
		}
		if (authorizationId.endsWith(",")) {
			authorizationId = authorizationId.substring(0, authorizationId.length() - 1);
		}
		if (authorizationName.endsWith(",")) {
			authorizationName = authorizationName.substring(0, authorizationName.length() - 1);
		}
		TAuthorization p = tAuthorizationService.queryPerson(id);
		if (p != null) {// 此人至少被授过一个权限；当再次授权时会即时把所有权限再次传递过来
			p.setAuthorizationId(authorizationId);
			p.setUthorizationName(authorizationName);
			tAuthorizationService.update(p);
		} else {// 此人没有被授过任何权限时
			TAuthorization tAuthorization = new TAuthorization();
			tAuthorization.setId(UUIDUtils.random());
			tAuthorization.setPersonId(id);
			UserInfo userinfo = orgService.getUserInfo(id);
			tAuthorization.setPersonName(userinfo.getFullname());
			tAuthorization.setAuthorizationId(authorizationId);
			tAuthorization.setUthorizationName(authorizationName);
			tAuthorizationService.save(tAuthorization);
		}
		return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"), HttpStatus.OK);
	}
	
	/**
	 * 菜单配置，部门授权
	 */
	@ResponseBody
	@RequestMapping("/orgauthority")				//ywpz是0，qxj是1，csldsp是2，jldsp是3			
	public ResponseEntity<JSONObject> org(String id,String QXJ,String CSLDSP,String JLDSP,String YWPZ){
		String qxjId = "";String csldspId = "";String jldspId = "";String ywpzId = "";
		String qxjName = "";String csldspName = "";String jldspName = "";String ywpzName = "";
		String authorizationId = "";
		String authorizationName = "";
		if(StringUtils.isNotEmpty(QXJ)){
			 qxjId = "2";
			 qxjName = "请销假";
			 authorizationId = authorizationId + qxjId + ",";
			 authorizationName = authorizationName + qxjName + ",";
		}
		if(StringUtils.isNotEmpty(CSLDSP)){
			 csldspId = "3";
			 csldspName = "处室领导审批";
			 authorizationId = authorizationId + csldspId + ",";
			 authorizationName = authorizationName + csldspName + ",";
		}
		if(StringUtils.isNotEmpty(JLDSP)){
			 jldspId = "4";
			 jldspName = "局领导审批";
			 authorizationId = authorizationId + jldspId + ",";
			 authorizationName = authorizationName + jldspName + ",";
		}
		if(StringUtils.isNotEmpty(YWPZ)){
			 ywpzId = "1";
			 ywpzName = "业务配置";
			 authorizationId = authorizationId + ywpzId;
			 authorizationName = authorizationName + ywpzName;
		}
		if(authorizationId.endsWith(",")){
			authorizationId = authorizationId.substring(0, authorizationId.length()-1);
		}
		if(authorizationName.endsWith(",")){
			authorizationName = authorizationName.substring(0, authorizationName.length()-1);
		}
		UserInfo[] users = orgService.getUserInfos(id);
		for(UserInfo user : users){
			TAuthorization p = tAuthorizationService.queryPerson(user.getUserid());
			if(p != null){//此人至少被授过一个权限；当再次授权时会即时把所有权限再次传递过来
				p.setAuthorizationId(authorizationId);
				p.setUthorizationName(authorizationName);
				tAuthorizationService.update(p);
			}else{//此人没有被授过任何权限时
				TAuthorization tAuthorization = new TAuthorization();
				tAuthorization.setId(UUIDUtils.random());
				tAuthorization.setPersonId(user.getUserid());
				tAuthorization.setPersonName(user.getFullname());
				tAuthorization.setAuthorizationId(authorizationId);
				tAuthorization.setUthorizationName(authorizationName);
				tAuthorizationService.save(tAuthorization);
			}
		}
		return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"),HttpStatus.OK);
	}
	
	
	/**
	 * 人员列表信息查询
	 */
	@ResponseBody
	@RequestMapping("/userlist")
	public Object getUsers(HttpServletRequest request) {
		JSONObject list=  getUsers("root");
		return list;
	}
	public JSONObject getUsers(String root){
		JSONObject result = new JSONObject();
		result.put("presonid","root");
		result.put("personName", "管理员");
		JSONArray jsons = new JSONArray();
		//UserInfo[] sysUsers = OrgServiceClient.getUserInfo(uuid);
		UserInfo[] sysUsers = orgService.getUserInfos(root);
		for (UserInfo sysUser:sysUsers) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("presonid", sysUser.getUserid());
			jsonUser.put("personName", sysUser.getFullname());
			jsons.add(jsonUser);
		}
		if (jsons.size()>0) {
			result.put("other", jsons);
		}
		return result;
	}
	
}
