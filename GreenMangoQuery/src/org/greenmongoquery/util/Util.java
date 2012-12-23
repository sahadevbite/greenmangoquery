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
 Author : sony john


 */
package org.greenmongoquery.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.greenmongoquery.GreenMangoQuery;
import org.greenmongoquery.ui.About;

/**
 * @author Sony john
 *
 */
/**
 
 *
 */
public class Util {
	static Logger logger = Logger.getLogger(About.class.getName());
	static{
		LogHandler.setup(logger);
	}
	  /**
	 * @param arg01
	 * @param delimiter
	 * @return
	 */
	public static String getSplitbyDelimiter(String arg01, char delimiter)
	  {
	      return arg01.substring(arg01.lastIndexOf(delimiter)+1, arg01.length()).replaceAll("]", "").trim();
	  }
	  
	/**
	 * @param query
	 * @return
	 */
	public static Map<String, String> getQuerySeperate(String query)
	  {
	      Map<String, String> result = new HashMap<String, String>();
	      StringBuilder sb = new StringBuilder(query);
	 int start = sb.indexOf("find(")+5;
	 int colEnd = sb.indexOf(")",start);
		 result.put("coll",sb.substring(sb.indexOf("db.")+3, sb.indexOf(".find(")));
	 result.put("query",sb.substring(start, colEnd));
	 
	 return result;
	  }
	public static String getRootPath()
	{
	Properties props =	PropsRead.setup();
	String data = getCurrentPath() + "/" + props.getProperty("data");	
	File f = new File( props.getProperty("data"));
	if (!f.exists())
	{
		f.mkdir();
	}
	return data ;
	}
	public static String getLogPath()
	{
	Properties props =	PropsRead.setup();
	String data = props.getProperty("fileDir") + "/" + props.getProperty("filename");	
	File f = new File( props.getProperty("fileDir"));
	if (!f.exists())
	{
		f.mkdir();
	}
	return data ;
	}
	public static String  getCurrentPath()
	{
		File dir = new File (".");
		try {
			return dir.getCanonicalFile().getAbsolutePath();
		} catch (IOException e) {
			logger.log(Level.INFO , "", e);
		}
		return null;
	}
}