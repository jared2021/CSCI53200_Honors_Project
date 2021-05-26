package ClassManager;
public class QuestionFactory {

	private Question question;
	
	public QuestionFactory()
	{
		
	}
	
	public Question createQuestion(int qid, int aid, String question, int points)
	{
		this.question = new Question(qid, aid, question, points);
		return this.question;
	}
}
