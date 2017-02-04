package com.javameta.web.demo.bankaccount.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.javameta.web.support.DaoSupport;

@Scope("prototype")
@Component
public class BankAccountDao extends DaoSupport {

}
