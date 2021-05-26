package ClassManager;
import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;

public class CreateGradeView implements ActionListener{

	private SQLConnection connection;
	private int aid;
	private int cid;
	
	private StudentList studentList;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = (int)screenSize.getWidth();
	private int screenHeight = (int)screenSize.getHeight();
	
	private JFrame frame;
	private JPanel screen;
	
	private JLabel title;
	private JButton studentButton;
	
	private ArrayList<JButton> studentButtonList = new ArrayList<JButton>();
	
	private StudentResponseView studentResponseView;
	
	public CreateGradeView(SQLConnection connection, int aid, int cid)
	{
		this.connection = connection;
		this.aid = aid;
		this.cid = cid;
		
		frame = new JFrame();
		frame.setSize(screenWidth/2, screenHeight/2);
		
		screen= new JPanel();
		screen.setLayout(null);
		
		title = new JLabel("Edit Grades");
		title.setSize(100,25);
		title.setLocation(frame.getWidth()/4 + 50, 0);
		screen.add(title);
		
		studentList = connection.getStudents(cid);
		
		for(int i=0;i<studentList.getSize();i++)
		{
			studentButton = new JButton(studentList.get(i).getUserName());
			studentButton.setSize(300,25);
			if(i==0)
			{
				studentButton.setLocation(frame.getWidth()/4, 100);
			}
			else
			{
				studentButton.setLocation(studentButtonList.get(i-1).getX(), studentButtonList.get(i-1).getY() + 25);
			}
			studentButton.addActionListener(this);
			screen.add(studentButton);
			studentButtonList.add(studentButton);
		}
		
		frame.add(screen,null);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Edit Grades");
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<studentButtonList.size();i++)
		{
			if(e.getSource() == studentButtonList.get(i))
			{
				studentResponseView = new StudentResponseView(connection, aid, studentList.get(i).getSID());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
	}
}
