/*
 *  Copyright 2007, Javid Jamae and Peter Johnson
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
package com.manning.jbia.calculator.ejb;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Service;

@Service(objectName = "jbia:service=interestRateManager")
@Depends ({"jboss.jca:name=DefaultDS,service=DataSourceBinding"})
public class InterestRateMBean implements InterestRateManager {

	private double interestRate;

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getInterestRate() {
		return interestRate;
	}

	// Lifecycle methods
	public void create() throws Exception {
		interestRate = 5.25;
		System.out.println("Calculator - Creating");
	}

	public void destroy() {
		System.out.println("Calculator - Destroying");
	}
}