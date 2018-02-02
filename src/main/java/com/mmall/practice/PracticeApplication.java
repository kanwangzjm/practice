package com.mmall.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableHystrixDashboard
@EnableCircuitBreaker
public class PracticeApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpFilter());
        registration.addUrlPatterns("/threadLocal/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
    }
}
