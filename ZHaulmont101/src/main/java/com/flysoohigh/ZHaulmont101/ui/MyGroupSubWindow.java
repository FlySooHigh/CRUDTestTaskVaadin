package com.flysoohigh.ZHaulmont101.ui;

import java.sql.SQLException;

import com.flysoohigh.ZHaulmont101.MyUI;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.service.ObjectService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class MyGroupSubWindow extends Window
{
	private TextField groupNumber = new TextField("Group number");
	private TextField facultyName = new TextField("Faculty name");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	
	private ObjectService service;
	private Group group;
	private MyUI myUI;
	private Binder<Group> binder = new Binder<Group>(Group.class);
	
	public MyGroupSubWindow(MyUI myUI)
	{
		super("Add new group / Update group");
		
		center();
		setHeight("300px");
		setWidth("300px");
		setModal(true);
		
		this.myUI = myUI;
		this.service = new ObjectService();
		binder.bindInstanceFields(this);
		
		HorizontalLayout buttons = new HorizontalLayout(save, cancel);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		cancel.addClickListener(e -> cancel());
		cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		VerticalLayout groupWindowLayout = new VerticalLayout();
		groupWindowLayout.addComponents(groupNumber, facultyName, buttons);
		
		setContent(groupWindowLayout);
		
		UI.getCurrent().addWindow(this);
	}
	
	public void setGroup(Group group)
	{
		this.group = group;
		binder.setBean(group);
		groupNumber.selectAll();
	}

	private void cancel()
	{
		close();
	}

	private void save()
	{
		if (groupExists(group))	// check by helper method if group exists, if true, then it means we have to update it			 
		{
			try
			{
				service.updateGroup(group);			
				myUI.updateGroupList();
				close();
			}
			catch(IllegalArgumentException ex)	
			{
				Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);	
			} 
			catch (SQLException ex)
			{
				Notification.show("Error", "You cannot change number of non-empty GROUP or "
						+ "GROUP with such number already exists.", Notification.Type.ERROR_MESSAGE);
			}
		}
		else // otherwise, we have to create new group
		{
			try
			{
				service.createGroup(group);
				myUI.updateGroupList();
				close();
			}
			catch(IllegalArgumentException ex)
			{
				Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			}
			catch(SQLException ex)
			{
				Notification.show("Error", "GROUP with such number already EXISTS. Choose "
						+ "another group number.", Notification.Type.ERROR_MESSAGE);
			}
		}
	}

	private boolean groupExists(Group groupToCheck)
	{
		return (groupToCheck.getGroupID() != 0);
	}
}
