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
 * One of the classes used to illustrate class loading and visibility. There are
 * two versions of this class. The first version resides in the JAR package in
 * the server/xxx/lib directory, and the second version in the EAR package. The
 * EAR uses a scoped class loader so that it gets the proper version.
 */
public class Version {

  /**
   * Initializes the simple name for the object.
   */
  public Version(List<String> path) {
    path.add("created " + getClass().getSimpleName() + " (version 2)");
  }
}
