package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;
	public int line_number;
  	public int column_number;
    TYPE leftTypte;
    TYPE rightType;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP, int line_number, int column_number)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
		this.line_number = line_number;
    	this.column_number = column_number;
    	this.leftTypte = null; 
    	this.rightType = null;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
        if (OP == 2) {sOP = "*";}
        if (OP == 3) {sOP = "/";}
        if (OP == 4) {sOP = "<";}
        if (OP == 5) {sOP = ">";}
        if (OP == 6) {sOP = "=";}
		
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	
	public TYPE SemantMe() throws SemanticException
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();
		
		//Oleg: needed for IRme
		leftTypte =t1; 
		rightType = t2;
		
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			return TYPE_INT.getInstance();
		}
		// Concating 2 strings with '+' sign is allowed
		else if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()) && (OP == 0))
		{
			return TYPE_STRING.getInstance();
		}
		
    if (OP == 6){
      if(t1==t2)
        return TYPE_INT.getInstance();
      if (t1 instanceof TYPE_CLASS && ( t2 instanceof TYPE_NIL || ((TYPE_CLASS)t1).isExtendBy(t2)))
        return TYPE_INT.getInstance();
      if (t2 instanceof TYPE_CLASS && ( t1 instanceof TYPE_NIL || ((TYPE_CLASS)t2).isExtendBy(t1)))
        return TYPE_INT.getInstance();
      if(t1 instanceof TYPE_ARRAY &&  ( t2 instanceof TYPE_NIL || (  t2 instanceof TYPE_NEW_ARRAY   &&   ((TYPE_ARRAY)t1).arrayType.name.equals(((TYPE_NEW_ARRAY)t2).arrayType.name)   )))
          return TYPE_INT.getInstance();
          if(t2 instanceof TYPE_ARRAY &&  ( t1 instanceof TYPE_NIL || (  t1 instanceof TYPE_NEW_ARRAY   &&   ((TYPE_ARRAY)t2).arrayType.name.equals(((TYPE_NEW_ARRAY)t1).arrayType.name)   )))
        return TYPE_INT.getInstance(); 
    }   
    System.out.print(t1);
        System.out.print(t2);
		String error = String.format(">> ERROR [%d:%d] binop (%d) is not allowed with these two exps(%s,%s)\n",line_number,column_number,OP,t1.getClass().getName(),t2.getClass().getName());
    throw new SemanticException(error,line_number);
	}
    
    public TEMP IRme()
	{
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		
		assert(false);
		
		if (left  != null) t1 = left.IRme();
		if (right != null) t2 = right.IRme();
		
        // Add binop
		if (OP == 0)
		{
			//Oleg
			if (leftTypte == TYPE_STRING.getInstance() && rightType == TYPE_STRING.getInstance()){

				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Concat_Strings(dst,t1,t2));
			}
			else{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Add_Integers(dst,t1,t2));
			}
		}
        
        // Minus binop
		if (OP == 1)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Minus_Integers(dst,t1,t2));
		}
        
        // Multiply binop
        if (OP == 2)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Muli_Integers(dst,t1,t2));
		}
        
        // Divide binop
        if (OP == 3)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_Div_Integers(dst,t1,t2));
		}
        
        // < binop
		if (OP == 4)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_LT_Integers(dst,t1,t2));
		}
        // > binop
		if (OP == 5)
		{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_GT_Integers(dst,t1,t2));
		}
        // = binop
		if (OP == 6)
		{
			
			//Oleg
			if (leftTypte == TYPE_STRING.getInstance() && rightType == TYPE_STRING.getInstance()){

				IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Strings(dst,t1,t2));
			}
			else{
			IR.
			getInstance().
			Add_IRcommand(new IRcommand_Binop_EQ_Integers(dst,t1,t2));
			}
		}
		return dst;
	}

}
