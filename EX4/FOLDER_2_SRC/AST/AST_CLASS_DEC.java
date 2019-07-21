package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CLASS_DEC extends AST_Node{
  
  public String className;
  public String extendName;
  public AST_C_FEILD_LIST cFeildList;
  public int extend_line_number;
  public int extend_column_number;
  
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_CLASS_DEC(String className, String extendName, AST_C_FEILD_LIST cFeildList,int extend_line_number ,int extend_column_number)
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
    this.extend_line_number = extend_line_number;
    this.extend_column_number = extend_column_number;
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
 
	public TYPE SemantMe() throws SemanticException
	{	
	
       
        
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();
  
    /*************************/
   	/* sorting               */
	  /*************************/
    //hen: sorting before semanting,  so: "A method M can refer to a data member d, even if d is defined after M in the class." 
    
    AST_C_FEILD_LIST varfirst = null;
    AST_C_FEILD_LIST varlast = null;
    AST_C_FEILD_LIST funcfirst = null;
    AST_C_FEILD_LIST funclast = null;
    
    AST_C_FEILD_LIST head=cFeildList; 
       
    head=cFeildList; 
    while(head!=null){
      if(head.head.getClass().getName().equals("AST.AST_VAR_FEILD")){
        if(varfirst == null){  
          varfirst = new AST_C_FEILD_LIST();
          varlast = varfirst;
          varfirst.head = head.head;              
        }
        else{ 
           varlast.tail = new AST_C_FEILD_LIST();
           varlast = varlast.tail;
           varlast.head = head.head;             
        }
      }
      if(head.head.getClass().getName().equals("AST.AST_FUNC_FEILD")){
        if(funcfirst == null){
           funcfirst = new AST_C_FEILD_LIST();
           funclast = funcfirst;
           funcfirst.head = head.head;
        }
        else{
          funclast.tail = new AST_C_FEILD_LIST();
          funclast = funclast.tail;
          funclast.head = head.head;
        }
      }
      head = head.tail;
    }
    
   
    //if(varfirst!=null){
    //  cFeildList=varfirst;
    //  varlast.tail = funcfirst;
    //}
   // else{
    //  cFeildList=funcfirst;
    //} 
    
    
    /******************************/
   	/* print the data members list*/
	  /******************************/

    /* 
    head=cFeildList; 
    while(head!=null){
      if(head.head.getClass().getName().equals("AST.AST_VAR_FEILD")){
        System.out.println(((AST_VAR_FEILD)head.head).varDec.param.name); 
      }
      if(head.head.getClass().getName().equals("AST.AST_FUNC_FEILD")){
        System.out.println(((AST_FUNC_FEILD)head.head).funcDec.return_type_and_name.name); 
      }
      head = head.tail;
    }   */
     
     
     
    
     TYPE_CLASS father_t = null;
     TYPE_LIST inherantnce_data_members=null;
      // ROY: Semant EXTENDS class name 
        if (extendName != null) {
                TYPE extendName_t = SYMBOL_TABLE.getInstance().find(extendName);
        
                
                /***************************************/
               	/* check if the father name is legit   */
            	  /***************************************/
                 
        		    if (extendName_t == null)
        		    {
                        /**************************/
                        /* ERROR: undeclared type */
                        /**************************/
                        String error = String.format(">> ERROR [%d:%d] Found an extends class name not defined in symbol table (remove me)!\n",extend_line_number,extend_column_number);
                        throw new SemanticException(error,extend_line_number);
     		       }
                
                
                /***************************************/
               	/* check if the father is a class      */
            	  /***************************************/
                if(!extendName_t.isClass()){
                                        /**************************/
                        /* ERROR: undeclared type */
                        /**************************/
                        String error = String.format(">> ERROR [%d:%d] trying to extand [%s], which is not a class\n",extend_line_number,extend_column_number,extendName_t.name);
                        throw new SemanticException(error,extend_line_number);
                
                }

                
                father_t = (TYPE_CLASS)extendName_t;
                
        
            	  /************************************************************/
               	/* collect datamembers of everyone in the inherantnce chain */
            	  /************************************************************/
            		
            		TYPE_CLASS poniter = father_t;
                do {
                  TYPE_LIST data_members = poniter.data_members;                        
                  if(inherantnce_data_members==null){
                    inherantnce_data_members = new TYPE_LIST(data_members);
                  }
                  else{
                    inherantnce_data_members.concat(new TYPE_LIST(data_members));
                  } 
            			poniter = poniter.father;
            		}	while(poniter!=null);
               
        }
     			
		/***************************/
		/* [2] Semant Data Members */
		/***************************/    

   
    //adding an class type with no data members, will be removed in the scope end
    //and will be added again with the data members
    TYPE_CLASS t = new TYPE_CLASS(father_t,className,null);
    SYMBOL_TABLE.getInstance().enter(className,t);	
    
    
    
    TYPE_LIST datamembers = null;        
    TYPE_LIST datamembers_v=null;
    TYPE_LIST datamembers_f=null;				
    if(varfirst!=null)        
      datamembers_v = varfirst.SemantMe(inherantnce_data_members);
      //adding fathers data members to the scope
      while(father_t!=null){
         TYPE_LIST data_members = father_t.data_members;
         while(data_members!=null){
          if(data_members.head instanceof TYPE_VAR)
            //System.out.format("--%s--\n",data_members.head.name);
            SYMBOL_TABLE.getInstance().enter(data_members.head.name,((TYPE_VAR)data_members.head).type);
          data_members =data_members.tail;
          }
        father_t = father_t.father;
      }	
        
    if(funcfirst!=null)
      datamembers_f = funcfirst.SemantMe(inherantnce_data_members);
    
    if(datamembers_v!=null){
      datamembers_v.concat(datamembers_f);
      datamembers =datamembers_v;
    }
    else{
      datamembers = datamembers_f;
    }
    		
		//TYPE_LIST datamembers = cFeildList.SemantMe(inherantnce_data_members);		
		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
   
    t.data_members = datamembers;
		SYMBOL_TABLE.getInstance().enter(className,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
}
