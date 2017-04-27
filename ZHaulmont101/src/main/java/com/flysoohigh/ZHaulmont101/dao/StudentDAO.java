package com.flysoohigh.ZHaulmont101.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.flysoohigh.ZHaulmont101.db.DBConnection;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.entity.Student;
import com.flysoohigh.ZHaulmont101.util.SQLQueries;

public class StudentDAO 
{
	private static final String EMPTY_MESSAGE = "";
	private static final Logger LOG = Logger.getLogger(StudentDAO.class);
	
	public String addStudent(Student student) 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.ADD_NEW_STUDENT);)	
		{
			ps.setString(1, student.getLastName());			
			ps.setString(2, student.getFirstName());		
			ps.setString(3, student.getMiddleName());
			ps.setDate(4, Date.valueOf(student.getBirthDate()));
			ps.setString(5, student.getGroup().getGroupNumber()); 	
			ps.executeUpdate();
			return EMPTY_MESSAGE;
		} 
		catch (SQLException e)
		{
			LOG.error("StudentDAO - addStudent() SQLException", e);
			return "SQL error";   							
		}
	}
	
	public List<Student> readAllStudents() 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		List<Student> students = new ArrayList<>();
		
		try(Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(SQLQueries.SELECT_ALL_STUDENTS))											 
		{																					
				while (result.next()) 
				{
					long studentID = result.getLong("students.id");  
					String lastName = result.getString("last_name");
					String firstName = result.getString("first_name");
					String middleName = result.getString("middle_name");
					LocalDate birthDate = result.getDate("date_of_birth").toLocalDate();
					
					String groupNumber = result.getString("number"); 
					String facultyName = result.getString("faculty_name");
					long groupId = result.getLong("groups.id");
					
					Group group = new Group(groupId, groupNumber, facultyName);
					Student student = new Student(studentID, firstName, middleName, lastName, birthDate, group);
					
					students.add(student);
				}
		} 
		catch (SQLException e)
		{
			LOG.error("StudentDAO - readAllStudents() SQLException", e);
		}
		return students;
	}
	
	public String updateStudent(Student student) 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.UPDATE_STUDENT);)
		{
			ps.setString(1, student.getLastName());
			ps.setString(2, student.getFirstName());
			ps.setString(3, student.getMiddleName());
			ps.setDate(4, Date.valueOf(student.getBirthDate()));
			ps.setString(5, student.getGroup().getGroupNumber()); 
			ps.setLong(6, student.getId());
			ps.executeUpdate();
			return EMPTY_MESSAGE;
		} 
		catch (SQLException e)
		{
			LOG.error("StudentDAO - updateStudent() SQLException", e);
			return "SQL error";
		}
	}
	
	public void deleteStudent(long studentID) 
	{
		Connection connection = DBConnection.getInstance().getConnection();
		
		try(PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_STUDENT))
		{
			ps.setLong(1, studentID);
			ps.executeUpdate();
		} 
		catch (SQLException e)
		{
			LOG.error("StudentDAO - deleteStudent() SQLException", e);
		}
	}
}
