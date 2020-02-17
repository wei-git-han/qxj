package com.css.app.qxjgl.business.service.impl;

import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.FileInfoService;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {
	@Autowired
	private DicVocationSortService dicVocationSortService;//请假类别
	

	
	@Override
	public InputStream createQXJExcel(List<Leaveorback> list, String fileName) throws Exception {
		
		FileOutputStream fout = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("请假情况详情列表");
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中
//			 HSSFFont createFont = wb.createFont();
//			 createFont.setFontName("黑体");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(style);					
			cell = row.createCell(1);
			cell.setCellValue("请假部门");
			cell.setCellStyle(style);			
			cell = row.createCell(2);
			cell.setCellValue("请假人");
			cell.setCellStyle(style);			
			cell = row.createCell(3);
			cell.setCellValue("应休天数");
			cell.setCellStyle(style);			
			cell = row.createCell(4);
			cell.setCellValue("请假类别");
			cell.setCellStyle(style);		
			
			cell = row.createCell(5);
			cell.setCellValue("地点");
			cell.setCellStyle(style);	
			
			cell = row.createCell(6);
			cell.setCellValue("请假事由");
			cell.setCellStyle(style);		
			
			cell = row.createCell(7);
			cell.setCellValue("休假天数");
			cell.setCellStyle(style);			
			cell = row.createCell(8);
			cell.setCellValue("起止日期");
			cell.setCellStyle(style);			
			cell = row.createCell(9);
			cell.setCellValue("法定节假日天数（已扣除）");
			cell.setCellStyle(style);			
			cell = row.createCell(10);
			cell.setCellValue("周六日天数（含）");
			cell.setCellStyle(style);			
			cell = row.createCell(11);
			cell.setCellValue("申请状态");
			cell.setCellStyle(style);			
			cell = row.createCell(12);
			cell.setCellValue("销假状态");
			cell.setCellStyle(style);						
//			cell = row.createCell(11);
//			cell.setCellValue("操作");
//			cell.setCellStyle(style);			
//			cell = row.createCell(11);
//			cell.setCellValue("备注");
//			cell.setCellStyle(style);			
			String statusName = null;
			String backStatusIdsName =null;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(1+i);
			String qjlb = "";
			Leaveorback Tleaveorback = list.get(i);
			Integer statusCode = Tleaveorback.getStatus();
			String backStatusId = Tleaveorback.getBackStatusId()== null ?"0" :Tleaveorback.getBackStatusId();						
			if(StringUtils.isNotEmpty(Tleaveorback.getVacationSortId())){
				DicVocationSort dicVocationSort =  dicVocationSortService.queryObject(Tleaveorback.getVacationSortId());
				if(dicVocationSort != null){
					qjlb = dicVocationSort.getVacationSortId();
				    HSSFCell contentCell=row.createCell(4);
					contentCell.setCellValue(qjlb);//请假类别
					contentCell.setCellStyle(style);
				}
			}else{
			    HSSFCell contentCell=row.createCell(4);
				contentCell.setCellValue(qjlb);//请假类别
				contentCell.setCellStyle(style);
			}
			
			switch (statusCode) {
			case 0:
				statusName="待提交";
				break;
			case 10:
				statusName="审批中";
				break;
			case 30:
				statusName="已通过";
				break;
			case 20:
				statusName="已退回";
				break;
			}
			
			switch (backStatusId) {
			case "0":
				backStatusIdsName="未销假";
				break;
			case "1":
				backStatusIdsName="已销假";
				break;
			/*case "2":
				backStatusIdsName="已通过";
				break;
			case "3":
				backStatusIdsName="未通过";
				break;*/
			}
			
			
			    HSSFCell contentCell=row.createCell(0);
				contentCell.setCellValue(1+i);
				contentCell.setCellStyle(style);	
				contentCell = row.createCell(1);
				contentCell.setCellValue(Tleaveorback.getOrgName()==null?"":Tleaveorback.getOrgName());//请假部门"
				contentCell.setCellStyle(style);			

				contentCell = row.createCell(2);
				contentCell.setCellValue(Tleaveorback.getProposer()==null?"":Tleaveorback.getProposer());//请假人
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(3);
				contentCell.setCellValue(Tleaveorback.getShouldTakDays()==null?0:Tleaveorback.getShouldTakDays());//应休天数
				contentCell.setCellStyle(style);	
		
				contentCell = row.createCell(5);
				contentCell.setCellValue(String.valueOf(Tleaveorback.getPlace()==null?0:Tleaveorback.getPlace()));//地点
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(6);
				contentCell.setCellValue(String.valueOf(Tleaveorback.getOrigin()==null?0:Tleaveorback.getOrigin()));//请假事由
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(7);
				contentCell.setCellValue(String.valueOf(Tleaveorback.getActualVocationDate()==null?0:Tleaveorback.getActualVocationDate()));//休假天数
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(8);
				contentCell.setCellValue(Tleaveorback.getPlanTimeStartEnd()==null?"":Tleaveorback.getPlanTimeStartEnd());//起止日期
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(9);
				contentCell.setCellValue(Tleaveorback.getHolidayNum()==null?0:Tleaveorback.getHolidayNum());//法定节假日天数（已扣除）
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(10);
				contentCell.setCellValue(Tleaveorback.getWeekendNum()==null?0:Tleaveorback.getWeekendNum());//周六日天数（含）
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(11);
				contentCell.setCellValue(statusName==null?"":statusName);//申请状态
				contentCell.setCellStyle(style);	
				
				contentCell = row.createCell(12);
				contentCell.setCellValue(backStatusIdsName==null?"":backStatusIdsName);//销假状态
				contentCell.setCellStyle(style);	
				
//				contentCell = row.createCell(11);
//				contentCell.setCellValue(Tleaveorback.getLeaveRemark());//备注
//				contentCell.setCellStyle(style);	
				
			}
					
				
				sheet.setColumnWidth(0, 2000);
				sheet.setColumnWidth(1, 6000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 3000);//应休天数
				sheet.setColumnWidth(4, 4000);//请假类别
				sheet.setColumnWidth(5, 3000);
				sheet.setColumnWidth(6, 7000);				
				sheet.setColumnWidth(7, 5600);//法定节假日天数（已扣除）
				sheet.setColumnWidth(8, 4000);
				sheet.setColumnWidth(9, 3000);
				sheet.setColumnWidth(10, 3000);
//				sheet.setColumnWidth(11, 10000);

				fout = new FileOutputStream(fileName);
				wb.write(fout);
				fout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				fout.close();
			}
		}
		return new FileInputStream(fileName);
	}
}
