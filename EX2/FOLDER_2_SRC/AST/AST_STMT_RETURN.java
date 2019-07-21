package AST;

public class AST_STMT_RETURN extends AST_STMT{
  public AST_EXP return_value;
  
  
  public AST_STMT_RETURN(AST_EXP exp)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(exp==null)
		  System.out.print("====================== stmt -> RETURN SEMICOLON\n");
    else
      System.out.print("====================== stmt -> RETURN exp SEMICOLON\n");
      
   
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.return_value = exp;
   
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE RETURN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (return_value != null) return_value.PrintMe();


		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"return");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (return_value != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,return_value.SerialNumber);

	}
 

}