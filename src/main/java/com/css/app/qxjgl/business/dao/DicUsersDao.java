package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.ZFDicUsersModel;
import com.css.app.qxjgl.business.entity.ZFOrginInfoModel;
import com.css.app.qxjgl.business.entity.ZFUserInfoModel;
import org.apache.ibatis.annotations.Mapper;

import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 11:05:13
 */
@Mapper
public interface DicUsersDao extends BaseDao<DicUsers> {
	
	DicUsers queryUserId(@Param("userId") String userId, @Param("rolecode") String rolecode);
	ZFUserInfoModel getUserInfoByEnti(ZFUserInfoModel model);
	ZFOrginInfoModel getOrginInfoById(@Param("bareauByUserId") String bareauByUserId);
	void addDeptAdmin(ZFDicUsersModel model);
	List<ZFDicUsersModel> selectDeptAdmin(ZFDicUsersModel model);
	ZFDicUsersModel selectDeptAdminInfo(ZFDicUsersModel model);
	Integer selectMaxLeave(String currentUserId);
	Integer deleteDeptAdmin(@Param("id") String id);
}
