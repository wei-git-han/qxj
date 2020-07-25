package com.css.app.qxjgl.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.app.qxjgl.business.dao.LeaveorbackDao;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.UUIDUtils;



@Service("leaveorbackService")
public class LeaveorbackServiceImpl implements LeaveorbackService {
	@Autowired
	private LeaveorbackDao leaveorbackDao;
	
	@Override
	public Leaveorback queryObject(String id){
		return leaveorbackDao.queryObject(id);
	}
	
	@Override
	public List<Leaveorback> queryList(Map<String, Object> map){
		return leaveorbackDao.queryList(map);
	}
	
	@Override
	public void save(Leaveorback item){
		Date date=new Date();
		String userId=CurrentUser.getUserId();
		String userName=CurrentUser.getSSOUser().getFullname();
		String id=StringUtils.isNotBlank(item.getId())?item.getId():UUIDUtils.random();
		item.setId(id);
		item.setCreateDate(date);
		item.setCreatorId(userId);
		item.setCreator(userName);
		item.setModifyDate(date);
		item.setModificatorId(userId);
		item.setModificator(userName);
		leaveorbackDao.save(item);
	}
	
	@Override
	public void update(Leaveorback item){
		Date date=new Date();
		String userId=CurrentUser.getUserId();
		String userName=CurrentUser.getSSOUser().getFullname();
		item.setModifyDate(date);
		item.setModificatorId(userId);
		item.setModificator(userName);
		leaveorbackDao.update(item);
	}

	@Override
	public void updateStatus(String id){
		leaveorbackDao.updateStatus(id);
	}
	
	@Override
	public void delete(String id){
		leaveorbackDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		leaveorbackDao.deleteBatch(ids);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return leaveorbackDao.queryTotal(map);
	}

	@Override
	public List<Leaveorback> queryQjCompletedUser(Map<String, Object> users) {
		return leaveorbackDao.queryQjCompletedUser(users);
	}

	@Override
	public int queryQjCompletedUserByOrgid(Map<String, Object> params) {
		return leaveorbackDao.queryQjCompletedUserByOrgid(params);
	}

	@Override
	public List<Leaveorback> queryQjCompletedUserByOrgid2(Map<String, Object> users) {
		return leaveorbackDao.queryQjCompletedUserByOrgid2(users);
	}

	@Override
	public List<BaseAppOrgan> queryBelongOrg(String deptid) {
		return leaveorbackDao.queryBelongOrg(deptid);
	}

	@Override
	public List<Leaveorback> queryQXJList(Map<String, Object> paraterLeaderMap) {
		return leaveorbackDao.queryQXJList(paraterLeaderMap);
	}

	@Override
	public List<Leaveorback> queryNewList(Map<String, Object> map) {
		return leaveorbackDao.queryNewList(map);
	}

	@Override
	public List<Leaveorback> queryNewList1(Map<String, Object> map) {
		return leaveorbackDao.queryNewList1(map);
	}

    @Override
    public int queryRealRestDays(String userId) {
        return leaveorbackDao.queryRealRestDays(userId);
    }

    @Override
    public List<Leaveorback> queryCurrYearRestDays(Map<String, Object> map) {
        return leaveorbackDao.queryCurrYearRestDays(map);
    }
    @Override
    public Leaveorback getQXJDefaultParam(String userId) {
    	 return leaveorbackDao.getQXJDefaultParam(userId);
    }

	@Override
	public List<Leaveorback> queryDeducttonDays(Map<String, Object> map) {
		return leaveorbackDao.queryDeducttonDays(map);
	}
	
	@Override
	public List<String> getIsJuGuanLi(String userId) {
		// TODO Auto-generated method stub
		return leaveorbackDao.getIsJuGuanLi(userId);
	}

	@Override
	public void updateWeekendHolidayNum(Leaveorback leaveorback) {
		// TODO Auto-generated method stub
		leaveorbackDao.updateWeekendHolidayNum(leaveorback);
	}

	@Override
	public String getBackStatusId(String id) {
		// TODO Auto-generated method stub
		return leaveorbackDao.getBackStatusId(id);
	}

	@Override
	public String getStatus(String id) {
		// TODO Auto-generated method stub
		return leaveorbackDao.getStatus(id);
	}

	@Override
	public List<Leaveorback> selByBackAndTenday() {
		// TODO Auto-generated method stub
		return leaveorbackDao.selByBackAndTenday();
	}

	@Override
	public void updateBackStatusId(Leaveorback leaveorbackk) {
		// TODO Auto-generated method stub
		leaveorbackDao.updateBackStatusId(leaveorbackk);
	}

	@Override
	public int selcount(Map<String, Object> paraterLeaderMap) {
		// TODO Auto-generated method stub
		return leaveorbackDao.selcount(paraterLeaderMap);
	}
	
	@Override
	public List<Leaveorback> queryQjUserIds(Map<String, Object> map) {
		return leaveorbackDao.queryQjUserIds(map);
	}

	@Override
	public Leaveorback queryIsView(String id,String userId){
		return leaveorbackDao.queryIsView(id,userId);
	}

	@Override
	public void deleteBubao(String id,String userId){
		leaveorbackDao.deleteBubao(id,userId);
	}
}
