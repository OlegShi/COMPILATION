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

public class IRcommand_load_local_var extends IRcommand {
	
	TEMP dst;
	int localVariablesCounter;
	
	public IRcommand_load_local_var(TEMP dst,int localVariablesCounter)
	{
		this.dst = dst;
		this.localVariablesCounter = localVariablesCounter;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP src = sir_MIPS_a_lot.getInstance().addressLocalVar(localVariablesCounter);

		sir_MIPS_a_lot.getInstance().load(dst,src);
	}

}
