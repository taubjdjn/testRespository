/* 
 * PostsDAO.java 
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
package com.mulodo.test.dao;

import java.util.List;

import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Posts;

/**
 * The DAO interface of post
 * 
 * @author UayLU
 * 
 */
public interface PostsDAO {
	
	/**
	 *  getAllUsers get all posts from database
	 *	
	 *	@param	animal for searching
	 *  @param  page for pagination
	 *
	 *	@return List<Posts> list of post
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public List<Posts> getAllUsers(String animal, int page) throws DAOException;
	
	/**
	 *  getTotalAnimal get total animal count by specific animal from database
	 *	
	 *	@param	animal for counting animal
	 *
	 *	@return int: total animal
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public int getTotalAnimal(String animal) throws DAOException;
}
