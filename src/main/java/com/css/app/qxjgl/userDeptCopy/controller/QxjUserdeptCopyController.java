package com.css.app.qxjgl.userDeptCopy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.userDeptCopy.entity.QxjUserdeptCopy;
import com.css.app.qxjgl.userDeptCopy.service.QxjUserdeptCopyService;
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
 * 用户原来部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-13 11:19:52
 */
@Controller
@RequestMapping("/qxjuserdeptcopy")
public class QxjUserdeptCopyController {
	@Autowired
	private QxjUserdeptCopyService qxjUserdeptCopyService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjuserdeptcopy:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<QxjUserdeptCopy> qxjUserdeptCopyList = qxjUserdeptCopyService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjUserdeptCopyList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjuserdeptcopy:info")
	public void info(@PathVariable("id") String id){
		QxjUserdeptCopy qxjUserdeptCopy = qxjUserdeptCopyService.queryObject(id);
		Response.json("qxjUserdeptCopy", qxjUserdeptCopy);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjuserdeptcopy:save")
	public void save(@RequestBody QxjUserdeptCopy qxjUserdeptCopy){
		qxjUserdeptCopy.setId(UUIDUtils.random());
		qxjUserdeptCopyService.save(qxjUserdeptCopy);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qxjuserdeptcopy:update")
	public void update(@RequestBody QxjUserdeptCopy qxjUserdeptCopy){
		qxjUserdeptCopyService.update(qxjUserdeptCopy);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjuserdeptcopy:delete")
	public void delete(@RequestBody String[] ids){
		qxjUserdeptCopyService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
