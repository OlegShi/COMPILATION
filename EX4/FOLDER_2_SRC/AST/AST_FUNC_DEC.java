package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_FUNC_DEC extends AST_Node
{

  public AST_PARAM return_type_and_name;
  public AST_PARAM_LIST parameters;
  public AST_STMT_LIST body;
    
  public AST_FUNC_DEC(AST_PARAM param, AST_PARAM_LIST param_list, AST_STMT_LIST statemnt_list){
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (param_list != null)
		  System.out.print("====================== funcDec -> param LPAREN paramList RPAREN LBRACE stmtList RBRACE \n");
    else
      System.out.print("====================== funcDec -> param LPAREN RPAREN LBRACE stmtList RBRACE \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		  this.return_type_and_name = param;
		  this.parameters = param_list;
		  this.body = statemnt_list;
    }
    
  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE FUNC DEC\n");
   
   
		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
   

  
		if (return_type_and_name != null) return_type_and_name.PrintMe();
		if (parameters != null) parameters.PrintMe();
    if (body != null) body.PrintMe();

		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("funcDec"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (return_type_and_name != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,return_type_and_name.SerialNumber);
		if (parameters != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,parameters.SerialNumber);
    if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
    
    }
    
	public TYPE SemantMe(TYPE_LIST father_data_members)  throws SemanticException
	{
 
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/

		returnType = SYMBOL_TABLE.getInstance().find(return_type_and_name.type);
		if (returnType == null && !return_type_and_name.type.equals("void"))
		{    	
       String error = String.format(">> ERROR [%d:%d] non existing return type [%s]\n",return_type_and_name.typeleft, return_type_and_name.typeright,return_type_and_name.type);	
       throw new SemanticException(error,return_type_and_name.typeleft);				
		}
   
   
   /************************************/
		/* checking double decleration      */
		/************************************/
     TYPE function =   SYMBOL_TABLE.getInstance().find(return_type_and_name.name);
     if(function!= null){
       String error = String.format(">> ERROR [%d:%d] already exists name [%s]\n",return_type_and_name.nameleft, return_type_and_name.nameright,return_type_and_name.name);
        throw new SemanticException(error,return_type_and_name.nameleft);
     
     }
   
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Input Params */
		/***************************/
    TYPE_LIST list_pointer = null;
    //System.out.format("=====funcdec semant: %s=====\n",return_type_and_name.name);
    int localVariablesCounterGenerator =0;
    
		for (AST_PARAM_LIST it = parameters; it  != null; it = it.tail)
		{
     
			t = SYMBOL_TABLE.getInstance().find(it.head.type);
       //System.out.format("%s,,",t.name);
			if (t == null)
			{
        /************************************/
	    	/* checking parameter type          */
	    	/************************************/
        String error = String.format(">> ERROR [%d:%d] non existing type (%s)\n",it.head.typeleft,it.head.typeright,it.head.type);
        throw new SemanticException(error,it.head.typeleft);			
			}
			else
			{
        if(list_pointer==null){
          type_list = new TYPE_LIST(t,null);
          list_pointer = type_list;
        }
        else{
          list_pointer.tail =  new TYPE_LIST(t,null);
          list_pointer = list_pointer.tail;
        }
				SYMBOL_TABLE.getInstance().enter(it.head.name,t);
        SYMBOL_TABLE.getInstance().getEntry(it.head.name).localVariablesCounter = localVariablesCounterGenerator;
        localVariablesCounterGenerator--;
			}
		}
   
   
    /************************************/
		/* checking method overloading      */
		/************************************/
   
    if(father_data_members!=null){
       
           TYPE overload = father_data_members.search_by_name(return_type_and_name.name);
           
           if(overload!=null){
              //System.out.print("found:"+overload.name+"\n");
             if(!overload.getClass().getName().equals("TYPES.TYPE_FUNCTION")){
                  String error = String.format(">> ERROR [%d:%d] a class trying to overload a var with a method\n",return_type_and_name.nameleft, return_type_and_name.nameright);
                  throw new SemanticException(error,return_type_and_name.nameleft);
             }
             
             TYPE_FUNCTION similar_function =  (TYPE_FUNCTION)overload;
             
             if(similar_function.returnType!=returnType){
                  String error = String.format(">> ERROR [%d:%d] a class trying to override a method with a diffrent return type\n",return_type_and_name.typeleft, return_type_and_name.typeright);
                  throw new SemanticException(error,return_type_and_name.typeleft);
             }  
            if((similar_function.params==null&&type_list!=null)){
                  String error = String.format(">> ERROR [%d:%d] a class trying to override a method with a diffrent parameters list\n",0,0);
                  throw new SemanticException(error,return_type_and_name.nameleft);
             }
             if(similar_function.params!=null&&!similar_function.params.compare_list(type_list)){
                  String error = String.format(">> ERROR [%d:%d] a class trying to override a method with a diffrent parameters list\n",-1,0);
                  throw new SemanticException(error,return_type_and_name.nameleft);
             }
    }    }

		/*******************/
		/* [3] Semant Body */
		/*******************/
    //added function type before body, for recursions, still added it again, after scope pop
    Integer generator =new Integer(0);     
    TYPE_FUNCTION function_type = new TYPE_FUNCTION(returnType,return_type_and_name.name,type_list);
		SYMBOL_TABLE.getInstance().enter(return_type_and_name.name,function_type);
		body.SemantMe(returnType,generator);

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(return_type_and_name.name,function_type);

    /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return function_type;		
	}
    
    public TEMP IRme()
	{
		
		
        
        //label for the func
        SYMBOL_TABLE_ENTRY e = SYMBOL_TABLE.getInstance().getEntry(return_type_and_name.name);
        String label = return_type_and_name.name + "_"+e.scope_depth;
        if (!return_type_and_name.name.equals("main"))
        	//jump to end, func dec phase should not execute the code
        	IR.getInstance().Add_IRcommand(new IRcommand_JUMP(label + "End"));
        //emit func label
        IR.getInstance().Add_IRcommand(new IRcommand_Label(label));

        
        
        TEMP temp = null;
        
        
        if (body != null) 
        	temp = body.IRme(); 
        
        if (!return_type_and_name.name.equals("main")){
	       
	        
	        //case void - return zero
	        if (return_type_and_name.type.equals("void")){
	          TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
	        	//temp = 0
	        	
	          IR.getInstance().Add_IRcommand(new IRcommandMove(t, null));
	 	        IR.getInstance().Add_IRcommand(new IRcommand_StoreReturnValueOnStack(t));
            //jump back 
	          IR.getInstance().Add_IRcommand(new IRcommand_Jr());
                                     
	        }
	  
	        //end of func
	        IR.getInstance().Add_IRcommand(new IRcommand_Label(label + "End"));
	        
        }
        else {
        	IR.getInstance().Add_IRcommand(new IRcommand_JUMP("End"));
        }
        
 
        

		return null;
	}
	
}
