package com.aa.module.order;

import com.aa.module.BaseVo;

public class OrderVo extends BaseVo {
	
	private String oSeq;
	private String oStateCd;
	private String opStateCd;
	
	
	public String getOpStateCd() {
		return opStateCd;
	}

	public void setOpStateCd(String opStateCd) {
		this.opStateCd = opStateCd;
	}

	private Integer shStateOption;			// null 값을 받아야 되는 경우가 있어서 int 대신 Integer 사용
	
	
	public Integer getShStateOption() {
		return shStateOption;
	}

	public void setShStateOption(Integer shStateOption) {
		this.shStateOption = shStateOption;
	}


	public String getoStateCd() {
		return oStateCd;
	}

	public void setoStateCd(String oStateCd) {
		this.oStateCd = oStateCd;
	}

	public String getoSeq() {
		return oSeq;
	}

	public void setoSeq(String oSeq) {
		this.oSeq = oSeq;
	}

}
