package com.javameta.web.support;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public abstract class DaoSupport {
	protected Logger logger = Logger.getLogger(getClass().getName());
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
//		jdbcTemplate.query
		return jdbcTemplate;
	}
	
	public <T> T test(T a) {
		return a;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
//		namedParameterJdbcTemplate.queryForInt(sql, paramMap)
//		namedParameterJdbcTemplate.queryForLong(sql, paramMap)
//		namedParameterJdbcTemplate.queryForObject(sql, paramMap, requiredType)
//		namedParameterJdbcTemplate.queryForMap(sql, paramMap)
//		namedParameterJdbcTemplate.queryForList(sql, paramMap)
//		namedParameterJdbcTemplate.queryForList(sql, paramMap, elementType)
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

}
