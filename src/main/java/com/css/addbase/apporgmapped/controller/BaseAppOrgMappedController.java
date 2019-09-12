package com.css.addbase.apporgmapped.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.Response;

/**
 * 应用配置控制类
 * 
 * @author gengds
 * @email
 * @date 2019-04-26 17:57:50
 */
@Controller
@RequestMapping("app/config")
public class BaseAppOrgMappedController {

	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;

	/**
	 * 清空应用配置缓存
	 */
	@ResponseBody
	@RequestMapping("/clearcache.htm")
	public void clearcache(Integer page, Integer pagesize) {
		baseAppOrgMappedService.clearConfigCache();
		Response.json("清空应用配置缓存成功");
	}

}
