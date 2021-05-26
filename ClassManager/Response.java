package ClassManager;

public class Response {

	private int rid;
	private int qid;
	private int sid;
	private String answer;
	
	public Response(int rid, int qid, int sid, String answer)
	{
		this.rid = rid;
		this.qid = qid;
		this.sid = sid;
		this.answer = answer;
	}
	
	public int getRID()
	{
		return rid;
	}
	
	public int getQID()
	{
		return qid;
	}
	
	public int getSID()
	{
		return sid;
	}
	
	public String getAnswer()
	{
		return answer;
	}
}
