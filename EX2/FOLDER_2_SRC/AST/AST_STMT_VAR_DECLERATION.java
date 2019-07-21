package AST;

public class AST_STMT_VAR_DECLERATION extends AST_STMT
{
  public AST_VAR_DEC varDec;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_VAR_DECLERATION(AST_VAR_DEC varDec)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== stmt -> varDec\n");
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varDec = varDec;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE VAR DEC STMT \n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (varDec != null) varDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	"VAR DEC\nSTATEMENT");
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);

	}
}