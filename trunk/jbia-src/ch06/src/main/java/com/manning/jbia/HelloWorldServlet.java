/*
 *  Copyright 2007, Javid Jamae and Peter Johnson
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */
package com.manning.jbia;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Hello World servlet used to demonstrate many configuration options in JBoss.
 */
@SuppressWarnings("serial")
public class HelloWorldServlet extends HttpServlet
{

	@Override
	public void service( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		out.println( "<html><body>Hello World!</body></html>" );
		out.close();
	}
}
