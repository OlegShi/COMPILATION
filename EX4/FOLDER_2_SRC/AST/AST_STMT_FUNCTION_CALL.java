package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_STMT_FUNCTION_CALL extends AST_STMT
{
  public AST_VAR var;
	public String name ;
	public AST_EXP_LIST paremeteres;
	public int name_line_number;
	public int name_column_number;
  public int var_line_number;
	public int var_column_number;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_FUNCTION_CALL(AST_VAR var,String name,AST_EXP_LIST paremeteres, int name_line_number, int name_column_number, int var_line_number, int var_column_number)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(var == null)
      if(paremeteres==null)
  		  System.out.format("====================== stmt -> ID( %s ) LPAREN RPAREN SEMICOLON\n",name);
      else
        System.out.format("====================== stmt -> ID( %s ) LPAREN expList RPAREN SEMICOLON\n",name);
    else
      if(paremeteres==null)
  		  System.out.format("====================== stmt -> var DOT ID( %s ) LPAREN RPAREN SEMICOLON\n",name);
      else
        System.out.format("====================== stmt -> var DOT ID( %s ) LPAREN expList RPAREN SEMICOLON\n",name);
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
   this.var=var;
		this.name = name;
		this.paremeteres = paremeteres;
		this.name_line_number=name_line_number;
		this.name_column_number=name_column_number;
    this.var_line_number=var_line_number;
		this.var_column_number=var_column_number;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.format("AST NODE FUNCTION CALL STMT (%s)\n",name);

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (paremeteres != null) paremeteres.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
    String p = "";
    if (paremeteres != null)
      p="right";
    
    String v = "";
    if (var != null)
      v="left.";
      
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	String.format(
			"%s%s(%s)",v,name,p));
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (paremeteres != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,paremeteres.SerialNumber);
	}
    
  public TYPE SemantMe(TYPE func_return_type, Integer generator)  throws SemanticException{
	     	
    TYPE f_t=null;
    //check if call is a method call ( i.e a.f() )
		if (var != null){
      TYPE v_t = var.SemantMe();	
       
      
      if(!v_t.isClass()){
        String error = String.format(">> ERROR [%d:%d] Trying to call a method of something that is not a class(%s)\n",var_line_number,var_column_number,v_t.name);
        throw new SemanticException(error,var_line_number);          
      }
    
      
      TYPE_CLASS p_pointer = ((TYPE_CLASS)v_t);
      
      while(p_pointer!=null&&f_t==null){     
        f_t =p_pointer.data_members.search_by_name(name);
         p_pointer = p_pointer.father;
      }
      
      if(f_t==null){
        String error = String.format(">> ERROR [%d:%d] Class (%s) have no method called (%s)\n",name_line_number,name_column_number,v_t.name,name);
        throw new SemanticException(error,name_line_number);
      }
		}

		
		else{
			//get function type by name
			f_t = SYMBOL_TABLE.getInstance().find(name);
      if (f_t == null){
  			/**************************/
  			/* ERROR: undeclared function */
  			/**************************/
  			String error = String.format(">> ERROR [%d:%d] Found a function name (%s) not defined in symbol table\n",name_line_number,name_column_number,name);
                          throw new SemanticException(error,name_line_number);
                          
  		}
		}
     
          	
		
     		//check if name exist
		
		
		if(!(f_t instanceof TYPE_FUNCTION)){
			String error = String.format(">> ERROR [%d:%d] Trying to call non-function (%s) as a function\n",name_line_number,name_column_number,name);
                        throw new SemanticException(error,name_line_number);
		}
   
    		
    		// Check if parameters are of types of the function args
    		
    		//get type list from AST_EXP_LIST.semantMe()
        TYPE_LIST dec_params =null;
    		if(paremeteres!=null)dec_params = paremeteres.SemantMe();	
		
		

     		
     		//check if type.params equal to paremeteres
     		TYPE_LIST func_params = ((TYPE_FUNCTION)f_t).params;
   //System.out.print(" HEY 1 ");   
//System.out.print(func_params);
      //System.out.print(" HEY 2 ");  
    // System.out.print(dec_params);
      //System.out.print(" HEY 3 ");  
              //System.out.println("function call:");
             	if(     (func_params==null&&dec_params!=null)     ||    (func_params!=null&&!func_params.compare_list(dec_params))  ){
                	String error = String.format(">> ERROR [%d:%d] a function call(%s) with wrong parameters list\n",name_line_number,name_column_number,name);
                	throw new SemanticException(error,name_line_number);
             	}
    
		return ((TYPE_FUNCTION)f_t).returnType;
	}
    
    
    
  public TEMP IRme()
	{
         TEMP t = null;
        if(name.equals("PrintInt")){
        	if (paremeteres != null) { t = paremeteres.IRme(); }
            IR.getInstance().Add_IRcommand(new IRcommandPrintInt(t));
            
            return null;
        }
        if(name.equals("PrintString")){
        	if (paremeteres != null) { t = paremeteres.IRme(); }
            IR.getInstance().Add_IRcommand(new IRcommandPrintString(t));
         
            return null;
        }
        

        //save the registers $t0-$t7 on the stack
        IR.getInstance().Add_IRcommand(new IRcommand_SaveRegsOnStack());
        
        //set space for return address
        IR.getInstance().Add_IRcommand(new IRcommandSetSpaceOnStack(1));
        
        //save return address, $ra on the stack 
        IR.getInstance().Add_IRcommand(new IRcommand_StoreOnStack(null,0));
        
        //save $fp on the stack
        IR.getInstance().Add_IRcommand(new IRcommand_saveFramePointerOnStack());
        
        int numOfArgs = 0;
        
        if (paremeteres != null) {
        	
            //count number of args
            
        	for (AST_EXP_LIST list = paremeteres; list  != null; list = list.tail)
        	{
                ++numOfArgs;

        	}
            
            //set space on stack
            IR.getInstance().Add_IRcommand(new IRcommandSetSpaceOnStack(numOfArgs));
            
            AST_EXP_LIST list = paremeteres;
            //save arguments on the stack
            for(int i = 0; i<numOfArgs ; ++i) {
            	t = list.head.IRme();
            	IR.getInstance().Add_IRcommand(new IRcommand_StoreOnStack(t,i));
            	list = list.tail;
            }
              
        }
        
       
        	
  
       
        
        //$fp = $sp
        IR.getInstance().Add_IRcommand(new IRcommandMoveSpFp(1));
        

        
        //jump and link
        SYMBOL_TABLE_ENTRY e = SYMBOL_TABLE.getInstance().getEntry(name);
        String label = name +"_"+e.scope_depth;
        IR.getInstance().Add_IRcommand(new IRcommand_Jal(label));


        //$sp = $fp
        IR.getInstance().Add_IRcommand(new IRcommandMoveSpFp(0));
        
        //restore t0 - t7
        IR.getInstance().Add_IRcommand(new IRcommandRestoreArguments(numOfArgs));

        //load the return value from stack
        TEMP returnValue = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommand_LoadReturnValueFromStack(returnValue));
        
        //pop return value
        //pop arguments
        //restore $fp & $ra
        //pop fp & return address & local vars from stack
        IR.getInstance().Add_IRcommand(new IRcommand_FunctionEpiloge(numOfArgs));
        return null;
	}
}
