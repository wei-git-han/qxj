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
import com.css.app.qxjgl.dictionary.entity.DicLeave;
import com.css.app.qxjgl.dictionary.service.DicLeaveService;
import com.github.pagehelper.PageHelper;


/**
 * 请假状态字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
@Controller
@RequestMapping("app/qxjgl/dicleave")
public class DicLeaveController {
	@Autowired
	private DicLeaveService dicLeaveService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<DicLeave> dicLeaveList = dicLeaveService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(dicLeaveList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		DicLeave dicLeave = dicLeaveService.queryObject(id);
		Response.json("dicLeave", dicLeave);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(@RequestBody DicLeave dicLeave){
		dicLeave.setId(UUIDUtils.random());
		dicLeaveService.save(dicLeave);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody DicLeave dicLeave){
		dicLeaveService.update(dicLeave);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		dicLeaveService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
