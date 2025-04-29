package com.aa.module.member;

public class MemberDto {
	
	private String uSeq;
	private String uId;
	private String uName;
	private String uNickname;
	private String uEmail;
	private String uPwd;
	private String uPhone;
	private String uPostalCode;
	private String uStreetAddr;
	private String uDetailAddr;
	private String uNoteAddr;
	private Double uLat;
	private Double uLon;
	private String uRegDate;
	private String uMofDate;
	private int uDelNy;
	private String uDelNyStr;
	
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
	
	public String getuNickname() {
		return uNickname;
	}
	
	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}
	
	public String getuEmail() {
		return uEmail;
	}
	
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	
	public String getuPwd() {
		return uPwd;
	}
	
	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	
	public String getuPhone() {
		return uPhone;
	}
	
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	
	public String getuPostalCode() {
		return uPostalCode;
	}
	
	public void setuPostalCode(String uPostalCode) {
		this.uPostalCode = uPostalCode;
	}
	
	public String getuStreetAddr() {
		return uStreetAddr;
	}
	
	public void setuStreetAddr(String uStreetAddr) {
		this.uStreetAddr = uStreetAddr;
	}
	
	public String getuDetailAddr() {
		return uDetailAddr;
	}
	
	public void setuDetailAddr(String uDetailAddr) {
		this.uDetailAddr = uDetailAddr;
	}
	
	public String getuNoteAddr() {
		return uNoteAddr;
	}
	
	public void setuNoteAddr(String uNoteAddr) {
		this.uNoteAddr = uNoteAddr;
	}
	
	public Double getuLat() {
		return uLat;
	}
	
	public void setuLat(Double uLat) {
		this.uLat = uLat;
	}
	
	public Double getuLon() {
		return uLon;
	}
	
	public void setuLon(Double uLon) {
		this.uLon = uLon;
	}
	
	public String getuRegDate() {
		return uRegDate;
	}
	
	public void setuRegDate(String uRegDate) {
		this.uRegDate = uRegDate;
	}
	
	public String getuMofDate() {
		return uMofDate;
	}
	
	public void setuMofDate(String uMofDate) {
		this.uMofDate = uMofDate;
	}

	public int getuDelNy() {
		return uDelNy;
	}

	public void setuDelNy(int uDelNy) {
		this.uDelNy = uDelNy;
		this.uDelNyStr = uDelNy == 1 ? "Y" : "N";
	}

	public String getuDelNyStr() {
		return uDelNyStr;
	}
	
}
