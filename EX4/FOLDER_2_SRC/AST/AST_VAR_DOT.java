package AST;
import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_VAR_DOT extends AST_VAR {
	
	  public AST_VAR var;  
	  public String fieldName;
    public int field_line_number;
    public int field_column_number;
    public int field_index = 0;
	  
		/*******************/
		/*  CONSTRUCTOR(S) */
		/*******************/
		public AST_VAR_DOT(AST_VAR var, String fieldName, int field_line_number ,int field_column_number)
		{
			
	     /******************************/
			/* SET A UNIQUE SERIAL NUMBER */
			/******************************/
			SerialNumber = AST_Node_Serial_Number.getFresh();
	   
	    /***************************************/
			/* PRINT CORRESPONDING DERIVATION RULE */
			/***************************************/
	  System.out.print("====================== var -> var.feild\n");

	      
	      
	   /*******************************/
			/* COPY INPUT DATA MEMBERS ... */
			/*******************************/
			this.var = var;
			this.fieldName = fieldName;
      this.field_line_number = field_line_number;
      this.field_column_number = field_column_number;
      int field_index=0;
		}
	 
	 
	 public void PrintMe()
		{
			/********************************************/
			/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
			/********************************************/
			System.out.print("AST NODE CLASS FEILD\n");

			/***********************************/
			/* RECURSIVELY PRINT VAR + EXP ... */
			/***********************************/
			if (var != null) var.PrintMe();

			/***************************************/
			/* PRINT Node to AST GRAPHVIZ DOT file */
			/***************************************/
	   

	    AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,	"CLASS FEILD");
	      
			/****************************************/
			/* PRINT Edges to AST GRAPHVIZ DOT file */
			/****************************************/
	    if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);

		}
	 
		public TYPE SemantMe() throws SemanticException
		{
			TYPE t = null;
			TYPE_CLASS tc = null;
			
			/******************************/
			/* [1] Recursively semant var */
			/******************************/
			if (var != null) t = var.SemantMe();
			
			/*********************************/
			/* [2] Make sure type is a class */
			/*********************************/
			if (t.isClass() == false)
			{
        String error = String.format(">> ERROR [%d:%d] access %s field of a non-class variable\n",field_line_number, field_column_number,fieldName);
        throw new SemanticException(error,field_line_number);
			}

      tc = (TYPE_CLASS) t;
	
			/************************************/
			/* [3] Look for fiedlName inside tc */
			/************************************/
      //should only be null if running inside the same class and it is not fully declared yet
      
      int i=0;
      
      TYPE_CLASS class_p = ((TYPE_CLASS)tc);
      do{
          TYPE_LIST data_members = class_p.data_members;
          if(data_members!=null){
      			for (TYPE_LIST it=data_members;it != null;it=it.tail)
      			{
      				if (it.head.name.equals(fieldName))
      				{
                field_index =i;
                //System.out.println(fieldName+" "+field_index);
      					return ((TYPE_VAR)it.head).type;
      				}
              if(it.head instanceof TYPE_VAR){
                i++;
              }
      			}  
         }
         class_p = class_p.father;
      }
       while(class_p!=null);
      
      
			
			/*********************************************/
			/* [4] fieldName does not exist in class var */
			/*********************************************/
			String error = String.format(">> ERROR [%d:%d] field (%s) does not exist in class\n",field_line_number, field_column_number,fieldName);							
			throw new SemanticException(error,field_line_number);
		}


    public TEMP IRme(boolean isStore)
  	{


        //get the heap_pointer  
        TEMP heap_pointer = var.IRme(false);
        
        IR.getInstance().Add_IRcommand(new IRcommand_check_not_null(heap_pointer)); 
        
        //calculate actual pointer 
        TEMP pointer_with_offset = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new  IRcommand_get_Class_Pointer(heap_pointer,pointer_with_offset,field_index));
        
        if(isStore)
          return pointer_with_offset;
        
        TEMP return_value = TEMP_FACTORY.getInstance().getFreshTEMP();    
        IR.getInstance().Add_IRcommand(new IRcommand_Load(return_value,pointer_with_offset));
        return return_value;
	  } 
}
