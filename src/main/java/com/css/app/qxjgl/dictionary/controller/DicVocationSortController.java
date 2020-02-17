package com.css.app.qxjgl.dictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
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
import com.css.base.utils.UUIDUtils;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;

/**
 * 休假类别字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-06-06 09:36:05
 */
@Controller
@RequestMapping("app/qxjgl/dicvocationsort")
public class DicVocationSortController {
	@Autowired
	private DicVocationSortService dicVocationSortService;
	@Autowired
	private LeaveorbackService tLeaveorbackService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	// 休假类别下拉框，dict
	@RequestMapping(value = "/dict")
	@ResponseBody
	public void dict(String orgId, String leaverIds) {
		JSONObject result = new JSONObject(true);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(orgId)) {
			map.put("orgId", orgId);
		} else {
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		}
		if (StringUtils.isNotBlank(leaverIds)) {
			String leaverId = leaverIds.split(",")[0];
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(leaverId));
		}
		List<DicVocationSort> dicVo = dicVocationSortService.queryList(map);
		for (DicVocationSort d : dicVo) {
			JSONObject jo = new JSONObject(true);
			jo.put("value", d.getId());
			jo.put("text", d.getVacationSortId());
			ja.add(jo);
		}
		result.put("xjlb", ja);
		Response.json(result);
	}

	/**
	 * 休假类别页面，list
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<JSONObject> list(String roleType) {
		JSONObject result = new JSONObject(true);
		Map<String, Object> map = new HashMap<>();
		if (com.css.base.utils.StringUtils.equals(roleType, "3") || com.css.base.utils.StringUtils.equals(roleType, "5")) {
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		}
		List<DicVocationSort> dicVo = dicVocationSortService.queryList(map);
		result.put("total", "1");
		JSONArray jsons = new JSONArray();
		JSONObject json = new JSONObject(true);
		result.put("name", "休假类别");
		result.put("type", "xjlb");
		for (DicVocationSort d : dicVo) {
			JSONObject jo = new JSONObject(true);
			jo.put("id", d.getId());
			jo.put("value", d.getVacationSortId());
			jo.put("flag", d.getDeleteMark());
			jo.put("deductionVacationDay", d.getDeductionVacationDay());
			jsons.add(jo);
			
		}
		result.put("rows", jsons);
		return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
	}

	

	/**
	 * 新增类别
	 * 
	 * @param textitem
	 *            字典值
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public void save(String textitem,String deductionVacationDay) {
		String[] dicts = textitem.split("\n");
		String userId = CurrentUser.getUserId();
		DicVocationSort dicVocationSort = new DicVocationSort();
		for (String dic : dicts) {
			dicVocationSort.setId(UUIDUtils.random());
			dicVocationSort.setVacationSortId(dic);
			// 设置为可删除
			dicVocationSort.setDeleteMark(1);
			String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
			BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(orgId);
			dicVocationSort.setOrgId(orgId);
			dicVocationSort.setOrgName(baseAppOrgan.getName());
			dicVocationSort.setDeductionVacationDay(deductionVacationDay);
			dicVocationSortService.save(dicVocationSort);
		}
		Response.json("result","success");
	}
	
	/**
	 * 新增字典值的校验
	 * 
	 * @param textitem
	 *            字典值
	 * @return
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public ResponseEntity<JSONObject> check(String textitem) {
		String[] dicts = textitem.split("\n");
		Map<String, Object> map = new HashMap<>();
		map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		List<DicVocationSort> dicVo = dicVocationSortService.queryList(map);
		boolean check = true;
		for (DicVocationSort sort : dicVo) {
			for (int i = 0; i < dicts.length; i++) {
				if (sort.getVacationSortId().equals(dicts[i])) {
					check = false;
					break;
				}
			}

		}
		if (check) {
			return new ResponseEntity<JSONObject>(
					JSON.parseObject("{\"result\":\"success\"}"), HttpStatus.OK);
		} else {
			return new ResponseEntity<JSONObject>(
					JSON.parseObject("{\"result\":\"fail\"}"), HttpStatus.OK);
		}
	}

	/**
	 * 编辑时查看
	 */
	@ResponseBody
	@RequestMapping("/info")
	public ResponseEntity<JSONObject> info(String id) {
		DicVocationSort dicVocationSort = dicVocationSortService.queryObject(id);
		if (dicVocationSort != null) {

			JSONObject json = new JSONObject(true);
			json.put("fieldValue", dicVocationSort.getVacationSortId());
			json.put("result", "success");
			return new ResponseEntity<JSONObject>(json, HttpStatus.OK);
			
		} else {
			return null;
		}

	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResponseEntity<JSONObject> update(String id, String fieldValue,String deductionVacationDay) {
		Map<String, Object> map = new HashMap<>();
		map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		List<DicVocationSort> dicVo = dicVocationSortService.queryList(map);
		boolean check = true;
		for (DicVocationSort sort : dicVo) {
			if (sort.getVacationSortId().equals(fieldValue)) {
				check = false;
				break;
			}
			if(sort.getDeductionVacationDay().equals(deductionVacationDay)) {
				check = false;
				break;
			}
		}
		if (check) {
			DicVocationSort dicVocationSort = dicVocationSortService
					.queryObject(id);
			if(StringUtils.isNotBlank(fieldValue)) {
				dicVocationSort.setVacationSortId(fieldValue);
			}
			if(StringUtils.isNotBlank(deductionVacationDay)) {
				dicVocationSort.setDeductionVacationDay(deductionVacationDay);
			}
				
			dicVocationSortService.update(dicVocationSort);
			return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"), HttpStatus.OK);
		} else {
			return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"fail\"}"), HttpStatus.OK);
		}
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ResponseEntity<JSONObject> delete(String id) {
		if (StringUtils.isNotEmpty(id)) {
			JSONObject result = new JSONObject();
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
			List<Leaveorback> list = tLeaveorbackService.queryList(map);
			for(Leaveorback l : list){
				if(id.equals(l.getVacationSortId())){
					result.put("sort", "true");
					break;
				}
			}
			if(result.isEmpty()){
				String[] idArrs = id.split(",");
				dicVocationSortService.deleteBatch(idArrs);
				return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"success\"}"), HttpStatus.OK);
			}else{
				return new ResponseEntity<JSONObject>(JSON.parseObject("{\"result\":\"fail\"}"), HttpStatus.OK);
			}
		}
		return null;
	}
}
