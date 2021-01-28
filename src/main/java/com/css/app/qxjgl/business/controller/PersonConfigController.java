package com.css.app.qxjgl.business.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.css.base.utils.CurrentUser;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/app/qxjgl/personconfig")
public class PersonConfigController {
	private final static Logger logger = LoggerFactory.getLogger(PersonConfigController.class);
	@Autowired
	private DicHolidayService dicHolidayService;
	@Autowired
	private LogHolidayService logHolidayService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	
	/**
	 * @description:获取当前登录人的应休天数
	 * @author:zhangyw
	 * @date:2019年8月15日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getPersonConfig")
	public void getPersonConfig() {
		DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(CurrentUser.getUserId());
		Response.json("perConfig",qxjDicHoliday);
	}

	/**
	 * 请假多个人时，判断所选择的人是否配置了请假天数
	 */
	@ResponseBody
	@RequestMapping("/getIsPersonConfig")
	public void getIsPersonConfig(String userId) {
		JSONObject jsonObject = new JSONObject();
		DicHoliday qxjDicHoliday = dicHolidayService.queryByUserId(userId);
		if(qxjDicHoliday != null){
			jsonObject.put("flag",true);
		}else{
			jsonObject.put("flag",false);
		}
		jsonObject.put("userId",userId);
		Response.json(jsonObject);
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer pagesize) {
		String loginUserId = CurrentUser.getUserId();
		List<DicHoliday> qxjDicHolidayList = null;
		if (StringUtils.isNotEmpty(loginUserId)) {
			PageHelper.startPage(page, pagesize);
			Map<String, Object> map = new HashMap<>();
			map.put("userId", loginUserId);
			qxjDicHolidayList = dicHolidayService.queryList(map);
		}
		PageUtils pageUtil = new PageUtils(qxjDicHolidayList);
		Response.json(pageUtil.getPageResult());
	}

	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	@RequiresPermissions("qxjdicholiday:info")
	public void info(String id) {
		DicHoliday qxjDicHoliday = dicHolidayService.queryObject(id);
		Response.json(qxjDicHoliday);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(DicHoliday qxjDicHoliday) {
		String userId = CurrentUser.getUserId();
		Integer year = qxjDicHoliday.getYear();
		DicHoliday qxjdicholiday = dicHolidayService.queryByUserId(userId);
		if (qxjdicholiday == null) {
			qxjDicHoliday.setId(UUIDUtils.random());
			qxjDicHoliday.setUserid(userId);
			qxjDicHoliday.setUsername(CurrentUser.getUsername());
			qxjDicHoliday.setDeptname(CurrentUser.getOrgName());
			qxjDicHoliday.setDeptid(CurrentUser.getDepartmentId());
			BaseAppOrgan baseAppOrgan = commonQueryManager.acquireLoginPersonOrgConfig(userId);
			if (baseAppOrgan != null) {
				qxjDicHoliday.setOrgId(baseAppOrgan.getId());
				qxjDicHoliday.setOrgName(baseAppOrgan.getName());
			} else {
				logger.info("根据用户 ID：{}，查不到所在局机构配置信息", userId);
			}
			dicHolidayService.save(qxjDicHoliday);
			Response.json("msg", "success");
		} else {
			Response.json("msg", "fail");
		}
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@Transactional
	public void update(DicHoliday qxjDicHoliday) {
		SSOUser loginUser = CurrentUser.getSSOUser();
		LogHoliday qxjLogHoliday = new LogHoliday();
		
		qxjLogHoliday.setId(UUIDUtils.random());
		qxjLogHoliday.setUserid(qxjDicHoliday.getUserid());
		qxjLogHoliday.setUsername(qxjDicHoliday.getUsername());
		qxjLogHoliday.setDeptcode(qxjDicHoliday.getDeptid());
		qxjLogHoliday.setDeptname(qxjDicHoliday.getDeptname());
		qxjLogHoliday.setAfterdays(qxjDicHoliday.getShouldtakdays());
		qxjLogHoliday.setAfterdays(qxjDicHoliday.getShouldtakdays());
		qxjLogHoliday.setLogTime(new Date());
		qxjLogHoliday.setModifyUserid(loginUser.getUserId());
		qxjLogHoliday.setModifyUserName(loginUser.getFullname());
		BaseAppOrgan baseAppOrgan = commonQueryManager.acquireLoginPersonOrgConfig(loginUser.getUserId());
		if (baseAppOrgan != null) {
			qxjDicHoliday.setOrgId(baseAppOrgan.getId());
			qxjDicHoliday.setOrgName(baseAppOrgan.getName());
		} else {
			logger.info("根据用户 ID：{}，查不到所在局机构配置信息", loginUser.getUserId());
		}
		dicHolidayService.update(qxjDicHoliday);
		logHolidayService.save(qxjLogHoliday);// 记录更新日志
		Response.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjdicholiday:delete")
	public void delete(String id) {
		dicHolidayService.delete(id);
		Response.ok();
	}

}
