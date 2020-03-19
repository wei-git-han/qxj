package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.Opinion;

import java.util.List;
import java.util.Map;

/**
 * 请销假意见表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-28 10:36:48
 */
public interface OpinionService {
	
	Opinion queryObject(String id);
	
	List<Opinion> queryList(Map<String, Object> map);
	
	void save(Opinion qxjOpinion);
	
	void update(Opinion qxjOpinion);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	void saveOpinion(String opinion, String opinionType, String tempType, String userId, String userName,String leaveId);
	
	Opinion queryLatestOpinion(String leaveId);
	
	void deleteByLeaveId(String id);
}
