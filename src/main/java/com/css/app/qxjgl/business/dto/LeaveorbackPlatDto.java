package com.css.app.qxjgl.business.dto;

import java.io.Serializable;

public class LeaveorbackPlatDto implements Serializable{
		private String plat; //地图名称
		private Integer number;// 人数
		public String getPlat() {
			return plat;
		}
		public void setPlat(String plat) {
			this.plat = plat;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		
}
