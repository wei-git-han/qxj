package com.css.app.qxjgl.vocationdate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.css.addbase.orgservice.Organ;
import com.css.addbase.orgservice.UserInfo;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.UUIDUtils;
import com.css.app.qxjgl.vocationdate.entity.TVocationDate;
import com.css.app.qxjgl.vocationdate.service.TVocationDateService;
import com.github.pagehelper.PageHelper;


/**
 * 定义休假天数表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:38:15
 */
@Controller
@RequestMapping("app/qxjgl/tvocationdate")
public class TVocationDateController {
	
	@Autowired
	private TVocationDateService tVocationDateService;
	
	@Autowired
	private OrgService orgService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseEntity<JSONObject> list(Integer page, Integer pagesize, String sortname, String sorttype){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sorttype", sorttype);
		JSONObject result = new JSONObject(true);
		result.put("total", tVocationDateService.queryTotal(map));//总行数
		result.put("page", page);//当前是第几页
		PageHelper.startPage(page, pagesize);//分页
		//查询列表数据
		List<TVocationDate> voList = tVocationDateService.queryList(map);
		JSONArray jsons = new JSONArray();
		for(TVocationDate v : voList){
			JSONObject json = new JSONObject(true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			json.put("id", v.getId());
			json.put("rwsj", v.getEnrolTime());//入伍时间
			json.put("cjgzsj", sdf.format(v.getJoinWorkTime()));//c参加工作时间
			json.put("leaveBackId", v.getLeaveBackId());//请销假id
			json.put("personId",v.getPersonId());//人员id
			json.put("name", v.getPersonName());//人员姓名
			json.put("yxjts", Integer.toString(v.getShouldVocationDate()));//应休假天数
			jsons.add(json);
		}
		result.put("rows", jsons);
		return new  ResponseEntity<JSONObject>(result,HttpStatus.OK);
	}
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")						//定义人id//定义人姓名
	public ResponseEntity<JSONObject> save(String nameId,String name,String rwsj,String cjgzsj,String yxjts){
		TVocationDate tVocationD = tVocationDateService.queryByUserId(nameId);
		if(tVocationD == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			TVocationDate tVocationDate = new TVocationDate();
			tVocationDate.setId(UUIDUtils.random());
			tVocationDate.setPersonName(name);
			tVocationDate.setPersonId(nameId);
			tVocationDate.setEnrolTime(rwsj);
			try {
				tVocationDate.setJoinWorkTime(sdf.parse(cjgzsj));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tVocationDate.setShouldVocationDate(Integer.parseInt(yxjts));
			tVocationDateService.save(tVocationDate);
			
			return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"),HttpStatus.OK);
		}else{
			return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"fail\"}"),HttpStatus.OK);
		}
		
	}
	
	/**
	 * 编辑时查看
	 */
	@ResponseBody
	@RequestMapping("/info")
	public ResponseEntity<JSONObject> info(String id){
		JSONObject result = new JSONObject(true);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TVocationDate tVocationDate = tVocationDateService.queryObject(id);
		result.put("nameId", tVocationDate.getPersonId());//人员姓名
		result.put("name", tVocationDate.getPersonName());
		result.put("rwsj", tVocationDate.getEnrolTime());
		result.put("cjgzsj", sdf.format(tVocationDate.getJoinWorkTime()));
		result.put("yxjts", Integer.toString(tVocationDate.getShouldVocationDate()));
		return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResponseEntity<JSONObject> update(String id,String name,String nameId,String rwsj,String cjgzsj,String yxjts ){
		TVocationDate tVocationDate = tVocationDateService.queryObject(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tVocationDate.setPersonId(nameId);
		tVocationDate.setPersonName(name);
		tVocationDate.setEnrolTime(rwsj);
		tVocationDate.setShouldVocationDate(Integer.parseInt(yxjts));
		try {
			tVocationDate.setJoinWorkTime(sdf.parse(cjgzsj));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tVocationDateService.update(tVocationDate);
		return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"),HttpStatus.OK);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ResponseEntity<JSONObject> delete(String id) {
		if (StringUtils.isNotEmpty(id)) {
			String[] idArrs = id.split(",");
			tVocationDateService.deleteBatch(idArrs);
		}
		return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"), HttpStatus.OK);
	}
	

	
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getUserTree(HttpServletRequest request) {
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
		List<TVocationDate> voList = tVocationDateService.queryList(null);
		JSONObject list=  getUserTree(endOrgId,voList);
		JSONObject json = new JSONObject();
		json.put("opened", true);
		list.put("state", json);
		return list;
	}
	
	public JSONObject getUserTree(String id,List<TVocationDate> voList){
		JSONObject result = new JSONObject();
		JSONArray jsons = new JSONArray();
		Organ organ = orgService.getOrgan(id);
		result.put("id", organ.getOrganId());
		result.put("text", organ.getOrganName());
		result.put("type", "0");
		Organ[] organs = orgService.getSubOrg(id);
		for (Organ sysOrgan:organs) {
			JSONObject json = new JSONObject();
			json.put("id", sysOrgan.getOrganId());
			json.put("text", sysOrgan.getOrganName());
			json.put("type", "0");
		    jsons.add(getUserTree(sysOrgan.getOrganId(),voList));
		}
		UserInfo[] sysUsers = orgService.getUserInfos(id);
		boolean flag = true;
		for (UserInfo sysUser:sysUsers) {
			flag = true;
			for (TVocationDate vo:voList) {
				if (StringUtils.equals(sysUser.getUserid(), vo.getPersonId())) {
					flag = false;
				}
			}
			if (flag) {
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", sysUser.getUserid());
				jsonUser.put("text", sysUser.getFullname());
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
