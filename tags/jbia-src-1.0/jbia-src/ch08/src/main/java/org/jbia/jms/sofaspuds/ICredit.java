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

import org.jboss.ejb3.annotation.Producer;

/**
 * Interface that will be implemented by the message-driven POJO. This same
 * interface is used by the message sender to send the message.
 */
@Producer
public interface ICredit {

  /**
   * The message is a request to charge an amount to a customer's account.
   * 
   * @param customer
   *          The name of the customer.
   * @param amount
   *          The amount to charge.
   */
  void charge(String customer, double amount);
}
