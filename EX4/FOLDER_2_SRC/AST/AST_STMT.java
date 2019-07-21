package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public abstract class AST_STMT extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE\n");
	}
	
	public TYPE SemantMe(TYPE func_return_type, Integer generator)  throws SemanticException
	{
		return null;
	}
    
    public TEMP IRme()
	{
		return null;
	}
}
