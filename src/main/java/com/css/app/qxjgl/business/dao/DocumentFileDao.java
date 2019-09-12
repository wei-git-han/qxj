package com.css.app.qxjgl.business.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.base.dao.BaseDao;

/**
 * 请假单的相关文件表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-14 11:19:46
 */
@Mapper
public interface DocumentFileDao extends BaseDao<DocumentFile> {
	
	int queryMinSort(String leaveId,String fileType);
	
	@Select("select * from QXJ_DOCUMENT_FILE where LEAVE_ID = #{0} AND FILE_TYPE = #{1} LIMIT 1")
	DocumentFile queryByLeaveId(String leaveId,String fileType);

	@Delete("delete from QXJ_DOCUMENT_FILE where LEAVE_ID = #{leaveId}")
	void deleteByLeaveId(String leaveId);
}
