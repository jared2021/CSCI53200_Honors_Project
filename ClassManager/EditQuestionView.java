package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class EditQuestionView implements ActionListener{
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	
	private JLabel questionLabel;
	private JTextField question;
	
	private JLabel pointsLabel;
	private JTextField points;
	
	private JButton submitQuestion;
	
	private QuestionList questionList;
	private int index;
	private CreateQuestionView createQuestionView;
	
	public EditQuestionView(CreateQuestionView createQuestionView, QuestionList questionList, int index)
	{
		this.createQuestionView = createQuestionView;
		this.questionList = questionList;
		this.index = index;
		
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
		question.setText(questionList.get(index).getQuestion());
		screen.add(question);
		
		pointsLabel = new JLabel("Points:");
		pointsLabel.setSize(100,25);
		pointsLabel.setLocation(questionLabel.getX(), questionLabel.getY() + 100);
		screen.add(pointsLabel);
		
		points = new JTextField();
		points.setSize(100,25);
		points.setLocation(pointsLabel.getX() + 100, pointsLabel.getY());
		points.setText(String.valueOf(questionList.get(index).getPoints()));
		screen.add(points);
		
		submitQuestion = new JButton("Edit Question");
		submitQuestion.setSize(200,25);
		submitQuestion.setLocation(pointsLabel.getX() + 250, pointsLabel.getY() + 150);
		submitQuestion.addActionListener(this);
		screen.add(submitQuestion);
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Edit Question");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == submitQuestion)
		{
			questionList.get(index).setQuestion(question.getText());
			questionList.get(index).setPoints(Integer.parseInt(points.getText()));
			createQuestionView.setQuestionList(questionList);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}
}
