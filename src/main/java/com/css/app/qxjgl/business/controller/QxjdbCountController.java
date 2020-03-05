package com.css.app.qxjgl.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.util.QxjStatusDefined;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;

@Controller
@RequestMapping("/leave/apply")
public class QxjdbCountController {

	@Autowired
    private LeaveorbackService leaveorbackService;
	/**
     * 请销假待办总数
     */
    @ResponseBody
    @RequestMapping("/qxjdbCount")
    public void qxjdbCount(){
        JSONObject jsonObject = new JSONObject();
        String userId = CurrentUser.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("loginUserId", userId);
        map.put("creatorId", userId);
        map.put("isBubbleStatistics","yes");
        List<Leaveorback> leaveLists = leaveorbackService.queryNewList(map);
        //请销假申请代办数
        int qxjsqNum = this.distictTLeaveorback(leaveLists);
        map.clear();
        map.put("loginUserId", userId);
        map.put("flowPeople", "yes");
        map.put("nostatus", QxjStatusDefined.DAI_TI_JIAO);
        map.put("isBubbleStatistics","yes");
        List<Leaveorback> leaveLists1= leaveorbackService.queryNewList(map);
        //请销假审批代办数
        int qxjspNum = this.distictTLeaveorback(leaveLists1);
        //请销假总的代办数
        jsonObject.put("qxjdbCount",qxjsqNum+qxjspNum);
        Response.json(jsonObject);
    }
    
    /**
     * 过滤数据
     * @param leaveLists1 请假数据
     * @return 数据量
     */
    private  int distictTLeaveorback(List<Leaveorback> leaveLists1){
        return (int) leaveLists1.stream().filter(leaveList -> (leaveList.getStatus() == 10 || leaveList.getStatus() == 20) && (leaveList.getReceiverIsMe() == null ? 0 : leaveList.getReceiverIsMe()) == 1).count();
    }
}
