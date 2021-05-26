package ClassManager;

public class Student {

	private int sid;
	private String userName;
	private String password;
	
	public Student(int sid, String userName, String password)
	{
		this.sid = sid;
		this.userName = userName;
		this.password = password;
	}
	
	public int getSID()
	{
		return sid;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getPassword()
	{
		return password;
	}
}
