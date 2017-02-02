package com.javameta.web.demo.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.demo.bank.dao.BankDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class BankService extends AFormService {
	@Autowired
	private BankDao bankDao;

	public BankDao getBankDao() {
		return bankDao;
	}

	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}
}
