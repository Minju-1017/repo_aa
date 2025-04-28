package com.aa.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aa.common.config.interceptor.CheckLoginSessionInterceptor;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckLoginSessionInterceptor())
				.addPathPatterns("/*/*/*HO*", "/*/*/*WH*", "/hoxdm/index", "/whxdm/index")
				.excludePathPatterns(
					"/assets/**", // static/assets 폴더안 모든 것은 허용하겠다는 것
					"/hoxdm/member/MemberHOXdmSignIn",
					"/hoxdm/member/MemberHOXdmSignInProc",
					"/whxdm/member/MemberWHXdmSignIn",
					"/whxdm/member/MemberWHXdmSignInProc"
		);
	}

}