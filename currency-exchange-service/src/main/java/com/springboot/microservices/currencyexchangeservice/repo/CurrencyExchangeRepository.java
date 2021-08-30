package com.springboot.microservices.currencyexchangeservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.microservices.currencyexchangeservice.bean.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	CurrencyExchange findByFromAndTo(String from, String to);
}
