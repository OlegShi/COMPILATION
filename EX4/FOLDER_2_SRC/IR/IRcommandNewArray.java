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

public class IRcommandNewArray extends IRcommand{
	
	
	TEMP heap_pointer;
  TEMP arr_size;
	int element_size;
	
	public IRcommandNewArray(TEMP heap_pointer ,TEMP arr_size,int element_size)
	{
		this.heap_pointer = heap_pointer;
    this.arr_size = arr_size;  //number of elements
		this.element_size = element_size;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
 
    //t0 = arr_size + 1
      TEMP t0 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().addi(t0,arr_size,1);
 
    //t1 = element_size
      TEMP t1 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().li(t1,sir_MIPS_a_lot.WORD_SIZE*element_size);
      
    //t2 = t0*t1 = (arr_size + 1)*element_size
      TEMP t2 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().multu(t2,t1,t0);
      
      
    //malloc
      sir_MIPS_a_lot.getInstance().malloc(t2);
      sir_MIPS_a_lot.getInstance().moveReturned(heap_pointer);
      
    //set arr[0] to arr_size
      sir_MIPS_a_lot.getInstance().store(heap_pointer,arr_size);

	}

}
