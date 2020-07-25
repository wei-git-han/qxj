package com.css.app.qxjgl.qxjbubao.service.impl;

import com.css.app.qxjgl.qxjbubao.dao.QxjFlowBubaoDao;
import com.css.app.qxjgl.qxjbubao.entity.QxjFlowBubao;
import com.css.app.qxjgl.qxjbubao.service.QxjFlowBubaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service("qxjFlowBubaoService")
public class QxjFlowBubaoServiceImpl implements QxjFlowBubaoService {
	@Autowired
	private QxjFlowBubaoDao qxjFlowBubaoDao;
	
	@Override
	public QxjFlowBubao queryObject(String id){
		return qxjFlowBubaoDao.queryObject(id);
	}
	
	@Override
	public List<QxjFlowBubao> queryList(Map<String, Object> map){
		return qxjFlowBubaoDao.queryList(map);
	}
	
	@Override
	public void save(QxjFlowBubao qxjFlowBubao){
		qxjFlowBubaoDao.save(qxjFlowBubao);
	}
	
	@Override
	public void update(QxjFlowBubao qxjFlowBubao){
		qxjFlowBubaoDao.update(qxjFlowBubao);
	}
	
	@Override
	public void delete(String id){
		qxjFlowBubaoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjFlowBubaoDao.deleteBatch(ids);
	}

	@Override
	public QxjFlowBubao queryLastBuBaoUser(String id){
		return qxjFlowBubaoDao.queryLastBuBaoUser(id);
	}

	@Override
	public List<QxjFlowBubao> queryAllBuBaoUser(String id){
		return qxjFlowBubaoDao.queryAllBuBaoUser(id);
	}

	@Override
	public void deleteBubao(String id,String userId){
		qxjFlowBubaoDao.deleteBubao(id,userId);
	}

	@Override
	public void updateBubao(String id,String approvalId){
		qxjFlowBubaoDao.updateBubao(id,approvalId);
	}
	
}
