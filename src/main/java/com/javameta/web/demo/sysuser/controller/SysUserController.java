package com.javameta.web.demo.sysuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.sysuser.service.SysUserService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public AFormService getService() {
		return sysUserService;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

}
