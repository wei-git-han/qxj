package com.css.app.qxjgl.business.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.QxjLeaveCancel;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.QxjLeaveCancelService;
import com.css.base.utils.DateUtil;
import com.github.pagehelper.util.StringUtil;

@Component
public class FissionTask {
	
	  @Autowired
      private LeaveorbackService leaveorbackService;
	  @Autowired
	  private QxjLeaveCancelService qxjLeaveCancelService;
	  
	  @Scheduled(cron = "0 0/5 * * * ?")
	  public void finishXjTask() throws ParseException {
		List<QxjLeaveCancel> xjList = qxjLeaveCancelService.findAll();
		Map<String,String> days= new HashMap<String, String>();
		for (QxjLeaveCancel qxjLeaveCancel : xjList) {
			days.put(qxjLeaveCancel.getDeptId(), qxjLeaveCancel.getDays());
		}
		List<Leaveorback> list = leaveorbackService.selByBackAndTenday();
		for(Leaveorback leaveorback:list) {
			Date planTimeEnd = leaveorback.getPlanTimeEnd();
			String xjDays = days.get(leaveorback.getOrgId());
			if(StringUtil.isNotEmpty(xjDays)) {
				Date addDays = DateUtil.addDays(planTimeEnd,Integer.parseInt(xjDays));
				Date nowDate = DateUtil.getCurrentDate(new Date(),null);
				int isum = addDays.compareTo(nowDate);
				//正数左侧大
				if(isum>0) {
					Leaveorback leaveorbackk = new Leaveorback();
					leaveorbackk.setBackStatusId("1");
					leaveorbackk.setId(leaveorback.getId());
					leaveorbackService.updateBackStatusId(leaveorbackk);
				}
			}
		}
	  }
	 
}
