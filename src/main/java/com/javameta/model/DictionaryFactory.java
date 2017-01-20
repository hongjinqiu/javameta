package com.javameta.model;

import java.util.List;
import java.util.Map;

import com.javameta.util.ApplicationContextUtil;
import com.javameta.util.New;

/**
 * 字典工厂,所谓的字典就是{
 * 		D_YESNO:[{
 * 			code:0,
 * 			name:'否'
 * 		}, {
 * 			code:1,
 * 			name:'是'
 * 		}],
 * 		D_BILLSTATUS:[{
 * 			code:0,
 * 			name:'否'
 * 		}, {
 * 			code:1,
 * 			name:'是'
 * 		}],
 * }
 * @author hongjinqiu
 *
 */
public class DictionaryFactory {

	public List<Map<String, Object>> getDictionary(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select c.* from pub_diccomm c ");
		sb.append(" left join pub_diclist l ");
		sb.append(" on c.DICLIST_ID=l.DICLIST_ID ");
		sb.append(" where 1=1 ");
		sb.append(" and l.DICCODE=? ");
		sb.append(" order by c.DISPORDER ");

		FormTemplateDao formTemplateDao = (FormTemplateDao) ApplicationContextUtil.getApplicationContext().getBean("formTemplateDao");
		List<Map<String, Object>> list = formTemplateDao.getJdbcTemplate().queryForList(sb.toString(), code);
		if (list.size() > 0) {
			return list;
		}

		return getProgramDictionary(code);
	}
	
	public List<Map<String, Object>> getProgramDictionary(String code) {
		return New.arrayList();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
