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
package com.manning.jbia.ejbsecurity.web;

import javax.ejb.EJB;
import javax.faces.event.ActionEvent;

import com.manning.jbia.ejbsecurity.ejb.Calculator;

public class CalculatorViewBean {

	@EJB(name = "ejb/Calculator")
	private Calculator calculator;

	private double presentValue;

	private double futureValue;

	private double interestEarned;

	private int years;

	public double getPresentValue() {
		return presentValue;
	}

	public void setPresentValue(double presentValue) {
		this.presentValue = presentValue;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public double getFutureValue() {
		return futureValue;
	}

	public double getInterestRate() {
		return calculator.getInterestRate();
	}

	public void calculate(ActionEvent e) {
		this.futureValue = calculator.calculateFutureValue(presentValue, years);
		this.interestEarned = calculator.calculateTotalInterest(presentValue,
				years);
	}

	public double getInterestEarned() {
		return interestEarned;
	}

}
