package com.javameta.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.javameta.model.template.Security;
import com.javameta.web.support.DaoSupport;

@Scope("prototype")
@Component
public class FormTemplateDao extends DaoSupport {
	/**
	 * 取得查询权限
	 * @param security
	 * @return
	 */
	public String getSecurity(Security security) {
		return "";
	}
}
