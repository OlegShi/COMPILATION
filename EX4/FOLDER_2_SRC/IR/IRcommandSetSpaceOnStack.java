/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommandSetSpaceOnStack extends IRcommand{

	int num;

	
	public IRcommandSetSpaceOnStack(int num)
	{
		this.num = num;
		
	}

	
	public void MIPSme() {
		
		sir_MIPS_a_lot.getInstance().moveSp(num);

	}
}
