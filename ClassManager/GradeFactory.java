package ClassManager;
public class GradeFactory {

	private Grade grade;
	
	public GradeFactory()
	{
		
	}
	
	public Grade createGrade(int gid, int aid, int sid, int score, int total)
	{
		grade = new Grade(gid,aid,sid,score,total);
		return grade;
	}
}
