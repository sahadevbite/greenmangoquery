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
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.greenmongoquery.GreenMangoQuery;
import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.impl.UiServiceImpl;
import org.greenmongoquery.ui.model.Connection;
import org.greenmongoquery.ui.model.DbModel;
import org.greenmongoquery.util.LogHandler;

import com.mongodb.Mongo;

public class RefreshCommand implements ActionListener {
	private static Logger logger = Logger.getLogger(RefreshCommand.class.getName());
	static
	{
		LogHandler.setup(logger);
	}
	private static DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	public TreePath path;
	public MutableTreeNode mNode;
	private MongoService mongoService = new MongoServiceImpl();
	private UiServiceImpl uiService;
	private Mongo mongo;
	private Connection connection;
	private JTree tree;
	private List<DbModel> dbs;
	private GreenMangoQuery parent;

	public RefreshCommand() {

	}

	public RefreshCommand(UiServiceImpl uiService, JTree tree, TreePath path,
			MutableTreeNode mNode, DefaultTreeModel model,
			DefaultMutableTreeNode root, Mongo mongo, Connection connection, GreenMangoQuery parent) {
		this.uiService = uiService;
		this.tree = tree;
		this.path = path;
		this.mNode = mNode;
		this.model = model;
		this.root = root;
		this.dbs = dbs;
		this.connection = connection;
		this.mongo = mongo;
		this.parent = parent;
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		logger.info("refresh");
		
		List<DbModel> dbmodels = mongoService.getDbAndCollections(mongo);
		
		tree = uiService.updateDatabaseNode(tree, path, mNode, model, root,
				dbmodels, connection.getConnectionName(), parent);

	}

}
