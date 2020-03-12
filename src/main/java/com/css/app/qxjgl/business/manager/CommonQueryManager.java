package com.css.app.qxjgl.business.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.css.addbase.apporgan.entity.BaseAppOrgan;
import com.css.addbase.apporgan.service.BaseAppOrganService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.css.addbase.apporgmapped.entity.BaseAppOrgMapped;
import com.css.addbase.apporgmapped.service.BaseAppOrgMappedService;
import com.css.addbase.constant.AppConstant;
import com.css.app.qxjgl.business.entity.DicUsers;
import com.css.app.qxjgl.business.service.DicUsersService;
import com.css.base.utils.CurrentUser;

/**
 * 公共查询控制
 * @author 任亚东
 * @date 2019-09-05
 */
@Component
public class CommonQueryManager {
    private final static Logger logger = LoggerFactory.getLogger(CommonQueryManager.class);

    @Autowired
    private BaseAppOrgMappedService baseAppOrgMappedService;
    @Autowired
    private DicUsersService dicUsersService;
    @Autowired
    private BaseAppOrganService baseAppOrganService;

    /**
     *  根据当前用户ID 查所在局ID
     * @param userId 当前用户的局ID
     * @return 局ID
     */
    public String acquireLoginPersonOrgId(String userId){
        return baseAppOrgMappedService.getBareauByUserId(userId);
    }

    /**
     *  根据当前用户ID 查所在局配置
     * @param userId 当前用户的局配置
     * @return 局配置
     */
    public BaseAppOrgan acquireLoginPersonOrgConfig(String userId){
        return  baseAppOrganService.queryObject(baseAppOrgMappedService.getBareauByUserId(userId));
    }
    /**
     *  根据当前用户ID 查用户角色
     * @param userId 当前用户的局ID
     * @return 用户角色类型
     */
    public int roleType(String userId){
        int roleType = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        List<DicUsers> dicUsers = dicUsersService.queryList(map);
        List<String> roleTypes = new ArrayList<>();
        if (dicUsers.size() > 0) {
            for (DicUsers dicUser : dicUsers) {
                roleTypes.add(dicUser.getRolecode());
            }
        }else {
            if (!isSupperAdmin()) {
                //超管
                roleType = 6;
            }
            return roleType;
        }
        if (isSupperAdmin()) {
            if (roleTypes.contains("2") && roleTypes.contains("3")) {
                //部、局管理员
                roleType = 5;
            } else if (!roleTypes.contains("2") && roleTypes.contains("3")) {
                //部管理员
                roleType = 4;
            } else if (roleTypes.contains("2") && !roleTypes.contains("3")) {
                //局管理员
                roleType = 3;
            } else if (!roleTypes.contains("2") && !roleTypes.contains("3") && roleTypes.contains("1")) {
                //局长
                roleType = 2;
            } else if (!roleTypes.contains("2") && !roleTypes.contains("3") && !roleTypes.contains("1") && roleTypes.contains("0")) {
                //处长
                roleType = 1;
            } else {
                logger.info("当前用户：{}的所有角色类型：{}", userId, roleTypes.toString());
                return roleType;
            }
        } else {
            if (roleTypes.contains("2")) {
                //和部、局管理员权限一致
                roleType = 5;
            }else {
                roleType = 6;
            }
        }
        return roleType;
    }

    /**
     * 获取当前登录人是否有局领导角色
     * @param userId
     * @return
     */
    public boolean isJz(String userId){
    	Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        List<DicUsers> dicUsers = dicUsersService.queryList(map);
        List<String> roleTypes = new ArrayList<>();
        if (dicUsers.size() > 0) {
            for (DicUsers dicUser : dicUsers) {
                roleTypes.add(dicUser.getRolecode());
            }
            if (roleTypes.contains("1") ) {
            	return true;
            }
            return false;
        }else {
            return false;
        }
    }
    /**
     * 是否超级管理员
     * @return boolean
     */
    private boolean isSupperAdmin(){
        BaseAppOrgMapped mapped = (BaseAppOrgMapped) baseAppOrgMappedService.orgMappedByOrgId(null, "root",
                AppConstant.APP_QXJGL);
        return mapped !=null && !CurrentUser.getIsManager(mapped.getAppId(), mapped.getAppSecret());
    }
}
