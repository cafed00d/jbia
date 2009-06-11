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

import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
	public static void main(String[] args) throws Exception {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		properties.put(Context.PROVIDER_URL, "localhost:1099");
		InitialContext ctx = new InitialContext(properties);

		// lookup printer bean with default jndi binding
		MessagePrinterRemote defaultPrinter = (MessagePrinterRemote) ctx
				.lookup("DefaultPrinterBean/remote");
		defaultPrinter
				.printRemoteMessage("Remote call to default printer successful.");

		// lookup printer bean with annotation-based jndi binding
		MessagePrinterRemote annotationPrinter_firstName = (MessagePrinterRemote) ctx
				.lookup("MyAnnotationPrinter");
		//MessagePrinterRemote annotationPrinter_secondName = (MessagePrinterRemote) ctx
		//		.lookup("MyAnnotationPrinter2");
		annotationPrinter_firstName
				.printRemoteMessage("Remote call to annotation printer (with first bound name) successful.");
		//annotationPrinter_secondName
		//		.printRemoteMessage("Remote call to annotation printer (with second bound name) successful.");

		// lookup printer bean with config-based jndi binding
		MessagePrinterRemote configPrinter = (MessagePrinterRemote) ctx
				.lookup("MyConfigPrinter");
		configPrinter
				.printRemoteMessage("Remote call to config printer successful.");

		/*
		 * This section is commented out because the overriding functionailty is not currently working in JBoss 5.0.0 CR2. These will be
		 * enabled again as soon as a release is made available in which they work.
		 */
		/*
		// lookup printer bean with annotation binding overriden in config
		MessagePrinterRemote overridePrinter = (MessagePrinterRemote) ctx
				.lookup("OverriddenGlobally");
		overridePrinter
				.printRemoteMessage("Remote call to override printer successful.");

		// lookup printer bean with annotation binding overriden in config
		try {
			ctx.lookup("MyOverridePrinter");
		} catch (Exception e) {
			System.out
					.println("Successful override (couldn't access overridden binding)");
		}
                     */
	}
}