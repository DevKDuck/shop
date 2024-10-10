package com.devkduck.duckshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("${uploadPath}") // app.prop 에 설정한 upload 프로퍼티 값
//    String uploadPath;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/images/**") //url에 images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일 읽어오도록 설정
//                .addResourceLocations(uploadPath); //로컬에 저장된 파일 일어올 경로설정
//    }
}
