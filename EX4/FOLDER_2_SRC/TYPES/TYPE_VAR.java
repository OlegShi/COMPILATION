package TYPES;

public class TYPE_VAR extends TYPE
{
	/****************/
	/* The var type */
	/****************/
 
  public class InitValue{
   public boolean isString = false;
   public String stringValue= null;
   public int intValue = 0; 
 }
 
 
	public TYPE type;
   public InitValue initValue = new InitValue();;

	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_VAR(String name,TYPE type)
	{
		this.name = name;
		this.type = type;
	}
}
