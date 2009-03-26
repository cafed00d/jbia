package com.manning.jbia;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
	public static void main(String[] args) throws Exception {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
//		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");
		properties.put(Context.PROVIDER_URL, "192.168.1.140:1100,192.168.1.141:1100");
		
		InitialContext ctx = new InitialContext(properties);

		Counter s = (Counter) ctx.lookup("CounterBean/remote");
		for (int i = 0; i < 100; i++) {
			s.printCount(i);
			Thread.sleep(1000);
		}
	}
}