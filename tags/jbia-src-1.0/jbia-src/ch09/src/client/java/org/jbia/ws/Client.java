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
package org.jbia.ws;

${Client.import}

/**
 * Web service client that obtains sales tax rates. To run, provide one or more
 * two character state code and the client prints the sales tax rate for each
 * state.
 */
public class Client {

  /**
   * Main body of program
   * 
   * @param args
   *          List of two-character state codes.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("usage: client <list-of-states>");
    } else {
      SalesTaxService svc = new SalesTaxService();
      SalesTax tax = svc.getSalesTaxPort();
      ${Client.authorization}
      for (int i = 0; i < args.length; i++) {
        double rate = tax.getRate(args[i]);
        System.out.println("Sales tax for " + args[i] + " is " + rate);
      }
    }
  }
}
