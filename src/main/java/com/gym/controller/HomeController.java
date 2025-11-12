package com.gym.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

// [251105] 일반 테스트용 홈 컨트롤러
// 목적 : 젠킨스와 정상 연동되는지 확인하는 용도
// @RestController
@Controller
@Slf4j
public class HomeController {
	
	// @GetMapping({"/", "/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
	@GetMapping({"/", "/board/**", "/facilities/**", "/contents/**", "/login", "/join", "/mypage", "/cms/login",
		"/cms", "/cms/home", "/cms/user", "/cms/facility", "/cms/reservation", "/cms/contents/**", "/cms/stats/**",
		"/upload_images/**"})
	public String home() {
		
		return "index"; // 메인 홈페이지 이동
	}
	
	/*
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', '책임자', 'admin')") // 관리자 권한만 접근 가능
	@GetMapping({"/cms", "/cms/home", "/cms/user", "/cms/facility", "/cms/reservation"})
	public String cms(Authentication auth) {
		
		log.info("auth : " + auth);
		
		return "index";
		//String movePath = "/cms/login";
		// 관리자로 로그인 되었으면 본화면으로 진입 그렇지 않으면 로그인 화면으로 진입
		//return "redirect:/" + movePath; // 관리자 로그인 페이지로 이동
	}
	*/
	// 푸시가 안되고 있음, non-fast-forward 상태...이유는? 
	
}
