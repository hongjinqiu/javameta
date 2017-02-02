package com.javameta.web.demo.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.bank.service.BankService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/bank")
public class BankController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BankService bankService;

	@Override
	public AFormService getService() {
		return bankService;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

}
