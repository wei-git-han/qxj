package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.QxjLeaveorbackPlaceCityDao;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.entity.QxjLeaveorbackPlaceCity;
import com.css.app.qxjgl.business.service.QxjLeaveorbackPlaceCityService;
import com.css.base.utils.StringUtils;
import com.css.base.utils.UUIDUtils;



@Service("qxjLeaveorbackPlaceCityService")
public class QxjLeaveorbackPlaceCityServiceImpl implements QxjLeaveorbackPlaceCityService {
	@Autowired
	private QxjLeaveorbackPlaceCityDao qxjLeaveorbackPlaceCityDao;
	
	@Override
	public QxjLeaveorbackPlaceCity queryObject(String id){
		return qxjLeaveorbackPlaceCityDao.queryObject(id);
	}
	
	@Override
	public List<QxjLeaveorbackPlaceCity> queryList(Map<String, Object> map){
		return qxjLeaveorbackPlaceCityDao.queryList(map);
	}
	
	@Override
	public void save(QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity){
		qxjLeaveorbackPlaceCityDao.save(qxjLeaveorbackPlaceCity);
	}
	
	@Override
	public void update(QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity){
		qxjLeaveorbackPlaceCityDao.update(qxjLeaveorbackPlaceCity);
	}
	
	@Override
	public void delete(String id){
		qxjLeaveorbackPlaceCityDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjLeaveorbackPlaceCityDao.deleteBatch(ids);
	}

	@Override
	public void savePlaces(Leaveorback leave) {
		if(leave !=null) {
			qxjLeaveorbackPlaceCityDao.deleteByLeaveorbackId(leave.getId());
			String place = leave.getPlace();
			String city = leave.getCity();
			String address = leave.getAddress();
			String level = leave.getLevel();
			if(StringUtils.isNotBlank(place) && place.contains(",")) {
				String[] split = place.split(",");
				String[] split2 = city.split(",");
				String[] split3 = address.split(",");
				QxjLeaveorbackPlaceCity qxjLeaveorbackPlaceCity2 = new QxjLeaveorbackPlaceCity();
				qxjLeaveorbackPlaceCity2.setLeaveorbackId(leave.getId());
				qxjLeaveorbackPlaceCity2.setUserId(leave.getDeleteMark());
				qxjLeaveorbackPlaceCity2.setUserName(leave.getProposer());
				qxjLeaveorbackPlaceCity2.setCraeateDate(new Date());
				qxjLeaveorbackPlaceCity2.setUpdateDate(new Date());
				for (int i = 0; i < split.length; i++) {
					qxjLeaveorbackPlaceCity2.setId(UUIDUtils.random());
					qxjLeaveorbackPlaceCity2.setPlace(split[i]);
					qxjLeaveorbackPlaceCity2.setCity(split2[i]);
					if(StringUtils.isNotBlank(split3[i]) && !split3[i].equals("无")) {
						qxjLeaveorbackPlaceCity2.setAddress(split3[i]);
					}
					if(StringUtils.isNotBlank(leave.getLevelStatus()) && leave.getLevelStatus().equals("1")) {
						String[] split4 = level.split(",");
						qxjLeaveorbackPlaceCity2.setLevel(split4[i]);
						qxjLeaveorbackPlaceCity2.setLevelStatus("1");
					}else {
						qxjLeaveorbackPlaceCity2.setLevelStatus("0");
					}
					qxjLeaveorbackPlaceCityDao.save(qxjLeaveorbackPlaceCity2);
				}
			}
		}
		
	}

	@Override
	public List<QxjLeaveorbackPlaceCity> queryPlcaeList(String leaveorbackId) {
		Map<String, Object> map = new HashMap<>();
		map.put("leaveorbackId", leaveorbackId);
		List<QxjLeaveorbackPlaceCity> queryList = qxjLeaveorbackPlaceCityDao.queryList(map);
		return queryList;
	}

	@Override
	public void deleteByLeaveorbackId(String id) {
		qxjLeaveorbackPlaceCityDao.deleteByLeaveorbackId(id);
	}
	
}
