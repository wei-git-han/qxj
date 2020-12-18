package com.css.app.qxjgl.business.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 请销假表
 *
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-06-06 09:26:59
 */
public class Leaveorback implements Serializable {
	private static final long serialVersionUID = 1L;
	//唯一标识
	private String id;
	//申请人Ids(原来为当前登录人id)
	private String deleteMark;
	//请假人
	private String proposer;
	//直接部门单位id	
	private String orgId;
	//局名称
	private String orgName;
	//申请日期
	private Date applicationDate;
	//预计休假开始时间
	private Date planTimeStart;
	//预计休假结束时间
	private Date planTimeEnd;
	//休假类别ID
	private String vacationSortId;
	//休假天数
	private Integer leaveDays;
	//交通工具
	private String vehicle;
	//假期天数--已扣除的休假期间法定节假日
	private Integer holidayNum;
	//周末天数--休假期间周六日天数
	private Integer weekendNum;
	//部职别
	private String deptDuty;
	//联系人
	private String linkMan;
	//联系人电话
	private String mobile;
	//承办人
	private String undertaker;
	//承办人id
	private String undertakerId;
	//承办人电话
	private String undertakerMobile;
	//地点
	private String place;
	//请假事由
	private String origin;
	//实际休假开始时间
	private Date actualTimeStart;
	//实际休假结束时间
	private Date actualTimeEnd;
	//实际请假天数(休假天数)
	private Integer actualVocationDate;
	//主文件状态：0=草稿，10=审批中，20=已退回，30=审批完毕，40=已作废
	private Integer status;
	//销假状态ID(1.审批中)
	private String backStatusId;
	//创建者ID
	private String creatorId;
	//创建者
	private String creator;
	//创建日期
	private Date createDate;
	//修改者
	private String modificator;
	//修改者ID
	private String modificatorId;
	//最后修改日期
	private Date modifyDate;
	private String deptId;
	//最后修改日期
	private String deptName;
	//局id
	private String parentOrgId;
	/*--------------以下为数据库字段，但目前不用了，有老数据-------*/
	//移交事宜（目前废弃）
	private String turnOver;
	//登记日期（目前废弃）
	private Date registrationDate;
	//请假状态ID（目前废弃）
	private String leaveStatusId;
	//休假地点（目前废弃）
	private String vocationPlace;
	//请假备注（目前废弃）
	private String leaveRemark;
	//处室领导姓名（目前废弃）
	private String leaderName;
	//处室领导id（目前废弃）
	private String leaderId;
	//局领导姓名（目前废弃）
	private String chairmanName;
	//局领导id（目前废弃）
	private String chairmanId;
	//处室领导请假审批意见（目前废弃）
	private String leaderLeaveView;
	//处室领导销假审批意见（目前废弃）
	private String leaderBackView;
	//局领导请假审批意见（目前废弃）
	private String chairmanView;
	//审批备注（目前废弃）
	private String viewRemark;
	//板式文件ID（目前废弃 老数据需处理）
	private String ofdFileId;

/*------------------以下字段用来传值用--------------*/
	//休假类别名字
	private String vacationSortName;
	//当前处理人姓名
	private String dealUserId;
	//当前处理人姓名
	private String dealUserName;
	//我是不是当前处理人(1.文在我这)
	private Integer receiverIsMe;
	//撤回标识
	private String withdrawFlag;
	//当前流转状态
	private String flowType;
	//列表起止时间使用
	private String planTimeStartEnd;
	
	//应休天数（目前废弃）
	private Integer shouldTakDays;
	private String isView;//审批人是否阅读  "0"代表未读"1"代表已读  addby weizy
	//实际应休假天数
	private Integer restDays;
	//请假实际起期
	private Date startTime;
	//请假实际止期
	private Date endTime;
	//20200415新增 应休天数、已休天数、未休天数、最小未休天数
	private String offDays;
	private String leavedDays;
	private String noLeaveDays;
	private Integer noLeaveMinDays;

	private String preId;//上一页
	private String sufId;//下一页

	private String preStatus;//操作前的状态

	private String sort;
	//请假人和当前登录人是否是同一个人
	private String isSame;

	//是否显示补报详情按钮
	private String isOpen;

	//请假说明
	private String explain;

	//地点说明
	private String address;

	//具体城市
	private String city;

	//到达单位
	private String toPlace;

	//地方驾驶证号
	private String carJsid;

	//驾驶人
	private String driver;

	//乘坐人
	private String passenger;

	//车型及出车数量
	private String cartypeCarnumber;

	//乘员及装载货物情况
	private String peopleThing;
	//请假类别
	private String xjlb;

	//车辆号牌
	private String carCard;

	public String getCarCard() {
		return carCard;
	}

	public void setCarCard(String carCard) {
		this.carCard = carCard;
	}

	public String getXjlb() {
		return xjlb;
	}

	public void setXjlb(String xjlb) {
		this.xjlb = xjlb;
	}

	public String getPeopleThing() {
		return peopleThing;
	}

	public void setPeopleThing(String peopleThing) {
		this.peopleThing = peopleThing;
	}

	public String getCartypeCarnumber() {
		return cartypeCarnumber;
	}

	public void setCartypeCarnumber(String cartypeCarnumber) {
		this.cartypeCarnumber = cartypeCarnumber;
	}

	public String getPassenger() {
		return passenger;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public String getCarJsid() {
		return carJsid;
	}

	public void setCarJsid(String carJsid) {
		this.carJsid = carJsid;
	}

	//车辆号牌
	private String car_card;

	public String getCar_card() {
		return car_card;
	}

	public void setCar_card(String car_card) {
		this.car_card = car_card;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Integer getNoLeaveMinDays() {
		return noLeaveMinDays;
	}
	public void setNoLeaveMinDays(Integer noLeaveMinDays) {
		this.noLeaveMinDays = noLeaveMinDays;
	}
	public String getOffDays() {
		return offDays;
	}
	/**
	 * 应休天数
	 * @param offDays
	 */
	public void setOffDays(String offDays) {
		this.offDays = offDays;
	}
	public String getLeavedDays() {
		return leavedDays;
	}
	/**
	 * 已休天数
	 * @param leavedDays
	 */
	public void setLeavedDays(String leavedDays) {
		this.leavedDays = leavedDays;
	}
	public String getNoLeaveDays() {
		return noLeaveDays;
	}
	/**
	 * 未休天数
	 * @param noLeaveDays
	 */
	public void setNoLeaveDays(String noLeaveDays) {
		this.noLeaveDays = noLeaveDays;
	}
	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：申请日期
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	/**
	 * 获取：申请日期
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}
	/**
	 * 设置：登记日期
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * 获取：登记日期
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * 设置：请假人
	 */
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	/**
	 * 获取：请假人
	 */
	public String getProposer() {
		return proposer;
	}
	/**
	 * 设置：休假类别ID
	 */
	public void setVacationSortId(String vacationSortId) {
		this.vacationSortId = vacationSortId;
	}
	/**
	 * 获取：休假类别ID
	 */
	public String getVacationSortId() {
		return vacationSortId;
	}
	/**
	 * 设置：休假地点
	 */
	public void setVocationPlace(String vocationPlace) {
		this.vocationPlace = vocationPlace;
	}
	/**
	 * 获取：休假地点
	 */
	public String getVocationPlace() {
		return vocationPlace;
	}
	/**
	 * 设置：预计休假开始时间
	 */
	public void setPlanTimeStart(Date planTimeStart) {
		this.planTimeStart = planTimeStart;
	}
	/**
	 * 获取：预计休假开始时间
	 */
	public Date getPlanTimeStart() {
		return planTimeStart;
	}
	/**
	 * 设置：预计休假结束时间
	 */
	public void setPlanTimeEnd(Date planTimeEnd) {
		this.planTimeEnd = planTimeEnd;
	}
	/**
	 * 获取：预计休假结束时间
	 */
	public Date getPlanTimeEnd() {
		return planTimeEnd;
	}
	/**
	 * 设置：实际休假开始时间
	 */
	public void setActualTimeStart(Date actualTimeStart) {
		this.actualTimeStart = actualTimeStart;
	}
	/**
	 * 获取：实际休假开始时间
	 */
	public Date getActualTimeStart() {
		return actualTimeStart;
	}
	/**
	 * 设置：实际休假结束时间
	 */
	public void setActualTimeEnd(Date actualTimeEnd) {
		this.actualTimeEnd = actualTimeEnd;
	}
	/**
	 * 获取：实际休假结束时间
	 */
	public Date getActualTimeEnd() {
		return actualTimeEnd;
	}
	/**
	 * 设置：请假状态ID
	 */
	public void setLeaveStatusId(String leaveStatusId) {
		this.leaveStatusId = leaveStatusId;
	}
	/**
	 * 获取：请假状态ID
	 */
	public String getLeaveStatusId() {
		return leaveStatusId;
	}
	/**
	 * 设置：销假状态ID
	 */
	public void setBackStatusId(String backStatusId) {
		this.backStatusId = backStatusId;
	}
	/**
	 * 获取：销假状态ID
	 */
	public String getBackStatusId() {
		return backStatusId;
	}
	/**
	 * 设置：请假备注
	 */
	public void setLeaveRemark(String leaveRemark) {
		this.leaveRemark = leaveRemark;
	}
	/**
	 * 获取：请假备注
	 */
	public String getLeaveRemark() {
		return leaveRemark;
	}
	/**
	 * 设置：处室领导姓名
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	/**
	 * 获取：处室领导姓名
	 */
	public String getLeaderName() {
		return leaderName;
	}
	/**
	 * 设置：处室领导id
	 */
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	/**
	 * 获取：处室领导id
	 */
	public String getLeaderId() {
		return leaderId;
	}
	/**
	 * 设置：局领导姓名
	 */
	public void setChairmanName(String chairmanName) {
		this.chairmanName = chairmanName;
	}
	/**
	 * 获取：局领导姓名
	 */
	public String getChairmanName() {
		return chairmanName;
	}
	/**
	 * 设置：局领导id
	 */
	public void setChairmanId(String chairmanId) {
		this.chairmanId = chairmanId;
	}
	/**
	 * 获取：局领导id
	 */
	public String getChairmanId() {
		return chairmanId;
	}
	/**
	 * 设置：处室领导请假审批意见
	 */
	public void setLeaderLeaveView(String leaderLeaveView) {
		this.leaderLeaveView = leaderLeaveView;
	}
	/**
	 * 获取：处室领导请假审批意见
	 */
	public String getLeaderLeaveView() {
		return leaderLeaveView;
	}
	/**
	 * 设置：处室领导销假审批意见
	 */
	public void setLeaderBackView(String leaderBackView) {
		this.leaderBackView = leaderBackView;
	}
	/**
	 * 获取：处室领导销假审批意见
	 */
	public String getLeaderBackView() {
		return leaderBackView;
	}
	/**
	 * 设置：局领导请假审批意见
	 */
	public void setChairmanView(String chairmanView) {
		this.chairmanView = chairmanView;
	}
	/**
	 * 获取：局领导请假审批意见
	 */
	public String getChairmanView() {
		return chairmanView;
	}
	/**
	 * 设置：审批备注
	 */
	public void setViewRemark(String viewRemark) {
		this.viewRemark = viewRemark;
	}
	/**
	 * 获取：审批备注
	 */
	public String getViewRemark() {
		return viewRemark;
	}
	/**
	 * 设置：删除标志
	 */
	public void setDeleteMark(String deleteMark) {
		this.deleteMark = deleteMark;
	}
	/**
	 * 获取：删除标志
	 */
	public String getDeleteMark() {
		return deleteMark;
	}
	/**
	 * 设置：实际请假天数
	 */
	public void setActualVocationDate(Integer actualVocationDate) {
		this.actualVocationDate = actualVocationDate;
	}
	/**
	 * 获取：实际请假天数
	 */
	public Integer getActualVocationDate() {
		return actualVocationDate;
	}
	public Integer getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origan) {
		this.origin = origan;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(String turnOver) {
		this.turnOver = turnOver;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModificator() {
		return modificator;
	}
	public void setModificator(String modificator) {
		this.modificator = modificator;
	}
	public String getModificatorId() {
		return modificatorId;
	}
	public void setModificatorId(String modificatorId) {
		this.modificatorId = modificatorId;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getOfdFileId() {
		return ofdFileId;
	}
	public void setOfdFileId(String ofdFileId) {
		this.ofdFileId = ofdFileId;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	
	public Integer getHolidayNum() {
		return holidayNum;
	}
	public void setHolidayNum(Integer holidayNum) {
		this.holidayNum = holidayNum;
	}
	public Integer getWeekendNum() {
		return weekendNum;
	}
	public void setWeekendNum(Integer weekendNum) {
		this.weekendNum = weekendNum;
	}
	public Integer getShouldTakDays() {
		return shouldTakDays;
	}
	public void setShouldTakDays(Integer shouldTakDays) {
		this.shouldTakDays = shouldTakDays;
	}
	public String getVacationSortName() {
		return vacationSortName;
	}
	public void setVacationSortName(String vacationSortName) {
		this.vacationSortName = vacationSortName;
	}
	public String getPlanTimeStartEnd() {
		return planTimeStartEnd;
	}
	public void setPlanTimeStartEnd(String planTimeStartEnd) {
		this.planTimeStartEnd = planTimeStartEnd;
	}
	public String getDealUserId() {
		return dealUserId;
	}
	public void setDealUserId(String dealUserId) {
		this.dealUserId = dealUserId;
	}
	public String getDealUserName() {
		return dealUserName;
	}
	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}
	public Integer getReceiverIsMe() {
		return receiverIsMe;
	}
	public void setReceiverIsMe(Integer receiverIsMe) {
		this.receiverIsMe = receiverIsMe;
	}
	public String getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public String getDeptDuty() {
		return deptDuty;
	}
	public void setDeptDuty(String deptDuty) {
		this.deptDuty = deptDuty;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getUndertaker() {
		return undertaker;
	}
	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}
	public String getUndertakerMobile() {
		return undertakerMobile;
	}
	public void setUndertakerMobile(String undertakerMobile) {
		this.undertakerMobile = undertakerMobile;
	}
	public String getUndertakerId() {
		return undertakerId;
	}
	public void setUndertakerId(String undertakerId) {
		this.undertakerId = undertakerId;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getRestDays() {
		return restDays;
	}

	public void setRestDays(Integer restDays) {
		this.restDays = restDays;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPreId() {
		return preId;
	}

	public void setPreId(String preId) {
		this.preId = preId;
	}

	public String getSufId() {
		return sufId;
	}

	public void setSufId(String sufId) {
		this.sufId = sufId;
	}

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}


	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getIsSame() {
		return isSame;
	}

	public void setIsSame(String isSame) {
		this.isSame = isSame;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
}
