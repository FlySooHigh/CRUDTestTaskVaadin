package com.flysoohigh.ZHaulmont101.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.flysoohigh.ZHaulmont101.db.DBConnection;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.util.SQLQueries;

public class GroupDAO 
{
	private static final String EMPTY_MESSAGE = "";
	private static final Logger LOG = Logger.getLogger(GroupDAO.class);
	
	public String addGroup(Group group)
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.ADD_NEW_GROUP))
		{
			ps.setString(1, group.getGroupNumber());
			ps.setString(2, group.getFacultyName());
			ps.executeUpdate();
			return EMPTY_MESSAGE;				
		} 
		catch (SQLException e)
		{
			LOG.error("GroupDAO - addGroup() SQLException", e);
			return "GROUP with such number already EXISTS. Choose another group number.";
		}
	}
	
	
	public List<Group> readAllGroups() 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		List<Group> groups = new ArrayList<>();
		
		try(Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQLQueries.SELECT_ALL_GROUPS);)		
		{
				while (rs.next()) {
					long groupID = rs.getLong("id");
					String number = rs.getString("number");
					String facultyName = rs.getString("faculty_name");

					groups.add(new Group(groupID, number, facultyName));
				}
		} 
		catch (SQLException e)
		{
			LOG.error("GroupDAO - readAllGroups() SQLException", e);
		}
		return groups;
	}
	
	public String updateGroup(Group group)
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.UPDATE_GROUP);)
		{
			ps.setString(1, group.getGroupNumber());
			ps.setString(2, group.getFacultyName());
			ps.setLong(3, group.getGroupID());
			ps.executeUpdate();
			return EMPTY_MESSAGE;
		}
		catch (SQLException e)					
		{
			LOG.error("GroupDAO - updateGroup() SQLException", e);
			return "You cannot change number of non-empty GROUP or GROUP with such number already exists.";
		}
	}
	
	public String deleteGroup(long groupID) 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_GROUP);)
		{
			ps.setLong(1, groupID);
			ps.executeUpdate();
			LOG.info("DAOGroup.deleteGroup() worked fine");
			return EMPTY_MESSAGE;
		} 
		catch (SQLException e)
		{
			LOG.error("GroupDAO - deleteGroup() SQLException", e);
			return "This GROUP is NOT EMPTY. You cannot delete it.";
		}
	}
}

