package com.allong.centerstation.config;

import com.allong.centerstation.handler.LoginFailureHandler;
import com.allong.centerstation.handler.LoginSuccessHandler;
import com.allong.centerstation.handler.LogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: WebSecurityConfigurator
 * Author:   YJ
 * Date:     2020/12/9 11:02
 * Description: Security Config
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/9 11:02        1.0              描述
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfigurator extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //登录成功handler
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    //登录失败handler
    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginValidateAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/*.ico",
                        "/static/*.*",
                        "/static/**",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/webSocket/**"
                ).permitAll()
                .antMatchers("/", "/login", "/register", "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/hospital").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home")
                .successHandler(loginSuccessHandler)//成功登录处理器
                .failureHandler(loginFailureHandler)//失败登录处理器
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
