// src/main/java/com/gym/security/TokenMaker.java
package com.gym.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

// 11월 5일 테스트 폴더로 이동
@SpringBootTest
@Log4j2
public class TokenMaker {
	
	@Test
    //public static void main(String[] args) { // main이 2개면 안됨
	public void test() { // main이 2개면 안됨
		String secret = "9PqZK5rX2tY7uAeH4mBvQ1sD8wCjR6LfT0NqU3xY"; // 서버랑 동일
		long validitySeconds = 3600L;
		String issuer = "gym-reservation";

        JwtTokenProvider provider = new JwtTokenProvider(secret, validitySeconds, issuer);
        String token = provider.createToken("hong8", List.of("ROLE_ADMIN"));
        //System.out.println("JWT=" + token);
        log.info("JWT=" + token);
    }
}
