package com.aa.module.member;

public class MemberDto {
	
	private String uSeq;
	private String uId;
	private String uName;
	private String uPwd;

	public String getuSeq() {
		return uSeq;
	}
	
	public void setuSeq(String uSeq) {
		this.uSeq = uSeq;
	}
	
	public String getuId() {
		return uId;
	}
	
	public void setuId(String uId) {
		this.uId = uId;
	}
	
	public String getuName() {
		return uName;
	}
	
	public void setuName(String uName) {
		this.uName = uName;
	}
	
	public String getuPwd() {
		return uPwd;
	}
	
	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	
}
