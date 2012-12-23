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
package org.greenmongoquery.db.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.greenmongoquery.ui.model.DbModel;
import org.greenmongoquery.ui.model.MongoCollectionModel;

import com.mongodb.Mongo;

public interface MongoService {
Set<String> getCollections(Mongo mongo, String db);
    List<String> getDbs(Mongo mongo);
    public Map getDbMap(Mongo mongo);
    public Mongo getConnection(String host, int port, String username, String password)throws UnknownHostException;
    public List<DbModel> getDbAndCollections(Mongo mongo);        
    public MongoCollectionModel getAllDocuments(Mongo mongo,String db, String collction);
    public String insertDocument(String json, String  dbname,String collectionName ,Mongo mong);
    public String updateDocument(String json, String  dbname,String collectionName ,Mongo mong, String id);
    public String deleteDocument(String json, String  dbname,String collectionName ,Mongo mong, String id);
    public void insertDb(String db , Mongo mongo);
    public void updatetDb(String db ,String updateDb, Mongo mongo);
    public void deleteDb(String db , Mongo mongo);
    public void insertCollection(String db ,String collection, Mongo mongo);
    public void updateCollection(String db, String collection ,String updatecollection, Mongo mongo);
    public void deleteCollection(String db ,String collection, Mongo mongo);
    public MongoCollectionModel getDocumentsByQuery(Mongo mongo,String db, String collction,String query);
             
}
