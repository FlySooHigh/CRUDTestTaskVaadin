package com.flysoohigh.ZHaulmont101.util;

public class SQLQueries
{
	public static final String ADD_NEW_STUDENT = "INSERT INTO students (last_name, first_name, middle_name, date_of_birth, student_group) " 
											   + "VALUES (?, ?, ?, ?, ?)";
	
	public static final String SELECT_ALL_STUDENTS = "SELECT *"
												  + " FROM students"
												  + " LEFT JOIN groups ON students.student_group = groups.number";		
	
	public static final String UPDATE_STUDENT = "UPDATE students "
											  + "SET last_name=?, first_name=?, middle_name=?, date_of_birth=?, student_group=? "
											  + "WHERE id=?";
	
	public static final String DELETE_STUDENT = "DELETE FROM students "
											  + "WHERE id=?";
	
	public static final String ADD_NEW_GROUP = "INSERT INTO groups (number, faculty_name) "
											 + "VALUES (?, ?)";
	
	public static final String SELECT_ALL_GROUPS = "SELECT id, number, faculty_name "
												 + "FROM groups "
												 + "ORDER BY number";
	
	public static final String UPDATE_GROUP = "UPDATE groups SET number=?, faculty_name=? "
											+ "WHERE id=?";
	
	public static final String DELETE_GROUP = "DELETE FROM groups "
											+ "WHERE id=?";
}
