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
package com.manning.jbia.jndibinding;

import java.util.Hashtable;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * The JNDI binding for the dynamic proxy for this bean is unchanged, so the
 * client accesses the dynamic proxy for this bean via the default binding.
 * 
 * @author Javid Jamae
 * 
 */
@Stateless
public class DefaultPrinterBean implements MessagePrinterRemote,
		MessagePrinterLocal {

	public void printRemoteMessage(String message) {
		System.out.println(message);

		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		properties.put(Context.PROVIDER_URL, "localhost:1099");
		InitialContext ctx;
		try {
			ctx = new InitialContext(properties);
			MessagePrinterLocal localCounter = (MessagePrinterLocal) ctx
					.lookup("DefaultPrinterBean/local");
			localCounter
					.printLocalMessage("Local call to default printer successful.");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void printLocalMessage(String message) {
		System.out.println(message);
	}

}
