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

public class IRcommandNewClass extends IRcommand{
	
	
	TEMP heap_pointer;
	int class_size;
	
	public IRcommandNewClass(TEMP heap_pointer ,int class_size)
	{
		this.heap_pointer = heap_pointer;
    this.class_size = class_size;  //number of elements
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
 
 
      TEMP t0 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().li(t0,sir_MIPS_a_lot.WORD_SIZE*class_size);
      
      
    //malloc
      sir_MIPS_a_lot.getInstance().malloc(t0);
      sir_MIPS_a_lot.getInstance().moveReturned(heap_pointer);

	}

}
