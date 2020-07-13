package com.css.app.qxjgl.userDeptCopy.service.impl;

import com.css.app.qxjgl.userDeptCopy.dao.QxjUserdeptCopyDao;
import com.css.app.qxjgl.userDeptCopy.entity.QxjUserdeptCopy;
import com.css.app.qxjgl.userDeptCopy.service.QxjUserdeptCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service("qxjUserdeptCopyService")
public class QxjUserdeptCopyServiceImpl implements QxjUserdeptCopyService {
	@Autowired
	private QxjUserdeptCopyDao qxjUserdeptCopyDao;
	
	@Override
	public QxjUserdeptCopy queryObject(String id){
		return qxjUserdeptCopyDao.queryObject(id);
	}
	
	@Override
	public List<QxjUserdeptCopy> queryList(Map<String, Object> map){
		return qxjUserdeptCopyDao.queryList(map);
	}
	
	@Override
	public void save(QxjUserdeptCopy qxjUserdeptCopy){
		qxjUserdeptCopyDao.save(qxjUserdeptCopy);
	}
	
	@Override
	public void update(QxjUserdeptCopy qxjUserdeptCopy){
		qxjUserdeptCopyDao.update(qxjUserdeptCopy);
	}
	
	@Override
	public void delete(String id){
		qxjUserdeptCopyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjUserdeptCopyDao.deleteBatch(ids);
	}

	@Override
	public List<String> queryDeptIds(String orgId){
		return qxjUserdeptCopyDao.queryDeptIds(orgId);
	}
	
}
