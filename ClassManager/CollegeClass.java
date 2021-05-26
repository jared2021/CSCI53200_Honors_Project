package ClassManager;

public class CollegeClass {
	
	private int cid;
	private int pid;
	private String name;
	
	public CollegeClass(int cid, int pid, String name)
	{
		this.cid = cid;
		this.pid = pid;
		this.name = name;
	}
	
	public int getCID()
	{
		return cid;
	}
	
	public void setCID(int cid)
	{
		this.cid = cid;
	}
	
	public int getPID()
	{
		return pid;
	}
	
	public void setPID(int pid)
	{
		this.pid = pid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
