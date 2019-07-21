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



public class IRcommand_SaveRegsOnStack extends IRcommand{
	
	public IRcommand_SaveRegsOnStack(){}

	
	public void MIPSme() {
		
		sir_MIPS_a_lot.getInstance().moveSp(8);
		sir_MIPS_a_lot.getInstance().saveRegsOnStack();
		
	}
	
	
	
}
