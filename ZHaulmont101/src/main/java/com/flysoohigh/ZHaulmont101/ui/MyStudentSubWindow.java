package com.flysoohigh.ZHaulmont101.ui;

import com.flysoohigh.ZHaulmont101.MyUI;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.entity.Student;
import com.flysoohigh.ZHaulmont101.service.ObjectService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class MyStudentSubWindow extends Window
{
	private TextField firstName = new TextField("First name");
	private TextField middleName = new TextField("Middle name");
	private TextField lastName = new TextField("Last name");
	private DateField birthDate = new DateField("Birthdate");		
	private ComboBox<Group> group = new ComboBox<>("Group number"); 
	
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	
	private ObjectService service;
	private Student student;
	private MyUI myUI;
	private Binder<Student> binder = new Binder<Student>(Student.class);
	
	public MyStudentSubWindow(MyUI myUI)
	{
		super("Add new student / Update student");
		
		center();
		setHeight("500px");
		setWidth("300px");
		setModal(true);
		
		this.myUI = myUI;
		this.service = new ObjectService();
		binder.bindInstanceFields(this);				

 		if (student != null)						//check if we update (not create) student
 		{
 			group.setValue(student.getGroup());		//then set default combobox value to current student group  			
 		}

 		group.setItems(service.findAllGroups());
 		group.setEmptySelectionAllowed(false);
 		group.setItemCaptionGenerator(Group::getGroupNumber);		
 																				
 		HorizontalLayout buttons = new HorizontalLayout(save, cancel);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		
		cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
		cancel.addClickListener(e -> cancel());
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponents(lastName, firstName, middleName, birthDate, group, buttons);
		
		setContent(mainLayout);
		
		UI.getCurrent().addWindow(this);
	}
	
	public void setStudent(Student student)
	{
		this.student = student;
		binder.setBean(student);
		lastName.selectAll();
	}

	private void cancel()
	{
		close();
	}

	private void save()
	{
		if (studentExists(student))	// check by helper method if student exists, if true, then it means we have to update it
		{
			try
			{
				service.updateStudent(student);
				myUI.updateStudentList();
				close();
			}
			catch(IllegalArgumentException ex)
			{
				Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			} 
		}
		else // otherwise, we have to create new student
		{
			try
			{
				service.createStudent(student);
				myUI.updateStudentList();
				close();
			}
			catch(IllegalArgumentException ex)
			{
				Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			}
		}
	}

	private boolean studentExists(Student studentToCheck)
	{
		return studentToCheck.getId() != 0;
	}
}
