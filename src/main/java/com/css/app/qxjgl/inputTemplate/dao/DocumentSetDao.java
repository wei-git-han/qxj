package com.css.app.qxjgl.inputTemplate.dao;

import com.css.app.qxjgl.inputTemplate.entity.DocumentSet;
import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签批输入模板设置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-10-22 16:52:18
 */
@Mapper
public interface DocumentSetDao extends BaseDao<DocumentSet> {

	DocumentSet querySetByUserId(String userId);
	
}
