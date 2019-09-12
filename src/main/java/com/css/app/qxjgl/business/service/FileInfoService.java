package com.css.app.qxjgl.business.service;

import com.css.app.qxjgl.business.entity.Leaveorback;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 电子收发文件表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-12 14:51:17
 */
public interface FileInfoService {
	InputStream createQXJExcel(List<Leaveorback> list, String fileName) throws Exception;
}
