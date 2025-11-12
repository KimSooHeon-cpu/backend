package com.gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	// 251112 추가 (CORS 대응)
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//
//		registry.addMapping("/**")
//	          .allowedOriginPatterns("*")
//	          .allowedMethods("*")
//	          .allowedHeaders("*")
//	          .allowCredentials(true);
//	}

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
		
		// React => Backend "/api/api/**"
		registry.addResourceHandler("/api/api/**")
	    		.addResourceLocations("classpath:/static/");

		// images
		registry.addResourceHandler("/images/**")
			.addResourceLocations("classpath:/static/images/");
		
		// 파일업로드 이미지 : update_images
		registry.addResourceHandler("/update_images/**")
			.addResourceLocations("/usr/local/tomcat/webapps/gym_reservation_files/");
	}

	// React URL 라우팅을 Spring Boot에서 처리하도록 설정 : 251110
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		// WebMvcConfigurer.super.addViewControllers(registry);
		
		//registry.addViewController("/")
        //		.setViewName("forward:/index.html");

		// Map "/word", "/word/word", and "/word/word/word" - except for anything starting with "/api/..." or ending with
		// a file extension like ".js" - to index.html. By doing this, the client receives and routes the url. It also
		// allows client-side URLs to be bookmarked.
		
		// Single directory level - no need to exclude "api"
		//registry.addViewController("/{x:[\\w\\-]+}")
		//        .setViewName("forward:/index.html");
		
		// Multi-level directory path, need to exclude "api" on the first part of the path
		//registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}")
		//       .setViewName("forward:/index.html");
	}
	
	
}