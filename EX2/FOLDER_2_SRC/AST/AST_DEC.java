package AST;

public class AST_DEC extends AST_Node{


  public AST_VAR_DEC varDec;
  public AST_FUNC_DEC funcDec;
  public AST_CLASS_DEC classDec;
  public AST_ARRAY_DEC arrayDec;
  
  /******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_DEC(AST_VAR_DEC varDec){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== dec -> varDec\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.varDec = varDec;

  }
  
    public AST_DEC(AST_FUNC_DEC funcDec){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== dec -> funcDec\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.funcDec = funcDec;

  }
  
    public AST_DEC(AST_CLASS_DEC classDec){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== dec -> classDec\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.classDec = classDec;

  }
  
    public AST_DEC(AST_ARRAY_DEC arrayDec){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    System.out.print("====================== dec -> arrayDec\n");

    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.arrayDec = arrayDec;

  }

  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE DEC\n");
   
   
		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (varDec != null) varDec.PrintMe();
		if (funcDec != null) funcDec.PrintMe();
    if (classDec != null) classDec.PrintMe();
    if (arrayDec != null) arrayDec.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("dec"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
		if (funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
    if (classDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,classDec.SerialNumber);
    if (arrayDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,arrayDec.SerialNumber);
    

   
  }

}
