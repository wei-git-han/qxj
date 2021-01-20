package com.css.app.qxjgl.business.dao;


import com.css.app.qxjgl.business.entity.QxjLeaveorbackFollow;
import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;

/**
 * 请假单-随员表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2021-01-19 16:27:19
 */
@Mapper
public interface QxjLeaveorbackFollowDao extends BaseDao<QxjLeaveorbackFollow> {

    @Select("select top 1* from QXJ_LEAVEORBACK_FOLLOW where USERID = #{0} order by CREATE_TIME desc")
   QxjLeaveorbackFollow queryTop1(String userId);
}
