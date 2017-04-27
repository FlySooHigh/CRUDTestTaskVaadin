package com.flysoohigh.ZHaulmont101.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBConnection 
{
	private static final Logger LOG = Logger.getLogger(DBConnection.class);
	
	private static DBConnection instance;
	private Connection connection;
	
	private DBConnection()
	{
		
	}
	
	public static DBConnection getInstance()
	{
		if (instance == null)
		{
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	
	public void initDBC()	
	{
		try
		{
			Class.forName("org.hsqldb.jdbcDriver");			
		} 
		catch (ClassNotFoundException e)
		{
			LOG.error(e, e);
		}
		
		if (connection == null)
		{
			String url = "jdbc:hsqldb:~/haulmont";
			String login = "sa";					
			String password = "";					

			try 
			{
				connection = DriverManager.getConnection(url, login, password);		
			}
			catch(SQLException ex)
			{
				LOG.error("Database connection failed. ", ex);					
			}
		}
	}

	public void destroyDBC()	
	{
		if (connection != null)
		{
			try
			{
				connection.close();
			}
			catch(SQLException ex)
			{
				LOG.error("Unable to close database connection", ex);
			}
		}
		connection = null;
	}
}
