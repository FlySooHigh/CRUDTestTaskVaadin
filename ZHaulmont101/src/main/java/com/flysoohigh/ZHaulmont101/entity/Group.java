package com.flysoohigh.ZHaulmont101.entity;

public class Group 
{
	private long groupID;
	private String groupNumber;
	private String facultyName;
	
	public Group()
	{
		
	}
	
	public Group(long groupID, String groupNumber, String facultyName) 
	{
		this.groupID = groupID;
		this.groupNumber = groupNumber;
		this.facultyName = facultyName;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
}
