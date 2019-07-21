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

public class IRcommandString extends IRcommand {
	
	TEMP t;
	String value;
	String labeledStr;
 
	public IRcommandString(TEMP t,String  value)
	{
		this.t = t;
		this.value = value;
     
     //get fresh number
		int counter = sir_MIPS_a_lot.strCounter;
		//create new label which will appear in text section
		labeledStr =  value + "_" + counter;
		//crate the whole format
		String asciiz = labeledStr+": .asciiz \""+value+"\"" ;
		//add to the array list
		sir_MIPS_a_lot.stringsDec.add(counter, asciiz);
		//Inc the counter for next use
		sir_MIPS_a_lot.getInstance().IncStrCounter();
		//add the command
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().la_hc(t,labeledStr);
	}
	

}
