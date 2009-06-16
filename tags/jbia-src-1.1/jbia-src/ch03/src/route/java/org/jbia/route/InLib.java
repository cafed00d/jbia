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
 * class resides in a JAR file in the server/xxx/lib directory.
 */
public class InLib {

  /**
   * The simple name of this class. We get it once and use it often.
   */
  private final String myName;


  /**
   * Initializes the simple name for the object.
   */
  public InLib() {
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
   * Handles route B. This class is the final one on the route, so just returns.
   * 
   * @param path
   *          Collection of calls made so far. This method adds an entry to this
   *          collection.
   */
  public void routeB(List<String> path) {
    append(path);
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
      callInDeploy(path);
    } catch (Throwable e) {
      path.add("exception when calling InDeploy: " + e);
    }
  }


  /**
   * Calls the next class in route C.
   * 
   * @param path
   *          Collection of calls made so far.
   * @see InDeploy#callInLib
   */
  private void callInDeploy(List<String> path) {
    InDeploy id = new InDeploy();
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
