package com.javameta.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DataProviderMongoDBQuery {

	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("aftermarket2");
		
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
