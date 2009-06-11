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

import javax.ejb.ActivationConfigProperty;

import org.jboss.ejb3.annotation.Consumer;

/**
 * This message-driven POJO receives charge requests on the queue testQueue.
 * 
 * @author Peter Johnson (peter dot johnson2 at unisys dot com)
 */
@Consumer(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue") })
public class Credit implements ICredit {

  /**
   * Called by the messaging server when a charge message is received.
   * 
   * @see org.jbia.jms.sofaspuds.ICredit
   */
  public void charge(String customer, double amount) {
    System.out.println("Credit charge to " + customer + " for " + amount);
  }
}
