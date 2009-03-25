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
package com.manning.jbia.ejbsecurity.ejb;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

${classlevel.securitydomain.annotation}
${classlevel.security.annotation}
@Stateless
public class StatelessCalculatorBean implements Calculator, CalculatorRemote {

	public double calculateTotalInterest(double presentValue, int years) {
		return calculateFutureValue(presentValue, years) - presentValue;
	}

	${calculateFutureValue.security.annotation}
	public double calculateFutureValue(double presentValue, int years) {
		double interestRate = 5.25 / 100;
		return presentValue * Math.pow((1.0 + interestRate), years);
	}

	${getInterestRate.security.annotation}
	public double getInterestRate() {
		return 5.25;
	}

	${getTheAnswerToLifeTheUniverseAndEverything.security.annotation}
	public String getTheAnswerToLifeTheUniverseAndEverything() {
		return "42";
	}

	${freeForAll.security.annotation}
	public String freeForAll() {
		return "You're in!";
	}

}
