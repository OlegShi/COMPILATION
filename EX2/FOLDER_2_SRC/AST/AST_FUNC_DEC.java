package AST;

public class AST_FUNC_DEC extends AST_Node
{
    
  public AST_PARAM return_type_and_name;
  public AST_PARAM_LIST parameters;
  public AST_STMT_LIST body;
    
  public AST_FUNC_DEC(AST_PARAM param, AST_PARAM_LIST param_list, AST_STMT_LIST statemnt_list){
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
    if (param_list != null)
		  System.out.print("====================== funcDec -> param LPAREN paramList RPAREN LBRACE stmtList RBRACE \n");
    else
      System.out.print("====================== funcDec -> param LPAREN RPAREN LBRACE stmtList RBRACE \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		  this.return_type_and_name = param;
		  this.parameters = param_list;
		  this.body = statemnt_list;
    }
    
  public void PrintMe(){
 
 
    /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE FUNC DEC\n");
   
   
		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
   

  
		if (return_type_and_name != null) return_type_and_name.PrintMe();
		if (parameters != null) parameters.PrintMe();
    if (body != null) body.PrintMe();

		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("funcDec"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
    if (return_type_and_name != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,return_type_and_name.SerialNumber);
		if (parameters != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,parameters.SerialNumber);
    if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
    
    }
    
    
}
