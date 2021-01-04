package com.css.addbase.orgservice;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.css.app.qxjgl.userDeptCopy.entity.QxjUserdeptCopy;
import com.css.app.qxjgl.userDeptCopy.service.QxjUserdeptCopyService;
import com.css.base.utils.StringUtils;
import com.css.base.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.base.utils.Response;

/**
 * 组织机构导入接口
 * @author gengds
 */
@Component
@RequestMapping("/app/org")
public class ImportOrganUtil {
	private final Logger logger =LoggerFactory.getLogger(ImportOrganUtil.class);
	
	@Autowired
	private BaseAppOrganService baseAppOrganService;
	
	@Autowired
	private BaseAppUserService baseAppUserService;
	
	@Autowired
	private OrgService orgService;

	@Autowired
	private QxjUserdeptCopyService qxjUserdeptCopyService;
	
	public ImportOrganUtil() {
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				try {
					importOrg("root");
					System.out.println("组织机构导入成功！");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("增量同步接口异常{}", StringUtils.isBlank(e.getMessage()) ? "请看后台日志："+e : e.getMessage());
				}
			}
		}, 60000);
	}
	
	/**
	 * 清空组织机构
	 */
	@ResponseBody
	@RequestMapping("/delete.htm")
	public void deleteOrgan() {
		try {
			baseAppOrganService.clearOrgan();
			baseAppUserService.clearUser();
			Response.json("清空组织结构成功");
		} catch (Exception e) {
			Response.json(e);
		}
	}
	/**
	 * 导入组织机构
	 */
	@ResponseBody
	@RequestMapping("/import.htm")
	public void importOrgan() {
		try {
			importOrg("root");
			Response.json("更新组织机构成功！");
		} catch (Exception e) {
			Response.json(e);
		}
	}
	
	/**
	 * 导入系统部门及其所属子部门和人员
	 */
	public void importOrg(String organId){
		Organ organ = orgService.getOrgan(organId);
		BaseAppOrgan baseAppOrgantemp = baseAppOrganService.queryObject(organId);
		BaseAppOrgan baseAppOrgan = new BaseAppOrgan();
		baseAppOrgan.setId(organ.getOrganId());
		baseAppOrgan.setName(organ.getOrganName());
		baseAppOrgan.setParentId(organ.getFatherId());
		baseAppOrgan.setTreePath(organ.getP());
		baseAppOrgan.setSort(organ.getOrderId());
		baseAppOrgan.setIsdelete(organ.getIsDelete());
		if(baseAppOrgantemp!=null){
			baseAppOrganService.update(baseAppOrgan);
		}else{
			baseAppOrganService.save(baseAppOrgan);
		}
		System.out.println(organ.getOrganId()+":"+organ.getOrganName()+"导入成功！");
		Organ[] organs = orgService.getSubOrg(organId);
		for (Organ sysOrgan:organs) {
			BaseAppOrgan appOrgantemp = baseAppOrganService.queryObject(organId);
			BaseAppOrgan appOrgan = new BaseAppOrgan();
			appOrgan.setId(organ.getOrganId());
			appOrgan.setName(organ.getOrganName());
			appOrgan.setParentId(organ.getFatherId());
			appOrgan.setTreePath(organ.getP());
			appOrgan.setSort(organ.getOrderId());
			appOrgan.setIsdelete(organ.getIsDelete());
			if(appOrgantemp!=null){
				baseAppOrganService.update(appOrgan);
			}else{
				baseAppOrganService.save(appOrgan);
			}
			System.out.println(organ.getOrganId()+":"+organ.getOrganName()+"导入成功！");
			appOrgan = null;
			importOrg(sysOrgan.getOrganId());
		}
		UserInfo[] userInfos = orgService.getUserInfos(organId);
		for (UserInfo userInfo:userInfos) {
			BaseAppUser baseAppUsertemp = baseAppUserService.queryObject(userInfo.getUserid());
			BaseAppUser baseAppUser=new BaseAppUser();
			baseAppUser.setId(userInfo.getUserid());
			baseAppUser.setUserId(userInfo.getUserid());
			baseAppUser.setAccount(userInfo.getAccount());
			baseAppUser.setTruename(userInfo.getFullname());
			baseAppUser.setMobile(userInfo.getMobile());
			baseAppUser.setSex(userInfo.getSex());
			baseAppUser.setTelephone(userInfo.getTel());
			baseAppUser.setIsdelete(userInfo.getIsDelete());
			Map map=(Map) userInfo.getRelations().get(0);
			baseAppUser.setOrganid((String) map.get("organId"));
			baseAppUser.setSort((int)map.get("orderId"));
			if(baseAppUsertemp!=null){
				baseAppUserService.update(baseAppUser);
			}else{
				baseAppUser.setSfyx("0");
				baseAppUserService.save(baseAppUser);
			}
			QxjUserdeptCopy qxjUserInfo = qxjUserdeptCopyService.queryObject(userInfo.getUserid());
			QxjUserdeptCopy qxjUserdeptCopy = new QxjUserdeptCopy();
			if(baseAppUsertemp != null){
				qxjUserdeptCopy.setUserId(baseAppUsertemp.getUserId());
				qxjUserdeptCopy.setOldDeptId(baseAppUsertemp.getOrganid());
				qxjUserdeptCopy.setNewDeptId((String) map.get("organId"));
			}else{
				qxjUserdeptCopy.setUserId(userInfo.getUserid());
				qxjUserdeptCopy.setOldDeptId((String) map.get("organId"));
				qxjUserdeptCopy.setNewDeptId((String) map.get("organId"));
			}
			if(qxjUserInfo != null){
				qxjUserdeptCopy.setId(qxjUserInfo.getId());
				qxjUserdeptCopyService.update(qxjUserdeptCopy);
			}else{
				qxjUserdeptCopy.setId(UUIDUtils.random());
				qxjUserdeptCopyService.save(qxjUserdeptCopy);
			}
			System.out.println(userInfo.getUserid()+":"+userInfo.getFullname()+"导入成功！");
			baseAppUser = null;
		}
	}

}
