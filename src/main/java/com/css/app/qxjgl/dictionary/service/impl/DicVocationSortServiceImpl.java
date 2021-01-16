package com.css.app.qxjgl.dictionary.service.impl;

import com.css.app.qxjgl.dictionary.entity.DicVocationSortPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.css.app.qxjgl.dictionary.dao.DicVocationSortDao;
import com.css.app.qxjgl.dictionary.entity.DicVocationSort;
import com.css.app.qxjgl.dictionary.service.DicVocationSortService;



@Service("dicVocationSortService")
public class DicVocationSortServiceImpl implements DicVocationSortService {
	@Autowired
	private DicVocationSortDao dicVocationSortDao;
	
	@Override
	public DicVocationSort queryObject(String id){
		return dicVocationSortDao.queryObject(id);
	}
	
	@Override
	public List<DicVocationSort> queryList(Map<String, Object> map){
		return dicVocationSortDao.queryList(map);
	}
	
	@Override
	public void save(DicVocationSort dicVocationSort){
		dicVocationSortDao.save(dicVocationSort);
	}
	
	@Override
	public void update(DicVocationSort dicVocationSort){
		dicVocationSortDao.update(dicVocationSort);
	}
	
	@Override
	public void delete(String id){
		dicVocationSortDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		dicVocationSortDao.deleteBatch(ids);
	}

	@Override
	public int queryTotal() {
		// TODO Auto-generated method stub
		return dicVocationSortDao.queryTotal();
	}

	@Override
	public List<DicVocationSortPlus> queryByType(String type, String orgId){
		return dicVocationSortDao.queryByType(type,orgId);
	}

	@Override
	public DicVocationSort queryByvacationSortId(String vacationSortId,String orgId){
		return dicVocationSortDao.queryByvacationSortId(vacationSortId,orgId);
	}

	@Override
	public DicVocationSort queryByVehicleAndorgId(String flag,String orgId){
		return dicVocationSortDao.queryByVehicleAndorgId(flag,orgId);
	}

    @Override
    public DicVocationSort queryObjectAll(String vacationSortId) {
        return dicVocationSortDao.queryObjectAll(vacationSortId);
    }

	@Override
	public List<DicVocationSort> queryDeductionVacationDay(String[] array) {
		return dicVocationSortDao.queryDeductionVacationDay(array);
	}
}
