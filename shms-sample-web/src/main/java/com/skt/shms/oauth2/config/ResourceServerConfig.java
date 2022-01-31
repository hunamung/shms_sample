package com.skt.shms.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        
        http.anonymous().disable()
            .authorizeRequests()
            .antMatchers("/user/info").access("#oauth2.hasScope('shms_user_info')")
            .anyRequest().authenticated();
        
        /*
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/").access("#oauth2.hasScope('shms_user_info')")
                .anyRequest().authenticated();
        */
    }
}