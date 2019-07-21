package TYPES;

public class TYPE_ARRAY extends TYPE
{

    public TYPE arrayType;
    
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_ARRAY(String name, TYPE arrayType)
	{
		this.name = name;
        this.arrayType = arrayType;
	}
 
   public boolean isArray(){ return true;}
}
