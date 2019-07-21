package AST;

import TYPES.*;

public class AST_VAR_DOT extends AST_VAR {
	
	  public AST_VAR var;  
	  public String fieldName;
    public int field_line_number;
    public int field_column_number;
	  
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
      if(tc.data_members!=null){
  			for (TYPE_LIST it=tc.data_members;it != null;it=it.tail)
  			{
  				if (it.head.name.equals(fieldName))
  				{
  					return ((TYPE_VAR)it.head).type;
  				}
  			}
      }
      
      //tc.data_members.printList();
      
      TYPE_CLASS class_p = tc.father;
      while(class_p!=null){
       //class_p.data_members.printList();      
  			for (TYPE_LIST it=class_p.data_members;it != null;it=it.tail)
  			{
          //System.out.format("%s   %s  \n",it,it.head);
  				if (it.head.name.equals(fieldName))
  				{
  					return ((TYPE_VAR)it.head).type;
  				}
  			}
			  class_p = class_p.father;
      }
			
			/*********************************************/
			/* [4] fieldName does not exist in class var */
			/*********************************************/
			String error = String.format(">> ERROR [%d:%d] field (%s) does not exist in class\n",field_line_number, field_column_number,fieldName);							
			throw new SemanticException(error,field_line_number);
		}

}
