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

import javax.ejb.Stateless;

/**
 * One of the classes used to illustrate class loading and visibility. This
 * class illustrates the use of a scoped class loader..
 */
@Stateless
public class InScopedEar implements IInScopedEar {

  /**
   * @see org.jbia.route.IInScopedEar#routeE(java.util.List)
   */
  public void routeE(List<String> path) {
    path.add("called " + getClass().getSimpleName());
    new Version(path);
  }
}
