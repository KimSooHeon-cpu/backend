package com.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	// 정적 자원(css, js, image 등) 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// CSS 경로 등록
		registry.addResourceHandler("/css/**")
			    .addResourceLocations("classpath:/static/css/");
		
		// React CSS/JS 경로 등록 "/assets/"
		registry.addResourceHandler("/assets/**")
	    		.addResourceLocations("classpath:/static/assets/");
		
		// static (정적) 경로 등록
		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/");
				
		// 모든 정적 자원(js, css, image 등) 경로 등록
		registry.addResourceHandler("/**")
	    		.addResourceLocations("classpath:/static/");
	}
}