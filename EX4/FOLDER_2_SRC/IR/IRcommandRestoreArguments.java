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

public class IRcommandRestoreArguments extends IRcommand{
	int num;
 
	public IRcommandRestoreArguments(int num)
	{
		this.num = num;

	}

	public void MIPSme() {
		
		sir_MIPS_a_lot.getInstance().RestoreArguments(num);

		
			
	}

}
