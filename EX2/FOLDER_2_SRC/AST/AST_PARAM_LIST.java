package AST;

public class AST_PARAM_LIST extends AST_Node
{
 public AST_PARAM head;
  public AST_PARAM_LIST tail;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_PARAM_LIST(AST_PARAM head, AST_PARAM_LIST tail)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(tail==null)
      System.out.print("====================== paramList -> param\n");
    else
      System.out.print("====================== paramList -> param COMMA paramList\n");
      
      
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
		System.out.print("AST NODE PARAM LIST\n");

		/*********************/
		/* RECURSIVELY PRINT */
		/*********************/
		if (head != null) head.PrintMe();
    if (tail != null) tail.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/

   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	"PARAM LIST");
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
    if (tail != null)   AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
}