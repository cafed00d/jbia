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

import java.util.List;

/**
 * One of the classes used to illustrate class loading and visibility. This
 * class resides in a SAR package in the server/xxx/deploy directory.
 */
public class InSar {

  /**
   * The simple name of this class. We get it once and use it often.
   */
  private final String myName;


  /**
   * Initializes the simple name for the object.
   */
  public InSar() {
    myName = getClass().getSimpleName();
  }


  /**
   * Handles route A, therefore the method simply returns.
   * 
   * @param path
   *          Collection of calls made so far. This method adds an entry to this
   *          collection.
   */
  public void routeA(List<String> path) {
    append(path);
  }


  /**
   * Handles route B, so the method calls the next object on the route.
   * 
   * @param path
   *          Collection of calls made so far. This method adds an entry to this
   *          collection.
   */
  public void routeB(List<String> path) {
    append(path);
    try {
      callInEar(path);
    } catch (Throwable e) {
      path.add("exception when calling InEar: " + e);
    }
  }


  /**
   * Calls the next class in route B.
   * 
   * @param path
   *          Collection of calls made so far.
   * @see InDeploy#callInLib
   */
  private void callInEar(List<String> path) {
    InEar id = new InEar();
    id.routeB(path);
  }


  /**
   * Handles route C, so the method calls the next object on the route.
   * 
   * @param path
   *          Collection of calls made so far. This method adds an entry to this
   *          collection.
   */
  public void routeC(List<String> path) {
    append(path);
    try {
      callInWar(path);
    } catch (Throwable e) {
      path.add("exception when calling InWar: " + e);
    }
  }


  /**
   * Calls the next class in route C.
   * 
   * @param path
   *          Collection of calls made so far.
   * @see InDeploy#callInLib
   */
  private void callInWar(List<String> path) {
    InWar id = new InWar();
    id.routeC(path);
  }


  /**
   * Adds information about this class to the path collection.
   * 
   * @param path
   *          Collection of calls made so far. This method adds an entry to this
   *          collection.
   */
  private void append(List<String> path) {
    path.add("called " + myName);
  }
}
