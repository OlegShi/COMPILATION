package AST;

import TYPES.*;


public class AST_VAR_FEILD extends AST_C_FEILD
{
  public AST_VAR_DEC varDec;  
  
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_VAR_FEILD(AST_VAR_DEC varDec)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
   System.out.print("====================== cField -> varDec\n");

      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varDec = varDec;
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
		if (varDec != null) varDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   

    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	"CLASS \nVAR FEILD");
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);

	}
 
	public TYPE SemantMe(TYPE_LIST father_data_members) throws SemanticException
	{
		return varDec.SemantMe(father_data_members);
	}

}
