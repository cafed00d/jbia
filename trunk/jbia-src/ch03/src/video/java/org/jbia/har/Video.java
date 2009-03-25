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

/**
 * Simple JavaBean used to illustrate Hibernate archives. This bean represents
 * data concerning a video as stored in a database.
 */
public class Video {

  /**
   * @see #getId()
   */
  private int id;

  /**
   * @see #getName()
   */
  private String name;

  /**
   * @see #getMinutes()
   */
  private int minutes;

  /**
   * @see #getPrice()
   */
  private float price;

  /**
   * Gets the object id used to uniquely identify this object.
   * 
   * @return The object id.
   */
  public int getId() {
    return id;
  }

  /**
   * @see #getId()
   * @param id
   *          The value of the object id.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name, or title, of the video.
   * 
   * @return The name.
   */
  public String getName() {
    return name;
  }

  /**
   * @see #getName()
   * @param name
   *          The name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the length of the video, in minutes.
   * 
   * @return The length of the video.
   */
  public int getMinutes() {
    return minutes;
  }

  /**
   * @see #getMinutes()
   * @param minutes
   *          The length of the video.
   */
  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  /**
   * Gets the sale price of the video.
   * 
   * @return The current sale price.
   */
  public float getPrice() {
    return price;
  }

  /**
   * @see #getPrice()
   * @param price
   *          The new sale price.
   */
  public void setPrice(float price) {
    this.price = price;
  }

  /**
   * Handy method for debugging.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "#" + id + ": " + name + " (" + minutes + "), " + price;
  }
}
