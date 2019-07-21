package AST;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_PARAM extends AST_Node{

  public String type;
  public String name;
   int typeleft; 
   int typeright;
   int nameleft; 
   int nameright;
   
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_PARAM(String type, String name, int typeleft, int typeright, int nameleft, int nameright){
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

    System.out.format("====================== param -> type(%s) name(%s)\n",type,name);


   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
    this.name = name;
    
    this.typeleft=typeleft; 
    this.typeright=typeright;
    this.nameleft=nameleft; 
    this.nameright=nameright;
   
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE PARAM\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   
   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	String.format("%s %s",type,name));
      
	}
 
	/*****************/
	/* SEMANT ME ... */
	/*****************/
	public TYPE SemantMe(TYPE_LIST father_data_members)  throws SemanticException
	{
		
			 
		TYPE t = SYMBOL_TABLE.getInstance().find(type);
		//Oleg: added check for different types
		if (t == null || !t.name.equals(type))
		{
			/**************************/
			/* ERROR: undeclared type */
			/**************************/
			String error = String.format(">> ERROR [%d:%d] Using a non existing type (%s)\n",typeleft, typeright,type);
      throw new SemanticException(error,typeleft);
		}
		else
		{
   
      //check if trying to shadowing
      if(father_data_members!=null){
        TYPE similar_var = father_data_members.search_by_name(name);
        if( similar_var!=null){ 
            if(similar_var instanceof TYPE_VAR && similar_var.name.equals(name)){
              String error = String.format(">> ERROR [%d:%d] trying to shadow [%s %s] with [%s %s], which is not a alowed\n",
              typeleft,
              typeright,
              ((TYPE_VAR)similar_var).type.name,
              similar_var.name,
              type,
              name);
            throw new SemanticException(error,typeleft);
        }  
        else{
          String error = String.format(">> ERROR [%d:%d] trying to overload a function with a datamember (%s) \n",nameleft,nameright,type);
            throw new SemanticException(error,nameleft);
        
        } 
                   
      }
         
  
         
      }
      
			/*******************************************************/
			/* Enter var with name=name and type=t to symbol table */
			/*******************************************************/
      //moved to  vardec
			//SYMBOL_TABLE.getInstance().enter(name,t);
		}

		/****************************/
		/* return (existing) type t */
		/****************************/
		return t;
				
	}	
 
}
