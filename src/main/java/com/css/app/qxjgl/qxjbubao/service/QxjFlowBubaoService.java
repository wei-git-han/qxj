package com.css.app.qxjgl.qxjbubao.service;


import com.css.app.qxjgl.qxjbubao.entity.QxjFlowBubao;

import java.util.List;
import java.util.Map;

/**
 * 补报表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2020-07-25 14:38:45
 */
public interface QxjFlowBubaoService {
	
	QxjFlowBubao queryObject(String id);
	
	List<QxjFlowBubao> queryList(Map<String, Object> map);
	
	void save(QxjFlowBubao qxjFlowBubao);
	
	void update(QxjFlowBubao qxjFlowBubao);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	QxjFlowBubao queryLastBuBaoUser(String id);

	List<QxjFlowBubao> queryAllBuBaoUser(String id);

	void deleteBubao(String id,String userId);

	void updateBubao(String id,String approvalId);

	List<QxjFlowBubao> queryIsexist(String id,String userId);

	List<QxjFlowBubao> queryUserId(String id,String userId);



}
