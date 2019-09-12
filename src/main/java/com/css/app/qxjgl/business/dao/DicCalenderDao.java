package com.css.app.qxjgl.business.dao;

import com.css.app.qxjgl.business.entity.DicCalender;
import com.css.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-02-28 15:14:15
 */
@Mapper
public interface DicCalenderDao extends BaseDao<DicCalender> {
	
	List<DicCalender> queryHoliday(Map<String, Object> map);

	void removeOneMouth(Map<String, Object> map);

}
