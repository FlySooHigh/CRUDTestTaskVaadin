package com.flysoohigh.ZHaulmont101;

import javax.servlet.annotation.WebServlet;
import com.flysoohigh.ZHaulmont101.entity.Group;
import com.flysoohigh.ZHaulmont101.entity.Student;
import com.flysoohigh.ZHaulmont101.service.ObjectService;
import com.flysoohigh.ZHaulmont101.ui.MyGroupSubWindow;
import com.flysoohigh.ZHaulmont101.ui.MyStudentSubWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class MyUI extends UI 
{
	private ObjectService service = new ObjectService();			

	private TextField filterNameGroup = new TextField();
	private Grid<Group> groupTable = new Grid<>(Group.class);
	private Grid<Student> studentTable = new Grid<>(Student.class);

	@Override
	protected void init(VaadinRequest vaadinRequest) 
	{
		VerticalLayout groupLayout = setUpGroupLayout();
		VerticalLayout studentLayout = setUpStudentLayout();
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.addComponentsAndExpand(groupLayout, studentLayout);
		
		setContent(mainLayout);
	}

	private VerticalLayout setUpStudentLayout()
	{
		VerticalLayout studentLayout = new VerticalLayout();
		
		studentTable.setColumns("id", "lastName", "firstName", "middleName", "birthDate");
		studentTable.setSizeFull();
		studentTable.addColumn(s -> s.getGroup().getGroupNumber()).setCaption("Group number");
		
        filterNameGroup.setPlaceholder("filter by name/group...");
        filterNameGroup.addValueChangeListener(e -> updateStudentList());
        filterNameGroup.setValueChangeMode(ValueChangeMode.LAZY);
        
        Button clearFilterNameBtn = new Button(VaadinIcons.ERASER);
        clearFilterNameBtn.setDescription("Clear the filter");
        clearFilterNameBtn.addClickListener(e -> filterNameGroup.clear());
        
        CssLayout filterNameLayout = new CssLayout();
        filterNameLayout.addComponents(filterNameGroup, clearFilterNameBtn);
        filterNameLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		Button createStudentBtn = new Button("Add new student");
		createStudentBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		createStudentBtn.addClickListener(e -> {
			new MyStudentSubWindow(this).setStudent(new Student());
		});
		
		Button updateStudentBtn = new Button("Update student");	
		updateStudentBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		updateStudentBtn.setVisible(false);
		updateStudentBtn.addClickListener(e -> {
			new MyStudentSubWindow(this).setStudent(studentTable.asSingleSelect().getValue());
		});
		
		Button deleteStudentBtn = new Button("Delete student");	
		deleteStudentBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteStudentBtn.setVisible(false);
		deleteStudentBtn.addClickListener(e -> {
			service.deleteStudent(studentTable.asSingleSelect().getValue());
			updateStudentList();
		});
		
		updateStudentList();
		
		studentTable.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null)											// if nothing is selected 
			{
				updateStudentBtn.setVisible(false);									// we keep update and delete buttons hidden
				deleteStudentBtn.setVisible(false);
			}
			else
			{
				updateStudentBtn.setVisible(true);									// otherwise, we show the buttons
				deleteStudentBtn.setVisible(true);
			}
		});
		
		HorizontalLayout studentBtns = new HorizontalLayout();
		studentBtns.addComponents(createStudentBtn, updateStudentBtn, deleteStudentBtn);
		
		studentLayout.addComponents(filterNameLayout, studentTable, studentBtns);
		return studentLayout;
	}

	private VerticalLayout setUpGroupLayout()
	{
		VerticalLayout groupLayout = new VerticalLayout();

		groupTable.setColumns("groupNumber", "facultyName");
		groupTable.setSizeFull();

		Button createGroupBtn = new Button("Add new group");
		createGroupBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		createGroupBtn.addClickListener(e -> {
			new MyGroupSubWindow(this).setGroup(new Group());
		});
		

		Button updateGroupBtn = new Button("Update group");	
		updateGroupBtn.setVisible(false);
		updateGroupBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		updateGroupBtn.addClickListener(e -> {
			new MyGroupSubWindow(this).setGroup(groupTable.asSingleSelect().getValue());
		});
		

		Button deleteGroupBtn = new Button("Delete group");		
		deleteGroupBtn.setVisible(false);
		deleteGroupBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		deleteGroupBtn.addClickListener(e -> {
			if (service.deleteGroup(groupTable.asSingleSelect().getValue()))	// if group is empty and was successfully deleted 
			{
				updateGroupList();												// then we just update the group list
			}
			else 
			{
				groupTable.select(groupTable.asSingleSelect().getValue());		// otherwise, we just keep the group selected
			}
		});

		updateGroupList();

		groupTable.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null)										// if nothing is selected 
			{
				updateGroupBtn.setVisible(false);								// we keep update and delete buttons hidden
				deleteGroupBtn.setVisible(false);	
				filterNameGroup.clear();										// and clear filter field
			}
			else
			{
				updateGroupBtn.setVisible(true);								// otherwise, we show the buttons	
				deleteGroupBtn.setVisible(true);
				filterNameGroup.setValue(event.getValue().getGroupNumber());	// and insert the group number into filter
			}																	// to show students in the group
		});
		

		HorizontalLayout groupBtns = new HorizontalLayout();
		groupBtns.addComponents(createGroupBtn, updateGroupBtn, deleteGroupBtn);

		groupLayout.addComponents(groupTable, groupBtns);
		return groupLayout;
	}

	public void updateGroupList()
	{
		groupTable.setItems(service.findAllGroups());
	}
	
	public void updateStudentList()
	{
		studentTable.setItems(service.findAll(filterNameGroup.getValue()));
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet 
	{
		
	}
}
