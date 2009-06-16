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
package org.jbia.route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to illustrate class loading and visibility.
 */
public class RouteServlet extends HttpServlet {

  /**
   * Default serial version id (to keep Eclipse happy...)
   */
  private static final long serialVersionUID = 1L;


  /**
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

    List<String> path = new ArrayList<String>();
    String scenario = req.getParameter("scenario");

    /**
     * Scenario A calls each class serially. That is, each class returns without
     * making any other calls, so the servlet calls each one.
     */
    if (scenario.equals("A")) {
      path.add("starting in servlet");
      try {
        InWar iw = new InWar();
        iw.routeA(path);
        path.add("in servlet");
        InSar is = new InSar();
        is.routeA(path);
        path.add("in servlet");
        InEar ie = new InEar();
        ie.routeA(path);
        path.add("in servlet");
        InDeploy id = new InDeploy();
        id.routeA(path);
        path.add("in servlet");
        InLib il = new InLib();
        il.routeA(path);
      } catch (Throwable e) {
        path.add("exception in servlet: " + e);
      }

      /**
       * Scenario B starts with the class in the WAR, which then calls the next
       * class in the route and so on. Expected route is war -> sar -> ear ->
       * deploy -> lib.
       */
    } else if (scenario.equals("B")) {
      path.add("starting in servlet");
      try {
        InWar iw = new InWar();
        iw.routeB(path);
      } catch (Throwable e) {
        path.add("exception in servlet: " + e);
      }

      /**
       * Scenario C starts with the JAR in the server/xxx/lib directory, which
       * then calls the next class in the route and so on. Expected route is lib ->
       * deploy -> ear -> sar -> war (last one causes exception).
       */
    } else if (scenario.equals("C")) {
      path.add("starting in servlet");
      try {
        InLib il = new InLib();
        il.routeC(path);
      } catch (Throwable e) {
        path.add("exception in servlet: " + e);
      }

      /**
       * Scenario D creates a local instance of Same, then calls InEar to create
       * another instance of Same, which it returns as an object. Casting that
       * object into Same causes a class cast exception.
       */
    } else if (scenario.equals("D")) {
      path.add("starting in servlet");
      try {
        path.add("in servlet, creating local instance of Same");
        Same same = new Same(path);
        path.add("in servlet, calling InEar to get another instance of Same");
        InEar ie = new InEar();
        Object obj = ie.routeD(path);
        path.add("in servlet, object returned is of type "
            + obj.getClass().getName());
        path.add("in servlet, casting to Same");
        Same s2 = (Same)obj;
      } catch (Throwable e) {
        path.add("exception in servlet: " + e);
      }

      /**
       * Scenario E creates a instance of Version, which should be version 1.
       * Then calls InScopedEar which creates an instance of version 2 of
       * Version. Note that we cannot call InScopedEar directly because it also
       * is also scoped, hence InScopedEar is an EJB and we call it as such.
       */
    } else if (scenario.equals("E")) {
      path.add("starting in servlet");
      try {
        path.add("in servlet, creating instance of Version");
        new Version(path);
        path.add("in servlet, calling InScopedEar to create instance of Version");
        InitialContext ctx = new InitialContext();
        IInScopedEar ie = (IInScopedEar)ctx.lookup("inscopedear/InScopedEar/local");
        ie.routeE(path);
      } catch (Throwable e) {
        path.add("exception in servlet: " + e);
      }
    }

    /**
     * Get data ready to display in the JSP.
     */
    req.setAttribute("route", scenario);
    req.setAttribute("path", path);

    /*
     * Defer to the JSP for display:
     */
    ServletContext sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
    rd.forward(req, resp);
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
