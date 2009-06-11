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

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Greeting implements Serializable {
	private int id;
	private String msg;

	private Greeting() {
	}

	public Greeting(String msg) {
		this.msg = msg;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public String getGreeting() {
		return msg;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setGreeting(String msg) {
		this.msg = msg;
	}
}
