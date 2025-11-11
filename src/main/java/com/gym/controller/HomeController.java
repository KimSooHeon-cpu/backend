package com.gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// [251105] 일반 테스트용 홈 컨트롤러
// 목적 : 젠킨스와 정상 연동되는지 확인하는 용도
// @RestController
@Controller
public class HomeController {
	
	// @GetMapping({"/", "/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
	@GetMapping({"/", "/board/**", "/facilities/**", "/contents/**", "/login", "/join", "/mypage" })
	public String home() {
		
		return "index"; // 메인 홈페이지 이동
	}
	
	@GetMapping({"/cms", "/cms/home" })
	public String cms() {
		
		return "redirect:/cms/login"; // 관리자 로그인 페이지로 이동
	}
	
	// 푸시가 안되고 있음, non-fast-forward 상태...이유는? 
	
}
