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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;

${ssl.remote.binding}
@Stateless
public class GreeterBean implements Greeter {
	@PersistenceContext
	private EntityManager em;

	public void greet(String message) {
		Greeting greeting = new Greeting(message);
		em.persist(greeting);
	}

	@SuppressWarnings("unchecked")
	public List<Greeting> getAllGreetings() {
		return em.createQuery("from Greeting").getResultList();
	}
}
