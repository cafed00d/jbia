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

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

@Stateless
public class VideoServiceImpl implements VideoService {

	@PersistenceContext(unitName = "java:/hibernate/jbia/VideoSF")
	private Session hs;

	public List<Video> getUpdatedVideoList() {
		return hs.createQuery("from org.jbia.har.Video order by name").list();
	}

	public void addVideo(String name, String minutes, String price) {
		if ((name != null) && (minutes != null) && (price != null)) {
			Video video = new Video();
			video.setName(name);
			video.setMinutes(Integer.parseInt(minutes));
			video.setPrice(Float.parseFloat(price));
			hs.save(video);
		}
	}

}
