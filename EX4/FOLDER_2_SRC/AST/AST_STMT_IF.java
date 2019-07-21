package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;
	public int line_number;
	public int column_number;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT_LIST body, int line_number, int column_number)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> IF LPAREN exp RPAREN LBRACE stmtList RBRACE\n");
   
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.cond = cond;
		this.body = body;
		this.line_number = line_number;
		this.column_number = column_number;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE IF STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"if(left)\n{right}");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
 
	public TYPE SemantMe(TYPE func_return_type, Integer generator)  throws SemanticException
	{
		/****************************/
		/* [0] Semant the Condition */
		/****************************/
		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			String error = String.format(">> ERROR [%d:%d] condition inside IF is not integral\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		body.SemantMe(func_return_type,generator);

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
    
    public TEMP IRme()
    {
        // Insert '1' (true) into a new temp
        TEMP trueVal = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.
			getInstance().
			Add_IRcommand(new IRcommandConstInt(trueVal, 1));
        
        TEMP condVal = cond.IRme();
        
        String szLabelIfTrue = "if_true";
        String szLabelIfEnd = "if_end";
        
        IRcommand_IF ifComm = new IRcommand_IF(condVal, trueVal, szLabelIfEnd, szLabelIfTrue);
        
        IR.
			getInstance().
			Add_IRcommand(ifComm);
        
        IR.
			getInstance().
			Add_IRcommand(new IRcommand_Label(ifComm.szLabelIfTrue));
        
        body.IRme();
        
        IR.
			getInstance().
			Add_IRcommand(new IRcommand_Label(ifComm.szLabelIfEnd));
        
        return null;
    }
}