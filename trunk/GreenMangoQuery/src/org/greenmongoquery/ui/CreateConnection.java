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
package org.greenmongoquery.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.greenmongoquery.GreenMangoQuery;

import org.greenmongoquery.ui.impl.UiServiceImpl;
import org.greenmongoquery.ui.model.Connection;
import org.greenmongoquery.ui.model.UITreeModel;
import org.greenmongoquery.util.LogHandler;
import org.greenmongoquery.util.Util;

public class CreateConnection extends JDialog implements ActionListener {
	private final static Logger LOGGER = Logger.getLogger(CreateConnection.class
			.getName());
	private static final String delete = "Delete";
	JTextField serverTextField = new JTextField("localhost", 20);
	JTextField serverHostTextField = new JTextField("127.0.0.1", 25);
	JTextField serverPortTextField = new JTextField("27017", 10);
	JPasswordField serverPasswordTextField = new JPasswordField("", 20);
	JTextField serverUserTextField = new JTextField("", 20);
	private JFrame parent;
	private GreenMangoQuery split;
	private boolean edit;
	private String oldPath;
	private UiServiceImpl uiservice = new UiServiceImpl();
	private UITreeModel model;
	private String oldConnction;

	

	public void setModel(UITreeModel model) {
		this.model = model;
	}

	public CreateConnection(JFrame parent, String title, String message,
			String connectionName, UITreeModel model) {
		super(parent, title, true);
		LogHandler.setup(LOGGER);
		LOGGER.info("connection to load" + connectionName);
		oldConnction = connectionName;
		if (oldConnction != null && !oldConnction.equalsIgnoreCase(""))
		{
			serverTextField.setEditable(false);
		}
		split = (GreenMangoQuery) parent;
		this.model = model;
		uiservice.setTreeModel(model);
		
		
		this.parent = parent;
	
			
			if (connectionName != null) {
				edit = true;
				Connection connection = getEditConnection(connectionName);

				serverTextField.setText(connection.getConnectionName());
				serverHostTextField.setText(connection.getHostName());
				serverPortTextField.setText(connection.getPort());
				serverUserTextField.setText(connection.getUsername());
				serverPasswordTextField.setText(connection.getPassword());

			}
			
		
		setSize(600, 300);
		JPanel messagePane = new JPanel();
		SpringLayout layout = new SpringLayout();
		JLabel serverLable = new JLabel("Server Name");
		JLabel serverHostLable = new JLabel("Server Host  ");
		JLabel serverPortLable = new JLabel("Server Port ");
		JLabel serverUserLable = new JLabel("User Name ");
		JLabel serverPaswordLable = new JLabel("Password ");
		JButton button = new JButton("Save & Update");
		JButton btnDelete = new JButton(delete);
		messagePane.add(serverLable);
		messagePane.add(serverTextField);
		messagePane.add(serverHostLable);
		messagePane.add(serverHostTextField);
		messagePane.add(serverPortLable);
		messagePane.add(serverPortTextField);
		messagePane.add(serverUserLable);
		messagePane.add(serverUserTextField);
		messagePane.add(serverPaswordLable);
		messagePane.add(serverPasswordTextField);
		messagePane.add(button);
		messagePane.add(btnDelete);
		layout.putConstraint(SpringLayout.WEST, serverLable, 10,
				SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverLable, 25,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverTextField, 25,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, serverTextField, 20,
				SpringLayout.EAST, serverLable);

		layout.putConstraint(SpringLayout.WEST, serverHostLable, 10,
				SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverHostLable, 55,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverHostTextField, 55,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, serverHostTextField, 20,
				SpringLayout.EAST, serverHostLable);

		layout.putConstraint(SpringLayout.WEST, serverPortLable, 10,
				SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverPortLable, 85,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverPortTextField, 85,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, serverPortTextField, 20,
				SpringLayout.EAST, serverHostLable);
		layout.putConstraint(SpringLayout.WEST, serverUserLable, 10,
				SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverUserLable, 115,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverUserTextField, 115,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, serverUserTextField, 20,
				SpringLayout.EAST, serverHostLable);
		layout.putConstraint(SpringLayout.WEST, serverPaswordLable, 10,
				SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverPaswordLable, 145,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, serverPasswordTextField, 145,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, serverPasswordTextField, 20,
				SpringLayout.EAST, serverHostLable);
		layout.putConstraint(SpringLayout.NORTH, button, 175,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, button, 120, SpringLayout.EAST,
				getContentPane());
		layout.putConstraint(SpringLayout.NORTH, btnDelete, 175,
				SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.WEST, btnDelete, 270,
				SpringLayout.EAST, getContentPane());
		messagePane.setLayout(layout);
		getContentPane().add(messagePane);
		// JPanel buttonPane = new JPanel();

		// buttonPane.add(button);
		button.addActionListener(this);
		btnDelete.addActionListener(this);

		// getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// pack();
		messagePane.setSize(600, 1000);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		Connection connection = new Connection();
		connection.setConnectionName(serverTextField.getText());
		connection.setHostName(serverHostTextField.getText());
		connection.setPassword(serverPasswordTextField.getText());
		connection.setPort(serverPortTextField.getText());
		connection.setUsername(serverUserTextField.getText());
		LOGGER.info(connection.toString());
		// AboutDialog.class.
		// InputStream main =
		// AboutDialog.class.getResourceAsStream("AboutDialog.class");

		String path = Util.getRootPath() + "/";
		boolean exists = (new File(path)).exists();
		if (!exists) {
			boolean success = (new File(path)).mkdir();
			LOGGER.info("succes create path " + success + path);
		} else {
			LOGGER.info("connection creates : " + path);
		}

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {

			exists = false;

			int result = 0;

			if (e.getActionCommand().equalsIgnoreCase(delete)) {
				exists = isExistConnection(path, connection.getConnectionName());
				if (exists) {
					result = JOptionPane
							.showConfirmDialog(
									parent,
									"Connection going to delete, Do you want to continue?",
									"Connection  exist",
									JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						new File(path + connection.getConnectionName())
								.delete();
						System.out.println("delete"
								+ connection.getConnectionName());
						if (connection.getConnectionName() != null) {
							uiservice
									.deleteTree(connection.getConnectionName());
							new File(oldPath).delete();

						}

					}
				}
			} else {
				if (oldPath == null) {
					oldPath = connection.getConnectionName();
				}
				LOGGER.info("update" + connection.getConnectionName());
				if (oldPath != null
						&& !(path + connection.getConnectionName())
								.equals(oldPath)) {
					new File(oldPath).delete();
				}
				
				if (oldConnction != null && !oldConnction.equalsIgnoreCase(""))
				{
				uiservice.updateTree(connection.getConnectionName(), oldConnction);
				}
				else
				{
					exists = isExistConnection(path, connection.getConnectionName());
					LOGGER.info("connection " + connection.getConnectionName() + " exit " + exists);
					if (!exists)
					{
				uiservice.createConnection(connection.getConnectionName());
					}
					else
					{
						JOptionPane.showMessageDialog(parent, "Connection exist", "Create Connection", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			if (result == 0 && !e.getActionCommand().equalsIgnoreCase(delete)) {
				LOGGER.info(" inside" + path + connection.getConnectionName());
				fos = new FileOutputStream(path
						+ connection.getConnectionName());
				oos = new ObjectOutputStream(fos);
				oos.writeObject(connection);
				oos.flush();
				oos.close();
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		setVisible(false);
		dispose();

	}

	public Connection getEditConnection(String connectionName) {

		String path = Util.getRootPath() + "/" + connectionName;
		oldPath = path;
		Connection connection = null;
		LOGGER.info("connection path :" + path);
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			try {
				connection = (Connection) ois.readObject();
				LOGGER.info("edit connection " + connection.getHostName()
						+ connection.getPassword() + connection.getPort());
			} catch (ClassNotFoundException ex) {
				LOGGER.info(ex.toString());
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return connection;
	}

	private static boolean isExistConnection(String path, String connection) {
		File directory = new File(path);
		boolean found = false;
		for (File f : directory.listFiles()) {
			if (f.getName().equals(connection)) {
				found = true;
				break;
			}
		}
		return found;

	}
}