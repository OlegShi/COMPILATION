package AST;

public class AST_VAR_DEC extends AST_Node
{
  public AST_PARAM param;
  public AST_EXP exp;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_VAR_DEC(AST_PARAM param, AST_EXP exp)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(exp==null)
      System.out.print("====================== varDec -> param SEMICOLON\n");
    else
      System.out.print("====================== varDec -> param ASSIGN exp SEMICOLON\n");
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.param = param;
    this.exp = exp;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE VAR DEC\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (param != null) param.PrintMe();
    if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   
   String e = "";
   if (exp != null) 
     e="\nleft:=right";
   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	String.format("VAR DEC%s",e));
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (param != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,param.SerialNumber);
    if (exp != null)   AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
}