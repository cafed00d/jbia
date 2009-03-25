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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Simple Hello World servlet used to demonstrate many configuration options in
 * JBoss.
 */
@SuppressWarnings("serial")
public class DataLoaderServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");

		Connection con = null;
		Statement stmt = null;

		try {
			Context ctx = new InitialContext();
			out.println("Getting datasource...");
			out.println("<br/>");
			DataSource datasource = (DataSource) ctx
					.lookup("java:/jdbc/securityDS");
			out.println("Got datasource: java:/jdbc/securityDS");
			con = datasource.getConnection();
			out.println("Created connection");
			out.println("<br/>");
			stmt = con.createStatement();
			stmt
					.executeUpdate("CREATE TABLE VUser ( vname VARCHAR(30) NOT NULL, vpassword VARCHAR(30) NOT NULL, PRIMARY KEY(vname))");
			out.println("Created VUser table");
			out.println("<br/>");
			stmt
					.executeUpdate("CREATE TABLE VRole ( vname VARCHAR(30) NOT NULL, vrole VARCHAR(30) NOT NULL, PRIMARY KEY(vname))");
			out.println("Created VRole table");
			out.println("<br/>");

			stmt
					.executeUpdate("INSERT INTO VUser (vname, vpassword) VALUES ('somebody' , 'special' )");
			stmt
					.executeUpdate("INSERT INTO VUser (vname, vpassword) VALUES ('another' , 'person' )");
			stmt
					.executeUpdate("INSERT INTO VRole (vname, vrole) VALUES ('somebody' , 'admin')");
			stmt
					.executeUpdate("INSERT INTO VRole (vname, vrole) VALUES ('another' , 'user')");
			out.println("Inserted users and roles");
			out.println("<br/>");
		} catch (NamingException e) {
			out.println("ERROR: " + e.getMessage());
			out.println("<br/>");
			e.printStackTrace();
		} catch (SQLException e) {
			out.println("ERROR: " + e.getMessage());
			out.println("<br/>");
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				out.println("Closed statement");
				out.println("<br/>");
			} catch (Exception e) {
			}
			try {
				con.close();
				out.println("Closed connection");
				out.println("<br/>");
			} catch (Exception e) {
			}
		}

		out.println("</body></html>");
		out.close();
	}
}