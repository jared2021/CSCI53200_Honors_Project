package ClassManager;

public class Grade {

	private int gid;
	private int aid;
	private int sid;
	private int score;
	private int total;
	
	public Grade(int gid, int aid, int sid, int score, int total)
	{
		this.gid = gid;
		this.aid = aid;
		this.sid = sid;
		this.score = score;
		this.total = total;
	}
	
	public int getGID()
	{
		return gid;
	}
	
	public int getAID()
	{
		return aid;
	}
	
	public int getSID()
	{
		return sid;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public void setTotal(int total)
	{
		this.total = total;
	}
}
