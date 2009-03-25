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
 * class resides in a JAR file in the server/xxx/deploy directory.
 */
public class InDeploy {

  /**
   * The simple name of this class. We get it once and use it often.
   */
  private final String myName;


  /**
   * Initializes the simple name for the object.
   */
  public InDeploy() {
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
      callInLib(path);
    } catch (Throwable e) {
      path.add("exception when calling InLib: " + e);
    }
  }


  /**
   * Calls the next class in route B. This code was extracted from the routeB
   * method so that, if the InLib class is not available, the path would still
   * register the "called InDeploy" step before throwing an exception that the
   * InLib class is not available.
   * 
   * @param path
   *          Collection of calls made so far.
   */
  private void callInLib(List<String> path) {
    InLib id = new InLib();
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
      callInEar(path);
    } catch (Throwable e) {
      path.add("exception when calling InEar: " + e);
    }
  }


  /**
   * Calls the next class in route C. This code was extracted from the routeC
   * method so that, if the InEar class is not available, the path would still
   * register the "called InDeploy" step before throwing an exception that the
   * InEar class is not available.
   * 
   * @param path
   *          Collection of calls made so far.
   */
  private void callInEar(List<String> path) {
    InEar id = new InEar();
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
