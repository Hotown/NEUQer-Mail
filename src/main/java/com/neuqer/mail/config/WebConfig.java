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
 * Created by dgy on 17-5-24.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private TokenInterceptor tokenInterceptor;
    private CORSInterceptor corsInterceptor;

    @Autowired
    public WebConfig(TokenInterceptor tokenInterceptor, CORSInterceptor corsInterceptor) {
        this.corsInterceptor = corsInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor);
        registry.addInterceptor(tokenInterceptor);
        super.addInterceptors(registry);
    }
}

