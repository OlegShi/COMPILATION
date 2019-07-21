package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public abstract class AST_VAR extends AST_Node
{
	
	public TYPE SemantMe() throws SemanticException
	{
		return null;
	}
    
  public TEMP IRme(boolean isStore)
	{
		return null;
	}
}
