package com.imxiaomai.bms.config;

import com.imxiaomai.bms.security.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private HandlerInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/rest/**").excludePathPatterns("/rest/user/loginInfo");
        super.addInterceptors(registry);
    }
}
