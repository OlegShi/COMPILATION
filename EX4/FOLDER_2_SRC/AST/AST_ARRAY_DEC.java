package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ARRAY_DEC extends AST_Node{

  public String name;
  public String type;
    int nameleft;
    int nameright;
    int typeleft;
    int typeright;
  
  /******************/
	/* CONSTRUCTOR(S) */
	/******************/
  public AST_ARRAY_DEC(String name,String type,int nameleft,int nameright,int typeleft,int typeright){
    
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

    this.nameleft=nameleft;
    this.nameright=nameright;
    this.typeleft=typeleft;
    this.typeright=typeright;
  
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
  
  public TYPE SemantMe() throws SemanticException
	{
		TYPE arrayname = SYMBOL_TABLE.getInstance().find(name);
		if (arrayname != null)
		{
			/**************************/
			/* ERROR: already declared array name */
			/**************************/
			String error = String.format(">> ERROR [%d:%d] Found an array with the same name - already defined \n",nameleft,nameright);
                throw new SemanticException(error,nameleft);
            
		}
      
      TYPE arraytype = SYMBOL_TABLE.getInstance().find(type);
      if (arraytype == null) {
          /**************************/
			/* ERROR: undeclared type */
			/**************************/
			String error = String.format(">> ERROR [%d:%d] didn't find the type used to the array \n",typeleft,typeright);
                throw new SemanticException(error,typeleft);
      }
      
      if(!(arraytype instanceof  TYPE_ARRAY ||  arraytype instanceof  TYPE_CLASS ||  arraytype instanceof  TYPE_INT ||  arraytype instanceof  TYPE_STRING )){
      			String error = String.format(">> ERROR [%d:%d] trying to make an array of type %s \n",typeleft,typeright,arraytype.getClass().getName());
            throw new SemanticException(error,typeleft);
      }
      
      TYPE typeToSend = new TYPE_ARRAY(name, arraytype);
        SYMBOL_TABLE.getInstance().enter(name,typeToSend);

		/****************************/
		/* return (existing) type t */
		/****************************/
		//maybe wrong, it was this before:
		//return t;
		
		return typeToSend;
	}

}
