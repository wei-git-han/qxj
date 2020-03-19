package com.css.app.qxjgl.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.business.dao.ApplyUserDao;
import com.css.app.qxjgl.business.entity.ApplyUser;
import com.css.app.qxjgl.business.service.ApplyUserService;



@Service("applyUserService")
public class ApplyUserServiceImpl implements ApplyUserService {
	@Autowired
	private ApplyUserDao applyUserDao;
	
	@Override
	public ApplyUser queryObject(String id){
		return applyUserDao.queryObject(id);
	}
	
	@Override
	public List<ApplyUser> queryList(Map<String, Object> map){
		return applyUserDao.queryList(map);
	}
	
	@Override
	public void save(ApplyUser qxjApplyUser){
		applyUserDao.save(qxjApplyUser);
	}
	
	@Override
	public void update(ApplyUser qxjApplyUser){
		applyUserDao.update(qxjApplyUser);
	}
	
	@Override
	public void delete(String id){
		applyUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		applyUserDao.deleteBatch(ids);
	}

	@Override
	public void deleteByLeaveId(String leaveId) {
		// TODO Auto-generated method stub
		applyUserDao.deleteByLeaveId(leaveId);
		
	}
	
}
