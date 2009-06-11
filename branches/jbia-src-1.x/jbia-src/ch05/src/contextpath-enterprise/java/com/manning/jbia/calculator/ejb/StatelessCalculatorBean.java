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

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StatelessCalculatorBean implements Calculator {


	public double calculateTotalInterest(double presentValue, int years) {
		return calculateFutureValue(presentValue, years) - presentValue;
	}
	
	public double calculateFutureValue(double presentValue, int years) {
		double interestRate = 8.0 / 100;
		return presentValue * Math.pow((1.0+interestRate), years);
	}
	
	public double getInterestRate() {
		return 8.0;
	}

}
