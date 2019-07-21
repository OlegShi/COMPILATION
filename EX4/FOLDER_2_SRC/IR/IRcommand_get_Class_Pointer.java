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

public class IRcommand_get_Class_Pointer extends IRcommand{
	
	
	TEMP heap_pointer;
  TEMP pointer_with_offset;
	int index;
 
	public IRcommand_get_Class_Pointer(TEMP heap_pointer , TEMP pointer_with_offset,int index)
	{
		this.heap_pointer = heap_pointer;
    this.index = index;  
    this.pointer_with_offset = pointer_with_offset;  
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
      sir_MIPS_a_lot.getInstance().addi(pointer_with_offset,heap_pointer,index*sir_MIPS_a_lot.WORD_SIZE);    
	}

}
