package TYPES;

public class TYPE_VAR extends TYPE
{
	/****************/
	/* The var type */
	/****************/
	public TYPE type;

	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_VAR(String name,TYPE type)
	{
		this.name = name;
		this.type = type;
	}
}
