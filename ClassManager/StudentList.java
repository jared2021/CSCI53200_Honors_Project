package ClassManager;
import java.util.ArrayList;

public class StudentList {

	private ArrayList<Student> student = new ArrayList<Student>();
	
	private StudentFactory studentFactory = new StudentFactory();
	
	public StudentList()
	{
		
	}
	
	public void addStudent(int sid, String userName, String password)
	{
		student.add(studentFactory.createStudent(sid, userName, password));
	}
	
	public void clear()
	{
		student.clear();
	}
	
	public Student get(int index)
	{
		return student.get(index);
	}
	
	public int getSize()
	{
		return student.size();
	}
}
