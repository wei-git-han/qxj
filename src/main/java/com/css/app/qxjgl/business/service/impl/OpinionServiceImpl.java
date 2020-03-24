package com.css.app.qxjgl.business.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgan.dao.BaseAppOrganDao;
import com.css.addbase.apporgan.dao.BaseAppUserDao;
import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.app.qxjgl.business.dao.OpinionDao;
import com.css.app.qxjgl.business.entity.Opinion;
import com.css.app.qxjgl.business.service.OpinionService;
import com.css.base.utils.UUIDUtils;



@Service("opinionService")
public class OpinionServiceImpl implements OpinionService {
	@Autowired
	private OpinionDao opinionDao;
	@Autowired
	private BaseAppUserDao baseAppUserDao;
	@Autowired
	private BaseAppOrganDao baseAppOrganDao;
	
	@Override
	public Opinion queryObject(String id){
		return opinionDao.queryObject(id);
	}
	
	@Override
	public List<Opinion> queryList(Map<String, Object> map){
		return opinionDao.queryList(map);
	}
	
	@Override
	public void save(Opinion qxjOpinion){
		opinionDao.save(qxjOpinion);
	}
	
	@Override
	public void update(Opinion qxjOpinion){
		opinionDao.update(qxjOpinion);
	}
	
	@Override
	public void delete(String id){
		opinionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		opinionDao.deleteBatch(ids);
	}

	@Override
	public void saveOpinion(String comment, String opinionType, String tempType, String userId, String userName,
			String leaveId) {
		Opinion opinion=new Opinion();
		opinion.setId(UUIDUtils.random());
        if(StringUtils.isNotBlank(comment)) {
        	opinion.setOpinion(comment);
        }
        if(StringUtils.isNotBlank(opinionType)) {
        	opinion.setOpinionType(opinionType);
        }
        if(StringUtils.isNotBlank(tempType)) {
        	opinion.setTempType(tempType);
        	opinion.setOpinionDate(new Date());
        }
        opinion.setUserId(userId);
        opinion.setUserName(userName);     
        opinion.setLeaveId(leaveId);
        if (StringUtils.isNotBlank(userId)) {
        	BaseAppUser user = baseAppUserDao.queryObject(userId);
        	if(user != null) {
        		 opinion.setDeptId(user.getOrganid());
        		BaseAppOrgan org = baseAppOrganDao.queryObject(user.getOrganid());
        		if(org != null) {
        			opinion.setDeptName(org.getName());
        		}
        	}
        }
		opinionDao.save(opinion);
	}

	@Override
	public Opinion queryLatestOpinion(String leaveId) {
		return opinionDao.queryLatestOpinion(leaveId);
	}

	@Override
	public void deleteByLeaveId(String id) {
		// TODO Auto-generated method stub
		opinionDao.deleteByLeaveId(id);
	}
	
}
