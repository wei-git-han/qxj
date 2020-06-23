package com.css.app.qxjgl.business.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;

@RestController
@RequestMapping("/app/qxjgl/fissionTask")
public class FissionTask {
	
	  @Autowired
      private LeaveorbackService leaveorbackService;

	  @Scheduled(cron = "0 0 7 * * ?")
	  @RequestMapping("/finishXjTask")
	  public void finishXjTask() throws ParseException {
		    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String NowDate = sdf.format(new Date());
		
		
		List<Leaveorback> list = leaveorbackService.selByBackAndTenday();
		for(int i = 0; i<list.size(); i++) {
			Leaveorback leaveorback = list.get(i);
			Date planTimeEnd = leaveorback.getPlanTimeEnd();
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = sdff.parse(NowDate);
			int isum = planTimeEnd.compareTo(nowDate);
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
