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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import org.greenmongoquery.db.service.MongoService;
import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.model.ActionEnum;
import org.greenmongoquery.ui.model.ConnectionModel;
import org.greenmongoquery.ui.model.MongoCollectionModel;

import com.mongodb.Mongo;

public class QueryDialog extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private boolean answer = false;
    private  JTextArea textQuery= null;
    private ConnectionModel model;
    private TableHorizontal table;
    private   JTextArea jsonText;
    private ActionEnum action;
    private String id;
    public boolean getAnswer() { return answer; }
    private MongoService mongoService = new MongoServiceImpl();

    public QueryDialog(JFrame frame, boolean modal, String myMessage, String query, ConnectionModel model, TableHorizontal table, JTextArea jsonText, ActionEnum action, String id) {
      
        myPanel = new JPanel();
         SpringLayout layout = new SpringLayout();
    Container contentPane = getContentPane();
    getContentPane().setLayout(layout);
    this.model = model;
    this.table= table;
    this.jsonText = jsonText;
    this.action = action;
    this.id= id;
    Component left = new JLabel(myMessage);
     textQuery = new JTextArea(20,60);
     textQuery.setText("{'database' : 'db','colection' : 'coll','detail' : {'name' : sony, 'age' : '14', 'active' : 'true'}}}");
     if (query != null )
     {
     textQuery.setText(query);
     }
     yesButton = new JButton("Run Query");
     yesButton.addActionListener(this);
    contentPane.add(left);
    JScrollPane scrolltxt = new JScrollPane(textQuery);
    contentPane.add(scrolltxt);
    contentPane.add(yesButton);
    layout.putConstraint(SpringLayout.WEST, left, 10, SpringLayout.WEST, contentPane);
    layout.putConstraint(SpringLayout.NORTH, left, 25, SpringLayout.NORTH, contentPane);
    layout.putConstraint(SpringLayout.NORTH, scrolltxt, 25, SpringLayout.NORTH, contentPane);
    layout.putConstraint(SpringLayout.WEST, scrolltxt, 20, SpringLayout.EAST, left);
    layout.putConstraint(SpringLayout.NORTH, yesButton, 320, SpringLayout.NORTH, left);
    layout.putConstraint(SpringLayout.WEST, yesButton, 25, SpringLayout.EAST, left);
     setSize(new Dimension(850,490));  
     setLocation(200, 200);
     

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String result=null;
        if(yesButton == e.getSource()) {

            System.err.println("User chose yes." + action);
            String query = textQuery.getText();
            if (action == ActionEnum.INSERT)
            {
           result = mongoService.insertDocument(query, model.getDbName(), model.getCollectionName(), model.getMongo());
            }
             if (action == ActionEnum.UPDATE)
             {
              result=   mongoService.updateDocument(query, model.getDbName(), model.getCollectionName(), model.getMongo(),id);  
             }
             if (action == ActionEnum.DELETE)
             {
                result = mongoService.deleteDocument(query, model.getDbName(), model.getCollectionName(), model.getMongo(),id);  
                 
             }
             JOptionPane.showMessageDialog(null, result);
           MongoCollectionModel resultList =      mongoService.getAllDocuments(model.getMongo(), model.getDbName(), model.getCollectionName());
               table.setupData(resultList.getResult());
               jsonText.setText(resultList.getBsonData().toString());
              
            
    }
    
}
}
