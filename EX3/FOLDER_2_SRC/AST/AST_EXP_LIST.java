package AST;

import TYPES.*;
public class AST_EXP_LIST extends AST_Node
{
 public AST_EXP head;
  public AST_EXP_LIST tail;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_LIST(AST_EXP head, AST_EXP_LIST tail)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(tail==null)
      System.out.print("====================== expList -> exp\n");
    else
      System.out.print("====================== expList -> exp COMMA expList\n");
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
    this.tail = tail;
	}
 
 public void PrintMe()
	{
		/*****************/
		/* AST NODE TYPE */
		/*****************/
		System.out.print("AST NODE EXP LIST\n");

		/*********************/
		/* RECURSIVELY PRINT */
		/*********************/
		if (head != null) head.PrintMe();
    if (tail != null) tail.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/

   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	"EXP LIST");
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
    if (tail != null)   AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
    
    
 public TYPE_LIST SemantMe() throws SemanticException{
	   
	  if (tail == null)
		{
			return new TYPE_LIST(
				head.SemantMe(),
				null);
		}
		else
		{
			return new TYPE_LIST(
				head.SemantMe(),
				tail.SemantMe());
		}
	   
	   
	   
 }
}
