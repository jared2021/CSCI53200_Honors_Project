package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;

public class LoginView implements ActionListener{
	
	//object to establish a connection to database
	private SQLConnection connection;
	
	//screen size parameters
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	//buttons, textboxes and labels
	private JFrame frame;
	private JPanel screen;
	private JButton login;
	private JTextField userName;
	private JLabel user;
	private JPasswordField password;
	private JLabel code;
	
	//arraylist to check to see if userName and password is valid and to store userName 
	private ArrayList<Integer> isUser = new ArrayList<Integer>();
	
	//arraylist to store assignmentlist for each class
	private ArrayList<AssignmentList> classAssignmentList = new ArrayList<AssignmentList>();
	
	//object to store classes
	private CollegeClassList collegeClassList;
	
	//object to display screen when a professor logs in
	private ProfessorView professorView;
	
	public LoginView(SQLConnection connection) {
		
		System.out.println("Creating screen");
		
		this.connection = connection;
		
		frame = new JFrame();
		frame.setSize(screenWidth, screenHeight);
		
		screen= new JPanel();
		screen.setLayout(null);
		
		userName = new JTextField();
		userName.setSize(100,25);
		userName.setLocation(screenWidth/2, screenHeight/2 - 50);
		
		user = new JLabel("USERNAME");
		user.setSize(100,25);
		user.setLocation(userName.getX() - 75, userName.getY());
		
		password = new JPasswordField();
		password.setSize(100,25);
		password.setLocation(userName.getX(), userName.getY() + 50);
		
		code = new JLabel("PASSWORD");
		code.setSize(100,25);
		code.setLocation(password.getX() - 75, password.getY());
		
		login = new JButton("Login");
		login.setSize(100,25);
		login.setLocation(password.getX(), password.getY() + 50);
		login.addActionListener(this);
		
		screen.add(userName);
		screen.add(password);
		screen.add(user);
		screen.add(code);
		screen.add(login);
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Login");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login)
		{
			System.out.println("Getting information from text fields");
			String potentialUser = userName.getText();
			String potentialPassword = password.getText();
			try {
					isUser.clear();
					isUser = connection.checkCredentials(potentialUser,potentialPassword);
					if(isUser.get(0) == 1)
					{
						System.out.println("Valid user");
						collegeClassList = connection.getCollegeClasses(isUser.get(1));
						professorView = new ProfessorView(collegeClassList, connection);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
					else if(isUser.get(0) == 0)
					{
						System.out.println("Either your username or password is incorrect");
					}
					else
					{
						System.out.println("Error: connection timed out");
					}
				}
			catch(SQLException f)
			{
				System.out.println("Something happened to the connection");
			}
		}
	}
}
