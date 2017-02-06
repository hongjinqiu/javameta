package com.javameta.web.demo.currencytype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.currencytype.service.CurrencyTypeService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/currencyType")
public class CurrencyTypeController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private CurrencyTypeService currencyTypeService;

	@Override
	public AFormService getService() {
		return currencyTypeService;
	}

	public CurrencyTypeService getCurrencyTypeService() {
		return currencyTypeService;
	}

	public void setCurrencyTypeService(CurrencyTypeService currencyTypeService) {
		this.currencyTypeService = currencyTypeService;
	}

}
