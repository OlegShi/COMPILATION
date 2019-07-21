package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_DEC extends AST_Node
{
  public AST_PARAM param;
  public AST_EXP exp;
  public int line_number;
  public int column_number;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_VAR_DEC(AST_PARAM param, AST_EXP exp, int line_number, int column_number)
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
    this.line_number = line_number;
    this.column_number = column_number;
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
 
	public boolean existInheritance(String ancestorClass, TYPE_CLASS t) {
		TYPE_CLASS currClass = t;
		TYPE s;
		while (currClass.father != null){
			//System.out.println("while");
			s = SYMBOL_TABLE.getInstance().find(currClass.name);
			if (s == null){
				System.out.format(">> ERROR [%d:%d]1 type mismatch for var := exp\n",line_number,column_number);
			}
			if (ancestorClass.equals(currClass.name))
				return true;
			currClass = currClass.father;
		}
		s = SYMBOL_TABLE.getInstance().find(currClass.name);
		if (s == null){
			System.out.format(">> ERROR [%d:%d]2 type mismatch for var := exp\n",line_number,column_number);
		}
		if (ancestorClass.equals(currClass.name))
			return true;
		return false;
		
	}

 
 public TYPE SemantMe(TYPE_LIST father_data_members) throws SemanticException {
	
	 
	 TYPE t1 = param.SemantMe(father_data_members);
	
	 
   TYPE_VAR tv = new TYPE_VAR(param.name,t1);

        
  /*********************************************************/
  /* check that name is not in symbol table already   */
  /********************************************************/
   
  TYPE e = SYMBOL_TABLE.getInstance().find(param.name);
  int scope = SYMBOL_TABLE.getInstance().getElementScope(param.name);
  //&& (e instanceof TYPE_CLASS||e instanceof TYPE_FUNCTION ))
  if (e != null && e.name.equals(param.name) ){
        String error = String.format(">> ERROR [%d:%d] using class name as a var name\n",param.nameleft,param.nameright);
        throw new SemanticException(error,param.nameleft);
    }
    
  if (e != null &&  scope == SYMBOL_TABLE.getCurrScope()) {
    String error = String.format(">> ERROR [%d:%d] Found var with the same name in symbol table\n",param.nameleft,param.nameright);
      System.out.print("Hey!!");
      System.out.print(line_number);
    throw new SemanticException(error,param.nameleft);
  }
   
  /******************************/
  /* Enter var  to symbol table */
  /******************************/
  SYMBOL_TABLE.getInstance().enter(param.name,t1);
	
	 if(exp==null){
	 	return tv;	
	 }
	
	 TYPE t2 = exp.SemantMe();
	 
		/*case: one of them INT*/
		if ((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_INT)){
			return tv;	
		}
        // Assigning int to array is good
        //else if ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_ARRAY)){
             //System.out.format("int to array is good\n");
            //return tv;
            //}
     
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_STRING)) || ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_STRING))){
			String error = String.format(">> ERROR [%d:%d]3 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		if ((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_NIL)){
			String error = String.format(">> ERROR [%d:%d] type mismatch for var := exp\n",line_number,column_number);
	         throw new SemanticException(error,line_number);	
		}
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_CLASS)) || ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_CLASS))){
			String error = String.format(">> ERROR [%d:%d]4 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_ARRAY))){
			String error = String.format(">> ERROR [%d:%d]5 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		/*case on of them STRING*/
		else if ((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_STRING)){
			return tv;
		}
		else if ((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_NIL)){
			String error = String.format(">> ERROR [%d]5.5 type mismatch for var := exp\n",line_number);
	         throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_CLASS)) || ((t2 instanceof TYPE_STRING) && (t1 instanceof TYPE_CLASS))){
			String error = String.format(">> ERROR [%d:%d]6 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_ARRAY)) || ((t2 instanceof TYPE_STRING) && (t1 instanceof TYPE_ARRAY))){
			String error = String.format(">> ERROR [%d:%d]7 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		/*case left var is NULL*/
		else if (t1 instanceof TYPE_NIL){
			String error = String.format(">> ERROR [%d:%d]8 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		/*case left is array and right is NULL*/
   
   		/*case left is array and right isn't*/
		else if((t1 instanceof TYPE_ARRAY)  && !(t2 instanceof TYPE_ARRAY)){
     if(t2 instanceof TYPE_NIL){
  			TYPE t = SYMBOL_TABLE.getInstance().find(t1.name);
  			if (t == null){
  				String error = String.format(">> ERROR [%d:%d]9 type mismatch for var := exp\n",line_number,column_number);
                  throw new SemanticException(error,line_number);
  			}
  			return tv;
      }
      if(t2 instanceof TYPE_NEW_ARRAY){
       if(((TYPE_ARRAY)t1).arrayType.name.equals(((TYPE_NEW_ARRAY)t2).arrayType.name)){
  				return null;
  			}
  			String error = String.format(">> ERROR [%d:%d]9.2 type mismatch for var := exp\n",line_number,column_number);
       throw new SemanticException(error,line_number);
     }
     
      String error = String.format(">> ERROR [%d:%d]9.5 type mismatch for var := exp\n",line_number,column_number);
      throw new SemanticException(error,line_number);

		}
   
   		/*case left is class and right isn't*/
		else if((t1 instanceof TYPE_CLASS) && !(t2 instanceof TYPE_CLASS)){
      if(t2 instanceof TYPE_NIL){
       //I don't understand what happens here. it seems that t can only be equal to t1      
  			TYPE t = SYMBOL_TABLE.getInstance().find(t1.name);
  			if (t == null){
  				String error = String.format(">> ERROR [%d:%d]10 type mismatch for var := exp\n",line_number,column_number);
          throw new SemanticException(error,line_number);
        }
  			return tv;
      }
      else{
        String error = String.format(">> ERROR [%d:%d]10.5a type mismatch for var := exp\n",line_number,column_number);
        throw new SemanticException(error,line_number);
      }
		}
		/*case both classes*/
		else if((t1 instanceof TYPE_CLASS) && (t2 instanceof TYPE_CLASS)){
     //System.out.format("calsses\n");
			String ancestorClass = t1.name;
			TYPE t = SYMBOL_TABLE.getInstance().find(ancestorClass);
			if (t == null){
				String error = String.format(">> ERROR [%d:%d]11 type mismatch for var := exp\n",line_number,column_number);
             throw new SemanticException(error,line_number);
			}
			//check that right's type or its ancestor type matches left's
			if(existInheritance(ancestorClass, (TYPE_CLASS)t2)){
				return tv;
			}
			String error = String.format(">> ERROR [%d:%d]12 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}
		/*case both ARRAYS*/
		else if((t1 instanceof TYPE_ARRAY) && (t2 instanceof TYPE_ARRAY)){
     //System.out.format("arrays\n");
			TYPE s1 = SYMBOL_TABLE.getInstance().find(t1.name);
			if (s1 == null){
				String error = String.format(">> ERROR [%d:%d]13 type mismatch for var := exp\n",line_number,column_number);
             throw new SemanticException(error,line_number);
			}
			TYPE s2 = SYMBOL_TABLE.getInstance().find(t2.name);
			if (s2 == null){
				String error = String.format(">> ERROR [%d:%d]14 type mismatch for var := exp\n",line_number,column_number);
             throw new SemanticException(error,line_number);
			}
      
      //System.out.format("(%s := %s)\n",((TYPE_ARRAY)t1).arrayType.name,((TYPE_ARRAY)t2).arrayType.name);
			if(((TYPE_ARRAY)t1).arrayType.name.equals(((TYPE_ARRAY)t2).arrayType.name)){
				return tv;
			}
			String error = String.format(">> ERROR [%d:%d]15 type mismatch for var := exp\n  (%s := %s) ",line_number,column_number,((TYPE_ARRAY)t1).arrayType.name,((TYPE_ARRAY)t2).arrayType.name);
         throw new SemanticException(error,line_number);
		}

		/*case right is FUNCTION*/
		else if (t2 instanceof TYPE_FUNCTION){
			TYPE s2 = SYMBOL_TABLE.getInstance().find(t2.name);
			if (s2 == null){
				String error = String.format(">> ERROR [%d:%d]16 type mismatch for var := exp\n",line_number,column_number);
             throw new SemanticException(error,line_number);
			}
			TYPE s1 = SYMBOL_TABLE.getInstance().find(t1.name);
			TYPE returnType = ((TYPE_FUNCTION)t2).returnType;
			// returned Type is VOID, ERROR
			if(returnType instanceof TYPE_VOID) {
				String error = String.format(">> ERROR [%d:%d]17 type mismatch for var := exp\n voiiid",line_number,column_number);
             throw new SemanticException(error,line_number);
			}
			//left is INT and returned Type is INT
			if((s1 instanceof TYPE_INT) && (returnType instanceof TYPE_INT))
				return tv;
			//left is STRING and returned Type is STRING
			if((s1 instanceof TYPE_STRING) && (returnType instanceof TYPE_STRING))
				return tv;
			//left is ARRAY and returned Type is ARRAY
			if((s1 instanceof TYPE_ARRAY) && (returnType instanceof TYPE_ARRAY)){
				TYPE r1 = SYMBOL_TABLE.getInstance().find(s1.name);
				if (r1 == null){
					String error = String.format(">> ERROR [%d:%d]18 type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);		
				}
				TYPE r2 = SYMBOL_TABLE.getInstance().find(returnType.name);
				if (r2 == null){
					String error = String.format(">> ERROR [%d:%d]19 type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);
				}
				if(r1.name.equals(r2.name))
					return tv;
				String error = String.format(">> ERROR [%d:%d]20 type mismatch for var := exp\n",line_number,column_number);
		        throw new SemanticException(error,line_number);
			}
			if((s1 instanceof TYPE_CLASS) && (returnType instanceof TYPE_CLASS)){
				String ancestorClass = s1.name;
				TYPE t = SYMBOL_TABLE.getInstance().find(ancestorClass);
				if (t == null){
					String error = String.format(">> ERROR [%d:%d]21 type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);
				}
				//check that right's type or its ancestor type matches left's type
				if(existInheritance(ancestorClass, (TYPE_CLASS)returnType)){
					return tv;
				}
				String error = String.format(">> ERROR [%d:%d]22 type mismatch for var := exp\n",line_number,column_number);
	            throw new SemanticException(error,line_number);
			}
			String error = String.format(">> ERROR [%d:%d]23 type mismatch for var := exp\n",line_number,column_number);
         throw new SemanticException(error,line_number);
		}

		return tv; // should not get here
	 
}
 
}
