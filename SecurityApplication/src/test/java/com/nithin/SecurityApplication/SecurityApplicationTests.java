package com.nithin.SecurityApplication;

import com.nithin.SecurityApplication.entities.User;
import com.nithin.SecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user = new User(1L,"user1@gmail.com","0987");
		String token = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getId(token);
		System.out.println(id);
	}

}
