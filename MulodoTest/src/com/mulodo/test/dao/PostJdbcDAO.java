/* 
 * PostJdbcDAO.java 
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mulodo.test.constraints.Constraint;
import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Posts;

/**
 * The JDBC DAO of post
 * 
 * @author UayLU
 * 
 */
public class PostJdbcDAO implements PostsDAO {
	
	private DataSource dataSource;

	
	/**
	 *  setDataSource data source from data-config.xml to PostJdbcDAO
	 *	
	 *	@param	dataSource
	 *	
	 *	
	 *  @exception  DAOException
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
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
	@Override
	public List<Posts> getAllUsers(String animal, int page) throws DAOException {
		Connection connection = null;
		List<Posts> users = new ArrayList<Posts>();
		try {
			
			//get connection from data source
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			
			//get SQL command from buildSQLCommand method
			StringBuilder sqlCommand = buildSQLCommand(animal,page);
			ResultSet rs = statement.executeQuery(sqlCommand.toString());
			Posts posts = null;
			while (rs.next()) {
				posts = new Posts();
				posts.setId(rs.getInt("id"));
				posts.setName(rs.getString("name"));
				posts.setText(rs.getString("text"));				
				posts.setUrl(rs.getString("profile_image_url"));
				users.add(posts);
			}
			return users;
		} catch (SQLException e) {
			// throw exception to DAOException
			throw new DAOException(e.getMessage());
		} catch (Exception e){
			// throw exception to DAOException
			throw new DAOException(e.getMessage());
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// throw exception to DAOException
				throw new DAOException(e.getMessage());
			}
		}
	}

	
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
	@Override
	public int getTotalAnimal(String animal) throws DAOException {
		Connection connection = null;
		
		try {
			//get connection from data source
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			
			//build SQL string, it have three case:
			// - SQL total by both cat, dog
			// - SQL total by dog or cat
			// - SQL total by all
			StringBuilder sqlCommand = new StringBuilder("select count(*) as total from posts ");
			if(Constraint.BOTH.equals(animal.toLowerCase())){
				sqlCommand.append(" where text like '%" + Constraint.DOG + "%' or text like '%" + Constraint.CAT + "%' ");
			}else if(Constraint.DOG.equals(animal.toLowerCase())
					|| Constraint.CAT.equals(animal.toLowerCase())){
				sqlCommand.append(" where text like '%" + animal + "%' ");
			}

			ResultSet rs = statement.executeQuery(sqlCommand.toString());
			int totalAnimal = 0;
			while (rs.next()) {
				totalAnimal = rs.getInt("total");
			}
			return totalAnimal;
		} catch (SQLException e) {
			// throw exception to DAOException
			throw new DAOException(e.getMessage());
		} catch (Exception e){
			// throw exception to DAOException
			throw new DAOException(e.getMessage());
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// throw exception to DAOException
				throw new DAOException(e.getMessage());
			}
		}
	}
	
	/**
	 *  buildSQLCommand build SQL string for searching animal
	 *	
	 *	@param	animal for counting animal
	 *	@param  page for pagination
	 *
	 *	@return StringBuilder: SQL string
	 *	
	 *	
	 *  @exception  DAOException
	 */
	private StringBuilder buildSQLCommand(String animal, int page){
		
		//Declare the begin row and the end row for getting 20 row per page
		int beginRow = (page-1)*Constraint.LIMIT_PAGE;
		
		//Declare SQL string
		StringBuilder sqlCommand = new StringBuilder("select * from posts ");
		
		//have three case: search both dog or cat; search by: dog, cat; search all
		if(Constraint.BOTH.equals(animal.toLowerCase())){
			sqlCommand.append(" where text like '%" + Constraint.DOG + "%' or text like '%" + Constraint.CAT + "%' ");
		}else if(Constraint.DOG.equals(animal.toLowerCase())
				|| Constraint.CAT.equals(animal.toLowerCase())){
			sqlCommand.append(" where text like '%" + animal + "%' ");	
		}
		
		sqlCommand.append(" order by id desc ");
		//get limit 20 row per page
		sqlCommand.append(" limit " + beginRow +","+Constraint.LIMIT_PAGE);
		return sqlCommand;		
	}
	
}
