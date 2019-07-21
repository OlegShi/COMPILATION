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

public class IRcommand_check_not_null extends IRcommand{
	
  TEMP heap_pointer;
  String pointer_not_null;

	public IRcommand_check_not_null(TEMP heap_pointer)
	{
		this.heap_pointer = heap_pointer;    
    this.pointer_not_null = getFreshLabel("pointer_not_null");     
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
 
  	 TEMP zero = TEMP_FACTORY.getInstance().getFreshTEMP();
     sir_MIPS_a_lot.getInstance().li(zero,0);
     
     
     sir_MIPS_a_lot.getInstance().bne(heap_pointer,zero,this.pointer_not_null);
     sir_MIPS_a_lot.getInstance().print_string("string_invalid_ptr_dref");
     sir_MIPS_a_lot.getInstance().jump(sir_MIPS_a_lot.end_label);
     sir_MIPS_a_lot.getInstance().label(this.pointer_not_null);


	}

}
