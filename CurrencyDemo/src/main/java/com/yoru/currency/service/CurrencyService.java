package com.yoru.currency.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yoru.currency.dao.CurrencyDao;
import com.yoru.currency.entity.Currency;


@Service
public class CurrencyService {
	@Autowired
	CurrencyDao dao;
	
	public List<Currency> fetchCurrency(){
		return (List<Currency>) dao.findAll();
	}
	
	public String saveCurrency(Currency currency) {
		return dao.save(currency).getCode();
	}
	
	public Boolean updCurrency(Currency currency, String code) {
		Optional<Currency> curr = dao.findById(code);
		if(!curr.isPresent()) {
			return false;
		}
		Currency updateCurr = curr.get();
		if(!StringUtils.isEmpty(currency.getName())) {
			updateCurr.setName(currency.getName());
		}
		updateCurr.setRate(currency.getRate());
		dao.save(updateCurr);
		return true;
	}
	
	public Boolean delCurrency(String code) {
		if(!dao.findById(code).isPresent()) {
			return false;
		}
		dao.deleteById(code);
		return true;
	}
}
