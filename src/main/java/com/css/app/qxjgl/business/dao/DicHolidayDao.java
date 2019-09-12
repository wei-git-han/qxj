package com.css.app.qxjgl.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.css.app.qxjgl.business.entity.DicHoliday;
import com.css.base.dao.BaseDao;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-27 18:34:56
 */
@Mapper
public interface DicHolidayDao extends BaseDao<DicHoliday> {
	void updateByUserId(DicHoliday qxjDicHoliday);

	DicHoliday queryByUserId(String userId);
	
}
