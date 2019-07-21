package AST;

public class AST_EXP_PAREN extends AST_EXP{

  public AST_EXP e;

  
  /******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_EXP_PAREN(AST_EXP e){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== exp -> LPAREN exp RPAREN\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/

    this.e = e;

  }
  

  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE PAREN EXP\n");
   
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"(exp)");
		if (e  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
   
  }

}
