package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class ProfessorView implements ActionListener{

	//object to store a list of classes
	private CollegeClassList collegeClassList;
	
	//object to store a list of assignments
	private AssignmentList assignmentList;
	
	//arraylist to store buttons that will change what is on the screen depending on the class
	private ArrayList<JButton> collegeClassButtonList = new ArrayList<JButton>();
	
	//object used to connect to database
	private SQLConnection connection;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	private JButton classButton;
	private JButton home;
	private JButton assignment;
	private JButton grades;
	private JLabel screenLabel;
	
	//used to store what class and what tab the screen is currently showing
	private String currentTab;
	private CollegeClass currentClass;
	
	//screen items for home screen only
	private JLabel classGreeting;
	
	//screen items for assignment screen only
	private JButton assignmentButton;
	private ArrayList<JButton> assignmentButtonList = new ArrayList<JButton>();
	private JButton createAssignmentButton;
	
	//object that will allow user to create a new assignment if user clicks on createAssignmentButton
	private CreateAssignmentView createAssignmentView;
	
	private CreateGradeView createGradeView;
	
	public ProfessorView(CollegeClassList collegeClassList, SQLConnection connection) 
	{
		this.collegeClassList = collegeClassList;
		//this.classAssignmentList = classAssignmentList;
		this.connection = connection;
		
		this.currentTab = "home";
		this.currentClass = collegeClassList.get(0);
		
		//enstantiates the screen and items that will be prevalent throughout each screen
		frame = new JFrame();
		frame.setSize(screenWidth, screenHeight);
		
		screen= new JPanel();
		screen.setLayout(null);
		
		for(int i=0;i<collegeClassList.getSize();i++)
		{
			classButton = new JButton(collegeClassList.get(i).getName());
			if(i==0)
			{
				classButton.setLocation(0, 0);
			}
			else
			{
				classButton.setLocation(collegeClassButtonList.get(i - 1).getX(), collegeClassButtonList.get(i - 1).getY() + collegeClassButtonList.get(i - 1).getHeight());
			}
			classButton.setSize(210,50);
			classButton.addActionListener(this);
			collegeClassButtonList.add(classButton);
		}
		
		//creating buttons for tabs
		home = new JButton("Home");
		home.setLocation(collegeClassButtonList.get(0).getX() + collegeClassButtonList.get(0).getWidth(), 0);
		home.setSize(110,50);
		home.addActionListener(this);
		screen.add(home);
		
		assignment = new JButton("Assignments");
		assignment.setLocation(home.getX(), home.getY() + home.getHeight());
		assignment.setSize(110,50);
		assignment.addActionListener(this);
		screen.add(assignment);
		
		grades = new JButton("Grades");
		grades.setLocation(assignment.getX(), assignment.getY() + assignment.getHeight());
		grades.setSize(110,50);
		grades.addActionListener(this);
		screen.add(grades);
		//creating buttons for tabs
		
		screenLabel = new JLabel(collegeClassList.get(0).getName());
		screenLabel.setSize(300,50);
		screenLabel.setLocation(screenWidth/2 - (int)(.5*screenLabel.getWidth()),0);
		screen.add(screenLabel);
		
		for(int i=0;i<collegeClassButtonList.size();i++)
		{
			screen.add(collegeClassButtonList.get(i));
		}
		
		//screen items only prevalent to home screen		
		classGreeting = new JLabel("Welcome to " + collegeClassList.get(0).getName());
		classGreeting.setSize(300,50);
		classGreeting.setLocation(screenWidth/2 - (int)(.5*classGreeting.getWidth()), screenLabel.getY() + 100);
		screen.add(classGreeting);
		//screen items only prevalent to home screen
		
		//screen items only prevalent to assignment screen
		createAssignmentButton = new JButton("Create new Assignment");
		createAssignmentButton.setSize(175,50);
		createAssignmentButton.setLocation(screenWidth - createAssignmentButton.getWidth() - 50,0);
		createAssignmentButton.addActionListener(this);
		createAssignmentButton.setVisible(false);
		screen.add(createAssignmentButton);
		//screen items only prevalent to assignment screen
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Portal");
		frame.setVisible(true);
	}
	
	public void getAssignmentButtonList()
	{
		try {
			assignmentList = connection.getAssignments(currentClass.getCID());
			if(assignmentButtonList.size() > 0)
			{
				for(int i=0;i<assignmentButtonList.size();i++)
				{
					assignmentButtonList.get(i).removeActionListener(this);
					screen.remove(assignmentButtonList.get(i));
				}
			}
			assignmentButtonList.clear();
			for(int j=0;j<assignmentList.getSize();j++)
			{
				assignmentButton = new JButton(assignmentList.get(j).getName());
				assignmentButton.setSize(150,50);
				if(assignmentButtonList.size() == 0)
				{
					assignmentButton.setLocation(screenWidth/2 - (int)(.5*assignmentButton.getWidth()), 100);
				}
				else
				{
					assignmentButton.setLocation(assignmentButtonList.get(j - 1).getX(), assignmentButtonList.get(j-1).getY() + 100);
				}
				assignmentButton.addActionListener(this);
				assignmentButtonList.add(assignmentButton);
			}
			for(int i=0;i<assignmentButtonList.size();i++)
			{
				screen.add(assignmentButtonList.get(i));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to get assignments");
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//functionality if a class button gets clicked
		for(int i=0;i<collegeClassButtonList.size();i++)
		{
			if(e.getSource() == collegeClassButtonList.get(i))
			{
				System.out.println("I'm in a if statement");
				currentClass = collegeClassList.get(i);
				currentTab = "home";
				for(int j=0;j<assignmentButtonList.size();j++)
				{
					assignmentButtonList.get(j).setVisible(false);
				}
				createAssignmentButton.setVisible(false);
				classGreeting.setVisible(true);
				screenLabel.setText(currentClass.getName());
				screenLabel.setSize(300,50);
				screenLabel.setLocation(screenWidth/2 - (int)(.5*screenLabel.getWidth()),0);
				classGreeting.setText("Welcome to " + currentClass.getName());
				screen.repaint();
			}
		}
		
		//functionality if home is clicked
		if(e.getSource() == home)
		{
			for(int j=0;j<assignmentButtonList.size();j++)
			{
				assignmentButtonList.get(j).setVisible(false);
			}
			createAssignmentButton.setVisible(false);
			currentTab = "home";
			screenLabel.setText(currentClass.getName());
			screenLabel.setSize(300,50);
			screenLabel.setLocation(screenWidth/2 - (int)(.5*screenLabel.getWidth()),0);
			classGreeting.setVisible(true);
			screen.repaint();
		}
		//functionality if home is clicked
		
		//functionality if Assignment gets clicked
		else if(e.getSource() == assignment)
		{
			currentTab = "assignments";
			screenLabel.setText("Assignments");
			screenLabel.setSize(100,50);
			screenLabel.setLocation(screenWidth/2 - (int)(.5*screenLabel.getWidth()),0);
			this.getAssignmentButtonList();
			classGreeting.setVisible(false);
			createAssignmentButton.setVisible(true);
			screen.repaint();
		}
		//functionality if assignment is clicked
		
		//functionality if grades is clicked
		else if(e.getSource() == grades)
		{
			currentTab = "grades";
			screenLabel.setText("Enter Grades");
			screenLabel.setSize(100,50);
			screenLabel.setLocation(screenWidth/2 - (int)(.5*screenLabel.getWidth()),0);
			this.getAssignmentButtonList();
			classGreeting.setVisible(false);
			createAssignmentButton.setVisible(false);
			screen.repaint();
		}
		//functionality if grades is clicked
		
		//functionality if create assignment button is clicked
		if(e.getSource() == createAssignmentButton)
		{
			createAssignmentView = new CreateAssignmentView(connection, currentClass.getCID());
		}
		//functionality if create assignment button is clicked
	
		//functionality if an assignment button is clicked when the current tab is grades
		for(int i=0;i<assignmentButtonList.size();i++)
		{
			if(e.getSource() == assignmentButtonList.get(i) && currentTab.equals("grades"))
			{
				createGradeView = new CreateGradeView(connection, assignmentList.get(i).getAID(), currentClass.getCID());
			}
		}
		//functionality if an assignment button is clicked when the current tab is grades
		
	}
	
}
