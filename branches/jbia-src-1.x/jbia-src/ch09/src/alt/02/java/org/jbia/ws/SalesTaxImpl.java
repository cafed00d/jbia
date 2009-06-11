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

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Simple Web service that returns the basic sales tax for a given state. This
 * variant uses a top-down approach where the WSDL is defined first.
 */

@WebService(endpointInterface = "org.jbia.ws.SalesTax", portName = "SalesTaxPort", wsdlLocation = "WEB-INF/wsdl/SalesTaxService.wsdl")
public class SalesTaxImpl {

  /**
   * Holds the state sales tax values. The key is the two character state code,
   * such as CA, and the value is the sales tax expressed as a percent.
   */
  private HashMap<String, Double> tax;


  /**
   * The constructor initializes the sales tax data.
   */
  public SalesTaxImpl() {
    init();
  }


  /**
   * This method sets up some hard-coded sales tax amounts.
   */
  public void init() {
    tax = new HashMap<String, Double>();
    tax.put("CA", 7.75);
    tax.put("NH", 0.0);
  }


  /**
   * This method provides the sales tax rate for the given state.
   * 
   * @param state
   *          A two character state code.
   * @return The sales tax rate, expressed as a percent.
   */

  public double getRate(String state) {
    Double result = tax.get(state);
    if (result == null) result = -1.0;
    return result;
  }
}
