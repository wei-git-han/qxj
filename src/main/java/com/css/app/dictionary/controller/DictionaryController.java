package com.css.app.dictionary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.app.dictionary.entity.DictionaryType;
import com.css.app.dictionary.entity.DictionaryValue;
import com.css.app.dictionary.service.DictionaryTypeService;
import com.css.app.dictionary.service.DictionaryValueService;
import com.css.base.utils.GwPageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;


/**
 * 公文字典类型表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-10-07 13:15:37
 */
@Controller
@RequestMapping("app/gwcl/dictionary")
public class DictionaryController {
	@Autowired
	private DictionaryTypeService dictionaryTypeService;
	@Autowired
	private DictionaryValueService dictionaryValueService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void list(Integer page, Integer pagesize){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(page != null && pagesize != null){
			PageHelper.startPage(page, pagesize);
		}
		
		//查询列表数据
		List<Map<String,Object>> dictionaryTypeList = dictionaryTypeService.queryDicList(map);
		
		GwPageUtils pageUtil = new GwPageUtils(dictionaryTypeList);
		Response.json(pageUtil);
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/zdlist")
	public void zdlist(Integer page, Integer pagesize){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(page != null && pagesize != null){
			PageHelper.startPage(page, pagesize);
		}
		
		List<Map<String, Object>> dictionaryTypeList = new ArrayList<Map<String, Object>>();
		//查询列表数据
		List<DictionaryType> list = dictionaryTypeService.queryList(map);
		for(DictionaryType dic : list){
			Map<String, Object> arg = new HashMap<String, Object>();
			arg.put("typeId", dic.getId());
			List<DictionaryValue> vlist = dictionaryValueService.queryListByTypeId(arg);			
			Map<String, Object> tep = new HashMap<String, Object>();
			tep.put("type", dic.getId());
			tep.put("name", dic.getDictionaryName());
			tep.put("children", vlist);
			dictionaryTypeList.add(tep);
		}
		
		GwPageUtils pageUtil = new GwPageUtils(dictionaryTypeList);
		Response.json(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public void info(@PathVariable("id") String id){
		Map<String, Object> ret = new HashMap<String, Object>();
		DictionaryType dictionaryType = new DictionaryType();
		List<DictionaryValue> dictionaryValueList = new ArrayList<DictionaryValue>();
		dictionaryType = dictionaryTypeService.queryObject(id);	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", dictionaryType.getId());	
		dictionaryValueList = dictionaryValueService.queryListByTypeId(map);		
		ret.put("dictionaryType", dictionaryType);
		ret.put("dictionaryValueList", dictionaryValueList);		
		Response.json(ret);
	}
	
	/**
	 * 保存
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/save")
	public void save(String dname,String values,String id){
		String typeId;
		DictionaryType dictionaryType = new DictionaryType();
		dictionaryType.setDictionaryName(dname);
		if(id != null && !id.equals("")){
			typeId =id;
			dictionaryTypeService.update(dictionaryType);
		}else{
			typeId = UUIDUtils.random();
			dictionaryType.setId(typeId);
			dictionaryTypeService.save(dictionaryType);
		}
		
		DictionaryValue dictionaryValue = new DictionaryValue();
		String arrValue[] = values.split("%0A");
		String temp = "";
		for(int i=0;i<arrValue.length;i++){
			int maxSort = dictionaryValueService.queryMaxSort(typeId);
			temp = java.net.URLDecoder.decode(arrValue[i]);
			if("".equals(temp)){
				continue;
			}
			dictionaryValue.setId(UUIDUtils.random());
			dictionaryValue.setDictionaryTypeId(typeId);
			dictionaryValue.setSort(maxSort+i+1);
			dictionaryValue.setSysType(0);
			dictionaryValue.setDictionaryValue(temp);
			dictionaryValueService.save(dictionaryValue);
		}
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody DictionaryType dictionaryType){
		//dictionaryTypeService.update(dictionaryType);
		
		Response.json("result","success");
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/updateVal")
	public void updateVal(String id,String value){
		DictionaryValue vo = new DictionaryValue();
		vo.setId(id);
		vo.setDictionaryValue(value);
		
		dictionaryValueService.update(vo);
		
		Response.json("result","success");
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void delete(String ids){
		String arrIds[] = ids.split(",");
		//dictionaryTypeService.deleteBatch(arrIds);
		
		for(String id : arrIds){
			id = id.substring(id.indexOf("&")+1);
			dictionaryValueService.delete(id);
		}
		
		Response.json("result","success");
	}
	
}
