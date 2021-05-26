package ClassManager;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class CreateQuestionView implements ActionListener{

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	private SQLConnection connection;
	
	private QuestionList questionList = new QuestionList();
	
	private int aid;
	
	private JLabel questionLabel;
	private JTextField question;
	
	private JLabel pointsLabel;
	private JTextField points;
	
	private JButton submitQuestion;
	
	private JLabel message;
	
	private int totalPoints;
	private int currentTotalPoints;
	
	private int qid;
	
	private JButton questionButton;
	private ArrayList<JButton> questionButtonList = new ArrayList<JButton>();
	
	private EditQuestionView editQuestionView;
	
	public CreateQuestionView(SQLConnection connection, int aid)
	{
		this.connection = connection;
		this.aid = aid;
		questionList.clear();
		qid = 0;
		
		totalPoints = connection.getAssignmentTotalPoints(aid);
		
		frame = new JFrame();
		frame.setSize(screenWidth/2, screenHeight/2);
		
		screen = new JPanel();
		screen.setLayout(null);
		
		questionLabel = new JLabel("Question:");
		questionLabel.setSize(100,25);
		questionLabel.setLocation(frame.getWidth()/4 - 100, frame.getHeight()/4);
		screen.add(questionLabel);
		
		question = new JTextField();
		question.setSize(300,25);
		question.setLocation(questionLabel.getX() + 100, questionLabel.getY());
		screen.add(question);
		
		pointsLabel = new JLabel("Points:");
		pointsLabel.setSize(100,25);
		pointsLabel.setLocation(questionLabel.getX(), questionLabel.getY() + 100);
		screen.add(pointsLabel);
		
		points = new JTextField();
		points.setSize(100,25);
		points.setLocation(pointsLabel.getX() + 100, pointsLabel.getY());
		screen.add(points);
		
		submitQuestion = new JButton("Submit Question");
		submitQuestion.setSize(200,25);
		submitQuestion.setLocation(pointsLabel.getX() + 250, pointsLabel.getY() + 150);
		submitQuestion.addActionListener(this);
		screen.add(submitQuestion);
		
		message = new JLabel("");
		message.setSize(200,25);
		message.setLocation(submitQuestion.getX(), submitQuestion.getY() + 100);
		screen.add(message);
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Create New Questions");
		frame.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<questionButtonList.size();i++)
		{
			if(e.getSource() == questionButtonList.get(i))
			{
				frame.setVisible(false);
				editQuestionView = new EditQuestionView(this, questionList, i);
			}
		}
		
		if(e.getSource() == submitQuestion)
		{
			questionList.addQuestion(qid, aid, question.getText(), Integer.parseInt(points.getText()));
			questionButton = new JButton(question.getText());
			if(questionButtonList.size() == 0)
			{
				questionButton.setLocation(frame.getWidth()/3, 0);
			}
			else
			{
				questionButton.setLocation(questionButtonList.get(questionButtonList.size() - 1).getX(), questionButtonList.get(questionButtonList.size() - 1).getY() + 25);
			}
			questionButton.setSize(200,25);
			questionButton.addActionListener(this);
			screen.add(questionButton);
			questionButtonList.add(questionButton);
			screen.repaint();
			this.checkCurrentTotalPoints();
		}
	}
	
	public void checkCurrentTotalPoints()
	{
		currentTotalPoints = 0;
		for(int i=0;i<questionList.getSize();i++)
		{
			currentTotalPoints = currentTotalPoints + questionList.get(i).getPoints();
		}
		if(currentTotalPoints < totalPoints)
		{
			message.setText("Question saved, enter more questions till you get a total of " + totalPoints + " points");
		}
		else if(currentTotalPoints > totalPoints)
		{
			message.setText("Question saved, but you have gone over the total allotted amount of points");
		}
		else
		{
			for(int i=0;i<questionList.getSize();i++)
			{
				connection.createQuestion(questionList.get(i).getAID(), questionList.get(i).getQuestion(), questionList.get(i).getPoints());
			}
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	public void setQuestionList(QuestionList questionList)
	{
		frame.setVisible(true);
		this.questionList = questionList;
		this.checkCurrentTotalPoints();
	}
}
