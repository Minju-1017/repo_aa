package com.aa.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.aa.module.member.MemberDto;
import com.aa.module.miss.MissDto;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	//	회원가입 축하 메일
    public void sendMailWelcome(MemberDto memberDto) throws Exception {
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
    	
    	mimeMessageHelper.setTo(memberDto.getuEmail()); 
    	mimeMessageHelper.setSubject("AA 회원 가입 축하 메일");
    	mimeMessageHelper.setText("AA에 오신 것을 환영합니다!", true); 
    	
    	javaMailSender.send(mimeMessage);
    }
    
    //	실종 신고 알림 메일
    public void sendMailMissAlert(MemberDto memberDto, MissDto missDto) throws Exception {
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
    	
    	String contentsHtml = 
    			"보호자 닉네임: " + missDto.getuNickname() + "<br><br>"
    			+ "반려동물 정보<br>"
    			+ "=============================<br>"
    			+ "<img style=\"height:300px;\" src=\"" + missDto.getfPath() +"\"><br><br>"
    			+ "이름: " + missDto.getUaName() + "<br>"
    			+ "나이: " + missDto.getAge() + "<br>"
    			+ "품종: " + missDto.getUaBreed() + "<br>"
    			+ "성별: " + missDto.getcName() + "<br>"
    			+ "실종날짜: " + missDto.getMbRegDate() + "<br>"
    			+ "실종 위치: " + missDto.getMbDetailAddr() + "<br>"
    			+ "사례금: " + missDto.getMbRewardformat() + "<br>"
    			+ "=============================<br><br>"
    			+ "보호자가 애타게 찾고 있습니다.<br>"
    			+ missDto.getUaName() + "를 보신분은 꼭 연락해 주세요.";
    	
    	//contentsHtml.replace("name", missDto.getUaName());
    	
    	mimeMessageHelper.setTo(memberDto.getuEmail()); 
    	mimeMessageHelper.setSubject("[AA 실종 알림] " + missDto.getUaName() + "를 찾아주세요!");
    	mimeMessageHelper.setText(contentsHtml, true); 
    	
    	javaMailSender.send(mimeMessage);
    }
}
