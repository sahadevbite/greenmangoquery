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

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.greenmongoquery.util.LogHandler;

public class About extends JDialog{
	Logger logger = Logger.getLogger(About.class.getName());
			
public About(JFrame parent)
{
	super(parent, "About Green Mango Query", true);
	LogHandler.setup(logger);
	StyleContext style = new StyleContext();
    final DefaultStyledDocument document = new DefaultStyledDocument(style);
    JTextPane pane = new JTextPane(document);
String text ="Green Mango Query for Mongo IDE \n Send queries or bugs to greenmangoquery@gmail.com\n Developer : Sony John \n ";
    // Create and add the style
    final Style headingStyle = style.addStyle("Heading", null);
    headingStyle.addAttribute(StyleConstants.Foreground, Color.GREEN);
    headingStyle.addAttribute(StyleConstants.LineSpacing,2f);
    headingStyle.addAttribute(StyleConstants.FontSize, new Integer(18));
    headingStyle.addAttribute(StyleConstants.FontFamily, "serif");
    headingStyle.addAttribute(StyleConstants.Bold, new Boolean(true));
    
    final Style paragraphStyle = style.addStyle("paragraph", null);
    paragraphStyle.addAttribute(StyleConstants.Foreground, Color.black);
    paragraphStyle.addAttribute(StyleConstants.LineSpacing,2f);
    paragraphStyle.addAttribute(StyleConstants.FontSize, new Integer(10));
    paragraphStyle.addAttribute(StyleConstants.FontFamily, "serif");
    paragraphStyle.addAttribute(StyleConstants.Bold, new Boolean(true));
    try {
		document.insertString(0, text, null);
	} catch (BadLocationException e) {
		// TODO Auto-generated catch block
		logger.log(Level.INFO, "", e);
	}

 
    document.setParagraphAttributes(0, 1, headingStyle, false);
    //document.setp
    document.setParagraphAttributes(60, 80,  paragraphStyle,false );
	setSize(600, 300);
	JPanel messagePane = new JPanel();
	SpringLayout layout = new SpringLayout();
	pane.setOpaque(false);
	pane.setBackground(Color.WHITE);
	messagePane.add(pane);
	messagePane.setLayout(layout);
	getContentPane().add(messagePane);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	messagePane.setSize(600, 1000);
	setVisible(true);
}
}
