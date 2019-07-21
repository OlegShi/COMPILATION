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

public class IRcommand_StoreOnStack extends IRcommand{
	
	public TEMP t;
	int offSet;
	
	public IRcommand_StoreOnStack(TEMP t, int offSet)
	{
		this.t = t;
		this.offSet =offSet;
	}

	
	public void MIPSme() {
		
		if( t == null)
			sir_MIPS_a_lot.getInstance().storeReturnAddrOnStack(offSet);
		else
			sir_MIPS_a_lot.getInstance().storeOnStack(t, offSet);

	}

}
