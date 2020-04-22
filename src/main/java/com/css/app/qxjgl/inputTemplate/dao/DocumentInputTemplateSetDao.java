package com.css.app.qxjgl.inputTemplate.dao;

import com.css.app.qxjgl.inputTemplate.entity.DocumentInputTemplateSet;
import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 签批输入模板设置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-10-22 16:52:18
 */
@Mapper
public interface DocumentInputTemplateSetDao extends BaseDao<DocumentInputTemplateSet> {
	
	@Select("select top 1 * from DOCUMENT_INPUT_NEW_TEMPLATE_SET where user_id=#{userId}")
	DocumentInputTemplateSet getSetByUserId(String userId);
	
	void delSetByUserId(String userId);
	
}
