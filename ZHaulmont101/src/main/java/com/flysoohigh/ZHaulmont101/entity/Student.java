package com.flysoohigh.ZHaulmont101.entity;
import java.time.LocalDate;

public class Student 
{
	private long studentID;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthDate;
	private Group group;
	
	public Student()
	{
		
	}
	
	public Student(long studentID, String firstName, String middleName, String lastName, LocalDate birthDate, Group group) 
	{
		this.studentID = studentID;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.group = group;
	}

	public long getId() {
		return studentID;
	}

	public void setId(long studentID) {
		this.studentID = studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
