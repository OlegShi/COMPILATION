package AST;

public class AST_PARAM extends AST_Node{

  public String type;
  public String name;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_PARAM(String type, String name){
     /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
   
    /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

    System.out.format("====================== param -> type(%s) name(%s)\n",type,name);


   /*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
    this.name = name;
	}
 
 public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE PARAM\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
   
   
    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,	String.format("%s %s",type,name));
      
	}
}
