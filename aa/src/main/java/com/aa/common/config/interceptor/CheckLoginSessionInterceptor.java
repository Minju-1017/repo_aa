package com.aa.common.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.aa.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckLoginSessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		// 관리자용
		if(request.getRequestURI().contains(Constants.ABBREVIATION_XDM)) {
			if (request.getSession().getAttribute(Constants.SESSION_SEQ_NAME_XDM) == null) {
				response.sendRedirect(Constants.URL_LOGIN_FORM_XDM);
		        return false;
			}
		}
		
		// 사용자용
		if(request.getRequestURI().contains(Constants.ABBREVIATION_USR)) {
			if (request.getSession().getAttribute(Constants.SESSION_SEQ_NAME_USR) == null) {
				response.sendRedirect(Constants.URL_LOGIN_FORM_USR);
				return false;
			}
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
