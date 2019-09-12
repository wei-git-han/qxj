package com.css.app.qxjgl.dictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.css.app.qxjgl.dictionary.entity.DicAuthorization;
import com.css.app.qxjgl.dictionary.service.DicAuthorizationService;
import com.github.pagehelper.PageHelper;


/**
 * 授权名称字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
@Controller
@RequestMapping("app/qxjgl/dicauthorization")
public class DicAuthorizationController {
	@Autowired
	private DicAuthorizationService dicAuthorizationService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<DicAuthorization> dicAuthorizationList = dicAuthorizationService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(dicAuthorizationList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		DicAuthorization dicAuthorization = dicAuthorizationService.queryObject(id);
		Response.json("dicAuthorization", dicAuthorization);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(String authorizationName){
	//public void save(@RequestBody DicAuthorization dicAuthorization){
		DicAuthorization dicAuthorization = new DicAuthorization();
		dicAuthorization.setId(UUIDUtils.random());
		dicAuthorization.setAuthorizationName(authorizationName);
		dicAuthorizationService.save(dicAuthorization);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody DicAuthorization dicAuthorization){
		dicAuthorizationService.update(dicAuthorization);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		dicAuthorizationService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
