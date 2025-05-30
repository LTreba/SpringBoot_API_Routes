package com.racetracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/routes").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return registrationBean;
    }
}
