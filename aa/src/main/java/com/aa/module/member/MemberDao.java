package com.aa.module.member;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {

	public int selectOneCount(MemberVo vo);
	public List<MemberDto> selectList(MemberVo vo); 
	public List<MemberDto> selectAllList(); 
	public MemberDto selectOne(MemberDto memberDto);
	public MemberDto selectSignInMember(MemberDto memberDto);
	public int insertCheckId(MemberDto memberDto);
	public int insert(MemberDto memberDto);
	public int update(MemberDto memberDto);
	public int updateForgotPwd(MemberDto memberDto);
	public MemberDto updatePwdCheck(MemberDto memberDto);
	public int updatePwd(MemberDto memberDto);
	public int delete(MemberDto memberDto);
	public int uelete(MemberDto memberDto);
	public int listDelete(List<String> seqList);
	public int listUelete(List<String> seqList);
	public MemberDto loginOne(MemberDto dto); 
}
