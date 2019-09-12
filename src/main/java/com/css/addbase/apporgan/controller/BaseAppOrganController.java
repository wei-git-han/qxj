package com.css.addbase.apporgan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.util.OrgUtil;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.github.pagehelper.util.StringUtil;

/**
 * 自定义部门表
 * 
 * @author gengds
 */
@Controller
@RequestMapping("app/base/dept")
public class BaseAppOrganController {

	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;

	/**
	 * 获取以当前登录人部门为根节点的部门树(获取全部的叶子节点)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public Object getDeptTree() {
		String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		JSONObject list = OrgUtil.getOrganTree(organs, organId);
		return list;
	}

	/**
	 * 获取以当前登录人部门为根节点的部门树(获取全部的叶子节点)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tree2")
	@ResponseBody
	public Object getDeptTree2() {
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		JSONObject list = OrgUtil.getOrganTree(organs, null);
		return list;
	}

	/**
	 * 获取以当前登录人部门为根节点的部门树(获取全部的叶子节点)附带人数统计
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/treeByNums")
//	@ResponseBody
//	public Object getDeptTreeByNums() {
//		String organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
//		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
//		JSONObject list = getOrganTree(organs, organId);
//		return list;
//	}

	/**
	 * 获取以指定部门ID为根节点的部门树(获取全部的叶子节点)
	 * 
	 * @param organId
	 *            指定部门ID
	 * @return
	 */
//	@RequestMapping(value = "/spetreeByNums")
//	@ResponseBody
//	public Object getSpeDeptTreeByNums(String organId) {
//		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
//		JSONObject list = getOrganTree(organs, organId);
//		return list;
//	}

	/**
	 * 获取以指定部门ID为根节点的部门树(获取全部的叶子节点)
	 * 
	 * @param organId
	 *            指定部门ID
	 * @return
	 */
	@RequestMapping(value = "/spetree")
	@ResponseBody
	public Object getSpeDeptTree(String organId) {
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		JSONObject list = OrgUtil.getOrganTree(organs, organId);
		return list;
	}

	/**
	 * util方法
	 */

	/**
	 * 根据部门Id获取部门树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @return
	 */
//	public JSONObject getOrganTree(List<BaseAppOrgan> organs, String organId) {
//		return getOrganTree(organs, organId, true, true);
//	}

//	public JSONObject getOrganTree(List<BaseAppOrgan> organs, String organId, boolean sublevel, boolean opened) {
//		Map<String, BaseAppOrgan> orgMap = orgListToMapByOrganId(organs);
//		if (StringUtils.isNotEmpty(organId)) {// 根节点不为空
//			JSONObject organTree = JSONObject.parseObject(setOrganTree(organs, orgMap, organId, sublevel).get("organTree").toString());
//			JSONObject json = new JSONObject();
//			json.put("opened", opened);
//			organTree.put("state", json);
//			return organTree;
//		} else {
//			// 根节点为空
//			JSONObject organTree = JSONObject.parseObject(setOrganTree(organs, orgMap, "root", sublevel).get("organTree").toString());
//			JSONObject json = new JSONObject();
//			json.put("opened", opened);
//			organTree.put("state", json);
//			return organTree;
//		}
//	}

	/**
	 * 将部门List转换为Map(以部门ID为key)
	 * 
	 * @param organs
	 * @return 部门信息集合
	 */
	public Map<String, BaseAppOrgan> orgListToMapByOrganId(List<BaseAppOrgan> organs) {
		Map<String, BaseAppOrgan> orgMap = new HashMap<String, BaseAppOrgan>();
		for (BaseAppOrgan organ : organs) {
			orgMap.put(organ.getId(), organ);
		}
		return orgMap;
	}

	/**
	 * 设置部门树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organs
	 *            部门信息集合(Map)
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @return
	 */
//	private Map<String, Object> setOrganTree(List<BaseAppOrgan> organs, Map<String, BaseAppOrgan> orgMap, String organId,
//			boolean sublevel) {
//		JSONObject organTree = new JSONObject();
//		JSONArray jsons = new JSONArray();
//		int nums0 = 0;
//		int nums1 = 0;
//		List<BaseAppOrgan> subOrgs = getSubOrg(organs, organId);
//		
//		for (BaseAppOrgan subOrg : subOrgs) {
//			Map<String, Object> minMap=setOrganTree(organs, orgMap, subOrg.getId(), sublevel);
//			
//			//计算直系子机构的人数
//			nums0 += Integer.parseInt(minMap.get("nums0").toString());
//			nums1 += Integer.parseInt(minMap.get("nums1").toString());
//			jsons.add(JSONObject.parse(minMap.get("organTree").toString()));
//		}
//		BaseAppOrgan BaseAppOrgan = getBaseAppOrgan(orgMap, organId);
//		
//		//计算本机构的人数
//		nums0+=baseAppUserService.queryNumByOrgId(BaseAppOrgan.getId());
//		nums1+=rytjRecordService.queryNumByOrgId(BaseAppOrgan.getId());
//		organTree.put("id", BaseAppOrgan.getId());
//		organTree.put("text", BaseAppOrgan.getName() + " (" + nums1 + "/" + nums0 + ")");
//		organTree.put("type", "0");
//		if (jsons.size() > 0) {
//			organTree.put("children", jsons);
//		}
//		Map<String, Object> map=new HashMap<>();
//		map.put("nums0", nums0);
//		map.put("nums1", nums1);
//		map.put("organTree", organTree);
//		return map;
//	}

	/**
	 * 根据部门Id获取部门信息
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门ID
	 * @return
	 */
	public static BaseAppOrgan getBaseAppOrgan(Map<String, BaseAppOrgan> organs, String organId) {
		return organs.get(organId);
	}

	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门ID
	 * @return
	 */
	public static List<BaseAppOrgan> getSubOrg(List<BaseAppOrgan> organs, String organId) {
		List<BaseAppOrgan> subOrgs = new ArrayList<BaseAppOrgan>();
		for (BaseAppOrgan organ : organs) {
			if (StringUtils.equals(organ.getParentId(), organId)) {
				subOrgs.add(organ);
			}
		}
		return subOrgs;
	}

	/**
	 * util方法
	 */

	/**
	 * 只获取root节点下的叶子节点
	 */
	@RequestMapping(value = "/tree_onlyroot")
	@ResponseBody
	public Object getDeptTreeOnlyRootChildren() {
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		JSONObject list = OrgUtil.getOrganTree(organs, "root", false, true);
		return list;
	}

	/**
	 * 根据部门ID获取子部门信息
	 * 
	 * @param organId
	 *            部门ID
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public void list(String organId) {
		List<BaseAppOrgan> depts = baseAppOrganService.findByParentId(organId);
		GwPageUtils pageUtil = new GwPageUtils(depts);
		Response.json(pageUtil);
	}

	/**
	 * 根据部门ID获取部门名称
	 * 
	 * @param organId
	 */
	@RequestMapping(value = "/deptname")
	@ResponseBody
	public void deptname(String organId) {
		if (StringUtils.isNotBlank(organId)) {
			BaseAppOrgan organ = baseAppOrganService.queryObject(organId);
			Response.json("name", organ.getName());
		}
	}

	/**
	 * 根据指定的部门ID获取部门字典
	 * 
	 * @param organId
	 */
	@ResponseBody
	@RequestMapping("/{organId}")
	public void info(@PathVariable("organId") String organId) {
		JSONObject deptDic = new JSONObject();
		JSONArray jsons = new JSONArray();
		List<BaseAppOrgan> depts = baseAppOrganService.findByParentId(organId);
		for (BaseAppOrgan dept : depts) {
			JSONObject json = new JSONObject();
			json.put("value", dept.getId());
			json.put("text", dept.getName());
			jsons.add(json);
		}
		deptDic.put("dept", jsons);
		Response.json(deptDic);
	}

	/**
	 * 获取以当所有部门为根节点的部门树(获取全部的叶子节点)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/allOrgTree")
	@ResponseBody
	public Object allOrgTree(String organId) {
		if (StringUtil.isEmpty(organId)) {
			organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		}
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<BaseAppOrgan> listOrg = new ArrayList<BaseAppOrgan>();
		for (BaseAppOrgan org : organs) {
			String[] arr = org.getTreePath().split(",");
			if (arr.length < 5) {
				listOrg.add(org);
			}
		}

		JSONObject list = OrgUtil.getOrganTree(listOrg, organId);
		return list;
	}

	/**
	 * 获取所有局级单位
	 * 
	 * @return
	 */
	@RequestMapping(value = "/allGeneralOrg")
	@ResponseBody
	public Object allGeneralOrg(String organId) {
		if (StringUtil.isEmpty(organId)) {
			organId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		}
		List<BaseAppOrgan> organs = baseAppOrganService.queryList(null);
		List<BaseAppOrgan> listOrg = new ArrayList<BaseAppOrgan>();
		for (BaseAppOrgan org : organs) {
			String[] arr = org.getTreePath().split(",");
			if (arr.length < 4) {
				listOrg.add(org);
			}
		}
		JSONObject list = OrgUtil.getOrganTree(listOrg, organId);
		return list;
	}

}
