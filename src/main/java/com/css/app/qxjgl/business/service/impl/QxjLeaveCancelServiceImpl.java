package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.QxjLeaveCancelDao;
import com.css.app.qxjgl.business.entity.QxjLeaveCancel;
import com.css.app.qxjgl.business.service.QxjLeaveCancelService;



@Service("qxjLeaveCancelService")
public class QxjLeaveCancelServiceImpl implements QxjLeaveCancelService {
	@Autowired
	private QxjLeaveCancelDao qxjLeaveCancelDao;
	
	
	@Override
	public void save(QxjLeaveCancel qxjLeaveCancel){
		qxjLeaveCancelDao.save(qxjLeaveCancel);
	}
	
	@Override
	public void update(QxjLeaveCancel qxjLeaveCancel){
		qxjLeaveCancelDao.update(qxjLeaveCancel);
	}

	@Override
	public QxjLeaveCancel findByDeptId(String orgId) {
		QxjLeaveCancel info=qxjLeaveCancelDao.findByDeptId(orgId);
		return info;
	}

	@Override
	public List<QxjLeaveCancel> findAll() {
		List<QxjLeaveCancel> all=qxjLeaveCancelDao.findAll();
		return all;
	}
	
}
