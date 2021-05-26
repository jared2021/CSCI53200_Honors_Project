package ClassManager;
import java.util.ArrayList;

public class CollegeClassList {

	private ArrayList<CollegeClass> collegeClassList = new ArrayList<CollegeClass>();
	
	private CollegeClassFactory collegeClassFactory = new CollegeClassFactory();
	
	public CollegeClassList()
	{
		
	}
	
	public void addCollegeClass(int cid, int pid, String name)
	{
		collegeClassList.add(collegeClassFactory.createCollegeClass(cid, pid, name));
	}
	
	public void clear()
	{
		collegeClassList.clear();
	}
	
	public CollegeClass get(int index)
	{
		return collegeClassList.get(index);
	}
	
	public int getSize()
	{
		return collegeClassList.size();
	}
}
