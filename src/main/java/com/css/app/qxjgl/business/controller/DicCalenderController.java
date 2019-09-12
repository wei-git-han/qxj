package com.css.app.qxjgl.business.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.css.app.qxjgl.business.entity.ResultModel;
import com.css.app.qxjgl.business.entity.ZFDicUsersModel;
import com.css.base.utils.GwPageUtils;
import com.github.pagehelper.PageInfo;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.CurrentUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.css.app.qxjgl.business.entity.DicCalender;
import com.css.app.qxjgl.business.service.DicCalenderService;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;


/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 15:14:15
 */
@Controller
@RequestMapping("app/qxjgl/qxjdiccalender")
public class DicCalenderController {
	@Autowired
	private DicCalenderService dicCalenderService;
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
	@RequiresPermissions("qxjdiccalender:list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<DicCalender> qxjDicCalenderList = dicCalenderService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjDicCalenderList);
		Response.json("page",pageUtil);
	}
	
	@ResponseBody
	@RequestMapping("/queryHoliday")
	public List<DicCalender> queryHoliday(String from, String to){
		Date date1 = null;
		Date date2 = null;
		List<DicCalender> queryHolidayLis =null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse("20190302");
			date2=new SimpleDateFormat("yyyy-MM-dd").parse("20190402");
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("startDate", date1);
			paraMap.put("toDate", date2);
			queryHolidayLis = dicCalenderService.queryHoliday(paraMap);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return queryHolidayLis;
	} 
	
	@ResponseBody
	@RequestMapping("/queryDate")
	public void queryDate(String Year,String Month){
		Date date1 = null;
		Date date2 = null;
		String start = Year+Month+"01";
		String end = Year+Month+"31";
		List<DicCalender> queryHolidayLis =null;
		try {
			date1 = new SimpleDateFormat("yyyyMMdd").parse(start);
			date2=new SimpleDateFormat("yyyyMMdd").parse(end);
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("startDate", date1);
			paraMap.put("toDate", date2);
			paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(CurrentUser.getUserId()));
			queryHolidayLis = dicCalenderService.queryHoliday(paraMap);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Response.json(queryHolidayLis);
	} 
	 
	@ResponseBody
	@RequestMapping("/queryAllYear")
	public void queryAllYear(String Year){
		Date date1 = null;
		Date date2 = null;
		String start = Year+"0101";
		String end = Year+"1231";
		List<DicCalender> queryHolidayLis =null;
		try {
			date1 = new SimpleDateFormat("yyyyMMdd").parse(start);
			date2=new SimpleDateFormat("yyyyMMdd").parse(end);
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("startDate", date1);
			paraMap.put("toDate", date2);
			queryHolidayLis = dicCalenderService.queryHoliday(paraMap);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Response.json(queryHolidayLis);
	} 
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("qxjdiccalender:info")
	public void info(@PathVariable("id") String id){
		DicCalender qxjDicCalender = dicCalenderService.queryObject(id);
		Response.json("qxjDicCalender", qxjDicCalender);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("qxjdiccalender:save")
		public void save(String[] dutyPeople){
		DicCalender qxjDicCalender=new DicCalender();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<dutyPeople.length;i++) {
			try {
				Date holidayDate = sdf.parse(dutyPeople[i]);
				qxjDicCalender.setId(UUIDUtils.random());
				qxjDicCalender.setCalenderDate(holidayDate);
				qxjDicCalender.setIsholiday("1");
				qxjDicCalender.setIsworkingday("0");
				qxjDicCalender.setCalenderDate(holidayDate);
				dicCalenderService.save(qxjDicCalender);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		Response.ok();
	}
	/**
	 * 日历页面切换先删除再保存
	 */
	@ResponseBody
	@RequestMapping("/deleteSave")
	public void deleteSave(String[] dutyPeople, String year, String month) {
		Map<String, Object> map = new HashMap<>();
		Calendar instance = Calendar.getInstance(Locale.CHINA);
		// instance.set(Calendar.YEAR,Integer.parseInt(year));
		// instance.set(Calendar.MONTH,Integer.parseInt(month));
		String userId = CurrentUser.getUserId();
		try {
			Date parse = new SimpleDateFormat("yyyyMMdd").parse(year + month + "01");
			instance.setTime(parse);
			int actualMaximum = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
			String start = year + "-" + month + "-01";
			String end = year + "-" + month + "-" + actualMaximum;
			map.put("startDate", new SimpleDateFormat("yyyy-MM-dd").parse(start));
			map.put("toDate", new SimpleDateFormat("yyyy-MM-dd").parse(end));
			map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		dicCalenderService.removeOneMouth(map);
		if (dutyPeople.length > 0) {
			DicCalender qxjDicCalender = new DicCalender();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < dutyPeople.length; i++) {
				try {
					Date holidayDate = sdf.parse(dutyPeople[i]);
					qxjDicCalender.setId(UUIDUtils.random());
					qxjDicCalender.setIsholiday("1");
					qxjDicCalender.setIsworkingday("0");
					qxjDicCalender.setCalenderDate(holidayDate);
					String orgId = baseAppOrgMappedService.getBareauByUserId(userId);
					BaseAppOrgan baseAppOrgan = baseAppOrganService.queryObject(orgId);
					qxjDicCalender.setOrgId(orgId);
					qxjDicCalender.setOrgName(baseAppOrgan.getName());
					dicCalenderService.save(qxjDicCalender);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("qxjdiccalender:delete")
	public void delete(@RequestBody String[] ids){
		dicCalenderService.deleteBatch(ids);
		
		Response.ok();
	}


}
