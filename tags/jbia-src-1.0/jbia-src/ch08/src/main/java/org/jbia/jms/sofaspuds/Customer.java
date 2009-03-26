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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Messaging client class that represents a customer in the video store example
 * application.
 */
public class Customer {

  /**
   * Used to read input from the command line.
   */
  private BufferedReader rdr = new BufferedReader(new InputStreamReader(
      System.in));

  /**
   * The session established with the messaging server.
   */
  private Session session;

  /**
   * The customer uses the message producer to send reservation requests to the
   * video store.
   */
  private MessageProducer producer;

  /**
   * The customer uses the message consumer to receive video availability
   * notifications from the video store.
   */
  private MessageConsumer consumer;


  /**
   * The primary method that establishes a session with the messaging server and
   * waits in a loop to receive video availability notifications.
   * 
   * @param customer
   *          The name of the customer. Any string is acceptable, but it should
   *          be non-null (the code does not check for nulls).
   * @throws Exception
   *           Bad things happened.
   */
  private void run(String customer) throws Exception {
    Connection connection = null;
    try {

      /*
       * Lookup the messaging connection factory and the destinations in JNDI:
       */
      Context initial = new InitialContext();
      ConnectionFactory cf = (ConnectionFactory)initial.lookup("ConnectionFactory");
      Destination notify = (Destination)initial.lookup("topic/${topic.notify}");
      Destination request = (Destination)initial.lookup("topic/${topic.request}");

      /*
       * Establish the connection and a session with the messaging server:
       */
      connection = cf.createConnection(${login.customer});
      connection.setClientID(customer);
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      /*
       * Create the consumer and producer used to send and receive messages:
       */
      producer = session.createProducer(request);
      consumer = session.createConsumer(notify);

      /*
       * No messages will be received, or can be sent, until the connection is
       * started:
       */
      connection.start();

      /*
       * Go into a loop listening for messages:
       */
      System.out.println("Listening for video notifications...");
      while (listen(customer)) {/* do nothing */}
    } catch (Exception e) {
      e.printStackTrace();
    } finally {

      /*
       * Close the connection when we are done, otherwise the messaging server
       * will complain if it detects that the client has gone away without first
       * closing the connection:
       */
      if (connection != null) {
        session.close();
        connection.close();
      }
    }
  }


  /**
   * Waits for a video availability notification and then asks the user if he or
   * she would like to reserve that video. If so, sends a video reservation
   * request to the video store.
   * 
   * @param customer
   *          The customer name.
   * @return True to continue, false to exit.
   * @throws Exception
   *           Bad things happened.
   */
  public boolean listen(String customer) throws Exception {
    boolean result = true;

    /*
     * A video availability message contains a video object, extract it and
     * display the information about it:
     */
    ObjectMessage om = (ObjectMessage)consumer.receive();
    Video video = (Video)om.getObject();
    System.out.println("New video available: " + video);

    /*
     * Ask the user if the video should be reserved. If so, send a reservation
     * request:
     */
    System.out.print("Reserve a copy? [y,n,x(to exit)] ");
    String input = rdr.readLine();
    if (input.equalsIgnoreCase("y")) {
      om = session.createObjectMessage(video);
      om.setStringProperty("SpudsCustomer", customer);
      producer.send(om);
    } else if (input.equalsIgnoreCase("x")) {
      result = false;
    }
    return result;
  }


  /**
   * Main entry point for program. Checks the command line arguments and if
   * acceptable runs the application.
   * 
   * @param args
   *          Expects one argument - the customer name.
   * @throws Exception
   *           Bad things happened.
   */
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("usage: customer <name>");
    } else {
      new Customer().run(args[0]);
    }
  }
}
