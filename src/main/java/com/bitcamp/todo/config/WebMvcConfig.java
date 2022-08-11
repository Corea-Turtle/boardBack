package com.bitcamp.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링 빈(Bean) 으로 등록
//상속을 받아야한다
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    //WebMvcConfigure 가 갖고 있는 메소드를 오버라이드
    public void addCorsMappings(CorsRegistry registry){
        //메소드 체이닝
        registry.addMapping("/**").allowedOrigins("http://localhost:3000","http://192.168.0.208:3000")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                //유효시간
                .maxAge(MAX_AGE_SECS);
    }
}