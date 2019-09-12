package com.css.app.qxjgl.authorization.service;

import com.css.app.qxjgl.authorization.entity.TAuthorization;
import java.util.List;
import java.util.Map;

/**
 * 人员授权表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-06-06 09:28:19
 */
public interface TAuthorizationService {
	
	TAuthorization queryObject(String id);
	
	List<TAuthorization> queryList(Map<String, Object> map);
	
	void save(TAuthorization tAuthorization);
	
	void update(TAuthorization tAuthorization);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	
	TAuthorization queryPerson(String personId);//根据人员id查询人员表

}
