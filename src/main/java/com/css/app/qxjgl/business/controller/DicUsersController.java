package com.css.app.qxjgl.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.entity.ZFDicUsersModel;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.app.qxjgl.business.service.DicUsersService;
import com.github.pagehelper.PageHelper;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 11:05:13
 */
@Controller
@RequestMapping("app/qxjgl/qxjdicusers")
public class DicUsersController {
	@Autowired
	private DicUsersService dicUsersService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjdicusers:list")
	public void list(Integer page, Integer pagesize, String roleType){
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.equals(roleType, "3") || StringUtils.equals(roleType, "5")) {
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		}
		//针对权限设置查询
		map.put("quanXianQuery", "quanXianQuery");
		PageHelper.startPage(page, pagesize);
		
		//查询列表数据
		List<DicUsers> qxjDicUsersList = dicUsersService.queryList(map);
		PageUtils pageUtil = new PageUtils(qxjDicUsersList);
		Response.json(pageUtil.getPageResult());	
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjdicusers:info")
	public void info(@PathVariable("id") Integer id){
		DicUsers qxjDicUsers = dicUsersService.queryObject(id);
		Response.json("qxjDicUsers", qxjDicUsers);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjdicusers:save")
	public void save(DicUsers qxjDicUsers){
		String userId = qxjDicUsers.getUserid();
		String rolecode = qxjDicUsers.getRolecode();
		DicUsers queryByUserId = dicUsersService.queryByUserId(userId, rolecode);
		BaseAppOrgan baseAppOrgan;
		if(queryByUserId==null) {
			qxjDicUsers.setId(UUIDUtils.random());
			String deptid = qxjDicUsers.getDeptid();
			baseAppOrgan = queryBaseAppOrgan(deptid);
			qxjDicUsers.setDeptname(baseAppOrgan.getName());
			String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
			qxjDicUsers.setOrgId(orgId);
			baseAppOrgan = queryBaseAppOrgan(orgId);
			qxjDicUsers.setOrgName(baseAppOrgan.getName());
			dicUsersService.save(qxjDicUsers);
			Response.ok();
		}else {
			Response.error(0, "repeat");
		}
	}
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("qxjdicusers:edit")
	public void edit(DicUsers qxjDicUser){
		Map<String, Object> map = new HashMap<>();
		map.put("id",qxjDicUser.getId());
		map.put("userId",qxjDicUser.getUserid());
		List<DicUsers> dicUsers = dicUsersService.queryList(map);
		if (dicUsers.size() > 0) {
			DicUsers dicUsers1 = dicUsers.get(0);
			dicUsers1.setRolecode(qxjDicUser.getRolecode());
			dicUsers1.setRolename(qxjDicUser.getRolename());
			dicUsersService.update(dicUsers1);
			Response.json("result", "success");
		} else {
			Response.json("result", "fail");
		}
	}
	/**
	 *
	 * @param deptid 直接部门ID
	 * @return BaseAppOrgan
	 */
	private BaseAppOrgan queryBaseAppOrgan(String deptid){
		return baseAppOrganService.queryObject(deptid);
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qxjdicusers:update")
	public void update(@RequestBody DicUsers qxjDicUsers){
		dicUsersService.update(qxjDicUsers);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjdicusers:delete")
	public void delete(String[] ids){
		dicUsersService.deleteBatch(ids);
		
		Response.ok();
	}

	/**
	 * 管理员 新增
	 */
	@ResponseBody
	@RequestMapping("/addDeptAdmin")
	public void addDeptAdmin(ZFDicUsersModel model){
		dicUsersService.addDeptAdmin(model);
	}

	/**
	 * 管理员删除
	 */
	@ResponseBody
	@RequestMapping("/deleteDeptAdmin")
	public void deleteDeptAdmin(ZFDicUsersModel model){
		dicUsersService.deleteDeptAdmin(model);
	}

	/**
	 * 部 管理员分页列表查询
	 */
	@ResponseBody
	@RequestMapping("/selectDeptAdminBu")
	public void selectDeptAdminBu(ZFDicUsersModel model){
		dicUsersService.selectDeptAdminBu(model);
	}

	/**
	 * 局 管理员分页列表查询
	 */
	@ResponseBody
	@RequestMapping("/selectDeptAdminJu")
	public void selectDeptAdminJu(ZFDicUsersModel model){
		dicUsersService.selectDeptAdminJu(model);
	}

	/**
	 * 局 管理员分页列表查询
	 */
	@ResponseBody
	@RequestMapping("/getUserRoleType")
	public void getUserRoleType(){
		dicUsersService.getUserRoleType();
	}
	
}
