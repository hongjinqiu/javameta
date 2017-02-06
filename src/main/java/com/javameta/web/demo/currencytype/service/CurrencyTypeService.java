package com.javameta.web.demo.currencytype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.demo.currencytype.dao.CurrencyTypeDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class CurrencyTypeService extends AFormService {
	@Autowired
	private CurrencyTypeDao currencyTypeDao;

	public CurrencyTypeDao getCurrencyTypeDao() {
		return currencyTypeDao;
	}

	public void setCurrencyTypeDao(CurrencyTypeDao currencyTypeDao) {
		this.currencyTypeDao = currencyTypeDao;
	}
}
