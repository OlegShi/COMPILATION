package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_PROGRAM extends AST_Node{

  public AST_PROGRAM program;
  public AST_DEC decleration;
 
 
 	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_PROGRAM(AST_DEC decleration,AST_PROGRAM program){
    
    /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
    SerialNumber = AST_Node_Serial_Number.getFresh();
    
    
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(program == null){
   	  System.out.print("====================== program -> dec\n");
    }
    else{
      System.out.print("====================== program -> dec program\n");
    }  
    
    /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
 	  this.program = program;
		this.decleration = decleration;
  }
  
  //public AST_PROGRAM(AST_DEC decleration){
   // this(decleration,null); 
  //}
  
  
  public void PrintMe()
	{
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE PROGRAM\n");
   
   
		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
    if (decleration != null) decleration.PrintMe();
		if (program != null) program.PrintMe();
		
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("program"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
   	if (decleration != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,decleration.SerialNumber);
		if (program  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,program.SerialNumber);
	
   
   
   
  }
  
	public TYPE SemantMe()  throws SemanticException
	{		
		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
   	if (decleration != null) decleration.SemantMe();   
		if (program != null) program.SemantMe();

		
		return null;	
	}
    
    public TEMP IRme()
	{
        if (decleration != null) decleration.IRme();   
		if (program != null) program.IRme();
		return null;
	}

 
}
