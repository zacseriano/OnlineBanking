package com.zacseriano.onlinebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.annotations.Api;

@SpringBootApplication
@Api(value="Online Banking API")
public class OnlinebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinebankingApplication.class, args);
	}

}
