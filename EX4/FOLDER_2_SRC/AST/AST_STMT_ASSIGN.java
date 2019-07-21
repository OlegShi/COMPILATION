package AST;
import TYPES.*;
import IR.*;
import TEMP.*;
import MIPS.*;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;
	public int line_number;
	public int column_number;
    
    public int classification = 0;
	

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, int line_number, int column_number)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
		this.line_number = line_number;
		this.column_number = column_number;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	
	public boolean existInheritance(String ancestorClass, TYPE_CLASS t) {
		TYPE_CLASS currClass = t;
		TYPE s;
		while (currClass.father != null){
			s = SYMBOL_TABLE.getInstance().find(currClass.name);
			if (s == null){
				System.out.format(">> ERROR [%d:%d]1a type mismatch for var := exp\n",line_number,column_number);
			}
			if (ancestorClass.equals(currClass.name))
				return true;
			currClass = currClass.father;
		}
		s = SYMBOL_TABLE.getInstance().find(currClass.name);
		if (s == null){
			System.out.format(">> ERROR [%d:%d]2a type mismatch for var := exp\n",line_number,column_number);
		}
		if (ancestorClass.equals(currClass.name))
			return true;
		return false;
		
	}
	//Oleg
	public TYPE SemantMe(TYPE func_return_type, Integer generator)  throws SemanticException
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();
        
		//System.out.format("%s-",t1);  
    //System.out.format("%s\n",t2);
    if(var instanceof AST_VAR_SIMPLE){
        this.classification = SYMBOL_TABLE.getInstance().getEntry(((AST_VAR_SIMPLE)var).name).getVariableClassification();
    }
		/*case: one of them INT*/
		if ((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_INT)){
			return null;	
		}
        // Assigning int to array is good
        //else if ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_ARRAY))
            //return t1;
            
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_STRING)) || ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_STRING))){
			String error = String.format(">> ERROR [%d:%d]3a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		if ((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_NIL)){
			String error = String.format(">> ERROR [%d:%d] type mismatch for var := exp\n",line_number,column_number);
	         throw new SemanticException(error,line_number);	
		}
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_CLASS)) || ((t2 instanceof TYPE_INT) && (t1 instanceof TYPE_CLASS))){
			String error = String.format(">> ERROR [%d:%d]4a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_INT) && (t2 instanceof TYPE_ARRAY))){
			String error = String.format(">> ERROR [%d:%d]5a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		/*case on of them STRING*/
		else if ((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_STRING)){
			return null;
		}
		else if ((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_NIL)){
			String error = String.format(">> ERROR [%d:%d]5.5a type mismatch for var := exp\n",line_number);
	         throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_CLASS)) || ((t2 instanceof TYPE_STRING) && (t1 instanceof TYPE_CLASS))){
			String error = String.format(">> ERROR [%d:%d]6a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		else if (((t1 instanceof TYPE_STRING) && (t2 instanceof TYPE_ARRAY)) || ((t2 instanceof TYPE_STRING) && (t1 instanceof TYPE_ARRAY))){
			String error = String.format(">> ERROR [%d:%d]7a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		/*case left var is NULL*/
		else if (t1 instanceof TYPE_NIL){
			String error = String.format(">> ERROR [%d:%d]8a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		/*case left is array and right isn't*/
		else if((t1 instanceof TYPE_ARRAY)  && !(t2 instanceof TYPE_ARRAY)){
     if(t2 instanceof TYPE_NIL){
  			TYPE t = SYMBOL_TABLE.getInstance().find(t1.name);
  			if (t == null){
  				String error = String.format(">> ERROR [%d:%d]9a type mismatch for var := exp\n",line_number,column_number);
                  throw new SemanticException(error,line_number);
  			}
  			return null;
      }
      
     if(t2 instanceof TYPE_NEW_ARRAY){
       if(((TYPE_ARRAY)t1).arrayType.name.equals(((TYPE_NEW_ARRAY)t2).arrayType.name)){
  				return null;
  			}
  			String error = String.format(">> ERROR [%d:%d]9.2a type mismatch for var := exp\n",line_number,column_number);
       throw new SemanticException(error,line_number);
     }

      String error = String.format(">> ERROR [%d:%d]9.5a type mismatch for var := exp\n",line_number,column_number);
      throw new SemanticException(error,line_number);

		}
		/*case left is class  and right isn't*/
		else if((t1 instanceof TYPE_CLASS) && !(t2 instanceof TYPE_CLASS)){
      if(t2 instanceof TYPE_NIL){
       //I don't understand what happens here. it seems that t can only be equal to t1      
  			TYPE t = SYMBOL_TABLE.getInstance().find(t1.name);
  			if (t == null){
  				String error = String.format(">> ERROR [%d:%d]10a type mismatch for var := exp\n",line_number,column_number);
          throw new SemanticException(error,line_number);
        }
  			return null;
      }
      else{
        String error = String.format(">> ERROR [%d:%d]10.5a type mismatch for var := exp\n",line_number,column_number);
        throw new SemanticException(error,line_number);
      }
		}
		/*case both classes*/
		else if((t1 instanceof TYPE_CLASS) && (t2 instanceof TYPE_CLASS)){
			String ancestorClass = t1.name;
			TYPE t = SYMBOL_TABLE.getInstance().find(ancestorClass);
			if (t == null){
				String error = String.format(">> ERROR [%d:%d]11a type mismatch for var := exp\n",line_number,column_number);
                throw new SemanticException(error,line_number);
			}
			//check that right's type or its ancestor type matches left's
      
			if(((TYPE_CLASS)t1).isExtendBy(t2)){
				return null;
			}
			String error = String.format(">> ERROR [%d:%d]12a type mismatch for var := exp  (%s := %s)\n ",line_number,column_number,t1,t2);
      throw new SemanticException(error,line_number);
		}
		/*case both ARRAYS*/
		else if((t1 instanceof TYPE_ARRAY) && (t2 instanceof TYPE_ARRAY)){
			TYPE s1 = SYMBOL_TABLE.getInstance().find(t1.name);
			if (s1 == null){
				String error = String.format(">> ERROR [%d:%d]13a type mismatch for var := exp\n",line_number,column_number);
                throw new SemanticException(error,line_number);
			}
			TYPE s2 = SYMBOL_TABLE.getInstance().find(t2.name);
			if (s2 == null){
				String error = String.format(">> ERROR [%d:%d]14a type mismatch for var := exp\n",line_number,column_number);
                throw new SemanticException(error,line_number);
			}
      
      //System.out.format("%s,%s\n",((TYPE_ARRAY)t1).name,((TYPE_ARRAY)t2).name);
			if(((TYPE_ARRAY)t1).name.equals(((TYPE_ARRAY)t2).name)){
				return null;
			}
			String error = String.format(">> ERROR [%d:%d]15a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}
		/*case right is FUNCTION*/
		else if (t2 instanceof TYPE_FUNCTION){
			TYPE s2 = SYMBOL_TABLE.getInstance().find(t2.name);
			if (s2 == null){
				String error = String.format(">> ERROR [%d:%d]16a type mismatch for var := exp\n",line_number,column_number);
                throw new SemanticException(error,line_number);
			}
			TYPE s1 = SYMBOL_TABLE.getInstance().find(t1.name);
			TYPE returnType = ((TYPE_FUNCTION)t2).returnType;
			// returned Type is VOID, ERROR
			if(returnType instanceof TYPE_VOID) {
				String error = String.format(">> ERROR [%d:%d]17a type mismatch for var := exp\n voiiid",line_number,column_number);
                throw new SemanticException(error,line_number);
			}
			//left is INT and returned Type is INT
			if((s1 instanceof TYPE_INT) && (returnType instanceof TYPE_INT))
				return null;
			//left is STRING and returned Type is STRING
			if((s1 instanceof TYPE_STRING) && (returnType instanceof TYPE_STRING))
				return null;
			//left is ARRAY and returned Type is ARRAY
			if((s1 instanceof TYPE_ARRAY) && (returnType instanceof TYPE_ARRAY)){
				TYPE r1 = SYMBOL_TABLE.getInstance().find(s1.name);
				if (r1 == null){
					String error = String.format(">> ERROR [%d:%d]18a type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);		
				}
				TYPE r2 = SYMBOL_TABLE.getInstance().find(returnType.name);
				if (r2 == null){
					String error = String.format(">> ERROR [%d:%d]19a type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);
				}
				if(r1.name.equals(r2.name))
					return null;
				String error = String.format(">> ERROR [%d:%d]20a type mismatch for var := exp\n",line_number,column_number);
		        throw new SemanticException(error,line_number);
			}
			if((s1 instanceof TYPE_CLASS) && (returnType instanceof TYPE_CLASS)){
				String ancestorClass = s1.name;
				TYPE t = SYMBOL_TABLE.getInstance().find(ancestorClass);
				if (t == null){
					String error = String.format(">> ERROR [%d:%d]21a type mismatch for var := exp\n",line_number,column_number);
	                throw new SemanticException(error,line_number);
				}
				//check that right's type or its ancestor type matches left's type
				if(existInheritance(ancestorClass, (TYPE_CLASS)returnType)){
					return null;
				}
				String error = String.format(">> ERROR [%d:%d]22a type mismatch for var := exp\n",line_number,column_number);
	            throw new SemanticException(error,line_number);
			}
			String error = String.format(">> ERROR [%d:%d]23a type mismatch for var := exp\n",line_number,column_number);
            throw new SemanticException(error,line_number);
		}

		return null; // should not get here
	}
		
	public TEMP IRme()
	{
        // Roy: used Oleg's 'store_local_var' command
        // Casted var to AST_SIMPLE_VAR to get the static 'localVariablesCounter'
    if (exp != null){
        if( var instanceof AST_VAR_SIMPLE){
            
            
      //Global
        if (classification == 4){
            IR.getInstance().Add_IRcommand(new IRcommand_store_global_var(
				    exp.IRme(), SYMBOL_TABLE.getInstance().getEntry(((AST_VAR_SIMPLE)var).name).getOrder()));
        }
        // Local
        else if (classification == 1 ) {
            IR.getInstance().Add_IRcommand(new IRcommand_store_local_var(
				    exp.IRme(), ((AST_VAR_SIMPLE)var).localVariablesCounter));
        }
        // TODO: add two other cases! function & class
        else {
            IR.getInstance().Add_IRcommand(new IRcommand_store_local_var(
				    exp.IRme(), ((AST_VAR_SIMPLE)var).localVariablesCounter));
        }
            
            
        }
        if( var instanceof AST_VAR_SUBSCRIPT || var instanceof AST_VAR_DOT ){
        
            //get the exp value
              TEMP assigned_value = exp.IRme();
            //get the heap pointer 
              TEMP heap_pointer = var.IRme(true);
            
            //ASSGIN
              //IR.getInstance().Add_IRcommand(new IRcommandPrintInt(assigned_value));       
              //IR.getInstance().Add_IRcommand(new IRcommandPrintInt(heap_pointer));   
              
              IR.getInstance().Add_IRcommand(new IRcommand_Store(heap_pointer,assigned_value));
        }
       
		}
        
		return null;
	}		
		
	
}
