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
package org.greenmongoquery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.greenmongoquery.db.service.impl.MongoServiceImpl;
import org.greenmongoquery.ui.About;
import org.greenmongoquery.ui.CreateConnection;
import org.greenmongoquery.ui.TableHorizontal;
import org.greenmongoquery.ui.event.CommandActionListner;
import org.greenmongoquery.ui.event.QueryCommand;
import org.greenmongoquery.ui.event.RefreshCommand;
import org.greenmongoquery.ui.impl.UiServiceImpl;
import org.greenmongoquery.ui.model.Cache;
import org.greenmongoquery.ui.model.ConnectionModel;
import org.greenmongoquery.ui.model.UITreeModel;
import org.greenmongoquery.util.LogHandler;

import com.mongodb.Mongo;

public class GreenMangoQuery
extends JFrame {
	/**
	 * 
	 */
private static final long serialVersionUID = -3128594885347496251L;
private final static Logger LOGGER = Logger.getLogger(GreenMangoQuery.class .getName()); 
private JSplitPane splitPaneV;
private JSplitPane splitPaneH;
private JPanel panel1;
private JPanel panel2;
private JPanel panel3;
private JLabel statusbar;
private JTree tree;
private JPopupMenu menu;
private JFrame parent = this;
private String editConnection;
private static DefaultTreeModel model;
private DefaultMutableTreeNode root;
public TreePath path;
public MutableTreeNode mNode;
private UiServiceImpl uiService;
private  Mongo mongo;
private  TableHorizontal tableUpdate;
private JTextArea jsonResult =new JTextArea(5, 20);
private JTextArea queryResult = new JTextArea();
private CommandActionListner commandListener;
private String activeConnection;
private ConnectionModel connectionModel;
private JButton theButton;
private JMenuItem newsf; 
private  CreateConnection createConnection;
private JButton refresh   = new JButton("Refresh");
private  UITreeModel uimodel;
private JButton btnInsert;
private JButton btnDelete;
private JButton btnUpdate;
public JButton getBtnInsert() {
	return btnInsert;
}
public void setBtnInsert(JButton btnInsert) {
	this.btnInsert = btnInsert;
}

public JButton getBtnUpdate() {
	return btnUpdate;
}
public void setBtnUpdate(JButton btnUpdate) {
	this.btnUpdate = btnUpdate;
}
public JButton getBtnDelete() {
	return btnDelete;
}
public void setBtnDelete(JButton btnDelete) {
	this.btnDelete = btnDelete;
}

//private Map<String, ConnectionModel> connectionCache = new Hashtable<String, ConnectionModel>();
private Cache cache = new Cache();
private QueryCommand queryCommand = new QueryCommand();
RefreshCommand command;

 public UiServiceImpl getUiService() {
	return uiService;
}
public GreenMangoQuery() {
	 {
LOGGER.info("initlize Mangoquery");		 
		 
new MongoServiceImpl();
uiService = new UiServiceImpl();

setTitle("Green Mango Query");
setBackground(Color.gray);
setExtendedState(JFrame.MAXIMIZED_BOTH);
JPanel topPanel = new JPanel();
JMenuBar menubar = new JMenuBar();

ImageIcon icon = new ImageIcon(getClass().getResource("/resources/exit.png"));
ImageIcon iconNew = new ImageIcon(getClass().getResource("/resources/new.png"));

JMenu file = new JMenu("File");
JMenu help = new JMenu("Help");
file.setMnemonic(KeyEvent.VK_F);

JMenuItem eMenuItemExit = new JMenuItem("Exit", icon);
JMenuItem fileNew = new JMenuItem("Add New Server ....", iconNew);
fileNew.setMnemonic(KeyEvent.VK_N);
fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
        ActionEvent.CTRL_MASK));
eMenuItemExit.setMnemonic(KeyEvent.VK_C);
eMenuItemExit.setToolTipText("Exit application");
eMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
        ActionEvent.CTRL_MASK));
eMenuItemExit.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent event) {
        System.exit(0);
    }
});
newsf = new JMenuItem("Add new server...");
//connectionModel = new ConnectionModel(activeConnection, mongo);
//commandListener.setConnectionModel(connectionModel);
 uimodel =new UITreeModel(activeConnection, cache, model, root, path, mNode, refresh, commandListener, mongo, tableUpdate, jsonResult, queryResult, queryCommand, tree, theButton,this);
fileNew.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent event) {
       // JDialog dialog = new CreateConnection(parent, "Shows ",	"Test", editConnection, uimodel.getModel());
    	createConnection=	new CreateConnection(parent, "Create Connections ",
				"Test", editConnection, uimodel);
	
    	//JDialog dialog = new About(parent);
    	LOGGER.info("about us");
        //createTree();
    }
});
final GreenMangoQuery parent = this;
JMenuItem helpAbout = new JMenuItem("About");
helpAbout.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent event) {
        //JDialog dialog = new AboutDialog(parent, "Shows dilo", "Test",editConnection,uimodel);
    	JDialog dialog = new About(parent);
    	LOGGER.info("about us");
        //createTree();
    }
});
help.add(helpAbout);
file.add(fileNew);
file.addSeparator();
file.add(eMenuItemExit);
JMenu view = new JMenu("View");
view.setMnemonic(KeyEvent.VK_V);

JCheckBoxMenuItem sbar = new JCheckBoxMenuItem("Show Status Bar");
sbar.setState(true);
JCheckBoxMenuItem query = new JCheckBoxMenuItem("Show Query Pan");
query.setState(true);

sbar.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent event) {
        if (statusbar.isVisible()) {
            statusbar.setVisible(false);
        } else {
            statusbar.setVisible(true);
        }
    }
});

view.add(sbar);
view.add(query);
menubar.add(file);
menubar.add(view);
menubar.add(help);
setJMenuBar(menubar);
statusbar = new JLabel(" Statusbar");
statusbar.setBorder(BorderFactory.createEtchedBorder(
        EtchedBorder.RAISED));
add(statusbar, BorderLayout.SOUTH);
menu = new JPopupMenu();
JMenuItem menuItemClose = new JMenuItem("Close");
menuItemClose.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
});

menu.add(menuItemClose);

this.addMouseListener(new MouseAdapter() {

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
});
JToolBar toolbar = new JToolBar();

ImageIcon iconexit = new ImageIcon(getClass().getResource("/resources/exit.png"));

JButton exitButton = new JButton(iconexit);
toolbar.add(exitButton);
exitButton.addActionListener(new ActionListener() {

    public void actionPerformed(ActionEvent event) {
        System.exit(0);
    }
});

add(toolbar, BorderLayout.NORTH);
topPanel.setLayout(new BorderLayout());
getContentPane().add(topPanel);

// Create the panels
createPanel1();
createPanel2();
createPanel3();

// Create a splitter pane
splitPaneV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
topPanel.add(splitPaneV, BorderLayout.CENTER);

splitPaneH = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
splitPaneH.setLeftComponent(panel2);

splitPaneH.setRightComponent(panel3);

splitPaneV.setLeftComponent(panel1);

splitPaneV.setRightComponent(splitPaneH);

pack();
}
 }
public void createPanel1() {
//Left Panel
panel1 = new JPanel();
panel1.setLayout(new BorderLayout());
panel1.setMinimumSize(new Dimension(200, 800));
panel1.setMinimumSize(new Dimension(200, 800));

panel1.add(refresh, BorderLayout.NORTH);
//createTree();
commandListener = new CommandActionListner(this, null,tableUpdate,jsonResult);
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
theButton = new JButton("Query");
tableUpdate = new TableHorizontal(screenSize);

  uimodel =new UITreeModel(activeConnection, cache, model, root, path, mNode, refresh, commandListener, mongo, tableUpdate, jsonResult, queryResult, queryCommand, tree, theButton,this);

uimodel = uiService.updateTree(uimodel);



panel1.add(uimodel.getTree());



}

public void createPanel2() {
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
LOGGER.info("screen size :" +screenSize);
panel2 = new JPanel();
//tableUpdate = new TableHorizontal(screenSize);
LOGGER.info("Panel2 starting ");

panel2.setLayout(new BorderLayout());
 btnInsert = new JButton("Insert");
btnInsert.setActionCommand("insert");
btnInsert.addActionListener(commandListener);
  btnUpdate = new JButton("Update");
 btnUpdate.setActionCommand("update");
btnUpdate.addActionListener(commandListener);
  btnDelete = new JButton("Delete");
    btnDelete.setActionCommand("delete");
btnDelete.addActionListener(commandListener);
JPanel flowPanel1 = new JPanel(new FlowLayout(10, 10, 10));
flowPanel1.setMaximumSize(new Dimension(100,100));
flowPanel1.add(btnInsert);
flowPanel1.add(btnUpdate);
flowPanel1.add(btnDelete);
panel2.add(BorderLayout.SOUTH, flowPanel1);

panel2.add(flowPanel1);
JTabbedPane tab = new JTabbedPane();
panel2.setMinimumSize(new Dimension((int)(screenSize.width*0.22), (int)(screenSize.height*0.67)));

//panel2.add(new PushHeaderExample());
panel2.add(new JButton("Northb"), BorderLayout.NORTH);
Dimension dm =panel2.getSize();

//tableUpdate.setupData();
tab.add("Result", tableUpdate);
tab.add("Json Result",    jsonResult);
jsonResult.setText("just for tesing purpos");
panel2.add(tab,BorderLayout.NORTH);
//panel2.add(tableUpdate,BorderLayout.NORTH);
Dimension dm1 =panel2.getPreferredSize();



}

public void createPanel3() {
panel3 = new JPanel();
panel3.setLayout(new BorderLayout());
//panel3.setPreferredSize( new Dimension( 400, 100 ) );
// panel3.setMinimumSize( new Dimension( 100, 50 ) );

panel3.add(new JLabel("Query:"), BorderLayout.NORTH);

panel3.add(queryResult, BorderLayout.CENTER);


queryResult.setText("text");
 

  theButton.addActionListener(queryCommand);
  
JPanel flowPanel = new JPanel(new FlowLayout(10, 10, 10));
flowPanel.setMaximumSize(new Dimension(100,100));
flowPanel.add(theButton);
panel3.add(BorderLayout.SOUTH, flowPanel);
btnInsert.addActionListener(uimodel.getCommandListener());

}


public static void main(String args[]) {

Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

GreenMangoQuery mainFrame = new GreenMangoQuery();
mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // added  
mainFrame.setBounds(0, 0, screenSize.width, screenSize.height);
mainFrame.setSize(screenSize.width, screenSize.height);

mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

mainFrame.setVisible(true);
}








private void contextMenu(Component component) {
final JPopupMenu menu = new JPopupMenu();

//Create and add a menu item
JMenuItem item = new JMenuItem("Edit");

// editConnection= editConnection.substring(editConnection.indexOf(",")+1, editConnection.lastIndexOf("]")).trim();
//System.err.println("Edit connection" + editConnection);
//System.out.println("double click f");
uimodel = new UITreeModel(activeConnection, cache, model, root, path, mNode, refresh, commandListener, mongo, tableUpdate, jsonResult, queryResult, queryCommand, tree, theButton,this);
item.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent ae) {
    	
        JDialog dialog = new CreateConnection(parent, "Create Connection--New", "Test",editConnection,uimodel);
    }
});
menu.add(item);

//Set the component to show the popup menu
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

public String getEditConnection() {
return editConnection;
}


}