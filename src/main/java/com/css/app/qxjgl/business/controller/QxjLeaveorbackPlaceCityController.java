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

import com.css.base.utils.PageUtils;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.QxjLeaveorbackPlaceCity;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.business.service.QxjLeaveorbackPlaceCityService;


/**
 * 保存请销假单-多个出差地点
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-06 16:01:57
 */
@Controller
@RequestMapping("/app/qxjgl/qxjleaveorbackplacecity")
public class QxjLeaveorbackPlaceCityController {
	@Autowired
	private QxjLeaveorbackPlaceCityService qxjLeaveorbackPlaceCityService;
    @Autowired
    private LeaveorbackService leaveorbackService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		PageHelper.startPage(page, limit);
		
		//查询列表数据
		List<QxjLeaveorbackPlaceCity> qxjLeaveorbackPlaceCityList = qxjLeaveorbackPlaceCityService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(qxjLeaveorbackPlaceCityList);
		Response.json("page",pageUtil);
	}
	/**
	 * 通过请销假单id
	 */
	@ResponseBody
	@RequestMapping("/queryPlcaeList")
	public void queryPlcaeList(String leaveorbackId) {
		List<QxjLeaveorbackPlaceCity> queryPlcaeList = qxjLeaveorbackPlaceCityService.queryPlcaeList(leaveorbackId);
		Response.json("list",queryPlcaeList);
	}
	
	/**
	 * 处理老数据接口
	 */
	@ResponseBody
	@RequestMapping("/savePlactList")
	public void savePlactList(){
		List<Leaveorback> queryAllByPlaceIsNotNull = leaveorbackService.queryAllByPlaceIsNotNull();
		for (Leaveorback leaveorback : queryAllByPlaceIsNotNull) {
			QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity = new QxjLeaveorbackPlaceCity();
			qxjLeaveorbackPlaceCity.setLeaveorbackId(leaveorback.getId());
			if(StringUtils.isNotBlank(leaveorback.getPlace())) {
				qxjLeaveorbackPlaceCity.setPlace(leaveorback.getPlace());
			}
			if(StringUtils.isNotBlank(leaveorback.getCity())) {
				qxjLeaveorbackPlaceCity.setCity(leaveorback.getCity());
			}
			if(StringUtils.isNotBlank(leaveorback.getAddress())) {
				qxjLeaveorbackPlaceCity.setAddress(leaveorback.getAddress());
			}
			qxjLeaveorbackPlaceCity.setLevelStatus("0");
			qxjLeaveorbackPlaceCity.setCraeateDate(leaveorback.getCreateDate());
			qxjLeaveorbackPlaceCity.setUpdateDate(leaveorback.getCreateDate());
			
			qxjLeaveorbackPlaceCity.setId(UUIDUtils.random());
			qxjLeaveorbackPlaceCity.setUserId(leaveorback.getDeleteMark());
			qxjLeaveorbackPlaceCity.setUserName(leaveorback.getProposer());
			qxjLeaveorbackPlaceCityService.save(qxjLeaveorbackPlaceCity);
			
			
		}
		Response.ok();
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity = qxjLeaveorbackPlaceCityService.queryObject(id);
		Response.json("qxjLeaveorbackPlaceCity", qxjLeaveorbackPlaceCity);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(@RequestBody QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity){
		qxjLeaveorbackPlaceCity.setId(UUIDUtils.random());
		qxjLeaveorbackPlaceCityService.save(qxjLeaveorbackPlaceCity);
		
		Response.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity){
		qxjLeaveorbackPlaceCityService.update(qxjLeaveorbackPlaceCity);
		
		Response.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(@RequestBody String[] ids){
		qxjLeaveorbackPlaceCityService.deleteBatch(ids);
		
		Response.ok();
	}
	
}
