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
package org.jbia.har;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Simple servlet used to illustrate working with a Hibernate archive. The
 * servlet accepts user input for a new video, adds the video to the database,
 * and then displays to the user all of the videos.
 */
public class VideoServlet extends HttpServlet {

  /**
   * Default serial version id (to keep Eclipse happy...)
   */
  private static final long serialVersionUID = 1L;

  /**
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Session hs = null;
    try {
      /*
       * Establish the Hibernate session:
       */
      InitialContext ctx = new InitialContext();
      SessionFactory hsf = (SessionFactory) ctx
          .lookup("java:/hibernate/jbia/VideoSF");
      hs = hsf.openSession();

      /*
       * If the user entered and data, add the video to the database.
       */
      String strName = req.getParameter("name");
      String strMinutes = req.getParameter("minutes");
      String strPrice = req.getParameter("price");
      if ((strName != null) && (strMinutes != null) && (strPrice != null)) {
        Video video = new Video();
        video.setName(strName);
        video.setMinutes(Integer.parseInt(strMinutes));
        video.setPrice(Float.parseFloat(strPrice));
        hs.save(video);
      }

      /*
       * Get a list of videos to display to the user:
       */
      Query qVideo = hs.createQuery("from org.jbia.har.Video order by name");
      List mvVideo = qVideo.list();
      req.setAttribute("videos", mvVideo);

      /*
       * Defer to the JSP for display:
       */
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
      rd.forward(req, resp);

    } catch (Throwable e) {
      /*
       * We catch throwable so that we can also get runtime errors such as
       * invalid number format when converting the minutes and price.
       */
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } finally {
      /*
       * Close the session now that we are done:
       */
      if (hs != null) {
        hs.close();
      }
    }
  }

  /**
   * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }

}
