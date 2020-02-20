package com.epam.bankproject.bankproject.config;

import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Locale;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/user/accounts").hasAuthority("ROLE_USER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/css/**","/js/**","/images/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login/error")
                .successHandler(customAuthenticationSuccessHandler);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
