package AST;

import TYPES.*;

public class AST_C_FEILD_LIST extends AST_Node
{
/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_C_FEILD head;
	public AST_C_FEILD_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_C_FEILD_LIST(AST_C_FEILD head,AST_C_FEILD_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) 
      System.out.print("====================== cFieldList -> cField cFieldList\n");
		else 
      System.out.print("====================== cFieldList -> cField\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}
 
   public AST_C_FEILD_LIST(){
   }

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS FEILD\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE_LIST SemantMe(TYPE_LIST father_data_members) throws SemanticException
	{
		if (tail == null)
		{
			return new TYPE_LIST(
				head.SemantMe(father_data_members),
				null);
		}
		else
		{
			return new TYPE_LIST(
				head.SemantMe(father_data_members),
				tail.SemantMe(father_data_members));
		}
	}
	
}

