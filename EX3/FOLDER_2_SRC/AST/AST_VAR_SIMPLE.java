package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
  public int name_line_number;
  public int name_column_number;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name,int name_line_number,int name_column_number)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
    this.name_line_number = name_line_number;
    this.name_column_number = name_column_number;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	public TYPE SemantMe() throws SemanticException
	{

      TYPE t = SYMBOL_TABLE.getInstance().find(name);
      
	    if (t == null)
	    {
              /**************************/
              /* ERROR: undeclared var */
              /**************************/

              String error = String.format(">> ERROR [%d:%d] var name (%s) does not exist\n",name_line_number,name_column_number,name);
              throw new SemanticException(error,name_line_number);
	   }
       
       if(!(t instanceof  TYPE_ARRAY || ( t instanceof  TYPE_CLASS && !(t.name.equals(name))) ||  t instanceof  TYPE_INT ||  t instanceof  TYPE_STRING )){
      			String error = String.format(">> ERROR [%d:%d] trying to return a non returnable type %s \n",name_line_number,name_column_number,t.getClass().getName());
            throw new SemanticException(error,name_line_number);
      }
       
     return SYMBOL_TABLE.getInstance().find(name);
     
	}
	
	
}
