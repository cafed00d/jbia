package com.manning.jbia;

import javax.ejb.Remote;

@Remote
public interface Counter {
	public void printCount(int messageNumber);
}
