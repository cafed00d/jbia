package com.manning.jbia.ejbsecurity.client;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import javax.naming.Context;

import org.jboss.security.SecurityAssociation;
import org.jboss.security.SimplePrincipal;

import com.manning.jbia.ejbsecurity.ejb.CalculatorRemote;
import org.jboss.security.auth.callback.SecurityAssociationHandler;	
import javax.security.auth.login.LoginContext;
import javax.security.auth.callback.CallbackHandler; 


public class Client {

	public static void main(String[] args) throws Exception {
		callEachCalculatorMethod("admin", "bank-manager, teller");
		callEachCalculatorMethod("bank-manager", "bank-manager");
		callEachCalculatorMethod("teller", "teller");
		callEachCalculatorMethod("joe", "customer");
	}

	private static void callEachCalculatorMethod(
			String principal, String roles) throws Exception {

		SecurityAssociationHandler handler = new SecurityAssociationHandler();
		SimplePrincipal user = new SimplePrincipal(principal);
		handler.setSecurityInfo(user, "special".toCharArray());
		LoginContext loginContext = 
			  new LoginContext("myClientDomain", (CallbackHandler) handler);
		loginContext.login();
		InitialContext ctx = new InitialContext();
              

		CalculatorRemote calculator = (CalculatorRemote) ctx
				.lookup("calculator/StatelessCalculatorBean/remote");
		System.out.println("--------------------------------------------");
		System.out.println("User: " + principal + ", Roles: " + roles);
		System.out.println("--------------------------------------------");
		try {
			calculator.calculateFutureValue(1000, 20);
			System.out.println(principal
					+ " could call calculateFutureValue (requires 'teller')");
		} catch (Exception e) {
			System.out
					.println(principal
							+ " could not call calculateFutureValue (requires 'teller') - "
							+ e.getMessage());
		}
		try {
			calculator.calculateTotalInterest(1000, 20);
			System.out
					.println(principal
							+ " could call calculateTotalInterest (requires 'bank-manager' or 'teller')");
		} catch (Exception e) {
			System.out
					.println(principal
							+ " could not call calculateTotalInterest (requires 'bank-manager' or 'teller') - "
							+ e.getMessage());
		}
		try {
			calculator.getInterestRate();
			System.out.println(principal
					+ " could call getInterestRate (requires 'bank-manager')");
		} catch (Exception e) {
			System.out
					.println(principal
							+ " could not call getInterestRate (requires 'bank-manager') - "
							+ e.getMessage());
		}
		try {
			calculator.getTheAnswerToLifeTheUniverseAndEverything();
			System.out
					.println(principal
							+ " could call getTheAnswerToLifeTheUniverseAndEverything (DenyAll)");
		} catch (Exception e) {
			System.out
					.println(principal
							+ " could not call getTheAnswerToLifeTheUniverseAndEverything (DenyAll) - "
							+ e.getMessage());
		}
		try {
			calculator.getTheAnswerToLifeTheUniverseAndEverything();
			System.out
					.println(principal + " could call freeForAll (PermitAll)");
		} catch (Exception e) {
			System.out.println(principal
					+ " could not call freeForAll (PermitAll) - "
					+ e.getMessage());
		}
	}
}
