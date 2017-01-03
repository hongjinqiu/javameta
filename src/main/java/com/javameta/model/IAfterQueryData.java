package com.javameta.model;

import java.util.Map;

/**
 * 模板查询后,对查询出来的数据做一些自定义处理
 * @author hongjinqiu
 *
 */
public interface IAfterQueryData {
	public void execute(Map<String, Object> result);
}
