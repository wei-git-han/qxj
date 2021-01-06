package com.css.app.qxjgl.business.entity;

public class LeavebackSaveModel {
	private String sqrq;
	private String sqr;
	private String sqrId;
	private String xjlb;
	private String syts;
	private String xjsjFrom;
	private String xjsjTo;
	private String xjts;
	private String csld;
	private String csldId;
	private String csparea;
	private String jld;
	private String jldId;
	private String jsparea;
	private String qjzt;
	private String spzt;
	//联系方式
	private String mobile;
	//地点
	private String place;
	//请假事由
	private String origin;
	//局ID
	private String parentOrgId;

	private String orgId;
	private String orgName;
	private String vehicle;
	private String turnOver;
	private Integer status;
	// addby  weizy   应休天数
	private Double shouldTakDays;
	private Integer holidayNum;
	private Integer weekendNum;
	//部职别
	private String deptDuty;
	//联系人
	private String linkMan;
	//承办人
	private String undertaker;
	//承办人id
	private String undertakerId;
	//承办人电话
	private String undertakerMobile;
	//销假实际开始日期：
	private String sjrqFrom;
	//销假实际结束日期：
	private String sjrqTo;
	//实际请假天数：
	private String sjqjts;
	//销假状态
	private String xjzt;

	//请假说明
	private String explain;

	//地点说明
	private String address;

	//具体城市
	private String city;

	//到达单位
	private String toPlace;

	//车辆号牌
	private String carCard;
	
	//风险等级
	private String level;

	//核算检测结果
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCarCard() {
		return carCard;
	}

	public void setCarCard(String carCard) {
		this.carCard = carCard;
	}

	public String getCarJsid() {
		return carJsid;
	}

	public void setCarJsid(String carJsid) {
		this.carJsid = carJsid;
	}

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

	public String getSpzt() {
		return spzt;
	}
	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}
	public String getQjzt() {
		return qjzt;
	}
	public void setQjzt(String qjzt) {
		this.qjzt = qjzt;
	}
	public String getSqrq() {
		return sqrq;
	}
	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getXjlb() {
		return xjlb;
	}
	public void setXjlb(String xjlb) {
		this.xjlb = xjlb;
	}
	public String getSyts() {
		return syts;
	}
	public void setSyts(String syts) {
		this.syts = syts;
	}
	public String getXjsjFrom() {
		return xjsjFrom;
	}
	public void setXjsjFrom(String xjsjFrom) {
		this.xjsjFrom = xjsjFrom;
	}
	public String getXjsjTo() {
		return xjsjTo;
	}
	public void setXjsjTo(String xjsjTo) {
		this.xjsjTo = xjsjTo;
	}
	public String getXjts() {
		return xjts;
	}
	public void setXjts(String xjts) {
		this.xjts = xjts;
	}
	public String getCsld() {
		return csld;
	}
	public void setCsld(String csld) {
		this.csld = csld;
	}
	public String getCsldId() {
		return csldId;
	}
	public void setCsldId(String csldId) {
		this.csldId = csldId;
	}
	public String getCsparea() {
		return csparea;
	}
	public void setCsparea(String csparea) {
		this.csparea = csparea;
	}
	public String getJld() {
		return jld;
	}
	public void setJld(String jld) {
		this.jld = jld;
	}
	public String getJldId() {
		return jldId;
	}
	public void setJldId(String jldId) {
		this.jldId = jldId;
	}
	public String getJsparea() {
		return jsparea;
	}
	public void setJsparea(String jsparea) {
		this.jsparea = jsparea;
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
	public Double getShouldTakDays() {
		return shouldTakDays;
	}
	public void setShouldTakDays(Double shouldTakDays) {
		this.shouldTakDays = shouldTakDays;
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
	public String getUndertakerId() {
		return undertakerId;
	}
	public void setUndertakerId(String undertakerId) {
		this.undertakerId = undertakerId;
	}
	public String getUndertakerMobile() {
		return undertakerMobile;
	}
	public void setUndertakerMobile(String undertakerMobile) {
		this.undertakerMobile = undertakerMobile;
	}
	public String getSqrId() {
		return sqrId;
	}
	public void setSqrId(String sqrId) {
		this.sqrId = sqrId;
	}
	public String getSjrqFrom() {
		return sjrqFrom;
	}
	public void setSjrqFrom(String sjrqFrom) {
		this.sjrqFrom = sjrqFrom;
	}
	public String getSjrqTo() {
		return sjrqTo;
	}
	public void setSjrqTo(String sjrqTo) {
		this.sjrqTo = sjrqTo;
	}
	public String getSjqjts() {
		return sjqjts;
	}
	public void setSjqjts(String sjqjts) {
		this.sjqjts = sjqjts;
	}
	public String getXjzt() {
		return xjzt;
	}
	public void setXjzt(String xjzt) {
		this.xjzt = xjzt;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
