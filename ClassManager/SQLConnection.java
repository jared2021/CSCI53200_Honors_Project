package ClassManager;
import java.sql.*;

import java.util.ArrayList;

public class SQLConnection {

private java.sql.Connection con;

private ArrayList<Integer> isValidUser = new ArrayList<Integer>();

private CollegeClassList collegeClassList = new CollegeClassList();

private AssignmentList assignmentList = new AssignmentList();

private Assignment assignment;

//private int pid;

//private int cid;

private ArrayList<Integer> studentID = new ArrayList<Integer>();

private StudentList studentList = new StudentList();

private QuestionList questionList = new QuestionList();

private Response response;
	
	SQLConnection()throws ClassNotFoundException, SQLException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://capstone-project.database.windows.net:1433;database=Capstone;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";  
		con = DriverManager.getConnection(connectionUrl,"jascho","Windmaster52631");
	}
	
	public ArrayList<Integer> checkCredentials(String potentialUserName, String potentialPassword)
	{
		boolean correctCredentials = false;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFESSOR");
			while(rs.next())
			{
				int pid = rs.getInt("PID");
				String userName = rs.getString("UserName");
				String password = rs.getString("Password");
				if(userName.equals(potentialUserName) && password.equals(potentialPassword))
				{
					isValidUser.clear();
					isValidUser.add(1);
					isValidUser.add(pid);
					correctCredentials = true;
				}
			}
			if(correctCredentials)
			{
				return isValidUser;
			}
			else
			{
				isValidUser.clear();
				isValidUser.add(0);
				return isValidUser;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Connection timed out");
		}
		isValidUser.clear();
		isValidUser.add(-1);
		return isValidUser;
	}
	
	public CollegeClassList getCollegeClasses(int pid) throws SQLException
	{
		collegeClassList.clear();
		//this.pid = pid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM CLASS WHERE PID = " + pid);
		while(rs.next())
		{
			int cid = rs.getInt("CID");
			pid = rs.getInt("PID");
			String name = rs.getString("Name");
			collegeClassList.addCollegeClass(cid, pid, name);
		}
		return collegeClassList;
	}
	
	public AssignmentList getAssignments(int cid) throws SQLException
	{
		assignmentList.clear();
		//this.cid = cid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ASSIGNMENT WHERE CID = " + cid);
		while(rs.next())
		{
			int aid = rs.getInt("AID");
			cid = rs.getInt("CID");
			String name = rs.getString("Name");
			String description = rs.getString("Description");
			int total = rs.getInt("Total");
			int isHidden = rs.getInt("Hidden");
			assignmentList.addAssignment(aid, cid, name, description, total, isHidden);
		}
		return assignmentList;
	}
	
	public Assignment getAssignment(int aid)
	{
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ASSIGNMENT WHERE AID = " + aid);
			while(rs.next())
			{
				aid = rs.getInt("AID");
				int cid = rs.getInt("CID");
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				int total = rs.getInt("Total");
				int isHidden = rs.getInt("Hidden");
				assignment = new Assignment(aid, cid, name, description, total, isHidden);
				return assignment;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong with trying to get an assignment");
			e.printStackTrace();
		}
		assignment = new Assignment(-1, -1, null, null, -1, 0);
		return assignment;
	}
	
	public boolean createAssignment(int cid, String name, String description, String dueDate, int total)
	{
		try {
			Statement stmt = con.createStatement();
			boolean isHidden = true;
			String statement = "'" + cid + "','" + name + "','" + description + "','" + dueDate + "','" + total + "','" + isHidden + "'";
			stmt.executeUpdate("INSERT INTO ASSIGNMENT VALUES (" + statement +")");
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public int getAssignmentTotalPoints(int cid)
	{
		try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Total from ASSIGNMENT WHERE AID = " + cid);
				while(rs.next())
				{
					int total = rs.getInt("Total");
					return total;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				System.out.println("Something went wrong when trying to get the total points for an assignment");
			}
		return -1;
	}
	
	public int getAssignmentID(int cid, String name)
	{
		try {
			String statement = String.valueOf(cid) + "AND Name = '" + name + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AID from ASSIGNMENT WHERE CID = " + statement);
			while(rs.next())
			{
				int aid = rs.getInt("AID");
				return aid;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong when trying to get the Assignment ID");
			System.out.println(cid);
		}
		return -1;
	}
	
	public boolean createQuestion(int aid, String question, int points)
	{
		try {
			String statement = "'" + aid + "','" + question + "','" + points + "'";
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO QUESTION VALUES (" + statement +")");
			return this.revealAssignment(aid);
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to create questions");
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean revealAssignment(int aid)
	{
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE ASSIGNMENT SET Hidden = 0 WHERE AID = " + aid);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to make an assignment unhidden");
			e.printStackTrace();
		}
		return false;
	}
	
	public StudentList getStudents(int cid)
	{
		try {
			studentID.clear();
			studentList.clear();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SID FROM ENROLLED WHERE CID = " + cid);
			while(rs.next())
			{
				studentID.add(rs.getInt("SID"));
			}
			for(int i=0;i<studentID.size();i++)
			{
				Statement statement = con.createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM STUDENT WHERE SID = " + studentID.get(i));
				while(result.next())
				{
					int sid = result.getInt("SID");
					String userName = result.getString("UserName");
					String password = result.getString("Password");
					studentList.addStudent(sid, userName, password);
				}
			}
			return studentList;
		}
		catch(SQLException e)
		{
			System.out.println("Error when trying to get students");
			e.printStackTrace();
		}
		studentList.addStudent(-1, null, null);
		return studentList;
	}
	
	public QuestionList getQuestion(int aid)
	{
		try {
			questionList.clear();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM QUESTION WHERE AID = " + aid);
			while(rs.next())
			{
				int qid = rs.getInt("QID");
				aid = rs.getInt("AID");
				String question = rs.getString("Question");
				int points = rs.getInt("Points");
				questionList.addQuestion(qid, aid, question, points);
			}
			return questionList;
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to get quesitons");
			e.printStackTrace();
		}
		questionList.addQuestion(-1, aid, null, aid);
		return questionList;
	}
	
	public Response getResponse(int qid, int sid)
	{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RESPONSE WHERE QID = " + qid + " AND SID = " + sid);
			while(rs.next())
			{
				int rid = rs.getInt("RID");
				qid = rs.getInt("QID");
				sid = rs.getInt("SID");
				String answer = rs.getString("Answer");
				response = new Response(rid,qid,sid,answer);
			}
			return response;
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to get responses");
			e.printStackTrace();
		}
		response = new Response(-1, qid, sid, null);
		return response;
	}
	
	public boolean createGrade(GradeList gradeList, int aid, int sid, int total)
	{
		try {
			int score = 0;
			for(int i=0;i<gradeList.getSize();i++)
			{
				score = score + gradeList.get(i).getScore();
			}
			Statement stmt = con.createStatement();
			String statement = "'" + aid + "','" + sid + "','" + score + "','" + total + "'";
			stmt.executeUpdate("INSERT INTO GRADE VALUES (" + statement + ")");
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to create a grade");
			e.printStackTrace();
		}
		return false;
	}
}