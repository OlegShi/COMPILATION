package AST;

import TYPES.*;

public class AST_STMT_RETURN extends AST_STMT{
  public AST_EXP return_value;
  public int exp_line_number;
  public int exp_column_number;
  
  public AST_STMT_RETURN(AST_EXP exp, int exp_line_number ,int exp_column_number)
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
    this.exp_line_number = exp_line_number;
    this.exp_column_number = exp_column_number;
   
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
 
  public TYPE SemantMe(TYPE func_return_type) throws SemanticException{
    
    
    
    if(return_value!=null){
      TYPE actual_return = return_value.SemantMe();
      //System.out.format("sup  %s   %s\n",t,func_return_type); 
      if(actual_return!= func_return_type){
        if(func_return_type instanceof TYPE_CLASS && ((TYPE_CLASS)func_return_type).isExtendBy(actual_return))
          return null;     
        if(func_return_type instanceof TYPE_ARRAY  && (actual_return instanceof TYPE_NIL ||  (actual_return instanceof TYPE_NEW_ARRAY &&  ((TYPE_ARRAY)func_return_type).arrayType.name.equals(((TYPE_NEW_ARRAY)actual_return).arrayType.name)) ))
         return null;
        String r_type ="";
        if(func_return_type!=null)
	        r_type =func_return_type.name;
	else
		r_type = "void";
        String error = String.format(">> ERROR [%d:%d] trying to return (%s) for a function with a return type(%s)\n",exp_line_number,exp_column_number,actual_return.name,r_type);
        throw new SemanticException(error,exp_line_number);
      }
    }
    else{
      if(func_return_type!=null){
        String error = String.format(">> ERROR [%d:%d] empty return statment for a function with a non-void return type(%s)\n",exp_line_number,exp_column_number,func_return_type.name);
        throw new SemanticException(error,exp_line_number);
      }
       
    }
    
    return null;
 }
 

}
