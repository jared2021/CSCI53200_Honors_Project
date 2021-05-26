package ClassManager;
public class AssignmentFactory {

	private Assignment assignment;
	
	public AssignmentFactory()
	{
		
	}
	
	public Assignment createAssignment(int aid, int cid, String name, String description, int total, int isHidden)
	{
		assignment = new Assignment(aid,cid,name,description,total, isHidden);
		return assignment;
	}
}
