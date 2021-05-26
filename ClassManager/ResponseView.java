package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class ResponseView implements ActionListener{

	private SQLConnection connection;
	private StudentResponseView studentResponseView;
	private int qid;
	private int sid;
	private int aid;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	
	private JLabel title;
	private JLabel questionLabel;
	private JLabel responseLabel;
	private JLabel scoreLabel;
	
	private JTextField score;
	
	private JButton submit;
	
	private Question question;
	
	private QuestionList questionList;
	
	private AssignmentList assignmentList;
	
	private Response response;
	
	private Grade grade;
	
	private JLabel message;
	
	public ResponseView(SQLConnection connection, StudentResponseView studentResponseView, int qid, int sid, int aid)
	{
		this.connection = connection;
		this.studentResponseView = studentResponseView;
		this.qid = qid;
		this.sid = sid;
		
		response = connection.getResponse(qid, sid);
		
		questionList = connection.getQuestion(aid);
		
		for(int i=0;i<questionList.getSize();i++)
		{
			if(questionList.get(i).getQID() == qid)
			{
				question = questionList.get(i);
			}
		}
		
		frame = new JFrame();
		frame.setSize(screenWidth/2, screenHeight/2);
		
		screen= new JPanel();
		screen.setLayout(null);
		
		title = new JLabel("Grade Response");
		title.setSize(100,25);
		title.setLocation(frame.getWidth()/4 + 50, 0);
		screen.add(title);
		
		questionLabel = new JLabel(question.getQuestion());
		questionLabel.setSize(400,25);
		questionLabel.setLocation(title.getX(), title.getY() + 50);
		screen.add(questionLabel);
		
		responseLabel = new JLabel(response.getAnswer());
		responseLabel.setSize(400,25);
		responseLabel.setLocation(questionLabel.getX(), questionLabel.getY() + 50);
		screen.add(responseLabel);
		
		score = new JTextField();
		score.setSize(50,25);
		score.setLocation(responseLabel.getX(), responseLabel.getY() + 50);
		screen.add(score);
		
		scoreLabel = new JLabel("/" + question.getPoints());
		scoreLabel.setSize(50,25);
		scoreLabel.setLocation(score.getX() + 50, score.getY());
		screen.add(scoreLabel);
		
		submit = new JButton("Submit score");
		submit.setSize(150,25);
		submit.setLocation(score.getX(),score.getY() + 100);
		submit.addActionListener(this);
		screen.add(submit);
		
		message = new JLabel();
		message.setSize(500,25);
		message.setLocation(submit.getX(),submit.getY() + 50);
		screen.add(message);
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Designate Points");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == submit)
		{
			try
			{
				if(Integer.parseInt(score.getText()) > question.getPoints() || Integer.parseInt(score.getText()) < 0)
				{
					message.setText("Invalid input, must be less than the questions total score and non-negative");
				}
				else
				{
				grade = new Grade(qid, aid, sid, Integer.parseInt(score.getText()), question.getPoints());
				studentResponseView.getGrade(grade);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			catch(NumberFormatException f)
			{
				message.setText("Invalid input, must be a number");
			}
		}
	}
}
