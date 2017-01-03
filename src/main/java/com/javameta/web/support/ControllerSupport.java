package com.javameta.web.support;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public abstract class ControllerSupport implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(getClass().getName());
}
