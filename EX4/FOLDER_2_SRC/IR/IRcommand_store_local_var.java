/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommand_store_local_var extends IRcommand{
	
	
	TEMP src;
	int localVariablesCounter;
	
	public IRcommand_store_local_var(TEMP src,int localVariablesCounter)
	{
		this.src = src;
		this.localVariablesCounter = localVariablesCounter;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	  
    TEMP dst = sir_MIPS_a_lot.getInstance().addressLocalVar(localVariablesCounter);
		sir_MIPS_a_lot.getInstance().store(dst,src);
	}

}
