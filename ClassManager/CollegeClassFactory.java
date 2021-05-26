package ClassManager;
public class CollegeClassFactory {

	private CollegeClass collegeClass;
	
	public CollegeClassFactory()
	{
		
	}
	
	public CollegeClass createCollegeClass(int cid, int pid, String name)
	{
		collegeClass = new CollegeClass(cid, pid, name);
		return collegeClass;
	}
}
