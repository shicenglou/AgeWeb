package com.age.security.config;

//import com.age.security.filter.JwtAuthenticationTokenFilter;
import com.age.security.filter.LoginAuthencationFilterPlus;
import com.age.security.filter.TokenAutoFilter;
import com.age.security.service.impl.UserDetailServiceImpl;
import com.age.security.utils.PasswordEncoderPlus;
import com.age.servicebase.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoderPlus passwordEncoderPlus;

    @Autowired
    private RedisUtil redisUtil;

    private RedisTemplate redisTemplate;

    @Autowired
    public SecurityConfig(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//
////        String idForEncode = "ssm";
////        Map encoders = new HashMap<>();
////        encoders.put(idForEncode, new BCryptPasswordEncoder());
////        encoders.put("noop", NoOpPasswordEncoder.getInstance());
////        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
////        encoders.put("scrypt", new SCryptPasswordEncoder());
////        encoders.put("sha256", new StandardPasswordEncoder());
////        encoders.put("argon2", new Argon2PasswordEncoder());
////
////        PasswordEncoder passwordEncoder =
////                new DelegatingPasswordEncoder(idForEncode, encoders);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder;
//    }

//    //??????????????????????????????
//    @Bean
//    public AuthenticationEventPublisher authenticationEventPublisher (ApplicationEventPublisher applicationEventPublisher) {
//        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
//    }

//    @Autowired
//    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        //??????csrf
//        http
//                //????????????????????????????????????
//                .formLogin(Customizer.withDefaults())
//                //?????????Session??????SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // ?????????????????? ??????????????????
//                .antMatchers("/user/**","/index","/login/**").permitAll()
//                // ???????????????????????????????????????????????????
//                .anyRequest().authenticated().and()
//                //??????csrf
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringAntMatchers(
//                        "/instances",
//                        "/actuator/**",
//                        "/test/**",
//                        "/advice/**",
//                        "/testes/**",
//                        "/user/**",
//                        "login/**",
//                        "/index"
//                );

        //

//        ???token???????????????????????????????????????
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint())//??????????????????
                .and().csrf().disable()
                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and().authorizeRequests()
                .antMatchers("/code/**").permitAll()    //?????????
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")//????????????
                .addLogoutHandler(new LogoutHandlerPlus(redisUtil)).and()
                .addFilter(new LoginAuthencationFilterPlus(authenticationManager(), redisTemplate))
                .addFilter(new TokenAutoFilter(authenticationManager(), redisUtil)).httpBasic();

    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    //??????userDetailsService???????????????
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderPlus);
    }
    //?????????????????????????????????????????????
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**");
    }
}
