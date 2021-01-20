package com.css.app.qxjgl.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.entity.QxjLeaveorbackFollow;
import com.css.app.qxjgl.business.service.QxjLeaveorbackFollowService;
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
 * 请假单-随员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-19 16:27:19
 */
@Controller
@RequestMapping("/qxjleaveorbackfollow")
public class QxjLeaveorbackFollowController {
	@Autowired
	private QxjLeaveorbackFollowService qxjLeaveorbackFollowService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjleaveorbackfollow:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<QxjLeaveorbackFollow> qxjLeaveorbackFollowList = qxjLeaveorbackFollowService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjLeaveorbackFollowList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjleaveorbackfollow:info")
	public void info(@PathVariable("id") String id){
		QxjLeaveorbackFollow qxjLeaveorbackFollow = qxjLeaveorbackFollowService.queryObject(id);
		Response.json("qxjLeaveorbackFollow", qxjLeaveorbackFollow);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjleaveorbackfollow:save")
	public void save(@RequestBody QxjLeaveorbackFollow qxjLeaveorbackFollow){
		qxjLeaveorbackFollow.setId(UUIDUtils.random());
		qxjLeaveorbackFollowService.save(qxjLeaveorbackFollow);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qxjleaveorbackfollow:update")
	public void update(@RequestBody QxjLeaveorbackFollow qxjLeaveorbackFollow){
		qxjLeaveorbackFollowService.update(qxjLeaveorbackFollow);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjleaveorbackfollow:delete")
	public void delete(@RequestBody String[] ids){
		qxjLeaveorbackFollowService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
