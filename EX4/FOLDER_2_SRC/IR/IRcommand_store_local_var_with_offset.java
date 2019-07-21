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

public class IRcommand_store_local_var_with_offset extends IRcommand{
	
	
	TEMP return_value;
  TEMP offset;
  TEMP heap_pointer;
  int element_size;
  boolean isStore;
  String index_is_inbound_label;

	public IRcommand_store_local_var_with_offset(TEMP return_value,TEMP offset, TEMP heap_pointer, int element_size, boolean isStore)
	{
		this.return_value = return_value;
    this.offset = offset;
		this.heap_pointer = heap_pointer;
    this.element_size = element_size; 
    this.isStore = isStore; 
    
    this.index_is_inbound_label = getFreshLabel("index_is_inbound");
     
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
 
 
  //get arr_size
	 TEMP arr_size = TEMP_FACTORY.getInstance().getFreshTEMP();
   sir_MIPS_a_lot.getInstance().load(arr_size,heap_pointer);
   
   //check for index out of range 
     //branch if ok : offset < size ( arr[2] is ok for new int[3] ) 
     sir_MIPS_a_lot.getInstance().blt(offset,arr_size,this.index_is_inbound_label);
     sir_MIPS_a_lot.getInstance().print_string("string_access_violation");
     sir_MIPS_a_lot.getInstance().jump(sir_MIPS_a_lot.end_label);
     sir_MIPS_a_lot.getInstance().label(this.index_is_inbound_label);
    
	
  //calculate address

   //t0 = element_size
      TEMP t0 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().li(t0,sir_MIPS_a_lot.WORD_SIZE*element_size);
      
   //use (offset+1) because arr_size is in the first spot   
     TEMP offset_plus_1 = TEMP_FACTORY.getInstance().getFreshTEMP();
     sir_MIPS_a_lot.getInstance().addi(offset_plus_1,offset,1);
     
   //t1 = t0*offset = element_size*(offset+1)
      TEMP t1 = TEMP_FACTORY.getInstance().getFreshTEMP();
      sir_MIPS_a_lot.getInstance().multu(t1,t0,offset_plus_1);
      
   //return_value = heap_pointer + t1 = heap_pointer + element_size*(offset+1)
     sir_MIPS_a_lot.getInstance().add(return_value,heap_pointer,t1);
	}

}
