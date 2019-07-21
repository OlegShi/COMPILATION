/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/**********************/
/* SYMBOL TABLE ENTRY */
/**********************/
public class SYMBOL_TABLE_ENTRY
{
	/*********/
	/* index */
	/*********/
	int index;
	
	/********/
	/* name */
	/********/
	public String name;

	/******************/
	/* TYPE value ... */
	/******************/
	public TYPE type;

	/*********************************************/
	/* prevtop and next symbol table entries ... */
	/*********************************************/
	public SYMBOL_TABLE_ENTRY prevtop;
	public SYMBOL_TABLE_ENTRY next;

	/****************************************************/
	/* The prevtop_index is just for debug purposes ... */
	/****************************************************/
	public int prevtop_index;
    
    //
    public int scope_depth;
    
    /* 
     0 - nothing
     1 - functionLocal
     2 - functionParameter
     3 - classVariable
     4 - globalVariable
    */
    public int variableClassification = 1;
    public int order = -1;
    
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public SYMBOL_TABLE_ENTRY(
		String name,
		TYPE type,
		int index,
		SYMBOL_TABLE_ENTRY next,
		SYMBOL_TABLE_ENTRY prevtop,
		int prevtop_index,
        int scope_depth)
	{
		this.index = index;
		this.name = name;
		this.type = type;
		this.next = next;
		this.prevtop = prevtop;
		this.prevtop_index = prevtop_index;
        this.scope_depth = scope_depth;
	}
 
   public int localVariablesCounter;
   public int element_size;
    
    public void setVariableInfo(int variableClassification, int order) {
        this.variableClassification = variableClassification;
        this.order = order;
    }

    public boolean isValidVariableInfo() {
        return variableClassification != 0 && order != -1;
    }

    public int getOrder() {
        return order;
    }

    public int getVariableClassification() {
        return variableClassification;
    }
}
