package com.css.app.qxjgl.business.manager;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.DicCalenderService;
import com.css.app.qxjgl.business.service.LeaveorbackService;
/**
 * 计算未消假天数
 * @author lenovo
 *
 */
@Component
public class CountActualRestDaysManager {

	@Autowired
	private LeaveorbackService leaveorbackService;
	@Autowired
    private DicCalenderService dicCalenderService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	
	/**
     *  计算本年总共休假天数
     * @return int
     */
    public int countActualRestDays(String userId,String newYearEnd) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        LocalDate newYearStartDate = LocalDate.parse(year + "-01" + "-01");
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        LocalDate newYearEndDate = LocalDate.parse(year + "-12" + "-31");
        //newYearEnd不为空，在本年度内查寻截止时间
        if(StringUtils.isNotEmpty(newYearEnd)&&newYearStartDate.isBefore(LocalDate.parse(newYearEnd))
        		&&newYearEndDate.isAfter(LocalDate.parse(newYearEnd))) {
        	map.put("newYearEndDate", newYearEnd);
        }else {
        	map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        }
        map.put("userId", userId);
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        List<Leaveorback> leaveOrBacks = leaveorbackService.queryCurrYearRestDays(map);
        int actualRestDays = 0;
        Date lastYearFirstDay = Date.from(currYearLastYearFirstDay.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        Date currYearLastDay = Date.from(newYearEndDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        
        Date startDate = Date.from(newYearStartDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        for (Leaveorback leaveOrBack : leaveOrBacks) {
        	//
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime()-startDate.getTime())/(24*60*60*1000)+1-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                //计算起始日期到年末的天数差
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        int queryDeducttonDays = queryDeducttonDays(newYearStartDate,newYearEndDate,lastYearFirstDay,currYearLastDay,startDate,userId,newYearEnd);
        actualRestDays = actualRestDays -queryDeducttonDays;
        return actualRestDays;
    }
    
    private int queryDeducttonDays(LocalDate newYearStartDate,LocalDate newYearEndDate,
    		Date lastYearFirstDay,	Date currYearLastDay ,Date startDate,String userId,String newYearEnd) {
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        map.put("newYearStartDate", newYearStartDate.format(dateTimeFormatter));
        if(StringUtils.isNotEmpty(newYearEnd)&&newYearStartDate.isBefore(LocalDate.parse(newYearEnd))
        		&&newYearEndDate.isAfter(LocalDate.parse(newYearEnd))) {
        	map.put("newYearEndDate", newYearEnd);
        }else {
        	map.put("newYearEndDate", newYearEndDate.format(dateTimeFormatter));
        }
        map.put("userId", userId);
        LocalDate currYearLastYearFirstDay = LocalDate.parse(year+1 + "-01" + "-01");
        map.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
        List<Leaveorback> queryDeducttonDays = leaveorbackService.queryDeducttonDays(map);
        int actualRestDays = 0;
        for (Leaveorback leaveOrBack : queryDeducttonDays) {
            if (leaveOrBack.getEndTime().before(lastYearFirstDay)) {
            	if(leaveOrBack.getStartTime().before(startDate)){
            		Map<String, Object> paraMap = new HashMap<String, Object>();
            		paraMap.put("startDate", startDate);
            		paraMap.put("toDate", leaveOrBack.getEndTime());
            		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
            		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
            		actualRestDays +=(int) (leaveOrBack.getEndTime().getTime() - startDate.getTime())/(24*60*60*1000)-holidayNum;
            	}else {
            		actualRestDays += leaveOrBack.getRestDays();
            	}
            }
            if (leaveOrBack.getEndTime().after(currYearLastDay)) {
                Date startTime = leaveOrBack.getStartTime();
                LocalDate localStartTime= startTime.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
                Map<String, Object> paraMap = new HashMap<String, Object>();
        		paraMap.put("startDate", startTime);
        		paraMap.put("toDate",newYearEndDate.format(dateTimeFormatter));
        		paraMap.put("orgId", commonQueryManager.acquireLoginPersonOrgId(userId));
        		int holidayNum = dicCalenderService.queryHolidaySum(paraMap);
                //计算起始日期到年末的天数差
                int currDateToYearEndTotalDays = (int)(newYearEndDate.toEpochDay() - localStartTime.toEpochDay()) + 1-holidayNum;
                actualRestDays += currDateToYearEndTotalDays;
            }
        }
        return actualRestDays ;
    }
}
