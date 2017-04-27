package com.flysoohigh.ZHaulmont101.service;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import com.flysoohigh.ZHaulmont101.dao.GroupDAO;
import com.flysoohigh.ZHaulmont101.dao.StudentDAO;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.entity.Student;
import com.vaadin.ui.Notification;

public class ObjectService
{
	private GroupDAO groupDAO = new GroupDAO();									
	private StudentDAO studentDAO = new StudentDAO(); 
	
	private static final Logger LOG = Logger.getLogger(ObjectService.class);
	
	/* simple patterns to check input data validity, could be enhanced in future if needed */
	private static final String NAME_PATTERN = "[A-Z][a-z]{1,15}"; 
//	private static final String DATE_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}"; 
	private static final String GROUP_NUMBER_PATTERN = "[0-9]{3}";
	private static final String FACULTY_NAME_PATTERN = "[A-Z][a-z]{1,15}";
	
	
	public void createGroup(Group group) throws SQLException
	{
		if (group.getGroupNumber() == null || !group.getGroupNumber().matches(GROUP_NUMBER_PATTERN))
		{
			LOG.info("Service - createGroup() Invalid group number format");
			throw new IllegalArgumentException("Invalid group number format: it must be in the form 'NNN'"); 
		}
		if (group.getFacultyName() == null || !group.getFacultyName().matches(FACULTY_NAME_PATTERN))
		{
			LOG.info("Service - createGroup() Invalid faculty name format");
			throw new IllegalArgumentException("Invalid faculty name format: it must start with capital letter and then "
					+ "consist of lowercase letters only and be 16 characters max");
		}
		
		String msg = groupDAO.addGroup(group);
		if (msg.isEmpty())	
		{
			Notification.show("Success", "GROUP was successfully CREATED", Notification.Type.WARNING_MESSAGE);
		}
		else 
		{
			Notification.show("Error", msg, Notification.Type.ERROR_MESSAGE);
		}
	}
	
	
	public boolean deleteGroup(Group group)
	{
		String msg = groupDAO.deleteGroup(group.getGroupID());
		
		if (msg.isEmpty())
		{
			Notification.show("Success", "GROUP was successfully DELETED", Notification.Type.WARNING_MESSAGE);
			LOG.info("Service - deleteGroup() Group record was successfully deleted from database");
			return true;
		}

		Notification.show("Error", msg, Notification.Type.ERROR_MESSAGE);
		LOG.info("Service - deleteGroup() Group record was not deleted from database due to DB constraint");
		return false;


	}
	
	
	public void deleteStudent(Student student)
	{
		studentDAO.deleteStudent(student.getId());
		Notification.show("Success", "STUDENT was successfully DELETED", Notification.Type.WARNING_MESSAGE);
	}
	
	
	public void updateGroup(Group group) throws SQLException
	{
		if (group.getGroupNumber() == null || !group.getGroupNumber().matches(GROUP_NUMBER_PATTERN))
		{
			LOG.info("Service - updateGroup() Invalid group number format");
			throw new IllegalArgumentException("Invalid group number format: it must be in the form 'NNN'");
		}
		if (group.getFacultyName() == null || !group.getFacultyName().matches(FACULTY_NAME_PATTERN))
		{
			LOG.info("Service - updateGroup() Invalid faculty name format");
			throw new IllegalArgumentException("Invalid faculty name format: it must start with capital letter and then "
					+ "consist of lowercase letters only and be 16 characters max");
		}
		
		String msg = groupDAO.updateGroup(group);
		
		if (msg.isEmpty())
		{
			Notification.show("Success", "GROUP was successfully UPDATED", Notification.Type.WARNING_MESSAGE);
		}
		else 
		{
			Notification.show("Error", msg, Notification.Type.ERROR_MESSAGE);
		}
		
		LOG.info("Service - updateGroup() Group record was successfully updated in database");	
	}

	
	public List<Group> findAllGroups()
	{
		return groupDAO.readAllGroups();
	}
	
	
	public List<Student> findAll(String stringFilter) 
	{
		List<Student> studentList = studentDAO.readAllStudents();
		
		if (stringFilter != null && !stringFilter.isEmpty())
		{
			studentList.removeIf(student -> { 
				return	!(student.getLastName().toLowerCase().contains(stringFilter.toLowerCase()) ||
					    student.getGroup().getGroupNumber().contains(stringFilter));
			});
		}
		
		return studentList;
	}

	
	public void createStudent(Student student)
	{
		if (isBadName(student.getLastName()))
		{
			LOG.info("Service - createStudent() Invalid student last name");
			throw new IllegalArgumentException("Invalid student last name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (isBadName(student.getFirstName()))
		{
			LOG.info("Service - createStudent() Invalid student first name");
			throw new IllegalArgumentException("Invalid student first name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (isBadName(student.getMiddleName()))
		{
			LOG.info("Service - createStudent() Invalid student middle name");
			throw new IllegalArgumentException("Invalid student middle name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (student.getBirthDate() == null)
		{
			LOG.info("Service - createStudent() Invalid student birthdate name");
			throw new IllegalArgumentException("You have to choose date of birth");		
		}
														
		if (student.getGroup() == null)
		{
			LOG.info("Service - createStudent() Student group is null");
			throw new IllegalArgumentException("You have to select one of the available group numbers");
		}															
		
		String msg = studentDAO.addStudent(student);
		
		if (msg.isEmpty())
		{
			Notification.show("Success", "STUDENT was successfully ADDED", Notification.Type.WARNING_MESSAGE);
		}
		else 
		{
			Notification.show("Error", msg, Notification.Type.ERROR_MESSAGE);
		}
		
		LOG.info("Servce createStudent() Student record was successfully added to database");			
	}
	
	
	public void updateStudent(Student student)
	{
		if (isBadName(student.getLastName()))
		{
			LOG.info("Service - updateStudent() Invalid student last name");
			throw new IllegalArgumentException("Invalid student last name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (isBadName(student.getFirstName()))
		{
			LOG.info("Service - updateStudent() Invalid student first name");
			throw new IllegalArgumentException("Invalid student first name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (isBadName(student.getMiddleName()))
		{
			LOG.info("Service - updateStudent() Invalid student middle name");
			throw new IllegalArgumentException("Invalid student middle name. The name must "
					+ "start with capital letter and be 16 characters max");
		}
		if (student.getBirthDate() == null)
		{
			LOG.info("Service - updateStudent() Date of birth is null");
			throw new IllegalArgumentException("You have to choose date of birth");
		}
		
		String msg = studentDAO.updateStudent(student);
		
		if (msg.isEmpty())
		{
			Notification.show("Success", "STUDENT was successfully UPDATED", Notification.Type.WARNING_MESSAGE);
		}
		else 
		{
			Notification.show("Error", msg, Notification.Type.ERROR_MESSAGE);
		}
		
		LOG.info("Service - updateStudent() Student record was successfully updated in database");	
	}
	
	
	/* helper method to check that String is not null and complies with pattern*/
	private boolean isBadName(String lastName)
	{
		return lastName == null || !lastName.matches(NAME_PATTERN);
	}
}

