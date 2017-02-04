package com.javameta.web.demo.bankaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.demo.bankaccount.dao.BankAccountDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class BankAccountService extends AFormService {
	@Autowired
	private BankAccountDao bankAccountDao;

	public BankAccountDao getBankAccountDao() {
		return bankAccountDao;
	}

	public void setBankAccountDao(BankAccountDao bankAccountDao) {
		this.bankAccountDao = bankAccountDao;
	}
}
