package com.aa.module.code;

import java.util.ArrayList;
import java.util.List;

public class CodeDto {
	
	public static List<CodeDto> cachedCodeArrayList = new ArrayList<CodeDto>(); // 전체 코드 리스트
	
	private String cSeq;
	private String cName;
	private String cRegDate;
	private String cMofDate;
	private String codeGroup_cgSeq;
	private String cgName;
	
	public static List<CodeDto> getCachedCodeArrayList() {
		return cachedCodeArrayList;
	}

	public static void setCachedCodeArrayList(List<CodeDto> cachedCodeArrayList) {
		CodeDto.cachedCodeArrayList = cachedCodeArrayList;
	}

	public String getcSeq() {
		return cSeq;
	}

	public void setcSeq(String cSeq) {
		this.cSeq = cSeq;
	}

	public String getcName() {
		return cName;
	}
	
	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcRegDate() {
		return cRegDate;
	}

	public void setcRegDate(String cRegDate) {
		this.cRegDate = cRegDate;
	}

	public String getcMofDate() {
		return cMofDate;
	}
	
	public void setcMofDate(String cMofDate) {
		this.cMofDate = cMofDate;
	}

	public String getCodeGroup_cgSeq() {
		return codeGroup_cgSeq;
	}

	public void setCodeGroup_cgSeq(String codeGroup_cgSeq) {
		this.codeGroup_cgSeq = codeGroup_cgSeq;
	}

	public String getCgName() {
		return cgName;
	}

	public void setCgName(String cgName) {
		this.cgName = cgName;
	}
	
}
