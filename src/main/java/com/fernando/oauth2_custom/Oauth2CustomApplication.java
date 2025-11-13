package com.fernando.oauth2_custom;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class Oauth2CustomApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2CustomApplication.class, args);
	}

}
