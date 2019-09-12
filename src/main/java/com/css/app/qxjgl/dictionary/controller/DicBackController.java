package com.css.app.qxjgl.dictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.css.app.qxjgl.dictionary.entity.DicBack;
import com.css.app.qxjgl.dictionary.service.DicBackService;


/**
 * 销假状态字典表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:36:05
 */
@Controller
@RequestMapping("app/qxjgl/dicback")
public class DicBackController {
	@Autowired
	private DicBackService dicBackService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<DicBack> dicBackList = dicBackService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(dicBackList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		DicBack dicBack = dicBackService.queryObject(id);
		Response.json("dicBack", dicBack);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(@RequestBody DicBack dicBack){
		dicBack.setId(UUIDUtils.random());
		dicBackService.save(dicBack);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody DicBack dicBack){
		dicBackService.update(dicBack);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		dicBackService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
