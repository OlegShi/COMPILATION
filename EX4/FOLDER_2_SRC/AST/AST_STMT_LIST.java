package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_STMT_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;
	public AST_STMT_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) 
      System.out.print("====================== stmt_list -> stmt stmt_list\n");
		else 
      System.out.print("====================== stmt_list -> stmt\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
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
			"STMT\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE SemantMe(TYPE func_return_type, Integer generator)  throws SemanticException
	{
     if(head instanceof AST_STMT_VAR_DECLERATION){
       ((AST_STMT_VAR_DECLERATION)head).varDec.localVariablesCounterGenerator = generator;
       generator = generator+1;
     }
		if (head != null) head.SemantMe(func_return_type,generator);
		if (tail != null) tail.SemantMe(func_return_type,generator);
		
		return null;
	}
    
    public TEMP IRme()
	{
    	TEMP t1 = null;
	TEMP t2 = null;
		if (head != null) t1 = head.IRme();
		if (tail != null) t2 = tail.IRme();
		if(t1!=null)
			return t1;
		else if (t2!=null)
			return t2;
		return null;			
	}
	
}
