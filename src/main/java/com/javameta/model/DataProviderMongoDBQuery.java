package com.javameta.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.javameta.JavametaException;
import com.javameta.model.queryparameter.mongodb.QueryParameterBuilder;
import com.javameta.model.template.DataProvider;
import com.javameta.model.template.FormTemplate;
import com.javameta.model.template.QueryParameters;
import com.javameta.model.template.QueryParameters.QueryParameter;
import com.javameta.util.New;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DataProviderMongoDBQuery {
	private Logger logger = Logger.getLogger(this.getClass());
	
	public long queryForCount(MongoDatabase mongoDatabase, String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		Map<String, Object> queryMap = getQuerySql(formTemplateId, dataProviderName, paramMap);
		String collection = (String)queryMap.get("collection");
		DBObject query = (DBObject)queryMap.get("query");
		
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		if (query != null) {
			long count = mongoCollection.count((BasicDBObject)query);
			return count;
		} else {
			long count =  mongoCollection.count();
			return count;
		}
	}
	
	public Map<String, Object> queryForMap(MongoDatabase mongoDatabase, String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		Map<String, Object> queryMap = getQuerySql(formTemplateId, dataProviderName, paramMap);
		String collection = (String)queryMap.get("collection");
		DBObject query = (DBObject)queryMap.get("query");
		
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		if (query != null) {
			MongoCursor<Document> cursor =  mongoCollection.find((BasicDBObject)query).limit(1).iterator();
			try {
				if (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					return map;
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		} else {
			MongoCursor<Document> cursor =  mongoCollection.find().limit(1).iterator();
			try {
				if (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					return map;
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		return null;
	}
	
	public List<Map<String, Object>> queryForList(MongoDatabase mongoDatabase, String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		Map<String, Object> queryMap = getQuerySql(formTemplateId, dataProviderName, paramMap);
		String collection = (String)queryMap.get("collection");
		DBObject query = (DBObject)queryMap.get("query");
		
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		if (query != null) {
			MongoCursor<Document> cursor =  mongoCollection.find((BasicDBObject)query).iterator();
			try {
				List<Map<String, Object>> result = New.arrayList();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					result.add(map);
				}
				return result;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		} else {
			MongoCursor<Document> cursor =  mongoCollection.find().iterator();
			try {
				List<Map<String, Object>> result = New.arrayList();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					result.add(map);
				}
				return result;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
	}
	
	public List<Map<String, Object>> queryForList(MongoDatabase mongoDatabase, String formTemplateId, String dataProviderName, Map<String, Object> paramMap, int pageNo, int pageSize) {
		Map<String, Object> queryMap = getQuerySql(formTemplateId, dataProviderName, paramMap);
		String collection = (String)queryMap.get("collection");
		DBObject query = (DBObject)queryMap.get("query");
		
		int skip = (pageNo - 1) * pageSize;
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		if (query != null) {
			MongoCursor<Document> cursor =  mongoCollection.find((BasicDBObject)query).skip(skip).limit(pageSize).iterator();
			try {
				List<Map<String, Object>> result = New.arrayList();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					result.add(map);
				}
				return result;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		} else {
			MongoCursor<Document> cursor =  mongoCollection.find().skip(skip).limit(pageSize).iterator();
			try {
				List<Map<String, Object>> result = New.arrayList();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					Map<String, Object> map = New.hashMap();
					map.putAll(document);
					result.add(map);
				}
				return result;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
	}
	
	private Map<String, Object> getQuerySql(String formTemplateId, String dataProviderName, Map<String, Object> paramMap) {
		Map<String, Object> result = New.hashMap();
		FormTemplateFactory formTemplateFactory = new FormTemplateFactory();
		FormTemplate formTemplate = formTemplateFactory.getFormTemplate(formTemplateId, FormTemplateEnum.QUERY);
		DataProvider dataProvider = formTemplate.getDataProvider(dataProviderName);

		if (dataProvider == null) {
			throw new JavametaException("formTemplateId:" + formTemplateId + " can't found dataProvider:" + dataProviderName);
		}

		String bodySql = dataProvider.getSql().trim();
		Map<String, Object> nameParameterMap = New.hashMap();
		nameParameterMap.putAll(paramMap);
		DBObject query = null;
		if (dataProvider.getQueryParameters() != null) {
			query = recursiveGetQuerySql(dataProvider.getQueryParameters(), paramMap, nameParameterMap);
			logger.info("mongoDB query is:");
			logger.info(query);
		}

		result.put("collection", bodySql);
		result.put("query", query);
		return result;
	}

	private DBObject recursiveGetQuerySql(QueryParameters queryParameters, Map<String, Object> paramMap, Map<String, Object> nameParameterMap) {
		BasicDBList sqlLi = new BasicDBList();
		QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder();
		for (QueryParameter queryParameter : queryParameters.getQueryParameter()) {
			if (StringUtils.isEmpty(queryParameter.getFieldType())) {
				throw new JavametaException("queryParameter.name:" + queryParameter.getName() + ",columnName:" + queryParameter.getColumnName() + " fieldType不能为空!");
			}
			if (StringUtils.isNotEmpty(queryParameter.getRestriction())) {
				String value = ObjectUtils.toString(paramMap.get(queryParameter.getName()));
				DBObject queryParameterSql = queryParameterBuilder.buildQuery(queryParameter, value, nameParameterMap);
				
				if (StringUtils.isEmpty(queryParameter.getUseIn()) || !queryParameter.getUseIn().equals("none")) {
					if (queryParameterSql != null) {
						sqlLi.add(queryParameterSql);
					}
				}
			}
		}
		for (QueryParameters subQueryParameters: queryParameters.getSubQueryParameters()) {
			DBObject subQuerySql = recursiveGetQuerySql(subQueryParameters, paramMap, nameParameterMap);
			if (subQuerySql != null) {
				sqlLi.add(subQuerySql);
			}
		}
		if (sqlLi.size() > 0) {
			if (StringUtils.isEmpty(queryParameters.getRestriction()) || queryParameters.getRestriction().equals("and")) {
				DBObject dbObject = new BasicDBObject();
				dbObject.put("$and", sqlLi);
				return dbObject;
			} else {
				DBObject dbObject = new BasicDBObject();
				dbObject.put("$or", sqlLi);
				return dbObject;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("aftermarket2");
		
		if (true) {
			DataProviderMongoDBQuery dataProviderMongoDBQuery = new DataProviderMongoDBQuery();
			/*{
				Map<String, Object> paramMap = New.hashMap();
				paramMap.put("id", "1,3,5");
				for (int i = 2; i < 20; i++) {
					paramMap.put("id" + i, "1");
				}
				paramMap.put("id3", "a,b,c");
				Map<String, Object> result = dataProviderMongoDBQuery.queryForMap(mongoDatabase, "GatheringBill", "subTest2", paramMap);
				if (result != null) {
					System.out.println(result.size());
					System.out.println(result);
				}
			}*/
			{
				Map<String, Object> paramMap = New.hashMap();
				paramMap.put("id", "1,554");
				List<Map<String, Object>> result = dataProviderMongoDBQuery.queryForList(mongoDatabase, "GatheringBill", "subTest2", paramMap, 1, 10);
				if (result != null) {
					System.out.println(result.size());
					System.out.println(result);
				}
			}
			return;
		}
		
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("BankAccount");
//		Bson bson = Filters.eq("_id", 1);
//		Filters.and(filters);
//		BasicDBList list;
		
//		FindIterable<Document> iterate = mongoCollection.find(bson);
		BasicDBObject basicDBObject = new BasicDBObject();
//		basicDBObject.put("_id", 1);
		{
			BasicDBList list = new BasicDBList();
			{
				BasicDBObject basicDBObject2 = new BasicDBObject();
//			basicDBObject2.put("$ne", 1);
				// $eq 会报错,只能直接赋值,
				basicDBObject2.put("$regex", "[^a]+");
				
				BasicDBObject basicDBObject3 = new BasicDBObject();
				basicDBObject3.put("A.name", basicDBObject2);
				
//				list.add(basicDBObject3);
			}
			{
				BasicDBObject basicDBObject2 = new BasicDBObject();
//			basicDBObject2.put("$ne", 1);
				// $eq 会报错,只能直接赋值,
				basicDBObject2.put("_id", 1);
				
				list.add(basicDBObject2);
			}
			basicDBObject.put("$and", list);
//			basicDBObject.put("name", basicDBObject2);
			
			
			
			System.out.println(basicDBObject);
			System.out.println(Filters.eq("_id", 1));
			System.out.println(Filters.regex("name", "aaaa"));
		}
		FindIterable<Document> iterate = mongoCollection.find(basicDBObject);
		
		MongoCursor<Document> cursor = iterate.iterator();
		while (cursor.hasNext()) {
			Document document = cursor.next();
			System.out.println(document);
			Map<String, Object> main = (Map<String, Object>)document.get("A");
			System.out.println(main);
			System.out.println(main.get("name"));
			List<Map<String, Object>> detailB = (List<Map<String, Object>>)document.get("B");
			System.out.println(detailB);
			for (Map<String, Object> item: detailB) {
				System.out.println(item);
			}
//			document.get("A");
		}
		cursor.close();
		
		mongoClient.close();
	}
}
