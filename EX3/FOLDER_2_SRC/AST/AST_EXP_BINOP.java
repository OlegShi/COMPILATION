package AST;

import TYPES.*;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;
	public int line_number;
  	public int column_number;
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

}
