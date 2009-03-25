package com.manning.jbia;

import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

@Stateless
@Clustered
public class CounterBean implements Counter {

	public void printCount(int countNumber) {
		System.out.println(countNumber);
	}

}
