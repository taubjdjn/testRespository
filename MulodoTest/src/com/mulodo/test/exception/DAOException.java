/* 
 * DAOException.java 
 *  
 * 1.0
 * 
 * 2014/12/27
 *  
 * Copyright (c) 2014 Le U Uay 
 * 
 * Modification Logs:  
 * DATE             AUTHOR     DESCRIPTION  
 * --------------------------------------------------------  
 * 2014/12/27       UayLU       Create
 */

package com.mulodo.test.exception;


/**
 * The exception thrown by DAO classes
 * 
 * @author UayLU
 * 
 */
@SuppressWarnings("serial")
public class DAOException extends Exception {
	
	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}
}
