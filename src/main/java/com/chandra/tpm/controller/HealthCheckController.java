package com.chandra.tpm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
	@Value("${server.port}")
	private String serverPort;

	@GetMapping("/health")
	public ResponseEntity<?> checkTpmHealth() {
		return new ResponseEntity("SpringBoot Server up on port = "+serverPort,HttpStatus.OK);
	}
}
