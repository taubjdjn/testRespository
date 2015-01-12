/* 
 * PostsController.java 
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

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mulodo.test.constraints.Constraint;
import com.mulodo.test.dao.PostJdbcDAO;
import com.mulodo.test.model.Posts;

/**
 * The rest controller for getting data from AJAX
 * 
 * @author UayLU
 * 
 */
@RestController
@RequestMapping("posts")
public class PostsController {
	
	
	/**
	 *  getAllUsers get list posts JSON format
	 *	
	 *	@param	animal for searching
	 *  @param  page for pagination
	 *
	 *	@return String
	 * @throws JSONException 
	 *	
	 */
	@SuppressWarnings("resource")
	@RequestMapping(method = RequestMethod.GET, headers="Accept=application/json")
	public String getAllUsers(@RequestParam("animal") String animal,@RequestParam("page") String page) throws JSONException {
		
		//Get configuration data source from data-config.xml
		//Set data source from context to dataSource in postJdbcDao 
		ApplicationContext context = new ClassPathXmlApplicationContext("resources/data-config.xml");
		PostJdbcDAO postJdbcDAO = (PostJdbcDAO) context.getBean("postJdbcDAO");
		//Declare JSon for return to client
		JSONObject result = new JSONObject();
		
		try{
			//validate type of animal send from client. If not match with constraint,
			// server return will message error about animal
			if(Constraint.DOG.equals(animal.toLowerCase()) 
					|| Constraint.CAT.equals(animal.toLowerCase()) 
					|| Constraint.BOTH.equals(animal.toLowerCase())
					|| Constraint.ALL.equals(animal.toLowerCase())){
				animal = animal.toLowerCase();
			}else{
				result.put(Constraint.MESSAGE_JSON, Constraint.ERROR_ANIMAL);
				return  result.toString();
			}
			
			//validate type of page send from client. If not match with integer type or
			// not positive number, server will return message error about page
			int pageTemp =0;
			try{
				//pageTemp using for get build SQL command for pagination
				pageTemp = Integer.parseInt(page);
				if(pageTemp < 0 ){
					result.put(Constraint.MESSAGE_JSON, Constraint.ERROR_PAGE);
					return result.toString();
				}
			}catch(Exception ex){
				result.put(Constraint.MESSAGE_JSON, Constraint.ERROR_PAGE);
				return result.toString();
			}
			
			//Call getAllUsers method in PostJdbcDAO for getting list of posts
			List<Posts> listPosts = postJdbcDAO.getAllUsers(animal,pageTemp);
			//Call getTotalAnimal method in PostJdbcDAO for getting number of posts
			int totalAnimalByType = postJdbcDAO.getTotalAnimal(animal);
			
			//build JSon object for return to client
			result.put(Constraint.LIST_ANIMAL_JSON, listPosts);
			result.put(Constraint.CURRENT_PAGE_JSON, pageTemp);
			result.put(Constraint.LIMIT_PAGE_JSON, Constraint.LIMIT_PAGE);			
			int totalPage = (int) Math.round(totalAnimalByType/Constraint.LIMIT_PAGE + 0.5);			
			result.put(Constraint.PAGE_SIZE_JSON, totalPage);
			result.put(Constraint.TOTAL_ANIMAL_BY_TYPE_JSON, totalAnimalByType);
			
			return result.toString();
		}
		catch (Exception ex){
			result.put(Constraint.MESSAGE_JSON, Constraint.ERROR);
			return result.toString();
		}
	}
	
	
	
}