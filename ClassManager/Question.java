package ClassManager;

public class Question {

	private int qid;
	private int aid;
	private String question;
	private int points;
	
	public Question(int qid, int aid, String question, int points)
	{
		this.qid = qid;
		this.aid = aid;
		this.question = question;
		this.points = points;
	}
	
	public int getQID()
	{
		return qid;
	}
	
	public int getAID()
	{
		return aid;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public void setQuestion(String question)
	{
		this.question = question;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}
}
