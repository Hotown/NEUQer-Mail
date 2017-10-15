package com.neuqer.mail.config;

import com.neuqer.mail.interceptor.CORSInterceptor;
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
    private CORSInterceptor corsInterceptor;

    @Autowired
    public WebConfig(CORSInterceptor corsInterceptor) {
        this.corsInterceptor = corsInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor);
        super.addInterceptors(registry);
    }
}

