package com.css.webservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.msg.MsgTipUtil;
import com.css.app.qxjgl.business.entity.Leaveorback;
import com.css.app.qxjgl.business.service.LeaveorbackService;
import com.css.app.qxjgl.util.QxjStatusDefined;
import com.css.base.utils.CurrentUser;
import com.css.base.utils.Response;
@RestController
@RequestMapping("/api")
public class ToDoApiController {
	
	@Autowired
    private LeaveorbackService leaveorbackService;
	private static Logger logger = LoggerFactory.getLogger(ToDoApiController.class);
	/**
     * 请销假待办总数
     */
    @ResponseBody
    @RequestMapping("/qxjgl/todo")
    public void qxjdbCount(){
    	logger.info("桌面接口开始调用代办总数接口：");
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
        jsonObject.put("result", "success");
        logger.info("桌面接口成功结束调用代办总数接口：",qxjsqNum+qxjspNum);
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
