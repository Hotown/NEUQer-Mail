package com.neuqer.mail.config;

import com.neuqer.mail.interceptor.CORSInterceptor;
import com.neuqer.mail.interceptor.TokenInterceptor;
import com.neuqer.mail.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Hotown on 17/5/24.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    TokenService tokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CORSInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new TokenInterceptor(tokenService))
                .addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
