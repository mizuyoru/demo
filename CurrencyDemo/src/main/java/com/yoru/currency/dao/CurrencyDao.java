package com.yoru.currency.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.yoru.currency.entity.Currency;

public interface CurrencyDao extends CrudRepository<Currency, String>{

}
