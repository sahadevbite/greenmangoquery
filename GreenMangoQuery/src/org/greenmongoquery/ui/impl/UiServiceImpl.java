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
package org.greenmongoquery.ui.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.greenmongoquery.GreenMangoQuery;
import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.About;
import org.greenmongoquery.ui.CreateConnection;
import org.greenmongoquery.ui.TableHorizontal;
import org.greenmongoquery.ui.event.CommandActionListner;
import org.greenmongoquery.ui.event.RefreshCommand;
import org.greenmongoquery.ui.model.Connection;
import org.greenmongoquery.ui.model.ConnectionModel;
import org.greenmongoquery.ui.model.DbModel;
import org.greenmongoquery.ui.model.MongoCollectionModel;
import org.greenmongoquery.ui.model.UITreeModel;
import org.greenmongoquery.util.LogHandler;
import org.greenmongoquery.util.Util;

import com.mongodb.Mongo;

public class UiServiceImpl {
	static Logger logger = Logger.getLogger(About.class.getName());
	static {
		LogHandler.setup(logger);
	}
	private MongoService mongoService = new MongoServiceImpl();
	private GreenMangoQuery parent;
	private UITreeModel treeModel;
	private UiServiceImpl uiservice;
	private String editConnection;

	public UITreeModel getTreeModel() {
		return treeModel;
	}

	public String getEditConnection() {
		return editConnection;
	}

	public void setEditConnection(String editConnection) {
		this.editConnection = editConnection;
	}

	public void setTreeModel(UITreeModel treeModel) {
		this.treeModel = treeModel;
	}

	private final static Logger LOGGER = Logger.getLogger(UiServiceImpl.class
			.getName());
	static {
		LogHandler.setup(LOGGER);
	}

	private String getJarFolder() {
		String s = Util.getRootPath();
		LOGGER.info("Jar File Location :" + s);
		return s;
	}

	public Connection geConnectionByName(String connectionName) {
		String path = getJarFolder() + "/" + connectionName;

		logger.info("connection path  to load" + path);
		boolean exists = isExistConnection(getJarFolder(), connectionName);
		Connection connection = null;

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path);

			ois = new ObjectInputStream(fis);
			try {
				connection = (Connection) ois.readObject();

			} catch (ClassNotFoundException ex) {
				logger.log(Level.SEVERE, null, ex);
			}

		} catch (IOException e) {
			logger.log(Level.SEVERE, null, e);
		}
		return connection;
	}

	private static boolean isExistConnection(String path, String connection) {
		File directory = new File(path);
		boolean found = false;
		if (directory.listFiles() != null) {
			for (File f : directory.listFiles()) {
				if (f.getName().equals(connection)) {
					found = true;
					break;
				}
			}
		}
		return found;

	}

	public JTree updateDatabaseNode(JTree tree, TreePath path,
			MutableTreeNode mNode, DefaultTreeModel model,
			DefaultMutableTreeNode root, List<DbModel> dbs, String connection,
			GreenMangoQuery parent) {
		this.parent = parent;

		path = tree.getNextMatch(connection, 0, Position.Bias.Forward);

		root = (DefaultMutableTreeNode) path.getLastPathComponent();

		for (DbModel db : dbs) {
			boolean found = false;
			for (Enumeration e = root.children(); e.hasMoreElements();) {
				String existing = e.nextElement().toString();

				if (existing != null
						&& existing.equalsIgnoreCase(db.getDatabase())) {
					found = true;
				}
			}

			if (!found) {
				DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(
						db.getDatabase());
				model.insertNodeInto(mtn, root, root.getChildCount());
				for (String child : db.getCollections()) {
					model.insertNodeInto(new DefaultMutableTreeNode(child),
							mtn, mtn.getChildCount());
				}

			}

		}

		tree.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {

			}
		});

		tree.expandPath(new TreePath(root.getPath()));

		return tree;

	}

	public JTable testTabledata() {
		String data[][] = { { "100", "Vinod", "programmer", "5000" },
				{ "101", "Deepak", "Content Writer", "20000" },
				{ "102", "Noor", "Techniqual Writer", "30000" },
				{ "104", "Rinku", "PHP programar", "25000" } };
		String col[] = { "Emp_Id", "Emp_name", "Emp_depart", "Emp_sal" };
		DefaultTableModel model = new DefaultTableModel(data, col);

		JTable table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.yellow);
		JScrollPane pane = new JScrollPane(table);

		return table;
	}

	public UITreeModel updateTree(UITreeModel UItreemodel) {
		LOGGER.info("started updated Tree");
		this.treeModel = UItreemodel;
		List<String> connectionList = getConnectionList();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Connection");
		treeModel.setTree(new JTree(new DefaultTreeModel(top)));
		// LOGGER.info("tree " + treeModel.getTree());
		treeModel.setModel((DefaultTreeModel) treeModel.getTree().getModel());
		treeModel.setRoot((DefaultMutableTreeNode) treeModel.getModel()
				.getRoot());

		if (connectionList != null && connectionList.size() > 0) {
			int j = 0;
			for (String connection : connectionList) {

				treeModel.getModel().insertNodeInto(
						new DefaultMutableTreeNode(connection),
						treeModel.getRoot(),
						treeModel.getRoot().getChildCount());

				j++;

			}
		}
		if (treeModel.getTree() != null
				&& treeModel.getTree().getModel() != null) {
			// model.reload();
		}

		treeModel.getTree().addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		treeModel.getTree().expandPath(
				new TreePath(treeModel.getRoot().getPath()));
		return treeModel;
	}

	private List<String> getConnectionList() {
		List<String> connectionList = new ArrayList<String>();

		String s = Util.getRootPath();
		// dataPath = s;
		File dir = new File(s);
		if (dir.listFiles() != null) {
			for (File file : dir.listFiles()) {
				connectionList.add(file.getName());
			}
		}

		return connectionList;

	}

	void doMouseClicked(MouseEvent me) {
		TreePath tp = treeModel.getTree().getPathForLocation(me.getX(),
				me.getY());
		Connection connection = null;
		if (me.getClickCount() == 2) {

			connection = geConnectionByName(tp.getPath()[1].toString());

			try {
				treeModel.setActiveConnection(connection.getConnectionName());
				treeModel.setMongo(mongoService.getConnection(
						connection.getHostName(),
						Integer.parseInt(connection.getPort()),
						connection.getUsername(), connection.getPassword()));
				treeModel.getCache().put(
						connection.getConnectionName(),
						new ConnectionModel(connection.getConnectionName(),
								treeModel.getMongo()));
				List<DbModel> dbmodels = mongoService
						.getDbAndCollections(treeModel.getMongo());

				List<String> dbnames = mongoService
						.getDbs(treeModel.getMongo());
				treeModel.setTree(updateDatabaseNode(treeModel.getTree(),
						treeModel.getPath(), treeModel.getmNode(),
						treeModel.getModel(), treeModel.getRoot(), dbmodels,
						connection.getConnectionName(), treeModel.getParent()));
				RefreshCommand command = new RefreshCommand(this,
						treeModel.getTree(), treeModel.getPath(),
						treeModel.getmNode(), treeModel.getModel(),
						treeModel.getRoot(), treeModel.getMongo(), connection,
						treeModel.getParent());

				treeModel.getRefresh().addActionListener(command);

			} catch (UnknownHostException ex) {
				logger.log(Level.SEVERE, null, ex);
			}
		}

		if (SwingUtilities.isLeftMouseButton(me)) {
			if (tp != null) {
				if (tp.getPathCount() == 4) {
					ConnectionModel connectionModel = (ConnectionModel) treeModel
							.getCache().get(treeModel.getActiveConnection());

					connectionModel.setDbName(tp.getPath()[2].toString());
					connectionModel.setCollectionName(tp.getPath()[3]
							.toString());
					logger.info("path : " + tp.getPath()[3].toString());
					treeModel.getCommandListener().setConnectionModel(
							connectionModel);
					MongoCollectionModel resultList = mongoService
							.getAllDocuments(treeModel.getMongo(),
									tp.getPath()[2].toString(),
									tp.getPath()[3].toString());
					treeModel.getTableUpdate()
							.setupData(resultList.getResult());
					treeModel.getQueryCommand().setDb(
							tp.getPath()[2].toString());
					treeModel.getQueryCommand().setQueryResult(
							treeModel.getQueryResult());
					treeModel.getQueryCommand().setJsonResult(
							treeModel.getJsonResult());

					treeModel.getQueryCommand().setCollection(
							tp.getPath()[3].toString());
					treeModel.getQueryCommand().setMongo(treeModel.getMongo());
					treeModel.getQueryCommand().setTableUpdate(
							treeModel.getTableUpdate());
					treeModel.getTheButton().addActionListener(
							treeModel.getQueryCommand());
					// resultList.
					treeModel.getJsonResult().setText(
							resultList.getBsonData().toString());
					treeModel.getQueryResult().setText(resultList.getQuery());

					if (treeModel.getCommandListener().getTable() == null) {

						treeModel.setCommandListener(new CommandActionListner(
								parent, treeModel.getCommandListener()
										.getConnectionModel(), treeModel
										.getTableUpdate(), treeModel
										.getJsonResult()));
						treeModel
								.getParent()
								.getBtnInsert()
								.addActionListener(
										treeModel.getCommandListener());
						treeModel
								.getParent()
								.getBtnDelete()
								.addActionListener(
										treeModel.getCommandListener());
						treeModel
								.getParent()
								.getBtnUpdate()
								.addActionListener(
										treeModel.getCommandListener());
					}
				}
				if (tp.getPathCount() == 2) {

					ConnectionModel connectionModel = null;

					connectionModel = (ConnectionModel) treeModel.getCache()
							.get(treeModel.getActiveConnection());

					if (connectionModel != null) {

						logger.info(connectionModel.getConnectionName());
						connectionModel = new ConnectionModel(
								connectionModel.getConnectionName(),
								treeModel.getMongo());
						treeModel.getCache().put(
								connectionModel.getConnectionName(),
								connectionModel);
						treeModel.setActiveConnection(connectionModel
								.getConnectionName());
						treeModel.getCommandListener().setConnectionModel(
								connectionModel);

						List dbs = mongoService.getDbs(connectionModel
								.getMongo());

						treeModel.getTableUpdate().setupData(dbs, "DB");

						treeModel.setCommandListener(new CommandActionListner(
								parent, treeModel.getCommandListener()
										.getConnectionModel(), treeModel
										.getTableUpdate(), treeModel
										.getJsonResult()));
						treeModel
								.getParent()
								.getBtnInsert()
								.addActionListener(
										treeModel.getCommandListener());
						treeModel
								.getParent()
								.getBtnDelete()
								.addActionListener(
										treeModel.getCommandListener());
						treeModel
								.getParent()
								.getBtnUpdate()
								.addActionListener(
										treeModel.getCommandListener());
						// }
					}

				}
				if (tp.getPathCount() == 3) {

					ConnectionModel connectionModel = null;

					connectionModel = (ConnectionModel) treeModel.getCache()
							.get(treeModel.getActiveConnection());

					treeModel.getCommandListener().setConnectionModel(
							connectionModel);
					if (connectionModel == null) {

						treeModel.getCache().put(
								connection.getConnectionName(),
								new ConnectionModel(connection
										.getConnectionName(), treeModel
										.getMongo()));
					}
					connectionModel.setDbName(tp.getPath()[2].toString());

					Set<String> dbs = mongoService.getCollections(
							connectionModel.getMongo(),
							connectionModel.getDbName());

					treeModel.getTableUpdate().setupData(dbs, "Collection");

				}
				logger.info("Connection " + tp.getPath()[1].toString());
				editConnection = tp.getPath()[1].toString();
				if (treeModel.getCommandListener().getTable() == null) {

					treeModel.setCommandListener(new CommandActionListner(
							parent, treeModel.getCommandListener()
									.getConnectionModel(), treeModel
									.getTableUpdate(), treeModel
									.getJsonResult()));
					treeModel.getParent().getBtnInsert()
							.addActionListener(treeModel.getCommandListener());
					treeModel.getParent().getBtnDelete()
							.addActionListener(treeModel.getCommandListener());
					treeModel.getParent().getBtnUpdate()
							.addActionListener(treeModel.getCommandListener());
				} else {

					treeModel.getCommandListener().setTable(
							treeModel.getTableUpdate());
					// logger.info(treeModel.getJsonResult().toString());
					for (ActionListener al : treeModel.getParent()
							.getBtnInsert().getActionListeners()) {
						treeModel.getParent().getBtnInsert()
								.removeActionListener(al);

					}
					for (ActionListener al : treeModel.getParent()
							.getBtnUpdate().getActionListeners()) {
						treeModel.getParent().getBtnUpdate()
								.removeActionListener(al);

					}
					for (ActionListener al : treeModel.getParent()
							.getBtnDelete().getActionListeners()) {
						treeModel.getParent().getBtnDelete()
								.removeActionListener(al);

					}
					// logger.info(treeModel.getTableUpdate().toString());
					treeModel.setCommandListener(new CommandActionListner(
							parent, treeModel.getCommandListener()
									.getConnectionModel(), treeModel
									.getTableUpdate(), treeModel
									.getJsonResult()));
					treeModel.getParent().getBtnInsert()
							.addActionListener(treeModel.getCommandListener());

					treeModel.getParent().getBtnDelete()
							.addActionListener(treeModel.getCommandListener());
					treeModel.getParent().getBtnUpdate()
							.addActionListener(treeModel.getCommandListener());

				}

			}
		}
		if (SwingUtilities.isRightMouseButton(me)) {

			if (tp != null) {

				contextMenu(treeModel.getTree());

				editConnection = tp.getPath()[1].toString();

			}
		}
	}

	public void updateTree(String connection, String oldConnection) {
		LOGGER.info("old connection :" + oldConnection);
		LOGGER.info("new connection :" + connection);
		// LOGGER.info(treeModel.toString());

		if (treeModel.getPath() != null) {
			treeModel.setPath(treeModel.getTree().getNextMatch(oldConnection,
					0, Position.Bias.Forward));
			treeModel.setmNode((MutableTreeNode) treeModel.getPath()
					.getLastPathComponent());
			treeModel.getModel().removeNodeFromParent(treeModel.getmNode());
			treeModel.getModel().insertNodeInto(
					new DefaultMutableTreeNode(connection),
					treeModel.getRoot(), treeModel.getRoot().getChildCount());
		} else {
			// LOGGER.info("tree " + treeModel.getTree());
			treeModel.setModel((DefaultTreeModel) treeModel.getTree()
					.getModel());
			treeModel.setRoot((DefaultMutableTreeNode) treeModel.getModel()
					.getRoot());
			treeModel.setPath(treeModel.getTree().getNextMatch(oldConnection,
					0, Position.Bias.Forward));
			treeModel.setmNode((MutableTreeNode) treeModel.getPath()
					.getLastPathComponent());
			treeModel.getModel().removeNodeFromParent(treeModel.getmNode());

			treeModel.getModel().insertNodeInto(
					new DefaultMutableTreeNode(connection),
					treeModel.getRoot(), 0);
		}

	}

	public void createConnection(String connection) {
		treeModel.getModel().insertNodeInto(
				new DefaultMutableTreeNode(connection), treeModel.getRoot(), 0);
	}

	public void deleteTree(String oldConnection) {
		logger.info("delete connection " + oldConnection);
		treeModel.setPath(treeModel.getTree().getNextMatch(oldConnection, 0,
				Position.Bias.Forward));
		treeModel.setmNode((MutableTreeNode) treeModel.getPath()
				.getLastPathComponent());
		treeModel.getModel().removeNodeFromParent(treeModel.getmNode());
	}

	private void contextMenu(Component component) {
		final JPopupMenu menu = new JPopupMenu();
		JTree tree = (JTree) component;

		JMenuItem item = new JMenuItem("Edit");

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		// System.out.println(node);

		uiservice = this;
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				JDialog dialog = new CreateConnection(parent,
						"Edit Connections ", "Test", editConnection, treeModel);
			}
		});
		menu.add(item);

		component.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}

			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		});
	}

	public void updateTable(Mongo mongo, String dbname, TableHorizontal table) {
		Set<String> dbs = mongoService.getCollections(mongo, dbname);

		table.setupData(dbs, "Collection");
	}

	public void updateTable(Mongo mongo, TableHorizontal table) {
		List<String> dbs = mongoService.getDbs(mongo);

		table.setupData(dbs, "db");
	}
}
