package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.app.qxjgl.business.entity.ZFDicUsersModel;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 11:05:13
 */
public interface DicUsersService {
	
	DicUsers queryObject(Integer id);
	DicUsers queryByUserId(String userId, String rolecode);
	
	List<DicUsers> queryList(Map<String, Object> map);
	
	void save(DicUsers qxjDicUsers);
	
	void update(DicUsers qxjDicUsers);
	
	void delete(Integer id);
	
	void deleteBatch(String[] ids);

	void addDeptAdmin(com.css.app.qxjgl.business.entity.ZFDicUsersModel model);

	void deleteDeptAdmin(ZFDicUsersModel model);

	void selectDeptAdminBu(ZFDicUsersModel model);

	void selectDeptAdminJu(ZFDicUsersModel model);

    void getUserRoleType();
}
