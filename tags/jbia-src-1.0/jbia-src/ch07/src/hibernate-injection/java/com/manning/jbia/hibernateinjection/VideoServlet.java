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
package com.manning.jbia.hibernateinjection;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple servlet that talks to an EJB that has an injected Hibernate Session.
 * The servlet accepts user input for a new video, calls the EJB to add the
 * video, calls the EJB to load the ordered list of videos, then displays all of
 * the videos to the user.
 */
@SuppressWarnings("serial")
public class VideoServlet extends HttpServlet {

	//@EJB(name = "java:comp/env/ejb/VideoService")
	//@EJB(mappedName="video/VideoServiceImpl/local")
	//@EJB(name = "ejb/VideoService")
	@EJB(mappedName="video/VideoServiceImpl/local")
	private VideoService videoService;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {

			String name = req.getParameter("name");
			String minutes = req.getParameter("minutes");
			String price = req.getParameter("price");

			videoService.addVideo(name, minutes, price);

			List<Video> videoList = videoService.getUpdatedVideoList();
			req.setAttribute("videos", videoList);

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
		}
	}

}
