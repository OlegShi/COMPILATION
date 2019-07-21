/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_LoadReturnValueFromStack extends IRcommand{
	
	public TEMP t;
	
	public IRcommand_LoadReturnValueFromStack(TEMP t)
	{
		this.t = t;
        
	}

	
	public void MIPSme() {
		

		sir_MIPS_a_lot.getInstance().loadReturnValueFromStack(t);

	}

}
