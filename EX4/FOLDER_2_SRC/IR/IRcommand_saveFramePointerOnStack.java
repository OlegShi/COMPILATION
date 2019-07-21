/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_saveFramePointerOnStack extends IRcommand{
	

	public IRcommand_saveFramePointerOnStack(){
		
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().saveFpOnStack();
	}

}
