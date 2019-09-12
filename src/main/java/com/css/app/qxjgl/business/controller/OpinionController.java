package com.css.app.qxjgl.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.css.base.utils.PageUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.service.OpinionService;


/**
 * 请销假意见表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-28 10:36:48
 */
@Controller
@RequestMapping("/qxjopinion")
public class OpinionController {
	@Autowired
	private OpinionService qxjOpinionService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjopinion:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<Opinion> qxjOpinionList = qxjOpinionService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjOpinionList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjopinion:info")
	public void info(@PathVariable("id") String id){
		Opinion qxjOpinion = qxjOpinionService.queryObject(id);
		Response.json("qxjOpinion", qxjOpinion);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjopinion:save")
	public void save(@RequestBody Opinion qxjOpinion){
		qxjOpinion.setId(UUIDUtils.random());
		qxjOpinionService.save(qxjOpinion);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qxjopinion:update")
	public void update(@RequestBody Opinion qxjOpinion){
		qxjOpinionService.update(qxjOpinion);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjopinion:delete")
	public void delete(@RequestBody String[] ids){
		qxjOpinionService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
