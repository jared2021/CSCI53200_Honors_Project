package ClassManager;
import java.util.ArrayList;

public class GradeList {

	private ArrayList<Grade> gradeList = new ArrayList<Grade>();
	
	private GradeFactory gradeFactory = new GradeFactory();
	
	public GradeList()
	{
		
	}
	
	public void addGrade(int gid, int aid, int sid, int score, int total)
	{
		gradeList.add(gradeFactory.createGrade(gid, aid, sid, score, total));
	}
	
	public void clear()
	{
		gradeList.clear();
	}
	
	public Grade get(int index)
	{
		return gradeList.get(index);
	}
	
	public int getSize()
	{
		return gradeList.size();
	}
}
