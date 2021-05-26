package ClassManager;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class CreateAssignmentView implements ActionListener{

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	private SQLConnection connection;
	private int cid;
	
	private JLabel assignmentName;
	private JTextField name;
	
	private JLabel assignmentDescription;
	private JTextField description;
	
	private JLabel dueMonth;
	private JLabel dueDay;
	private JLabel dueYear;
	private JTextField month;
	private JTextField day;
	private JTextField year;
	
	private JLabel totalPoints;
	private JTextField total;
	
	private JLabel dueTime;
	private JComboBox submitHour;
	private JComboBox submitMinute;
	private JComboBox submitPeriod;
	
	private String[] hour = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private String[] minute = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
	private String[] period = {"AM","PM"};
	
	private JButton submit;
	
	private JLabel message;
	
	private boolean isValid = true;
	
	private CreateQuestionView createQuestionView;
	
	public CreateAssignmentView(SQLConnection connection, int cid)
	{
		this.connection = connection;
		this.cid = cid;
		
		frame = new JFrame();
		frame.setSize(screenWidth/2, screenHeight/2);
		
		screen = new JPanel();
		screen.setLayout(null);
		
		assignmentName = new JLabel("Assignment");
		assignmentName.setSize(100,25);
		assignmentName.setLocation(frame.getWidth()/4, frame.getHeight()/4);
		screen.add(assignmentName);
		
		name = new JTextField();
		name.setSize(300, 25);
		name.setLocation(assignmentName.getX() + 100, assignmentName.getY());
		screen.add(name);
		
		assignmentDescription = new JLabel("Description");
		assignmentDescription.setSize(100,25);
		assignmentDescription.setLocation(assignmentName.getX(),assignmentName.getY() + 75);
		screen.add(assignmentDescription);
		
		description = new JTextField();
		description.setSize(300,25);
		description.setLocation(assignmentDescription.getX() + 100, assignmentDescription.getY());
		screen.add(description);
		
		dueMonth = new JLabel("Month");
		dueMonth.setSize(100,25);
		dueMonth.setLocation(assignmentDescription.getX() - 100, assignmentDescription.getY() + 75);
		screen.add(dueMonth);
		
		month = new JTextField();
		month.setSize(40,25);
		month.setLocation(dueMonth.getX() + 50, dueMonth.getY());
		screen.add(month);
		
		dueDay = new JLabel("Day");
		dueDay.setSize(50,25);
		dueDay.setLocation(month.getX() + 75, month.getY());
		screen.add(dueDay);
		
		day = new JTextField();
		day.setSize(40,25);
		day.setLocation(dueDay.getX() + 40, dueDay.getY());
		screen.add(day);
		
		dueYear = new JLabel("Year");
		dueYear.setSize(50,25);
		dueYear.setLocation(day.getX() + 75, day.getY());
		screen.add(dueYear);
		
		year = new JTextField();
		year.setSize(40,25);
		year.setLocation(dueYear.getX() + 50, dueYear.getY());
		screen.add(year);
		
		dueTime = new JLabel("Due Time:");
		dueTime.setSize(75,25);
		dueTime.setLocation(year.getX() + 50, year.getY());
		screen.add(dueTime);
		
		submitHour = new JComboBox(hour);
		submitHour.setSize(50,25);
		submitHour.setLocation(dueTime.getX() + 75, dueTime.getY());
		screen.add(submitHour);
		
		submitMinute = new JComboBox(minute);
		submitMinute.setSize(50,25);
		submitMinute.setLocation(submitHour.getX() + 75, submitHour.getY());
		screen.add(submitMinute);
		
		submitPeriod = new JComboBox(period);
		submitPeriod.setSize(50,25);
		submitPeriod.setLocation(submitMinute.getX() + 75, submitMinute.getY());
		screen.add(submitPeriod);
		
		totalPoints = new JLabel("Total Points:");
		totalPoints.setSize(75,25);
		totalPoints.setLocation(submitPeriod.getX() + 75, submitPeriod.getY());
		screen.add(totalPoints);
		
		total = new JTextField();
		total.setSize(50,25);
		total.setLocation(totalPoints.getX() + 75, totalPoints.getY());
		screen.add(total);
		
		submit = new JButton("Create Assignment");
		submit.setSize(150,25);
		submit.setLocation(submitMinute.getX() - 250, submitMinute.getY() + 100);
		submit.addActionListener(this);
		screen.add(submit);
		
		message = new JLabel("");
		message.setSize(300,25);
		message.setLocation(submit.getX(), submit.getY() + 75);
		screen.add(message);
		
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Create New Assignment");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == submit)
		{
			LocalDate currentDate = LocalDate.now();
			String due = "";
			isValid = true;
			if(currentDate.getYear() > Integer.parseInt(year.getText())
			|| this.getMonth(currentDate.getMonth().toString()) > Integer.parseInt(month.getText()) && currentDate.getYear() >= Integer.parseInt(year.getText())
			|| currentDate.getDayOfMonth() >= Integer.parseInt(day.getText()) && this.getMonth(currentDate.getMonth().toString()) >= Integer.parseInt(month.getText()) && currentDate.getYear() >= Integer.parseInt(year.getText()))
			{
				message.setVisible(false);
				message.setText("Invalid date");
				message.setVisible(true);
				isValid = false;
			}
			String potentialMonth = month.getText();
			String potentialDay = day.getText();
			if(month.getText().length() == 1)
			{
				potentialMonth = "0" + month.getText();
			}
			if(day.getText().length() == 1)
			{
				potentialDay = "0" + day.getText();
			}
			due = year.getText() + potentialMonth + potentialDay + " " + submitHour.getSelectedItem().toString() + ":" + submitMinute.getSelectedItem().toString() + ":00 " + submitPeriod.getSelectedItem().toString();
			if(isValid)
			{
				try {
					if(connection.createAssignment(this.cid, name.getText(), description.getText(), due, Integer.parseInt(total.getText())))
					{
						message.setVisible(false);
						message.setText("Assignment successfully created");
						message.setVisible(true);
						createQuestionView = new CreateQuestionView(connection, connection.getAssignmentID(cid, name.getText()));
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
					else
					{
						message.setVisible(false);
						message.setText("Something went wrong");
						message.setVisible(true);
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					message.setText("Invalid total, must be a number");
				}	 
			}
		}
	}
	public int getMonth(String month)
	{
		if(month.equalsIgnoreCase("january"))
		{
			return 1;
		}
		else if(month.equalsIgnoreCase("february"))
		{
			return 2;
		}
		else if(month.equalsIgnoreCase("march"))
		{
			return 3;
		}
		else if(month.equalsIgnoreCase("april"))
		{
			return 4;
		}
		else if(month.equalsIgnoreCase("may"))
		{
			return 5;
		}
		else if(month.equalsIgnoreCase("june"))
		{
			return 6;
		}
		else if(month.equalsIgnoreCase("july"))
		{
			return 7;
		}
		else if(month.equalsIgnoreCase("august"))
		{
			return 8;
		}
		else if(month.equalsIgnoreCase("september"))
		{
			return 9;
		}
		else if(month.equalsIgnoreCase("october"))
		{
			return 10;
		}
		else if(month.equalsIgnoreCase("november"))
		{
			return 11;
		}
		else
		{
			return 12;
		}
	}
}
