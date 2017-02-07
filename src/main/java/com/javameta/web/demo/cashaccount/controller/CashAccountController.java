package com.javameta.web.demo.cashaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.cashaccount.service.CashAccountService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/cashAccount")
public class CashAccountController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private CashAccountService cashAccountService;

	@Override
	public AFormService getService() {
		return cashAccountService;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

}
