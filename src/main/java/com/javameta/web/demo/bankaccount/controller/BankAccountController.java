package com.javameta.web.demo.bankaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.bankaccount.service.BankAccountService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/bankAccount")
public class BankAccountController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BankAccountService bankAccountService;

	@Override
	public AFormService getService() {
		return bankAccountService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

}
