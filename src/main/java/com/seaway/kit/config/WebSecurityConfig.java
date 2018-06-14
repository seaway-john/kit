package com.seaway.kit.config;

import com.seaway.kit.config.jwt.JwtAuthenticationFilter;
import com.seaway.kit.config.jwt.JwtLoginFilter;
import com.seaway.kit.service.MongoUserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MongoUserDetailService mongoUserDetailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(MongoUserDetailService mongoUserDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mongoUserDetailService = mongoUserDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mongoUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }

    /*
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/kit/**");
    }
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
//                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll()
//                .and().logout().permitAll()
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

}
