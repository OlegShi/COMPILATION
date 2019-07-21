package AST;

import TYPES.*;

public abstract class AST_STMT extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");
	}
	
	public TYPE SemantMe(TYPE func_return_type)  throws SemanticException
	{
		return null;
	}
}
