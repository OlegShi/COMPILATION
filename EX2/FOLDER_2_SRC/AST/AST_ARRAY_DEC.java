package AST;

public class AST_ARRAY_DEC extends AST_Node{

  public String name;
  public String type;

  
  /******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_ARRAY_DEC(String name,String type){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== arrayDec -> ARRAY ID EQ ID LBRACK RBRACK\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.name = name;
    this.type = type;

  }
  

  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE ARRAY DEC\n");
   
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("%s %s[]",type,name));
		
   
  }

}
