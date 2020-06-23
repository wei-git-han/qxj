package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.QxjModleDeptDao;
import com.css.app.qxjgl.business.entity.QxjModleDept;
import com.css.app.qxjgl.business.service.QxjModleDeptService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.UUIDUtils;



@Service("qxjModleDeptService")
public class QxjModleDeptServiceImpl implements QxjModleDeptService {
	@Autowired
	private QxjModleDeptDao qxjModleDeptDao;
	
	@Override
	public QxjModleDept queryObject(String id){
		return qxjModleDeptDao.queryObject(id);
	}
	
	@Override
	public List<QxjModleDept> queryList(Map<String, Object> map){
		return qxjModleDeptDao.queryList(map);
	}
	
	@Override
	public void save(QxjModleDept qxjModleDept){
		qxjModleDept.setId(UUIDUtils.random());
		qxjModleDept.setCreatedTime(new Date());
		qxjModleDept.setUpdateTime(new Date());
		qxjModleDept.setCreatedUserId(CurrentUser.getUserId());
		qxjModleDeptDao.save(qxjModleDept);
	}
	
	@Override
	public void update(QxjModleDept qxjModleDept){
		qxjModleDept.setUpdateTime(new Date());
		qxjModleDeptDao.update(qxjModleDept);
	}
	
	@Override
	public void delete(String id){
		qxjModleDeptDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjModleDeptDao.deleteBatch(ids);
	}

	@Override
	public QxjModleDept findByDept(String deptId) {
		QxjModleDept modle= qxjModleDeptDao.findByDept(deptId);
		return modle;
	}
	
}
