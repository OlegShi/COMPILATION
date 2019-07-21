package AST;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;
import SYMBOL_TABLE.*;

public abstract class AST_C_FEILD extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST C_FEILD NODE\n");
	}
	
	public TYPE SemantMe(TYPE_LIST father_data_members) throws SemanticException
	{
		return null;
	}
 
}
