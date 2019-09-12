package com.css.app.qxjgl.business.service.impl;

import com.css.app.qxjgl.business.dao.DicCalenderDao;
import com.css.app.qxjgl.business.entity.ZFDicUsersModel;
import com.css.app.qxjgl.business.entity.ZFUserInfoModel;
import com.css.app.qxjgl.business.manager.CommonQueryManager;
import com.css.base.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutDicCalenderParam {

    @Autowired
    private CommonQueryManager commonQueryManager;

    /**
     * 管理员新增参数校验
     * @param model
     * @return
     */
    public Boolean checkAddDAParam(ZFDicUsersModel model) {
        if(null == model){
            return false;
        }
        if(null == model.getType()){
            return false;
        }
        if(null == model.getUserid()){
            return false;
        }
        if(null == model.getUsername()){
            return false;
        }
        Integer maxLeave = commonQueryManager.roleType(CurrentUser.getUserId());
        if(null == maxLeave || maxLeave < 3){
            return false;
        }

        return true;
    }

    public boolean checkDelDAParam(ZFDicUsersModel model) {
        if(model.getIds() == null){
            return false;
        }
        return true;
    }

    public boolean checkSelectDAParam(ZFDicUsersModel model) {

        return true;
    }

    public boolean checkselectDeptAdminJuParam(ZFDicUsersModel model) {
        if(model == null){
            return false;
        }
        if(model.getPage() == null){
           model.setPage(1);
        }
        if(model.getPageSize() == null){
            model.setPageSize(15);
        }
        return true;
    }

    public boolean checkselectDeptAdminBuParam(ZFDicUsersModel model) {
        if(model == null){
            return false;
        }
        if(model.getPage() == null){
            model.setPage(1);
        }
        if(model.getPageSize() == null){
            model.setPageSize(15);
        }
        return true;


    }
}

