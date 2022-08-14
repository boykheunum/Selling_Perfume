package com.example.sellingperfume.config;

import com.example.sellingperfume.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityFilterConfig {
    @Bean
    public FilterRegistrationBean<AdminFilter>adminFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        return registrationBean;
    }
}
