/**
 * 
/*
 * Copyright (c) 2012 Sony John 
Contact me @ greenmangoquery@gmail.com
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of
 *  this software and associated documentation files (the "Software"), to deal in
 *  the Software without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 


 */
package org.greenmongoquery.db.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.ui.model.Cache;
import org.greenmongoquery.ui.model.DbModel;
import org.greenmongoquery.ui.model.MongoCollectionModel;
import org.greenmongoquery.util.LogHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class MongoServiceImpl implements MongoService {
	private Logger logger = Logger.getLogger(MongoServiceImpl.class.getName());

	public MongoServiceImpl() {
		LogHandler.setup(logger);
	}

	@Override
	public Set<String> getCollections(Mongo mongo, String dbname) {
		DB db = mongo.getDB(dbname);
		return db.getCollectionNames();
	}

	@Override
	public List<String> getDbs(Mongo mongo) {
		return mongo.getDatabaseNames();

	}

	@Override
	public Map getDbMap(Mongo mongo) {
		Map dbMap = new HashMap();
		dbMap.put("DB", getDbs(mongo));
		return dbMap;

	}

	@Override
	public Mongo getConnection(String host, int port, String username,
			String password) throws UnknownHostException {
		logger.info("connect to " + host + port);
		Mongo mongo = null;

		mongo = new Mongo(host, port);
		DB admin = mongo.getDB("admin");
		if (username != null && username.length() > 0) {
			try {
				admin.authenticateCommand(username, password.toCharArray());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "", e);
			}
		}

		return mongo;
	}

	@Override
	public List<DbModel> getDbAndCollections(Mongo mongo) {
		ArrayList<DbModel> dbList = new ArrayList<DbModel>();
		List<String> dbs = getDbs(mongo);
		for (String db : dbs) {
			DbModel dbmodel = new DbModel();
			dbmodel.setDatabase(db);
			dbmodel.setCollections(getCollections(mongo, db));
			dbList.add(dbmodel);
		}
		return dbList;
	}

	@Override
	public MongoCollectionModel getAllDocuments(Mongo mongo, String dbname,
			String collectionName) {
		Gson gson = new GsonBuilder().create();
		MongoCollectionModel model = new MongoCollectionModel();
		StringBuilder bsonData = new StringBuilder();
		List<Map> result = new ArrayList<Map>();
		model.setQuery("db." + collectionName + ".find();");
		Map data = new TreeMap();
		DB db = mongo.getDB(dbname);
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find();
		String output = null;
		try {
			while (cursor.hasNext()) {
				output = cursor.next().toString();
				bsonData.append(output + "\n");
				data = gson.fromJson(output, Map.class);

				result.add(data);

			}
		} finally {
			cursor.close();
		}
		model.setBsonData(bsonData);
		model.setResult(result);
		return model;

	}

	@Override
	public String insertDocument(String json, String dbname,
			String collectionName, Mongo mongo) {
		String result = null;
		DB db = mongo.getDB(dbname);
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(json);
		coll.insert(dbObject);
		logger.info("insert object " + dbObject.toString());
		result = "insert document : _id=  " + dbObject.get("_id").toString();
		return result;

	}

	public String updateDocument(String json, String dbname,
			String collectionName, Mongo mongo, String id) {
		logger.info("start update");
		String result = null;
		DB db = mongo.getDB(dbname);
		DBObject orgDbObject = new BasicDBObject("_id", new ObjectId(id));

		// id ="509e55b844aefa5410c8e656";
		// orgDbObject.put("_id", id);
		logger.info("update id " + id);
		logger.info("Object to update" + orgDbObject.toString());

		DBCollection coll = db.getCollection(collectionName);
		DBObject found = coll.findOne(orgDbObject);
		DBObject dbObject = (DBObject) JSON.parse(json);
		coll.update(found, dbObject);
		
		result = "update document : _id=  " + id;
		return result;
	}

	public String deleteDocument(String json, String dbname,
			String collectionName, Mongo mongo, String id) {

		String result = null;
		DB db = mongo.getDB(dbname);
		DBObject orgDbObject = new BasicDBObject("_id", new ObjectId(id));
		logger.info("id " + id);
		logger.info("k object " + orgDbObject.toString());

		DBCollection coll = db.getCollection(collectionName);
		DBObject found = coll.findOne(orgDbObject);
		coll.remove(found);
		result = "delete document : _id=  " + id;
		return result;

	}

	@Override
	public void insertCollection(String db, String collection, Mongo mongo) {
		DBObject option = new BasicDBObject();
		DB dbs = mongo.getDB(db);
		dbs.createCollection(collection, option);

	}

	@Override
	public void insertDb(String db, Mongo mongo) {
		DBObject option = new BasicDBObject();

		DB dbs = mongo.getDB(db);
		dbs.createCollection("Dummmy_Collection", option);

		List<String> dbStr = getDbs(mongo);
		logger.info("Database : s" + dbStr);
		dbs.getCollection("Dummmy_Collection").drop();
		dbStr = getDbs(mongo);
		// System.out.println(dbStr);
	}

	@Override
	public void updatetDb(String db, String updateDb, Mongo mongo) {
		DB dbs = mongo.getDB("admin");
		DBObject cmd = new BasicDBObject();
		DBObject key = new BasicDBObject();
		logger.info("host " + mongo.getAddress().getHost() + " from db " + db
				+ " updte db " + updateDb);

		cmd.put("copydb", 1);

		cmd.put("fromhost", mongo.getAddress().getHost());
		cmd.put("todb", updateDb);
		cmd.put("fromdb", db);

		CommandResult result = dbs.command(cmd);

		DB dbsDop = mongo.getDB(db);
		dbsDop.dropDatabase();
		logger.info(result.getErrorMessage() + result.getException());
	}

	@Override
	public void deleteDb(String db, Mongo mongo) {
		DB dbsDop = mongo.getDB(db);
		dbsDop.dropDatabase();
	}

	@Override
	public void updateCollection(String db, String collection,
			String updatecollection, Mongo mongo) {
		DBObject option = new BasicDBObject();
		DB dbs = mongo.getDB(db);
		DBCollection coll = dbs.getCollection(collection);
		coll.rename(updatecollection, true);

	}

	@Override
	public void deleteCollection(String db, String collection, Mongo mongo) {
		DB dbs = mongo.getDB(db);
		DBCollection coll = dbs.getCollection(collection);
		coll.drop();
	}

	@Override
	public MongoCollectionModel getDocumentsByQuery(Mongo mongo, String db,
			String collection, String query) {
		Map<String, String> queryDelimit = org.greenmongoquery.util.Util
				.getQuerySeperate(query);
		collection = (String) queryDelimit.get("coll");
		query = queryDelimit.get("query");
		logger.info("Query " + query);

		Gson gson = new GsonBuilder().create();
		MongoCollectionModel model = new MongoCollectionModel();
		StringBuilder bsonData = new StringBuilder();
		List<Map> result = new ArrayList<Map>();
		DBObject object = (DBObject) JSON.parse(query);
		DB dbs = mongo.getDB(db);
		DBCollection coll = dbs.getCollection(collection);
		DBCursor cursor = coll.find(object);
		Map data = new TreeMap();
		String output = null;
		try {
			while (cursor.hasNext()) {
				output = cursor.next().toString();
				bsonData.append(output + "\n");
				data = gson.fromJson(output, Map.class);

				result.add(data);

			}
		} finally {
			cursor.close();
		}
		model.setBsonData(bsonData);
		model.setResult(result);
		return model;

	}

}
