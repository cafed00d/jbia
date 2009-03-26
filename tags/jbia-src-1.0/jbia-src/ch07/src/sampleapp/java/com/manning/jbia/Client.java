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
package com.manning.jbia;

import java.util.List;

import javax.naming.InitialContext;

public class Client {
	public static void main(String[] args) throws Exception {
		InitialContext ctx = new InitialContext();

		Greeter greeter = (Greeter) ctx.lookup("StatelessSSL");
		greeter.greet("Hello, world!");
		greeter.greet("Hola, mundo!");
		greeter.greet("Salam, donya!");
		greeter.greet("Bonjour, monde!");
		greeter.greet("Ciao, mondo!");
		
		List<Greeting> greets = greeter.getAllGreetings();
		for (Greeting greeting : greets) {
			System.out.println(greeting.getGreeting());
		}
	}
}