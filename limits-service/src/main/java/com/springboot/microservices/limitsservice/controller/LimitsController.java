package com.springboot.microservices.limitsservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservices.limitsservice.bean.Limits;
import com.springboot.microservices.limitsservice.config.Configuration;

@RestController
public class LimitsController {

	@Autowired
	Configuration configuration;
	
	@GetMapping("/limits")
	public Limits retrieveLimits() {
		//return new Limits(1, 1000);
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
	}
}
