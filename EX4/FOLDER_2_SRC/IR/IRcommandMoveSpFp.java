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

public class IRcommandMoveSpFp extends IRcommand{
	
	 int cmdNum;
	
	public IRcommandMoveSpFp(int cmdNum)
	{
		this.cmdNum = cmdNum;

	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(cmdNum == 1)
			sir_MIPS_a_lot.getInstance().moveSptoFp();
		else
			sir_MIPS_a_lot.getInstance().moveFptoSp();
			
	}


}
