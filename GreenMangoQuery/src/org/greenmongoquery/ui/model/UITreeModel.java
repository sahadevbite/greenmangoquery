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
package org.greenmongoquery.ui.model;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.greenmongoquery.GreenMangoQuery;
import org.greenmongoquery.ui.TableHorizontal;
import org.greenmongoquery.ui.event.CommandActionListner;
import org.greenmongoquery.ui.event.QueryCommand;

import com.mongodb.Mongo;

public class UITreeModel {
	private String activeConnection;
	private Cache cache;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	private TreePath path;
	public MutableTreeNode mNode;
	private JButton refresh;
	private CommandActionListner commandListener;
	private Mongo mongo;
	private TableHorizontal tableUpdate;
	private JTextArea jsonResult;
	private JTextArea queryResult;
	private QueryCommand queryCommand;
	private  JTree tree;
	private JButton theButton;
	private GreenMangoQuery parent;
	
	public GreenMangoQuery getParent() {
		return parent;
	}
	public void setParent(GreenMangoQuery parent) {
		this.parent = parent;
	}
	public JTree getTree() {
		return tree;
	}
	public void setTree(JTree tree) {
		this.tree = tree;
	}
	public String getActiveConnection() {
		return activeConnection;
	}
	public void setActiveConnection(String activeConnection) {
		this.activeConnection = activeConnection;
	}
	public Cache getCache() {
		return cache;
	}
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	public DefaultTreeModel getModel() {
		return model;
	}
	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}
	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	public void setRoot(DefaultMutableTreeNode root) {
		this.root = root;
	}
	public TreePath getPath() {
		return path;
	}
	public void setPath(TreePath path) {
		this.path = path;
	}
	public MutableTreeNode getmNode() {
		return mNode;
	}
	public void setmNode(MutableTreeNode mNode) {
		this.mNode = mNode;
	}
	public JButton getRefresh() {
		return refresh;
	}
	public void setRefresh(JButton refresh) {
		this.refresh = refresh;
	}
	public CommandActionListner getCommandListener() {
		return commandListener;
	}
	public void setCommandListener(CommandActionListner commandListener) {
		this.commandListener = commandListener;
	}
	public Mongo getMongo() {
		return mongo;
	}
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}
	public TableHorizontal getTableUpdate() {
		return tableUpdate;
	}
	public void setTableUpdate(TableHorizontal tableUpdate) {
		this.tableUpdate = tableUpdate;
	}
	public JTextArea getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(JTextArea jsonResult) {
		this.jsonResult = jsonResult;
	}
	public JTextArea getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(JTextArea queryResult) {
		this.queryResult = queryResult;
	}
	public QueryCommand getQueryCommand() {
		return queryCommand;
	}
	public void setQueryCommand(QueryCommand queryCommand) {
		this.queryCommand = queryCommand;
	}
	public JButton getTheButton() {
		return theButton;
	}
	public void setTheButton(JButton theButton) {
		this.theButton = theButton;
	}
	public UITreeModel(String activeConnection, Cache cache,
			DefaultTreeModel model, DefaultMutableTreeNode root, TreePath path,
			MutableTreeNode mNode, JButton refresh,
			CommandActionListner commandListener, Mongo mongo,
			TableHorizontal tableUpdate, JTextArea jsonResult,
			JTextArea queryResult, QueryCommand queryCommand, JTree tree,
			JButton theButton, GreenMangoQuery parent) {
		super();
		this.activeConnection = activeConnection;
		this.cache = cache;
		this.model = model;
		this.root = root;
		this.path = path;
		this.mNode = mNode;
		this.refresh = refresh;
		this.commandListener = commandListener;
		this.mongo = mongo;
		this.tableUpdate = tableUpdate;
		this.jsonResult = jsonResult;
		this.queryResult = queryResult;
		this.queryCommand = queryCommand;
		this.tree = tree;
		this.theButton = theButton;
		this.parent = parent;
	}
	
	
	
}
