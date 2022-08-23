package com.example.springbootquant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.springbootquant.mapper")
public class SpringBootQuantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootQuantApplication.class, args);
	}

}
