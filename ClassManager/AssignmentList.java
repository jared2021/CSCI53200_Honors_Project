package ClassManager;
import java.util.ArrayList;

public class AssignmentList {

	private ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();

	private AssignmentFactory assignmentFactory = new AssignmentFactory();
	
	public AssignmentList()
	{
		
	}
	
	public void addAssignment(int aid, int cid, String name, String description, int total, int isHidden)
	{
		assignmentList.add(assignmentFactory.createAssignment(aid, cid, name, description, total, isHidden));
	}
	
	public void clear()
	{
		assignmentList.clear();
	}
	
	public Assignment get(int index)
	{
		return assignmentList.get(index);
	}
	
	public int getSize()
	{
		return assignmentList.size();
	}
}
