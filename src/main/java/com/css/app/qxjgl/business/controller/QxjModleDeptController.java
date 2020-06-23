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

import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.app.qxjgl.business.entity.QxjModleDept;
import com.css.app.qxjgl.business.service.QxjModleDeptService;


/**
 * 请销假模板部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-06-22 14:47:08
 */
@Controller
@RequestMapping("/app/qxjgl/modledept")
public class QxjModleDeptController {
	@Autowired
	private QxjModleDeptService qxjModleDeptService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer pagesize, String roleType){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, pagesize);
		//查询列表数据
		List<QxjModleDept> qxjModleDeptList = qxjModleDeptService.queryList(map);
		PageUtils pageUtil = new PageUtils(qxjModleDeptList);
		Response.json(pageUtil.getPageResult());	
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	public void info(String id){
		QxjModleDept qxjModleDept = qxjModleDeptService.queryObject(id);
		Response.json("qxjModleDept", qxjModleDept);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(QxjModleDept qxjModleDept){
		qxjModleDeptService.save(qxjModleDept);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody QxjModleDept qxjModleDept){
		qxjModleDeptService.update(qxjModleDept);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		qxjModleDeptService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
