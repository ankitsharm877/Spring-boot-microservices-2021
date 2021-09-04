package com.springboot.v1.currencyexchangeservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.v1.currencyexchangeservice.bean.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	CurrencyExchange findByFromAndTo(String from, String to);
}
