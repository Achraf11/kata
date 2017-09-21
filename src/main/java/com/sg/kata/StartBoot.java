package com.sg.kata;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class StartBoot {

	public static void main(String[] args) {
		log.info("Start application ...");
		SpringApplication.run(StartBoot.class, args);
	}

}
