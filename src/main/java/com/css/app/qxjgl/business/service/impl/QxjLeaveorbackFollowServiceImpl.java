package com.css.app.qxjgl.business.service.impl;

import com.css.app.qxjgl.business.dao.QxjLeaveorbackFollowDao;
import com.css.app.qxjgl.business.entity.QxjLeaveorbackFollow;
import com.css.app.qxjgl.business.service.QxjLeaveorbackFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service("qxjLeaveorbackFollowService")
public class QxjLeaveorbackFollowServiceImpl implements QxjLeaveorbackFollowService {
	@Autowired
	private QxjLeaveorbackFollowDao qxjLeaveorbackFollowDao;
	
	@Override
	public QxjLeaveorbackFollow queryObject(String id){
		return qxjLeaveorbackFollowDao.queryObject(id);
	}
	
	@Override
	public List<QxjLeaveorbackFollow> queryList(Map<String, Object> map){
		return qxjLeaveorbackFollowDao.queryList(map);
	}
	
	@Override
	public void save(QxjLeaveorbackFollow qxjLeaveorbackFollow){
		qxjLeaveorbackFollowDao.save(qxjLeaveorbackFollow);
	}
	
	@Override
	public void update(QxjLeaveorbackFollow qxjLeaveorbackFollow){
		qxjLeaveorbackFollowDao.update(qxjLeaveorbackFollow);
	}
	
	@Override
	public void delete(String id){
		qxjLeaveorbackFollowDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjLeaveorbackFollowDao.deleteBatch(ids);
	}

	@Override
	public QxjLeaveorbackFollow queryTop1(String userId){
		return qxjLeaveorbackFollowDao.queryTop1(userId);
	}
	
}
