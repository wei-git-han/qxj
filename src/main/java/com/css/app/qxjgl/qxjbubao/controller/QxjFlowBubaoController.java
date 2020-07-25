package com.css.app.qxjgl.qxjbubao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.qxjbubao.entity.QxjFlowBubao;
import com.css.app.qxjgl.qxjbubao.service.QxjFlowBubaoService;
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
 * 补报表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-25 14:38:45
 */
@Controller
@RequestMapping("/qxjflowbubao")
public class QxjFlowBubaoController {
	@Autowired
	private QxjFlowBubaoService qxjFlowBubaoService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjflowbubao:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<QxjFlowBubao> qxjFlowBubaoList = qxjFlowBubaoService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjFlowBubaoList);
		Response.json("page",pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjflowbubao:info")
	public void info(@PathVariable("id") String id){
		QxjFlowBubao qxjFlowBubao = qxjFlowBubaoService.queryObject(id);
		Response.json("qxjFlowBubao", qxjFlowBubao);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjflowbubao:save")
	public void save(@RequestBody QxjFlowBubao qxjFlowBubao){
		qxjFlowBubao.setId(UUIDUtils.random());
		qxjFlowBubaoService.save(qxjFlowBubao);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qxjflowbubao:update")
	public void update(@RequestBody QxjFlowBubao qxjFlowBubao){
		qxjFlowBubaoService.update(qxjFlowBubao);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjflowbubao:delete")
	public void delete(@RequestBody String[] ids){
		qxjFlowBubaoService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
