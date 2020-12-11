package com.css.app.qxjgl.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.css.app.qxjgl.business.entity.ProvinceCityDistrict;
import com.css.app.qxjgl.business.service.ProvinceCityDistrictService;
import com.css.base.utils.StringUtils;
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


/**
 * 省市县数据表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-12-11 15:18:35
 */
@Controller
@RequestMapping("/app/qxjgl/provincecitydistrict")
public class ProvinceCityDistrictController {
	@Autowired
	private ProvinceCityDistrictService provinceCityDistrictService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("provincecitydistrict:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<ProvinceCityDistrict> provinceCityDistrictList = provinceCityDistrictService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(provinceCityDistrictList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("provincecitydistrict:info")
	public void info(@PathVariable("id") String id){
		ProvinceCityDistrict provinceCityDistrict = provinceCityDistrictService.queryObject(id);
		Response.json("provinceCityDistrict", provinceCityDistrict);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("provincecitydistrict:save")
	public void save(@RequestBody ProvinceCityDistrict provinceCityDistrict){
		provinceCityDistrict.setId(UUIDUtils.random());
		provinceCityDistrictService.save(provinceCityDistrict);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("provincecitydistrict:update")
	public void update(@RequestBody ProvinceCityDistrict provinceCityDistrict){
		provinceCityDistrictService.update(provinceCityDistrict);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("provincecitydistrict:delete")
	public void delete(@RequestBody String[] ids){
		provinceCityDistrictService.deleteBatch(ids);
		
		Response.ok();
	}

	/**
	 * 省市区三级联动查询
	 * @param pid
	 */
	@ResponseBody
	@RequestMapping("/getPCD")
	public void getPCD(String pid){
		JSONObject jsonObject = new JSONObject();
		List<ProvinceCityDistrict> list = new ArrayList<>();
		if(StringUtils.isNotBlank(pid)){
			list = provinceCityDistrictService.findByPid(pid);
		}else{
			list = provinceCityDistrictService.findAll("all");
		}

		jsonObject.put("result","success");
		jsonObject.put("list",list);
		Response.json(jsonObject);
	}
	
}
