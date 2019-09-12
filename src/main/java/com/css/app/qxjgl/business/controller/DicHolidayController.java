package com.css.app.qxjgl.business.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.app.qxjgl.business.entity.LogHoliday;
import com.css.app.qxjgl.business.service.DicHolidayService;
import com.css.app.qxjgl.business.service.LogHolidayService;
import com.css.base.entity.SSOUser;
import com.github.pagehelper.PageHelper;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-27 18:34:56
 */
@Controller
@RequestMapping("app/qxjgl/qxjdicholiday")
public class DicHolidayController {
	@Autowired
	private DicHolidayService dicHolidayService;
	@Autowired
	private LogHolidayService logHolidayService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("qxjdicholiday:list")
	public void list(Integer page, Integer pagesize, String roleType){
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.equals(roleType, "3") || StringUtils.equals(roleType, "5")) {
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
		}
		PageHelper.startPage(page, pagesize);
		//查询列表数据
		List<DicHoliday> qxjDicHolidayList = dicHolidayService.queryList(map);
		PageUtils pageUtil = new PageUtils(qxjDicHolidayList);
		Response.json(pageUtil.getPageResult());		
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	@RequiresPermissions("qxjdicholiday:info")
	public void info(String id){
		DicHoliday qxjDicHoliday = dicHolidayService.queryObject(id);
		Response.json(qxjDicHoliday);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(DicHoliday qxjDicHoliday){
		String userId = qxjDicHoliday.getUserid();
		DicHoliday queryByUserid = dicHolidayService.queryByUserId(userId);
		if(queryByUserid==null) {
			qxjDicHoliday.setId(UUIDUtils.random());
			String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
			BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(orgId);
			qxjDicHoliday.setOrgId(orgId);
			qxjDicHoliday.setOrgName(baseAppOrgan.getName());
			dicHolidayService.save(qxjDicHoliday);
		Response.ok();
		}else {			
			Response.error(0, "repeat");
		}
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Transactional
	public void update(DicHoliday qxjDicHoliday){
		SSOUser loginUser = CurrentUser.getSSOUser();
		LogHoliday qxjLogHoliday=new LogHoliday();
//		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").pars
//        long currentTimeMillis = System.currentTimeMillis();
		qxjLogHoliday.setId(UUIDUtils.random());
		qxjLogHoliday.setUserid(qxjDicHoliday.getUserid());
		qxjLogHoliday.setUsername(qxjDicHoliday.getUsername());
		qxjLogHoliday.setDeptcode(qxjDicHoliday.getDeptid());		
		qxjLogHoliday.setDeptname(qxjDicHoliday.getDeptname());
		qxjLogHoliday.setAfterdays(qxjDicHoliday.getShouldtakdays());
		qxjLogHoliday.setLogTime(new Date());
		qxjLogHoliday.setModifyUserid(loginUser.getUserId());
		qxjLogHoliday.setModifyUserName(loginUser.getFullname());
		dicHolidayService.update(qxjDicHoliday);
		logHolidayService.save(qxjLogHoliday);//记录更新日志
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjdicholiday:delete")
	public void delete(String[] id){
		dicHolidayService.deleteBatch(id);
		
		Response.ok();
	}
	
}
