package TYPES;

public class TYPE_NEW_ARRAY extends TYPE
{

    public TYPE arrayType;
    
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_NEW_ARRAY(String name, TYPE arrayType)
	{
		this.name = name;
    this.arrayType = arrayType;
	}
 
}
