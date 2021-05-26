package ClassManager;
public class StudentFactory {

	private Student student;
	
	public StudentFactory()
	{
		
	}
	
	public Student createStudent(int sid, String userName, String password)
	{
		student = new Student(sid,userName,password);
		return student;
	}
}
