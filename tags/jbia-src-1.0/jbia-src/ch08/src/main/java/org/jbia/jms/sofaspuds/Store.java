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
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Messaging client class that represents the video store in the example
 * messaging application.
 */
public class Store implements MessageListener {

  /**
   * Used to read input from the command line.
   */
  private BufferedReader rdr = new BufferedReader(new InputStreamReader(
      System.in));

  /**
   * The session established with the messaging server for purposes of sending
   * messages.
   */
  private Session sessionProducer;

  /**
   * The session established with the messaging server for purposes of receiving
   * messages. Because we receive messages asynchronously on another thread, we
   * must have a separate session to do so.
   */
  private Session sessionConsumer;

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
   * goes into a loop, asking for new videos and sending out messages about
   * them.
   * 
   * @throws Exception
   *           Bad things happened.
   */
  private void run() throws Exception {
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
       * Establish the connection and the sessions with the messaging server:
       */
      connection = cf.createConnection(${login.store});
      sessionProducer = connection.createSession(
          false,
          Session.AUTO_ACKNOWLEDGE);
      sessionConsumer = connection.createSession(
          false,
          Session.AUTO_ACKNOWLEDGE);

      /*
       * Create the consumer and producer used to send and receive messages:
       */
      producer = sessionProducer.createProducer(notify);
      consumer = sessionConsumer.createConsumer(request);

      /*
       * Indicate that we want to be notified asynchronously of incoming
       * messages:
       */
      consumer.setMessageListener(this);

      /*
       * No messages will be received, or can be sent, until the connection is
       * started:
       */
      connection.start();

      /*
       * Go into a loop to gather and send information about newly available
       * videos. The 10 second wait is there to give the customers time to
       * respond so that the text printed to the console concerning their
       * response does not visually mess up the text in the console window. This
       * was done mainly to make the text more readable for the examples and is
       * not a general requirement for this type of application.
       */
      while (notifyOfVideos()) {
        Thread.sleep(10 * 1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {

      /*
       * Close the connection when we are done, otherwise the messaging server
       * will complain if it detects that the client has gone away without first
       * closing the connection:
       */
      if (connection != null) {
        sessionProducer.close();
        sessionConsumer.close();
        connection.close();
      }
    }
  }


  /**
   * Requests information about a video from the user, and once the information
   * is entered, sends a video availability message to the customers.
   * 
   * @return True to continue, false to exit.
   * @throws Exception
   *           Bad things happened.
   */
  private boolean notifyOfVideos() throws Exception {

    /*
     * Get information about a video:
     */
    System.out.println("Supply info for new video:");
    System.out.print("Name: ");
    String name = rdr.readLine();
    System.out.print("Genre: ");
    String genre = rdr.readLine();

    /*
     * If no information given, then let's exit:
     */
    if (name.length() == 0 || genre.length() == 0) {
      return false;
    }

    /*
     * Build the video object based on the input and sent it to the customers:
     */
    Video video = new Video();
    video.setName(name);
    video.setGenre(genre);
    ObjectMessage om = sessionProducer.createObjectMessage(video);
    producer.send(om);
    return true;
  }


  /**
   * Called by the messaging server whenever a message appears in the "request"
   * topic.
   * 
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  public void onMessage(Message msg) {
    try {

      /*
       * A video reservation request message contains a video object, extract it
       * and display the information about it and the customer who reserved it:
       */
      ObjectMessage om = (ObjectMessage)msg;
      Video video = (Video)om.getObject();
      String customer = om.getStringProperty("SpudsCustomer");
      System.out.println("Reservation request:");
      System.out.println("\tcustomer: " + customer);
      System.out.println("\tvideo   : " + video);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }


  /**
   * Main entry point for program.
   * 
   * @param args
   *          Ignored, no arguments expected.
   * @throws Exception
   *           Bad things happened.
   */
  public static void main(String[] args) throws Exception {
    new Store().run();
  }
}
