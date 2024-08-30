package com.devkduck.duckshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DuckshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuckshopApplication.class, args);
	}

	@GetMapping("/test")
	public UserDto test(){
		UserDto duck = new UserDto();
		duck.setAge(30);
		duck.setName("DevKducK");
		return duck;
	}

}
