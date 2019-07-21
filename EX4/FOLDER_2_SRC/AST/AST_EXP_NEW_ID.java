package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;
import java.util.*;
import TYPES.TYPE_VAR.InitValue;

public class AST_EXP_NEW_ID extends AST_EXP {
	

	public String name;
	public AST_EXP exp;
    public int exp_line_number;
    public int exp_column_number;
    public int name_line_number;
    public int name_column_number;
    
    public int arr_element_size=1; 
    public int class_size = 1;
    public ArrayList<InitValue> inital_values = new ArrayList<>();

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_NEW_ID(String name, AST_EXP exp, int exp_line_number ,int exp_column_number, int name_line_number ,int name_column_number)
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
        this.exp_line_number = exp_line_number;
        this.exp_column_number = exp_column_number;
         this.name_line_number = name_line_number;
        this.name_column_number = name_column_number;
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
	
 public TYPE SemantMe() throws SemanticException
 {
     // Check if the class/array type exists in SYMBOL_TABLE
     TYPE id_name = SYMBOL_TABLE.getInstance().find(name);
       //if(exp != null){
       //  System.out.format("=====%s\n",id_name.name);
       //}
  		  if (id_name == null) {
                  /**************************/
                  /* ERROR: undeclared type */
                  /**************************/
                  String error = String.format(">> ERROR [%d:%d] Trying to use NEW before a class/array name doesn't exists\n",name_line_number, name_column_number);
                  throw new SemanticException(error,name_line_number);
	       }
  		  //Oleg
  		  //else if(exp == null) {
  			//  String error = String.format(">> ERROR [%d:%d] Trying to use NEW with null subscript \n",name_line_number,-1);
  			//  throw new SemanticException(error,name_line_number);  			  
  		  //}
              // Semant the subscript if exists - check that its an integer
        else{ 
            if (exp != null) {
                 
                          
              //TYPE exp_t = exp.SemantMe();
              if (!(exp instanceof AST_EXP_INT)) {
                  String error = String.format(">> ERROR [%d:%d] Not an integer in subscript\n",exp_line_number, exp_column_number);
                  throw new SemanticException(error,exp_line_number);
              }
              
              if(id_name instanceof TYPE_INT){
                arr_element_size =1 ;
              }
              
              
              return new TYPE_NEW_ARRAY(name,id_name);
              //return id_name;
            }
            else{
                if(!(id_name instanceof TYPE_CLASS)){
                  String error = String.format(">> ERROR [%d:%d] trying to create an instance of a non-class (%s)\n",name_line_number, name_column_number,id_name.name);
                  throw new SemanticException(error,name_line_number);
                } 
                
                
                //save the data members [int/string/label] 
                
                TYPE_CLASS class_p = ((TYPE_CLASS)id_name);
                do{
                  TYPE_LIST data_members = class_p.data_members;
                  if(data_members!=null){
                			for (TYPE_LIST it=data_members;it != null;it=it.tail){
                          if(it.head instanceof TYPE_VAR){
                                   
                               inital_values.add(((TYPE_VAR)it.head).initValue);
                               //System.out.println("const value:" + ((TYPE_VAR)it.head).const_value);                      
                          }
                			}
                  }
                  
                  class_p = class_p.father;
                }
                while(class_p!=null);
      
            }               
        }
        return id_name;
 }
 
    public TEMP IRme(){
    
      TEMP heap_pointer =  TEMP_FACTORY.getInstance().getFreshTEMP();
      
      if (exp != null) {
          TEMP arr_size = exp.IRme();
          IR.getInstance().Add_IRcommand(new  IRcommandNewArray(heap_pointer,arr_size,arr_element_size));
      }
      else{
          IR.getInstance().Add_IRcommand(new  IRcommandNewClass(heap_pointer,inital_values.size()));
          for(int i=0; i<inital_values.size(); i++){
              TEMP pointer_with_offset = TEMP_FACTORY.getInstance().getFreshTEMP();
              IR.getInstance().Add_IRcommand(new  IRcommand_get_Class_Pointer(heap_pointer,pointer_with_offset,i));
              
              
              
              TEMP temp_with_value = TEMP_FACTORY.getInstance().getFreshTEMP();
              InitValue initValue = inital_values.get(i);
              
              if(!initValue.isString){
                IR.getInstance().Add_IRcommand(new  IRcommandConstInt(temp_with_value,initValue.intValue));
              }
              else{
                IR.getInstance().Add_IRcommand(new IRcommandString(temp_with_value,initValue.stringValue));
              }
              
              
              
              IR.getInstance().Add_IRcommand(new  IRcommand_Store(pointer_with_offset,temp_with_value));
              
              
          }
          
      }
        
        return  heap_pointer;
    }

}
