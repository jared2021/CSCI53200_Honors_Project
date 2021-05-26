package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class StudentResponseView implements ActionListener{

	private SQLConnection connection;
	private int aid;
	private int sid;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	
	private JLabel title;
	
	private JButton questionButton;
	private ArrayList<JButton> questionButtonList = new ArrayList<JButton>();
	
	private JButton submitGrade;
	
	private QuestionList questionList = new QuestionList();
	
	private GradeList gradeList = new GradeList();
	
	private ResponseView responseView;
	
	private JLabel message;
	
	public StudentResponseView(SQLConnection connection, int aid, int sid)
	{
		System.out.println(aid);
		this.connection = connection;
		this.aid = aid;
		this.sid = sid;
		
		questionList = connection.getQuestion(aid);
		
		frame = new JFrame();
		frame.setSize(screenWidth/2, screenHeight/2);
		
		screen= new JPanel();
		screen.setLayout(null);
		
		title = new JLabel("View Responses");
		title.setSize(100,25);
		title.setLocation(frame.getWidth()/4 + 50, 0);
		screen.add(title);
		
		for(int i=0;i<questionList.getSize();i++)
		{
			questionButton = new JButton(questionList.get(i).getQuestion());
			questionButton.setSize(300,25);
			if(questionButtonList.size() == 0)
			{
				questionButton.setLocation(frame.getWidth()/4, frame.getHeight()/4);
			}
			else
			{
				questionButton.setLocation(questionButtonList.get(i-1).getX(), questionButtonList.get(i-1).getY()+25);
			}
			questionButton.addActionListener(this);
			screen.add(questionButton);
			questionButtonList.add(questionButton);
		}
		
		submitGrade = new JButton("Submit Grade");
		submitGrade.setSize(100,25);
		submitGrade.setLocation(frame.getWidth()/4, questionButtonList.get(questionButtonList.size() - 1).getY() + 75);
		submitGrade.addActionListener(this);
		submitGrade.setVisible(false);
		screen.add(submitGrade);
		
		message = new JLabel();
		message.setSize(500,25);
		message.setLocation(submitGrade.getX(), submitGrade.getY() - 25);
		screen.add(message);
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("View Responses");
		frame.setVisible(true);
	}
	
	public void getGrade(Grade grade)
	{
		boolean gradeExists = false;
		for(int i=0;i<gradeList.getSize();i++)
		{
			if(gradeList.get(i).getGID() == grade.getGID())
			{
				gradeList.get(i).setScore(grade.getScore());
				gradeExists = true;
			}
		}
		if(!gradeExists)
		{
			gradeList.addGrade(grade.getGID(), grade.getAID(), grade.getScore(), grade.getScore(), grade.getTotal());
		}
		if(gradeList.getSize() == questionList.getSize())
		{
			submitGrade.setVisible(true);
			screen.repaint();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<questionButtonList.size();i++)
		{
			if(e.getSource() == questionButtonList.get(i))
			{
				responseView = new ResponseView(connection, this, questionList.get(i).getQID(), sid, aid);
			}
		}
		
		if(e.getSource() == submitGrade)
		{
			System.out.println(connection.getAssignment(aid).getTotal());
			if(connection.createGrade(gradeList, aid, sid, connection.getAssignment(aid).getTotal()))
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
			else
			{	
				message.setText("Something went wrong when trying to create the grade");
			}
		}
	}
}
