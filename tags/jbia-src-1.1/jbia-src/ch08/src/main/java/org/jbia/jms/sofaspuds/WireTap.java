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

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

${WireTap.import}

/**
 * This message-driven EJB listens to video rental requests and reports on
 * selected requests.
 */
${WireTap.annotation}
public class WireTap implements MessageListener {

  /**
   * Called by the messaging server when a message appears in the topic.
   * 
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message msg) {
    try {
      ObjectMessage objmsg = (ObjectMessage)msg;
      Video video = (Video)objmsg.getObject();
      String perp = msg.getStringProperty("SpudsCustomer");
      System.out.println("Perpetrator " + perp + " requested video " + video);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
