package com.age.user.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录成功处理类
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");

        http.authorizeRequests()
                //静态文件允许访问
                .antMatchers().permitAll()
                // 放行
                .antMatchers( "/assets/**","/login","/test/**","/testfather/**","/testes/**","/advice/**","css/**","/js/**","/image/*","/user/sso/**").permitAll()
                //其他所有请求需要登录, anyRequest 不能配置在 antMatchers 前面
                .anyRequest().authenticated()
                .and()
                //登录页面配置，用于替换security默认页面
                .formLogin().loginPage(  "/login").successHandler(successHandler).and()
                //登出页面配置，用于替换security默认页面
                .logout().logoutUrl( "/logout").and()
                .httpBasic().and()
                // 开启了csrf防御 如果要放行/test/** 还需要在下面ignoringAntMatchers进行配置
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // 这里还需要放行 当然如果是 .csrf().disable()就不会有这个问题了
                .ignoringAntMatchers(
                        "/instances",
                        "/actuator/**",
                        "/test/**",
                        "/advice/**",
                        "/testes/**"
//                        "/user/sso/**"
                );

    }
}
