package com.flysoohigh.ZHaulmont101.listener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.flysoohigh.ZHaulmont101.db.DBConnection;

@WebListener															
public class StartLogSQLListener implements ServletContextListener
{
	private static final Logger LOG = Logger.getLogger(StartLogSQLListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		DBConnection dbConnection = DBConnection.getInstance();
		dbConnection.destroyDBC();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		/* initializing log4j */
		Properties props = new Properties();
		try
		{
			props.load(new FileInputStream("src\\main\\resources\\log\\log4j.properties"));
			PropertyConfigurator.configure(props);
		} 
		catch (IOException e2)
		{
			System.out.println("Can't initialize log4j" + e2.getMessage());
			System.err.println("Can't initialize log4j" + e2.getMessage());
		}
		
		/* getting db connection then reading sql-scripts and executing them in order to fill GUI tables with data */
		DBConnection instance = DBConnection.getInstance();
		instance.initDBC();
		Connection connection = instance.getConnection();
		
		try(InputStream streamToCreate = getStream("sql\\HaulmontHSQLCreate.sql");
			InputStream streamToInsert = getStream("sql\\HaulmontHSQLInsert.sql");
			Statement statement = connection.createStatement();)
		{
			String hsqldbScriptToCreate = new BufferedReader(new InputStreamReader(streamToCreate)).lines().collect(Collectors.joining("\n"));
			statement.execute(hsqldbScriptToCreate); 
			
			String hsqldbScriptToInsert = new BufferedReader(new InputStreamReader(streamToInsert)).lines().collect(Collectors.joining("\n"));
			statement.execute(hsqldbScriptToInsert);
		} 
		catch (IOException | SQLException e)
		{
			LOG.error(e, e);
		} 
	}

	private InputStream getStream(String path)
	{
		return StartLogSQLListener.class.getClassLoader().getResourceAsStream(path);
	}
}
