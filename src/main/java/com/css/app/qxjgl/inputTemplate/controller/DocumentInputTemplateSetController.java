package com.css.app.qxjgl.inputTemplate.controller;

import com.alibaba.fastjson.JSONObject;
import com.css.app.qxjgl.inputTemplate.entity.DocumentInputTemplateSet;
import com.css.app.qxjgl.inputTemplate.service.DocumentInputTemplateSetService;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 签批输入模板设置表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2018-10-22 16:52:18
 */
@Controller
@RequestMapping("app/gwcl/documentinputtemplateset")
public class DocumentInputTemplateSetController {
	@Autowired
	private DocumentInputTemplateSetService documentInputTemplateSetService;
	
	/**
	 * //记忆个人使用签批或手写模式
	 * @param tempIndex 签批模式1.键盘输入2手写输入
	 */
	@ResponseBody
	@RequestMapping("/save")
	public void save(String tempIndex, String penWidth) {
		String userId=CurrentUser.getUserId();
		documentInputTemplateSetService.delSetByUserId(userId);
		DocumentInputTemplateSet set =new DocumentInputTemplateSet();
		set.setUserId(userId);
		set.setId(UUIDUtils.random());
		set.setPenWidth(penWidth);
		set.setInputTemplate(tempIndex);
		set.setCreatedDate(new Date());
		documentInputTemplateSetService.save(set);	
		Response.json("result", "success");
	}
		
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	public void info(){
		String userId=CurrentUser.getUserId();
		String tempIndex="1";
		String penWidth="signpen_05mm";
		DocumentInputTemplateSet tempSet = documentInputTemplateSetService.getSetByUserId(userId);
		if(tempSet!=null) {
			if(tempSet.getInputTemplate()!=null){
				tempIndex=tempSet.getInputTemplate();
			}
			if(tempSet.getPenWidth()!=null){
				penWidth=tempSet.getPenWidth();
			}
		}
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("tempIndex", tempIndex);
		jsonobject.put("penWidth", penWidth);
		Response.json("info",jsonobject);
	}
}
