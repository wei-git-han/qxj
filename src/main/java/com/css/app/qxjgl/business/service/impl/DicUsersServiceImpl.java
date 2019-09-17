package com.css.app.qxjgl.business.service.impl;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.app.qxjgl.business.entity.ZFDicUsersModel;
import com.css.app.qxjgl.business.entity.ZFOrginInfoModel;
import com.css.app.qxjgl.business.entity.ZFUserInfoModel;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.*;
import com.github.pagehelper.PageHelper;
import dm.jdbc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.css.app.qxjgl.business.dao.DicUsersDao;
import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.app.qxjgl.business.service.DicUsersService;

import javax.annotation.Resource;


@Service("qxjDicUsersService")
public class DicUsersServiceImpl implements DicUsersService {
    org.apache.log4j.Logger logger = Logger.getLogger(DicCalenderServiceImpl.class);
	@Resource
	private DicUsersDao qxjDicUsersDao;
	@Autowired
	private CheckoutDicCalenderParam checkoutDicCalenderParam;
	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedService;
	@Autowired
	private CommonQueryManager commonQueryManager;
	@Autowired
	private BaseAppUserService baseAppUserService;
	@Autowired
	private BaseAppOrganService baseAppOrganService;

	@Override
	public DicUsers queryObject(Integer id){
		return qxjDicUsersDao.queryObject(id);
	}
	
	@Override
	public List<DicUsers> queryList(Map<String, Object> map){
		return qxjDicUsersDao.queryList(map);
	}
	
	@Override
	public void save(DicUsers qxjDicUsers){
		qxjDicUsersDao.save(qxjDicUsers);
	}
	
	@Override
	public void update(DicUsers qxjDicUsers){
		qxjDicUsersDao.update(qxjDicUsers);
	}
	
	@Override
	public void delete(Integer id){
		qxjDicUsersDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		qxjDicUsersDao.deleteBatch(ids);
	}

	@Override
	public DicUsers queryByUserId(String userId, String rolecode) {
		return 	qxjDicUsersDao.queryUserId(userId, rolecode);

	}

	/**
	 * 管理员 新增
	 */
	@Override
	public void addDeptAdmin(ZFDicUsersModel model) {
		BaseAppOrgan baseAppOrgan = null;
		if(!checkoutDicCalenderParam.checkAddDAParam(model)){
			Response.json("result","999");
		}else{

			int roleType = commonQueryManager.roleType(CurrentUser.getUserId());
			//待新增管理员局ID
            String orgId = commonQueryManager.acquireLoginPersonOrgId(model.getUserid());
			baseAppOrgan = this.acquireUserOrgConfig(model.getUserid(), orgId);
			String orgName = null;
			if (baseAppOrgan != null) {
				orgName = baseAppOrgan.getName();
			}
			//当前登录人直接部门ID
			baseAppOrgan = this.acquireUserOrgConfig(model.getUserid(), null);
			String deptname = null;
			String deptid = null;
			if (baseAppOrgan != null) {
				deptname = baseAppOrgan.getName();
				deptid = baseAppOrgan.getId();
			}
			//级别小于部长 无权限新增部管理员
            if(model.getType() == 3 && roleType >= 4){
                //新增部管理员   3部 2局
                model.setRolecode("3");
                model.setRolename("部管理员");
                model.setDeptid(deptid);
                model.setDeptname(deptname);
                model.setOrgId(orgId);
                model.setOrgName(orgName);
				saveAndResult(qxjDicUsersDao.selectDeptAdminInfo(model),model);
            }else if(model.getType() == 2 && roleType >= 3) {
            	//当前登录人为局管理员以下的级别时 若当前待添加人员所在部门非当前登录人部门则不予新增
            	if(roleType == 3 && StringUtils.equals(orgId, deptid)){
					Response.json("result","只能新增本部门人员");
				}else{
					//新增局管理员  3部 2局
					model.setRolecode("2");
					model.setRolename("局管理员");
                    model.setDeptid(deptid);
                    model.setDeptname(deptname);
                    model.setOrgId(orgId);
                    model.setOrgName(orgName);
					saveAndResult(qxjDicUsersDao.selectDeptAdminInfo(model),model);
				}
            }
        }
	}

	//获取Orgname  直接部门ID —> 直接部门名称  局ID -> 局名称
    public String getOrgName(String orgId){
	    String orgiNname = "";
        if(StringUtil.isNotEmpty(orgId)){
            ZFOrginInfoModel orginInfo = qxjDicUsersDao.getOrginInfoById(orgId);
            if(null != orginInfo && null != orginInfo.getName()){
                orgiNname = orginInfo.getName();
            }
        }
        return orgiNname;
    }

	//获取局 ID  BASE_APP_USER表中 Organid
	private BaseAppOrgan acquireUserOrgConfig(String userId, String orgId){
		BaseAppOrgan baseAppOrgan = null;
		if (StringUtils.isBlank(orgId)) {
			BaseAppUser baseAppUser = baseAppUserService.queryByUserId(userId);
			if (baseAppUser != null) {
				baseAppOrgan = baseAppOrganService.queryObject(baseAppUser.getOrganid());
			}
		} else {
			baseAppOrgan = baseAppOrganService.queryObject(orgId);
		}
		return baseAppOrgan;
    }


	//校验参数是否存在，不存在则新增
	public void saveAndResult(ZFDicUsersModel zfDicUsersModel,ZFDicUsersModel model){
		if(zfDicUsersModel != null){
			Response.json("result","exist");
		}else{
			model.setId(UUIDUtils.random());
			qxjDicUsersDao.addDeptAdmin(model);
			Response.json("result","success");
		}
	}

	/**
	 * 管理员 删除
	 */
	@Override
	public void deleteDeptAdmin(ZFDicUsersModel model) {
		if(!checkoutDicCalenderParam.checkDelDAParam(model)){
			 Response.json("result","exist");
		}else{
		if(model.getIds().indexOf(",")>0){
			String[] ids = model.getIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				qxjDicUsersDao.deleteDeptAdmin(ids[i]);
			}
		}else{
			qxjDicUsersDao.deleteDeptAdmin(model.getIds());
		}
		 Response.json("result","success");
        }
	}

	/**
	 *  局管理员列表 查询
     *  commonQueryManager.roleType(currentUserId) 返回的数据  4部门管理员 3局管理员
     *  DIC_USERS 3部门管理员 2局管理员
	 */
	@Override
	public void selectDeptAdminJu(ZFDicUsersModel model) {
		if(!checkoutDicCalenderParam.checkselectDeptAdminJuParam(model)){

		}
		String currentUserId = CurrentUser.getUserId();
		Integer maxLeave = commonQueryManager.roleType(currentUserId);
		//当前登录人为部管理员以上的级别 查询所有局
		if(null != maxLeave && maxLeave >= 4){
			model.setRolecode("2");
			PageHelper.startPage(model.getPage(), model.getPageSize());
			GwPageUtils pageInfo = new GwPageUtils(qxjDicUsersDao.selectDeptAdmin(model));
			Response.json(pageInfo);
		}else if(null != maxLeave && maxLeave >= 3){
			//当前登录人为局长 查询本局所有管理员
			model.setOrgId(baseAppOrgMappedService.getBareauByUserId(CurrentUser.getUserId()));
			model.setRolecode("2");
			PageHelper.startPage(model.getPage(), model.getPageSize());
			GwPageUtils pageInfo = new GwPageUtils(qxjDicUsersDao.selectDeptAdmin(model));
			Response.json(pageInfo);
		}else{
			Response.json("result","非管理员");
		}
	}

	/**
	 * 获取角色
	 */
	@Override
	public void getUserRoleType() {
		Response.json(commonQueryManager.roleType(CurrentUser.getUserId()));
	}

	/**
	 * 部管理员列表查询
	 */
	@Override
	public void selectDeptAdminBu(ZFDicUsersModel model) {
		if(!checkoutDicCalenderParam.checkselectDeptAdminBuParam(model)){

		}
		String currentUserId = CurrentUser.getUserId();
		Integer maxLeave = commonQueryManager.roleType(currentUserId);
		//当前登录人为部长
		if(null != maxLeave && maxLeave >= 4){
			model.setRolecode("3");
			PageHelper.startPage(model.getPage(), model.getPageSize());
			GwPageUtils pageInfo = new GwPageUtils(qxjDicUsersDao.selectDeptAdmin(model));
			Response.json(pageInfo);
		}else if(null != maxLeave && maxLeave >= 3){
			//当前登录人为局长 不予查询部管理员信息
			logger.info("当前登录人为局长 不予查询部管理员信息 用户ID "+ currentUserId);
			PageHelper.startPage(model.getPage(), model.getPageSize());
			GwPageUtils pageInfo = new GwPageUtils(null);
			Response.json(pageInfo);
			}else{
            Response.json("result","非管理员");
        }
	}


	
}
