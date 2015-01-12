/* 
 * Constraint.java 
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
 * 2014/12/30       UayLU       Create
 */
package com.mulodo.test.constraints;

/**
 * The Constraint of project
 * 
 * @author UayLU
 * 
 */
public class Constraint {
	
	// Message error to client
	public final static String ERROR_PAGE = "Page must be a positive number";
	public final static String ERROR_ANIMAL = "Type of animal must be a dog, cat or both and all";
	public final static String ERROR = "Have some error--Please try agian later";
	
	//Constraint about type of searching
	public final static String DOG = "dog";
	public final static String CAT = "cat";
	public final static String BOTH = "both";
	public static final String ALL = "all";
	
	//Constraint about pagination
	public final static int LIMIT_PAGE = 20;
	public final static int MAX_PAGE_NUMBER = 5;
	public final static int MID_PAGE_NUMBER = 3;
	
	//Constraint about JSON format
	public final static String MESSAGE_JSON = "message";
	public final static String LIST_ANIMAL_JSON = "listAnimal";
	public final static String CURRENT_PAGE_JSON = "pageCurrent";
	public final static String LIMIT_PAGE_JSON = "limitPage";
	public final static String PAGE_SIZE_JSON = "pageSize";
	public final static String TOTAL_ANIMAL_BY_TYPE_JSON = "totalAnimalByType";
	
	
}
