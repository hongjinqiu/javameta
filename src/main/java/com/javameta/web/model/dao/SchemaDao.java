package com.javameta.web.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.javameta.web.support.DaoSupport;

@Scope("prototype")
@Component
public class SchemaDao extends DaoSupport {
	public Map<String, Object> getObject() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from test ");
		sb.append(" where 1=1 ");
		sb.append(" limit 1 ");
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sb.toString());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public void insert1() {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into test values(:id, :name) ");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 0);
		paramMap.put("name", "写入1");

		this.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
	}
	
	public void insert2() {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into test values(:id, :name) ");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 0);
		paramMap.put("name", "写入2");

		this.getNamedParameterJdbcTemplate().update(sb.toString(), paramMap);
	}
	
}
