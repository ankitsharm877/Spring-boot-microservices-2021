package com.springboot.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.springboot.microservices.currencyexchangeservice.repo.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;

	//URI = http://localhost:8000/currency-exchange/from/USD/to/INR
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
						@PathVariable String from, 
						@PathVariable String to) {
		//CurrencyExchange currencyExchange = new CurrencyExchange(100L, from, to, BigDecimal.valueOf(50));
		String port = environment.getProperty("local.server.port");
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " to " + to);
		}
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
