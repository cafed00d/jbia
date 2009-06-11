/*
 *  Copyright 2008, Javid Jamae and Peter Johnson
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
package org.jbia.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple echoing servlet used to illustrate logging to a specific file.
 */
public class VoteServlet extends HttpServlet {

  /**
   * Default serial version id (to keep Eclipse happy...)
   */
  private static final long serialVersionUID = 1L;

  /**
   * Get a logger for this class. The prefix for this logger, "org.jbia",
   * matches the prefix used in the jboss-log4j.xml file.
   */
  private Log log = LogFactory.getLog(VoteServlet.class);


  /**
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

    /*
     * Get the system properties that contain the text to display and log:
     */
    String strDisplay = System.getProperty(
        "jbia.display",
        "property jbia.display not found");
    String strLog = System.getProperty(
        "jbia.log",
        "property jbia.log not found ");

    /*
     * Generate the display and log entry:
     */
    PrintWriter out = resp.getWriter();
    out.write("<html><body><h1>" + strDisplay + "</h1></body></html>");
    log.info(String.format(strLog, req.getRemoteHost()));
  }


  /**
   * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    doGet(req, resp);
  }

}
