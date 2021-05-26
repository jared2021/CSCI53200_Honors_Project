package ClassManager;
import java.util.ArrayList;

public class ResponseList {

	private ArrayList<Response> responseList = new ArrayList<Response>();
	
	private ResponseFactory responseFactory = new ResponseFactory();
	
	public ResponseList()
	{
		
	}
	
	public void addResponse(int rid, int qid, int sid, String answer)
	{
		responseList.add(responseFactory.createResponse(rid, qid, sid, answer));
	}
	
	public void clear()
	{
		responseList.clear();
	}
	
	public Response get(int index)
	{
		return responseList.get(index);
	}
	
	public int getSize()
	{
		return responseList.size();
	}
}
