package AST;

public class AST_EXP_NEW_ID extends AST_EXP {
	
	public String name;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_NEW_ID(String name, AST_EXP exp)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(exp==null)
      System.out.print("====================== exp -> NEW ID\n");
    else
      System.out.print("====================== exp ->NEW ID exp\n");
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.exp = exp;
	}
	
	
	 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST EXP NEW ID  */
		/********************************************/
		System.out.print("AST NODE EXP NEW ID\n");

		/***********************************/
		/* RECURSIVELY PRINT EXP ... */
		/***********************************/
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
	   if(exp==null)
	   	AST_GRAPHVIZ.getInstance().logNode(SerialNumber,String.format("new %s",name));
	   else
      		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,String.format("new %s[]\n",name));   
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
	    if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		}
	
 
}
