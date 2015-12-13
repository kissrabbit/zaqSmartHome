package com.zaq.smartHome.ws;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(AuthFilter filter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/admin");
        return filterRegistrationBean;
    }   
}