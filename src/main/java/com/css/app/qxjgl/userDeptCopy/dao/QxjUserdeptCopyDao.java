package com.css.app.qxjgl.userDeptCopy.dao;


import com.css.app.qxjgl.userDeptCopy.entity.QxjUserdeptCopy;
import org.apache.ibatis.annotations.Mapper;

import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户原来部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-13 11:19:52
 */
@Mapper
public interface QxjUserdeptCopyDao extends BaseDao<QxjUserdeptCopy> {

    @Select("select distinct new_dept_id from QXJ_USERDEPT_COPY where old_dept_id =#{0} and new_dept_id != #{0}")
    List<String> queryDeptIds(String orgId);
}
