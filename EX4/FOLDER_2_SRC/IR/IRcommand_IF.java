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

public class IRcommand_IF extends IRcommand
{
	public TEMP condVal;
    public TEMP trueVal;
    public String szLabelIfTrue;
    public String szLabelIfEnd;

	public IRcommand_IF(TEMP condVal, TEMP trueVal, String szLabelIfEnd, String szLabelIfTrue)
	{
		this.condVal = condVal;
        this.trueVal = trueVal;
        this.szLabelIfEnd = getFreshLabel(szLabelIfEnd);
        this.szLabelIfTrue = getFreshLabel(szLabelIfTrue);
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		
		/******************************************/
		/* [2] if (dst == 1) goto label_if_true;  */
		/*     if (dst != 1) goto label_if_end; */
		/******************************************/
		sir_MIPS_a_lot.getInstance().bne(condVal,trueVal,szLabelIfEnd);
	}
}
