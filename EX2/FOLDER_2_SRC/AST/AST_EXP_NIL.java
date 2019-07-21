package AST;

public class AST_EXP_NIL extends AST_EXP{

  
  /******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_EXP_NIL(){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== exp -> NIL\n");

    
  }
  

  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE NIL EXP\n");
   
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NULL");
   
  }

}
