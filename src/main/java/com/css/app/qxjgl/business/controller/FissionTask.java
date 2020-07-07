package com.css.app.qxjgl.business.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.QxjLeaveCancel;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.QxjLeaveCancelService;
import com.css.base.utils.DateUtil;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.util.StringUtil;

@Component
public class FissionTask {
	
	@Autowired
	private LeaveorbackService leaveorbackService;
	@Autowired
	private QxjLeaveCancelService qxjLeaveCancelService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private BaseAppUserService baseAppUserService;
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void finishXjTask() throws ParseException {
		List<BaseAppOrgan> deptIds = baseAppOrganService.findByParentId("root");
		for (BaseAppOrgan baseAppOrgan : deptIds) {
			Map<String,String> map = new HashMap<>();
			map.put("deptId", baseAppOrgan.getId());
			QxjLeaveCancel qxjLeaveCancel = qxjLeaveCancelService.findByDeptId(map);
			if(qxjLeaveCancel==null) {
				qxjLeaveCancel= new QxjLeaveCancel();
				qxjLeaveCancel.setId(UUIDUtils.random());
				qxjLeaveCancel.setDays("15");
				qxjLeaveCancel.setType("0");
				qxjLeaveCancel.setDeptId(baseAppOrgan.getId());
				BaseAppOrgan org = baseAppOrganService.queryObject(baseAppOrgan.getId());
				qxjLeaveCancel.setDeptName(org.getName());
				qxjLeaveCancelService.save(qxjLeaveCancel);
			}
		}
		List<QxjLeaveCancel> xjList = qxjLeaveCancelService.findAll();
		Map<String,String> days= new HashMap<String, String>();
		for (QxjLeaveCancel qxjLeaveCancel : xjList) {
			days.put(qxjLeaveCancel.getDeptId(), qxjLeaveCancel.getDays());
		}
		List<Leaveorback> list = leaveorbackService.selByBackAndTenday();
		for(Leaveorback leaveorback:list) {
			Date planTimeEnd = leaveorback.getPlanTimeEnd();
			String juId = leaveorback.getParentOrgId();
			if(StringUtil.isEmpty(juId)) {
				juId = baseAppUserService.getBareauByUserId(leaveorback.getDeleteMark());
			}
			String xjDays = days.get(juId);
			if(StringUtil.isNotEmpty(xjDays)) {
				Date addDays = DateUtil.addDays(planTimeEnd,Integer.parseInt(xjDays));
				Date nowDate = DateUtil.getCurrentDate(new Date(),null);
				boolean isum = addDays.before(nowDate);
				//正数左侧大
				if(isum) {
					Leaveorback leaveorbackk = leaveorbackService.queryObject(leaveorback.getId());
					leaveorbackk.setBackStatusId("1");
					leaveorbackk.setActualTimeStart(leaveorback.getPlanTimeStart());
					leaveorbackk.setActualTimeEnd(leaveorback.getPlanTimeEnd());
					leaveorbackk.setActualVocationDate(leaveorback.getLeaveDays());
					leaveorbackService.updateWeekendHolidayNum(leaveorbackk);
				}
			}
		}
	}
	 
}
