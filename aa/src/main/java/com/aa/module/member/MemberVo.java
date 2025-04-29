package com.aa.module.member;

import com.aa.module.BaseVo;

public class MemberVo extends BaseVo {
	
	private String uSeq;
	
	// Search
	private Integer shGenderCd; // null 값을 받아야 되는 경우가 있어서 int 대신 Integer 사용
	private Integer shGradeCd; 	// null 값을 받아야 되는 경우가 있어서 int 대신 Integer 사용

	public String getuSeq() {
		return uSeq;
	}

	public void setuSeq(String uSeq) {
		this.uSeq = uSeq;
	}

	public Integer getShGenderCd() {
		return shGenderCd;
	}

	public void setShGenderCd(Integer shGenderCd) {
		this.shGenderCd = shGenderCd;
	}

	public Integer getShGradeCd() {
		return shGradeCd;
	}

	public void setShGradeCd(Integer shGradeCd) {
		this.shGradeCd = shGradeCd;
	}

}
