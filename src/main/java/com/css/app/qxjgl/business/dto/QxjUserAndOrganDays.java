package com.css.app.qxjgl.business.dto;

import java.io.Serializable;

public class QxjUserAndOrganDays implements Serializable{
		//user表id
		private String id;
		//用户id
		private String userId;
		//删除标志
		private Integer isdelete;
		//更新时间
		private Long timestamp;
		//排序字段
		private Integer sort;
		//真实姓名
		private String truename;
		//更新类型
		private Integer type;
		//部门id
		private String organid;
		//部门名称
		private String organName;
		//等级
		private String seclevel;
		//性别(0:男，1:女)
		private String sex;
		//休假完成率
		private Double rate;
		//职务
		private String job;
		//在位情况
		private String reign;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public Integer getIsdelete() {
			return isdelete;
		}
		public void setIsdelete(Integer isdelete) {
			this.isdelete = isdelete;
		}
		public Long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}
		public Integer getSort() {
			return sort;
		}
		public void setSort(Integer sort) {
			this.sort = sort;
		}
		public String getTruename() {
			return truename;
		}
		public void setTruename(String truename) {
			this.truename = truename;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getOrganid() {
			return organid;
		}
		public void setOrganid(String organid) {
			this.organid = organid;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getSeclevel() {
			return seclevel;
		}
		public void setSeclevel(String seclevel) {
			this.seclevel = seclevel;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public Double getRate() {
			return rate;
		}
		public void setRate(Double rate) {
			this.rate = rate;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getReign() {
			return reign;
		}
		public void setReign(String reign) {
			this.reign = reign;
		}
		
}
