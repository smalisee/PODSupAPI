package com.amos.podsupapi.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterChain {

  @Bean
  public FilterRegistrationBean<CorsFilter> CorsFilterRegistrationBean() {
    FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setName("CrossOrginSupportFilter");

    CorsFilter corsFilter = new CorsFilter();
    registrationBean.setFilter(corsFilter);
    registrationBean.setOrder(1);
    return registrationBean;
  }

  // @Bean
  // public FilterRegistrationBean<OncePerRequestFilter> securityFilterRegistrationBean() {
  // FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<OncePerRequestFilter>();
  // registrationBean.setName("SecurityFilter");
  //
  // OncePerRequestFilter securityFilter = new SecurityFilter();
  // registrationBean.setFilter(securityFilter);
  // registrationBean.setOrder(2);
  // return registrationBean;
  // }

}
