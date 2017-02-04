package com.javameta.web.demo.sysuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.demo.sysuser.dao.SysUserDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class SysUserService extends AFormService {
	@Autowired
	private SysUserDao sysUserDao;

	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
}
