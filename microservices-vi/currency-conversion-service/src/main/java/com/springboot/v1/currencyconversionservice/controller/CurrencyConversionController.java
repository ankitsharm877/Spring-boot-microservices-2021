package com.springboot.v1.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.v1.currencyconversionservice.CurrencyExchangeProxy;
import com.springboot.v1.currencyconversionservice.bean.CurrencyConversion;
import com.springboot.v1.currencyconversionservice.bean.CurrencyExchange;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeProxy proxy;

	//http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
	
	@GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
				@PathVariable String from, @PathVariable String to,
				@PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyExchange> responseEntity = 
				new RestTemplate().getForEntity(
						"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
						CurrencyExchange.class, uriVariables);
		CurrencyExchange currencyExchange = responseEntity.getBody();
		
		return new CurrencyConversion(currencyExchange.getId(), from, to, quantity, 
				currencyExchange.getConversionMultiple(), 
				quantity.multiply(currencyExchange.getConversionMultiple()), 
				currencyExchange.getEnvironment() +" Rest Template");
	}
	
	@GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
				@PathVariable String from, @PathVariable String to,
				@PathVariable BigDecimal quantity) {
		
		CurrencyExchange currencyExchange = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyExchange.getId(), from, to, quantity, 
				currencyExchange.getConversionMultiple(), 
				quantity.multiply(currencyExchange.getConversionMultiple()), 
				currencyExchange.getEnvironment() + " Feign");
	}
}
