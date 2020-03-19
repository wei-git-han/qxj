package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.ApplyUser;

import java.util.List;
import java.util.Map;

/**
 * 请销假申请人表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-26 16:11:48
 */
public interface ApplyUserService {
	
	ApplyUser queryObject(String id);
	
	List<ApplyUser> queryList(Map<String, Object> map);
	
	void save(ApplyUser qxjApplyUser);
	
	void update(ApplyUser qxjApplyUser);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	void deleteByLeaveId(String leaveId);
}
