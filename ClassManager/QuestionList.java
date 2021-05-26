package ClassManager;
import java.util.ArrayList;

public class QuestionList {

	private ArrayList<Question> questionList = new ArrayList<Question>();
	
	private QuestionFactory questionFactory = new QuestionFactory();
	
	public QuestionList()
	{
		
	}
	
	public void addQuestion(int qid, int aid, String question, int points)
	{
		questionList.add(questionFactory.createQuestion(qid, aid, question, points));
	}
	
	public void clear()
	{
		questionList.clear();
	}
	
	public Question get(int index)
	{
		return questionList.get(index);
	}
	
	public int getSize()
	{
		return questionList.size();
	}
}
