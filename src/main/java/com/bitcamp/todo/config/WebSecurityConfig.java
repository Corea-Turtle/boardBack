package com.bitcamp.todo.config;

import com.bitcamp.todo.security.JwtAuthaticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthaticationFilter jwtAuthaticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //jwt filter 등록
        //매 요청 마다 jwtAuthenticationFilter 실행
        http.addFilterAfter(jwtAuthaticationFilter, CorsFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/","/auth/**");
    }

}
