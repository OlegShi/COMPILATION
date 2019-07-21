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

public class IRcommandMove extends IRcommand{
	
	TEMP dst;
	TEMP src; 
	
	public IRcommandMove(TEMP dst,TEMP src)
	{
		this.dst = dst;
		this.src = src;

	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if( src == null)
			sir_MIPS_a_lot.getInstance().moveZero(dst);
		else
			sir_MIPS_a_lot.getInstance().move(dst, src);
	}

}
