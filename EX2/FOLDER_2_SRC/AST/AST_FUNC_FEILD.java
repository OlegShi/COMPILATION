package AST;

public class AST_FUNC_FEILD extends AST_C_FEILD
{
  public AST_FUNC_DEC funcDec;  
  
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_FUNC_FEILD(AST_FUNC_DEC funcDec)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
   System.out.print("====================== cField -> funcDec\n");

      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.funcDec = funcDec;
	}
 
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE CLASS FEILD\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (funcDec != null) funcDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   

    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	"CLASS FEILD");
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);

	}
}
