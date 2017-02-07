package com.javameta.web.demo.cashaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.demo.cashaccount.dao.CashAccountDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class CashAccountService extends AFormService {
	@Autowired
	private CashAccountDao cashAccountDao;

	public CashAccountDao getCashAccountDao() {
		return cashAccountDao;
	}

	public void setCashAccountDao(CashAccountDao cashAccountDao) {
		this.cashAccountDao = cashAccountDao;
	}
}
