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

public class IRcommand_WHILE extends IRcommand
{
	public TEMP condVal;
    public TEMP trueVal;
    public String szLabelWhileStart;
    public String szLabelWhileEnd;

	//public IRcommand_WHILE(TEMP condVal, TEMP trueVal, String szLabelWhileEnd, String szLabelWhileStart)
	//{
	    //this.condVal = condVal;
      //this.trueVal = trueVal;
      //this.szLabelWhileEnd = getFreshLabel(szLabelWhileEnd);
      //this.szLabelWhileStart = getFreshLabel(szLabelWhileStart);
	//}
 
 	public IRcommand_WHILE(String szLabelWhileEnd, String szLabelWhileStart)
	{
        
        this.szLabelWhileEnd = getFreshLabel(szLabelWhileEnd);
        this.szLabelWhileStart = getFreshLabel(szLabelWhileStart);
	}
 
  public void setCondVal(TEMP condVal){
        this.condVal = condVal;
  }
	
 public void setTrueVal(TEMP trueVal){
   this.trueVal = trueVal;
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
		sir_MIPS_a_lot.getInstance().bne(condVal,trueVal,szLabelWhileEnd);
	}
}
