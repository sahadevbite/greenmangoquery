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
package org.greenmongoquery.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JTextArea;

import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.TableHorizontal;
import org.greenmongoquery.ui.model.Cache;
import org.greenmongoquery.ui.model.MongoCollectionModel;
import org.greenmongoquery.util.LogHandler;

import com.mongodb.Mongo;

public class QueryCommand implements ActionListener {
	private static Logger logger = Logger.getLogger(QueryCommand.class.getName());
	static
	{
		LogHandler.setup(logger);
	}
	   private String db;
	   private String collection;
	   private Mongo mongo;
	   private TableHorizontal tableUpdate;
	   private JTextArea queryResult;
	   private JTextArea jsonResult;

	    public void setJsonResult(JTextArea jsonResult) {
	        this.jsonResult = jsonResult;
	    }

	    public void setQueryResult(JTextArea queryResult) {
	        this.queryResult = queryResult;
	    }
	   
	   private MongoService mongoService = new MongoServiceImpl();
	   private String query="db.too.find()";

	    public void setQuery(String query) {
	        this.query = query;
	    }
	   

	    public void setTableUpdate(TableHorizontal tableUpdate) {
	        this.tableUpdate = tableUpdate;
	    }

	    public void setCollection(String collection) {
	        this.collection = collection;
	    }

	    public void setDb(String db) {
	        this.db = db;
	    }

	    public void setMongo(Mongo mongo) {
	        this.mongo = mongo;
	    }

	    
	     public QueryCommand()
	     {
	         
	     }
	     
	    @Override
	    public void actionPerformed(ActionEvent ae) {
	      logger.info("query performed " + queryResult.getText());
	        query = queryResult.getText();
	        MongoCollectionModel model =  mongoService.getDocumentsByQuery(mongo, db, collection, query);
	        
	       tableUpdate.setupData(model.getResult());
	       jsonResult.setText(model.getBsonData().toString());
	      
	       
	           
	    }
	    
	}
