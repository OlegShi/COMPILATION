package AST;

public class AST_EXP_FUNCTION_CALL extends AST_EXP
{
  public AST_VAR var;
	public String name ;
	public AST_EXP_LIST paremeteres;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_FUNCTION_CALL(AST_VAR var,String name,AST_EXP_LIST paremeteres)
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
  		  System.out.format("====================== exp -> ID( %s ) LPAREN RPAREN SEMICOLON\n",name);
      else
        System.out.format("====================== exp -> ID( %s ) LPAREN expList RPAREN SEMICOLON\n",name);
    else
      if(paremeteres==null)
  		  System.out.format("====================== exp -> var DOT ID( %s ) LPAREN RPAREN SEMICOLON\n",name);
      else
        System.out.format("====================== exp -> var DOT ID( %s ) LPAREN expList RPAREN SEMICOLON\n",name);
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.paremeteres = paremeteres;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.format("AST NODE FUNCTION CALL EXP (%s)\n",name);

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
}