package ClassManager;

public class Assignment {

	private int aid;
	private int cid;
	private String name;
	private String description;
	private int total;
	private boolean isHidden;
	
	public Assignment(int aid, int cid, String name, String description, int total, int isHidden)
	{
		this.aid = aid;
		this.cid = cid;
		this.name = name;
		this.description = description;
		this.total = total;
		if(isHidden == 1)
		{
			this.isHidden = true;
		}
		else
		{
			this.isHidden = false;
		}
	}
	
	public int getAID()
	{
		return aid;
	}
	
	public void setAID(int aid)
	{
		this.aid = aid;
	}
	
	public int getCID()
	{
		return cid;
	}
	
	public void setCID(int cid)
	{
		this.cid = cid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public void setTotal(int total)
	{
		this.total = total;
	}
	
	public boolean getIsHidden()
	{
		return isHidden;
	}
	
	public void setIsHidden(boolean isHidden)
	{
		this.isHidden = isHidden;
	}
}
