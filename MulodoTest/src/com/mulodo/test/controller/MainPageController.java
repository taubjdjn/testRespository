/* 
 * MainPageController.java 
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
package com.mulodo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The first controller will be load 
 * 
 * @author UayLU
 * 
 */
@Controller
@RequestMapping("/")
public class MainPageController {
	
	/**
	 *  mainPage call index.jsp in the first load
	 *	
	 *	@return String
	 *	
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String mainPage() {
	
		return "index";
	}
}
