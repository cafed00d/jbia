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
package org.jbia.jms.sofaspuds;

/**
 * Represents information about a video. Used as the payload in the messages in
 * the example.
 */
public class Video implements java.io.Serializable {

  /**
   * Version id generated on 9-Sep-07 at 11:53.
   */
  private static final long serialVersionUID = 4925761261141825955L;

  /**
   * @see #getName()
   */
  private String name;

  /**
   * @see #getGenre()
   */
  private String genre;


  /**
   * Gets the genre (such as 'comedy', 'drama', etc.) of the video.
   * 
   * @return Text string that identifies the genre.
   */
  public final String getGenre() {
    return genre;
  }


  /**
   * @see #getGenre()
   */
  public final void setGenre(String genre) {
    this.genre = genre;
  }


  /**
   * Gets the name, or title, of the video.
   * 
   * @return The name of the video.
   */
  public final String getName() {
    return name;
  }


  /**
   * @see #getName()
   */
  public final void setName(String name) {
    this.name = name;
  }


  /**
   * Returns a string containing the video name and genre.
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return name + " [" + genre + "]";
  }
}
