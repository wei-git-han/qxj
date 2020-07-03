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
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.entity.QxjLeaveCancel;
import com.css.app.qxjgl.business.service.QxjLeaveCancelService;


/**
 * 销假天数配置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-03 10:26:36
 */
@Controller
@RequestMapping("/app/qxjgl/leavecancel")
public class QxjLeaveCancelController {
	@Autowired
	private QxjLeaveCancelService qxjLeaveCancelService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	public void info(){
		String orgId = baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId());
		QxjLeaveCancel qxjLeaveCancel = qxjLeaveCancelService.findByDeptId(orgId);
		if(qxjLeaveCancel==null) {
			qxjLeaveCancel= new QxjLeaveCancel();
			qxjLeaveCancel.setId(UUIDUtils.random());
			qxjLeaveCancel.setDays("15");
			qxjLeaveCancel.setType("0");
			qxjLeaveCancel.setDeptId(orgId);
			BaseAppOrgan org = baseAppOrganService.queryObject(orgId);
			qxjLeaveCancel.setDeptName(org.getName());
			qxjLeaveCancelService.save(qxjLeaveCancel);
		}
		Response.json("qxjLeaveCancel", qxjLeaveCancel);
	}
	
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(QxjLeaveCancel qxjLeaveCancel){
		qxjLeaveCancelService.update(qxjLeaveCancel);
		Response.ok();
	}
	
	
}
