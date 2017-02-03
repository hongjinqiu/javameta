package com.javameta.model;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Component;

import com.javameta.model.datasource.Datasource;
import com.javameta.model.datasource.Field;
import com.javameta.model.datasource.MasterData;
import com.javameta.model.iterate.DatasourceIterator;
import com.javameta.model.iterate.IDatasourceFieldIterate;
import com.javameta.model.template.Security;
import com.javameta.util.New;
import com.javameta.web.support.DaoSupport;

@Scope("prototype")
@Component
public class FormTemplateDao extends DaoSupport {
	
	/**
	 * 保存数据,返回写入后id
	 * @param datasourceId
	 * @param data
	 * @return
	 */
	public int insert(Datasource datasource, MasterData masterData, final Map<String, Object> data) {
		String tableName = datasource.getCalcTableName();
		String insertSql = "insert into {tableName}({keyLi}) values ({valueLi})";
		insertSql = insertSql.replace("{tableName}", tableName);
		final List<String> keyLi = New.arrayList();
		final List<String> valueLi = New.arrayList();
		final Map<String, Object> valueDict = New.hashMap();
		DatasourceIterator.iterateField(datasource, new IDatasourceFieldIterate() {
			@Override
			public void iterate(Field field) {
				if (data.get(field.getId()) != null) {
					String fieldName = field.getCalcFieldName();
					keyLi.add(fieldName);
					valueLi.add(":" + field.getId());
					valueDict.put(field.getId(), data.get(field.getId()));
				} else {
					/*	放null,jdbc template会报异常,不知道是不是mysql的问题,但是也可以插入,但是控制台不想出现错误信息,因此,null的就暂时不赋值,直接写到sql里面去,
[org.springframework.jdbc.core.StatementCreatorUtils]Setting SQL statement parameter value: column index 6, parameter value [null], value class [null], SQL type unknown
[org.springframework.jdbc.core.StatementCreatorUtils]Could not check database or driver name
java.sql.SQLException: Parameter metadata not available for the given statement
					 */
					String fieldName = field.getCalcFieldName();
					keyLi.add(fieldName);
					valueLi.add("null");
//					valueLi.add(":" + field.getId());
//					valueDict.put(field.getId(), null);
				}
			}
		});
		// 主键放0,自动递增
		valueDict.put(masterData.getFixField().getPrimaryKey().getId(), 0);
		insertSql = insertSql.replace("{keyLi}", StringUtils.join(keyLi.toArray(), ","));
		insertSql = insertSql.replace("{valueLi}", StringUtils.join(valueLi.toArray(), ","));
		this.getNamedParameterJdbcTemplate().update(insertSql, valueDict);
		return this.getNamedParameterJdbcTemplate().queryForInt("select LAST_INSERT_ID()", new HashMap<String, Object>());
	}
	
	/**
	 * 取得查询权限,只能用threadLocal的方式来从session中读取数据
	 * @param security
	 * @return
	 */
	public String getSecurity(Security security) {
		return "";
	}
}
