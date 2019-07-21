package AST;

public class AST_CLASS_DEC extends AST_Node{
  
  public String className;
  public String extendName;
  public AST_C_FEILD_LIST cFeildList;
  
  
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_CLASS_DEC(String className, String extendName, AST_C_FEILD_LIST cFeildList)
	{
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if(extendName==null)
      System.out.print("====================== classDec -> CLASS ID LBRACE cFieldList RBRACE\n");
    else
      System.out.print("====================== classDec -> CLASS ID EXTENDS ID LBRACE cFieldList RBRACE\n");
      
      
   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.className = className;
    this.extendName = extendName;
    this.cFeildList = cFeildList;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE CLASS DEC\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (cFeildList != null) cFeildList.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   
   String e = "";
   if (extendName != null) 
     e="extends "+extendName;
   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	String.format("class %s %s",className,e));
      
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (cFeildList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cFeildList.SerialNumber);
	}
}