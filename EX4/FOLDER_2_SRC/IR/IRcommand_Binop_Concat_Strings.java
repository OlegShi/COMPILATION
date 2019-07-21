/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class IRcommand_Binop_Concat_Strings extends IRcommand {
	
	public TEMP s1;
	public TEMP s2;
	public TEMP dst;
	
	public IRcommand_Binop_Concat_Strings(TEMP dst,TEMP s1,TEMP s2)
	{
		this.dst = dst;
		this.s1 = s1;
		this.s2 = s2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		
		
		/**********************************************************/
		/*  Allocate a fresh temporary  for the concatenation */
		/********************************************************/
		TEMP t1 = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP t2 = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP a1 = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP a2 = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP t0 = TEMP_FACTORY.getInstance().getFreshTEMP();

		/**********************************/
		/*  Allocate a fresh labels      */
		/********************************/
		String label_strlen_first         = getFreshLabel("strlen_first_string");
		String label_strlen_second    = getFreshLabel("strlen_second_string");
		String label_alloc_memory = getFreshLabel("alloc_mem");
		String label_first_load_byte = getFreshLabel("first_load_byte");
		String label_second_load_byte = getFreshLabel("second_load_byte");
		String label_strcopy_first_loop = getFreshLabel("strcopy_first_loop");
		String label_strcopy_second_loop = getFreshLabel("strcopy_second_loop");
		String label_exit_procedure = getFreshLabel("exit_procedure");

		/* Init variables: a0, a1, a2, t1, t2 */
		//copy addr into $t1 for later access
		sir_MIPS_a_lot.getInstance().la(t1, s1);
		//copy addr into $t2 for later access
		sir_MIPS_a_lot.getInstance().la(t2, s2);
		//load byte from first string addr
		sir_MIPS_a_lot.getInstance().lb(a1, s1);
		//load byte from second string addr
		sir_MIPS_a_lot.getInstance().lb(a2, s2);
		sir_MIPS_a_lot.getInstance().move_a0_zer0();
		
		
		/* count the length of first string */
		sir_MIPS_a_lot.getInstance().label(label_strlen_first);
		sir_MIPS_a_lot.getInstance().beqz(a1,label_strlen_second);
		//increment strlen_counter
		sir_MIPS_a_lot.getInstance().addi("$a0", "$a0", 1);
		//increment ptr
		sir_MIPS_a_lot.getInstance().addi(s1, s1, 1);
		//load the byte
		sir_MIPS_a_lot.getInstance().lb(a1, s1);
		sir_MIPS_a_lot.getInstance().jump(label_strlen_first);
		
		/* count the length of second string */
		sir_MIPS_a_lot.getInstance().label(label_strlen_second);
		sir_MIPS_a_lot.getInstance().beqz(a2,label_alloc_memory);
		//increment strlen_counter
		sir_MIPS_a_lot.getInstance().addi("$a0", "$a0", 1);
		//increment ptr
		sir_MIPS_a_lot.getInstance().addi(s2, s2, 1);
		//load the byte
		sir_MIPS_a_lot.getInstance().lb(a2, s2);
		sir_MIPS_a_lot.getInstance().jump(label_strlen_second);
		
		//allocate memory 
		sir_MIPS_a_lot.getInstance().label(label_alloc_memory);
		sir_MIPS_a_lot.getInstance().li_v0(9);
		sir_MIPS_a_lot.getInstance().syscall();
		//save allocated address
		sir_MIPS_a_lot.getInstance().la_v0(t0);
		//reset addr
		sir_MIPS_a_lot.getInstance().la(s1,t1);
		
		sir_MIPS_a_lot.getInstance().label(label_first_load_byte);
		//copy first byte from first string
		sir_MIPS_a_lot.getInstance().lb(a1, t1);
		//start copy first string
		sir_MIPS_a_lot.getInstance().label(label_strcopy_first_loop);
		//check if current byte is NULL
		sir_MIPS_a_lot.getInstance().beqz(a1,label_second_load_byte);
		//store the byte at the target pointer
		sir_MIPS_a_lot.getInstance().sb(a1, t0);
		//increment dst ptr
		sir_MIPS_a_lot.getInstance().addi(t0, t0, 1);
		//increment source ptr
		sir_MIPS_a_lot.getInstance().addi(t1, t1, 1);
		//load next byte from source ptr
		sir_MIPS_a_lot.getInstance().lb(a1, t1);
		sir_MIPS_a_lot.getInstance().jump(label_strcopy_first_loop);
		
		//copy first byte from second string
		sir_MIPS_a_lot.getInstance().label(label_second_load_byte);
		sir_MIPS_a_lot.getInstance().lb(a1, t2);
		//start copy second string
		sir_MIPS_a_lot.getInstance().label(label_strcopy_second_loop);

		//check if current byte is NULL
		sir_MIPS_a_lot.getInstance().beqz(a1,label_exit_procedure);
		//store the byte at the target pointer
		sir_MIPS_a_lot.getInstance().sb(a1, t0);
		//increment dst ptr
		sir_MIPS_a_lot.getInstance().addi(t0, t0, 1);
		//increment source ptr
		sir_MIPS_a_lot.getInstance().addi(t2, t2, 1);
		//load next byte from source ptr
		sir_MIPS_a_lot.getInstance().lb(a1, t2);
		sir_MIPS_a_lot.getInstance().jump(label_strcopy_second_loop);
		
		sir_MIPS_a_lot.getInstance().label(label_exit_procedure);

		
		//store our string in dst reg
		sir_MIPS_a_lot.getInstance().la_v0(dst);
    }
}
