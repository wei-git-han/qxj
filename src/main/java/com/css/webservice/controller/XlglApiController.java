package com.css.webservice.controller;

import com.css.addbase.apporgan.entity.BaseAppUser;
import com.css.addbase.apporgan.service.BaseAppUserService;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.base.utils.DateUtil;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/qxjgl/api")
public class XlglApiController {
    @Autowired
    private LeaveorbackService leaveorbackService;
    @Autowired
    private BaseAppUserService baseAppUserService;

    /**
     * 训练管理获取请假人员
     * @param fromFlag ：来自首页还是列表的请求：listPage-来自列表页；其他为来自首页
     * */
    @ResponseBody
    @RequestMapping("/getQjUserIds")
    public void getQjUserIds(String fromFlag,String deptId) { // 返回整个部门下所有请假通过且在期限内的用户登录名
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        List<Leaveorback> qjUserIdsList = leaveorbackService.queryQjUserIds(map);

        if(qjUserIdsList == null || qjUserIdsList.size() == 0) {
            Response.json(null);
            return;
        }

        String qjUserIds = "";
        for(Leaveorback leaveorback : qjUserIdsList) {
            qjUserIds += "," + leaveorback.getDeleteMark();
        }

        List<BaseAppUser> baseAppUserList = baseAppUserService.queryObjectByUserIds(qjUserIds.split(","));
        Map<String, Object> returnMap = new HashMap<>();
        if(baseAppUserList != null) {
            for(int i = 0; i < baseAppUserList.size(); i++) {
                for(Leaveorback leaveorback : qjUserIdsList) {
                    if("listPage".equals(fromFlag) && StringUtils.contains(leaveorback.getDeleteMark(), baseAppUserList.get(i).getUserId())) {
                        returnMap.put(baseAppUserList.get(i).getAccount(), new SimpleDateFormat("yyyy-MM-dd").format(leaveorback.getRegistrationDate()));
                        returnMap.put("userId",baseAppUserList.get(i).getUserId());
                        break;
                    }
                }
            }
        }

        Response.json(returnMap);
    }

    @ResponseBody
    @RequestMapping("/getQjNum")
    public void getQjNum(String fromFlag,String deptId) { // 返回整个部门下所有请假通过且在期限内的用户登录名
        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.getDate().substring(0,10)+" 00:00:00";
        String endTime = DateUtil.getDate().substring(0,10)+" 23:59:59";
        map.put("deptId", deptId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        List<Leaveorback> qjUserIdsList = leaveorbackService.getQjNum(map);
        int num = 0;
        if(qjUserIdsList != null && qjUserIdsList.size() > 0){
            num = qjUserIdsList.size();
        }
        Response.json("num",num);
    }
}
