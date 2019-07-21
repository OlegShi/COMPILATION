package AST;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;
  public int var_line_number;
  public int var_column_number;
  public int exp_line_number;
  public int exp_column_number;
	
 public int arr_element_size=1; 
 
 //TODO get real values
 public int element_size = 1; 
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR var,AST_EXP subscript, int var_line_number ,int var_column_number, int exp_line_number ,int exp_column_number)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;
    this.var_line_number = var_line_number;
    this.var_column_number = var_column_number;
    this.exp_line_number = exp_line_number;
    this.exp_column_number = exp_column_number;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}
 
  public TYPE SemantMe() throws SemanticException{
   
			TYPE t = null;
			
			/******************************/
			/* [1] Recursively semant var */
			/******************************/
			if (var != null) t = var.SemantMe();
			
      
      
			/*********************************/
			/* [2] Make sure type is a array */
			/*********************************/
			if (t.isArray() == false)
			{
        String error = String.format(">> ERROR [%d:%d] array access to non-array variable\n",var_line_number, var_column_number);
        throw new SemanticException(error,var_line_number);
			}
			
      if((((TYPE_ARRAY)t).arrayType) instanceof TYPE_INT){
          arr_element_size =1 ;
      }
      
			/************************************/
			/* [3] check exp is int */
			/************************************/
      
      if(!(subscript.SemantMe() instanceof TYPE_INT) ){
        String error = String.format(">> ERROR [%d:%d] array access with a non int expression\n",exp_line_number, exp_column_number);
        throw new SemanticException(error,exp_line_number);
      }              
    
      return ((TYPE_ARRAY)t).arrayType;    
  }
  
  
  public TEMP IRme(boolean isStore)
	{

      //return_value 
  	  	TEMP heap_with_offset= TEMP_FACTORY.getInstance().getFreshTEMP();   
  		  
      
      //get the arr_offset 
        TEMP arr_offset = subscript.IRme();  
       
      //get the heap_pointer  
        TEMP heap_pointer = var.IRme(false);  
        
        IR.getInstance().Add_IRcommand(new IRcommand_check_not_null(heap_pointer));  
      
      //calculate heap_with_offset
        IR.getInstance().Add_IRcommand(new IRcommand_calculate_address_for_arr(heap_with_offset , arr_offset , heap_pointer)); 
        
      if(isStore)
        return heap_with_offset;
        
      TEMP return_value = TEMP_FACTORY.getInstance().getFreshTEMP();    
      IR.getInstance().Add_IRcommand(new IRcommand_Load(return_value,heap_with_offset));
      return return_value;
	}  

}
