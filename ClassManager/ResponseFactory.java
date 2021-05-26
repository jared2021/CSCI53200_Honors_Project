package ClassManager;
public class ResponseFactory {

	private Response response;
	
	public ResponseFactory()
	{
		
	}
	
	public Response createResponse(int rid, int qid, int sid, String answer)
	{
		response = new Response(rid, qid, sid, answer);
		return response;
	}
}
