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

public class IRcommandRetrieveArguments extends IRcommand{

	int numOfArgs;

	public IRcommandRetrieveArguments(int numOfArgs)
	{
		this.numOfArgs = numOfArgs;

	}

	public void MIPSme() {
		for(int i=0; i<numOfArgs; ++i){
			sir_MIPS_a_lot.getInstance().retrieveArg(i);

		}
			
	}
	
	
}
