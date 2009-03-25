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
 * class resides in both the EAR and WAR packages in the server/xxx/deploy
 * directory. Because different class loader repositories are used, the class in
 * the WAR package is actually different than the one in the EAR package.
 */
public class Same {

  /**
   * Initializes the simple name for the object.
   */
  public Same(List<String> path) {
    path.add("created " + getClass().getSimpleName());
  }
}
