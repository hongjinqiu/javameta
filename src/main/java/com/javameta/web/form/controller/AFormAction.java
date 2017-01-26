package com.javameta.web.form.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.javameta.web.support.ControllerSupport;

@Scope("prototype")
@Controller
public abstract class AFormAction extends ControllerSupport {
	private static final long serialVersionUID = 1L;

}
