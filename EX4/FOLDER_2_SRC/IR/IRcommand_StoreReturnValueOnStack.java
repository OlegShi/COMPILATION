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

public class IRcommand_StoreReturnValueOnStack extends IRcommand{
	
	public TEMP t;
	
	public IRcommand_StoreReturnValueOnStack(TEMP t)
	{
		this.t = t;
        
	}

	
	public void MIPSme() {
		

		sir_MIPS_a_lot.getInstance().storeReturnValueONStack(t);

	}

}
