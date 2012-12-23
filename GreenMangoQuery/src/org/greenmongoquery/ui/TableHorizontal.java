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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableHorizontal extends JPanel {
	private JTable table;
	private DefaultTableModel model;

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public TableHorizontal(Dimension screenSize) {

		model = new DefaultTableModel();
		table = new JTable(model) {

			public boolean getScrollableTracksViewportWidth() {
				// return getPreferredSize().width < getParent().getWidth();
				return false;
			}
		};

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(scrollPane);

		scrollPane.setPreferredSize(new Dimension(
				(int) (screenSize.width * 0.72),
				(int) (screenSize.height * 0.44)));

	}

	public void setupData(List<Map> data) {
		model.getDataVector().removeAllElements();
		model.setColumnCount(0);

		for (int x = 0; x < model.getColumnCount(); x++) {
			String column = model.getColumnName(x);
		
			TableColumn tcol = table.getColumnModel().getColumn(x);
			table.removeColumn(tcol);

		}
		Set<String> columnNames = new HashSet();
		int i = 0;
		
		for (Map map : data) {
			Iterator k = map.keySet().iterator();
			while (k.hasNext()) {
				String key = (String) k.next();
				if (!columnNames.contains(key)) {
					model.addColumn(key);
				}
				columnNames.add(key);


			}
		}
		for (Map map : data) {
			List<String> valueList = new ArrayList<String>();
			for (Object key : map.keySet()) {
				String tmp = (String) key;
				Object value = map.get(tmp);
				//System.out.printf("%s = %s%n", key, value);
				columnNames.add(key.toString());
				valueList.add(value.toString());
			}
			String[] countries = valueList
					.toArray(new String[valueList.size()]);
			
			model.insertRow(i, countries);
			i++;
		}

	
		model.insertRow(0, new String[] { "r1", "test" });
	}

	public void setupData(List<String> data, String header) {
		model.getDataVector().removeAllElements();
		model.setColumnCount(0);
		
		
		for (int x = 0; x < model.getColumnCount(); x++) {
			String column = model.getColumnName(x);
			
			TableColumn tcol = table.getColumnModel().getColumn(x);
			table.removeColumn(tcol);

		}
		Set<String> columnNames = new HashSet();
		int i = 0;
		

		model.addColumn(header);
		for (String item : data) {
			String[] row = new String[] { item };
			
			model.insertRow(i, row);
			i++;
		}
	}

	public void setupData(Set<String> data, String header) {
		model.getDataVector().removeAllElements();
		model.setColumnCount(0);
		
		for (int x = 0; x < model.getColumnCount(); x++) {
			String column = model.getColumnName(x);
			
			TableColumn tcol = table.getColumnModel().getColumn(x);
			table.removeColumn(tcol);

		}
		Set<String> columnNames = new HashSet();
		int i = 0;
		

		model.addColumn(header);
		for (String item : data) {
			String[] row = new String[] { item };
			
			model.insertRow(i, row);
			i++;
		}

		
	}

	public static void main(String[] args) {
		
	}
}
