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
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.About;
import org.greenmongoquery.ui.QueryDialog;
import org.greenmongoquery.ui.TableHorizontal;
import org.greenmongoquery.ui.impl.UiServiceImpl;
import org.greenmongoquery.ui.model.ActionEnum;
import org.greenmongoquery.ui.model.ConnectionModel;
import org.greenmongoquery.ui.model.IdObject;
import org.greenmongoquery.util.LogHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommandActionListner implements ActionListener {
	static Logger logger = Logger.getLogger(CommandActionListner.class
			.getName());
	static {
		LogHandler.setup(logger);
	}

	private JFrame frame;
	private TableHorizontal table;
	private ConnectionModel connectionModel;
	private JTextArea jsonText;
	private String id;
	private MongoService mongoService = new MongoServiceImpl();
	private UiServiceImpl uiservice = new UiServiceImpl();

	public TableHorizontal getTable() {
		return table;
	}

	public void setTable(TableHorizontal table) {
		this.table = table;
	}

	public JTextArea getJsonText() {
		return jsonText;
	}

	public void setJsonText(JTextArea jsonText) {
		this.jsonText = jsonText;
	}

	public MongoService getMongoService() {
		return mongoService;
	}

	public void setMongoService(MongoService mongoService) {
		this.mongoService = mongoService;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public ConnectionModel getConnectionModel() {
		return connectionModel;
	}

	public void setConnectionModel(ConnectionModel connectionModel) {
		this.connectionModel = connectionModel;
	}

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	public CommandActionListner(JFrame frame, ConnectionModel connectionModel,
			TableHorizontal table, JTextArea jsonText) {
		this.frame = frame;
		this.table = table;
		this.connectionModel = connectionModel;
		this.jsonText = jsonText;

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		ActionEnum action = null;
		String dbOrCollections = "colelction";

		if (table.getTable().getColumnModel().getColumnCount() < 1) {
		} else {
			dbOrCollections = table.getTable().getColumnModel().getColumn(0)
					.getHeaderValue().toString();
		}
		logger.info("Collection  :" + dbOrCollections);

		boolean isDborCollections = false;
		logger.info("collection name" + dbOrCollections);
		if (table.getTable().getColumnCount() == 1
				&& ((dbOrCollections.equalsIgnoreCase("db")) || (dbOrCollections
						.equalsIgnoreCase("collection")))) {
			isDborCollections = true;
		}
		if (ae.getActionCommand().equalsIgnoreCase("insert")) {
			if (!isDborCollections) {
				action = ActionEnum.INSERT;
				dailogueBox(displayRowValues(action), action);
			}

			else {

				action = ActionEnum.INSERT;
				logger.info("collection name" + dbOrCollections);

				dailogueBoxDborCollection(action, dbOrCollections,
						connectionModel.getDbName());
			}
		}
		if (ae.getActionCommand().equalsIgnoreCase("delete")) {
			if (!isDborCollections) {
				action = ActionEnum.DELETE;
				dailogueBox(displayRowValues(action), action);

			} else {
				action = ActionEnum.DELETE;

				dailogueBoxDborCollection(action, dbOrCollections,
						connectionModel.getDbName());
			}
		}
		if (ae.getActionCommand().equalsIgnoreCase("update")) {
			if (!isDborCollections) {
				action = ActionEnum.UPDATE;
				dailogueBox(displayRowValues(action), action);
			} else {

				action = ActionEnum.UPDATE;

				dailogueBoxDborCollection(action, dbOrCollections,
						connectionModel.getDbName());
			}

		}

	}

	public void dailogueBoxDborCollection(ActionEnum action,
			String dbOrCollections, String db) {

		String title = null;
		if (dbOrCollections.equalsIgnoreCase("db")) {
			title = " database name";
		} else if (dbOrCollections.equalsIgnoreCase("collection")) {
			title = " collection name";
		}

		if (action == ActionEnum.INSERT
				&& dbOrCollections.equalsIgnoreCase("db")) {
			title = "New" + title;
			String s = (String) JOptionPane.showInputDialog(frame, "Input the "
					+ title, title, JOptionPane.PLAIN_MESSAGE, null, null,
					"ham");

			mongoService.insertDb(s, connectionModel.getMongo());
			uiservice.updateTable(connectionModel.getMongo(), table);

		}
		if (action == ActionEnum.UPDATE
				&& dbOrCollections.equalsIgnoreCase("db")) {
			title = "Update" + title;
			String s = (String) JOptionPane.showInputDialog(frame, "Input the "
					+ title, title, JOptionPane.PLAIN_MESSAGE, null, null,
					"ham");

			String updateDb = displayRowValue();
			mongoService.updatetDb(updateDb, s, connectionModel.getMongo());
			uiservice.updateTable(connectionModel.getMongo(), table);

		}
		if (action == ActionEnum.DELETE
				&& dbOrCollections.equalsIgnoreCase("db")) {
			String updateDb = displayRowValue();
			title = "Delete  database " + ": " + updateDb + " ?";
			int response = JOptionPane.showConfirmDialog(frame, title,
					"Delete", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (response == 0) {
				mongoService.deleteDb(updateDb, connectionModel.getMongo());
				uiservice.updateTable(connectionModel.getMongo(), table);
			}
		}
		if (action == ActionEnum.INSERT
				&& dbOrCollections.equalsIgnoreCase("collection")) {
			title = "New" + title;
			String s = (String) JOptionPane.showInputDialog(frame, "Input the "
					+ title, title, JOptionPane.PLAIN_MESSAGE, null, null,
					"ham");
			logger.info("insert collection : " + s + " db" + db);
			mongoService.insertCollection(db, s, connectionModel.getMongo());
			// logger.info(table.toString());
			uiservice.updateTable(connectionModel.getMongo(), db, table);

		}
		if (action == ActionEnum.UPDATE
				&& dbOrCollections.equalsIgnoreCase("collection")) {
			title = "Update" + title;
			String s = (String) JOptionPane.showInputDialog(frame, "Input the "
					+ title, title, JOptionPane.PLAIN_MESSAGE, null, null,
					"ham");

			String updateCollection = displayRowValue();
			mongoService.updateCollection(db, updateCollection, s,
					connectionModel.getMongo());
			uiservice.updateTable(connectionModel.getMongo(), db, table);

		}
		if (action == ActionEnum.DELETE
				&& dbOrCollections.equalsIgnoreCase("collection")) {
			String updateDb = displayRowValue();
			title = "Delete  collection " + ": " + updateDb + " ?";
			int response = JOptionPane.showConfirmDialog(frame, title,
					"Delete", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (response == 0) {
				mongoService.deleteCollection(db, updateDb,
						connectionModel.getMongo());
				uiservice.updateTable(connectionModel.getMongo(), db, table);
			}
		}
	}

	public void dailogueBox(String query, ActionEnum action) {

		if (action == ActionEnum.INSERT) {
			logger.info("ConnectionModel" + connectionModel.toString());
			QueryDialog myDialog = new QueryDialog(frame, true, "Insert Query",
					query, connectionModel, table, jsonText, action, id);
			myDialog.setLocation(250, 100);
		}
		if (action == ActionEnum.UPDATE) {
			QueryDialog myDialog = new QueryDialog(frame, true, "Update Query",
					query, connectionModel, table, jsonText, action, id);
			myDialog.setLocation(250, 100);
		}
		if (action == ActionEnum.DELETE) {
			QueryDialog myDialog = new QueryDialog(frame, true, "Delete Query",
					query, connectionModel, table, jsonText, action, id);
			myDialog.setLocation(250, 100);
		}

		// createFrame( query);

	}

	protected void createFrame(String query) {
	}

	private String displayRowValue() {

		int rowIndex = table.getTable().getSelectedRow();
		Object o = table.getTable().getValueAt(rowIndex, 0);
		return o.toString();
	}

	private String displayRowValues(ActionEnum action) {

		int rowIndex = table.getTable().getSelectedRow();
		logger.info("row count " + rowIndex);
		Map<String, String> jsonMap = new TreeMap<String, String>();
		Map<String, String> idMap = new TreeMap<String, String>();
		int columns = table.getTable().getColumnCount();
		String result = null;
		IdObject idinner = null;
		if (rowIndex > 0) {
			for (int col = 0; col < columns; col++) {

				Object o = table.getTable().getValueAt(rowIndex, col);
				Object header = table.getTable().getColumnModel()
						.getColumn(col).getHeaderValue();
				if (action.INSERT == ActionEnum.INSERT) {
					if (!header.toString().equalsIgnoreCase("_id")) {
						jsonMap.put(header.toString(), o.toString());
					} else {

						idMap.put(header.toString(), o.toString());

						Map test = gson.fromJson(o.toString(), Map.class);
						id = (String) test.get("$oid");

					}

				}

			}
		}

	
		result = gson.toJson(jsonMap);
		

		result = result.replaceAll("\"\\{", "\\{");
		result = result.replaceAll("\\}\"", "\\}");
		
		return result;

	}
	
}
