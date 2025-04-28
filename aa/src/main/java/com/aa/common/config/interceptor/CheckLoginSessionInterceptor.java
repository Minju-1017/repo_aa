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
		if(request.getRequestURI().contains(Constants.ABBREVIATION_HO_XDM)) {
			if (request.getSession().getAttribute(Constants.SESSION_SEQ_NAME_HO_XDM) == null) {
				response.sendRedirect(Constants.URL_LOGIN_FORM_HO_XDM);
		        return false;
			}
		}
		
		// 사용자용
		if(request.getRequestURI().contains(Constants.ABBREVIATION_WH_XDM)) {
			if (request.getSession().getAttribute(Constants.SESSION_SEQ_NAME_WH_XDM) == null) {
				response.sendRedirect(Constants.URL_LOGIN_FORM_WH_XDM);
				return false;
			}
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
