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

public class IRcommand_Binop_Div_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Div_Integers(TEMP dst,TEMP t1,TEMP t2)
	{
		this.t1 = t1;
		this.t2 = t2;
        this.dst = dst;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		
		/******************************************************/
		/* Oleg: Check Division by zero */
		/******************************************************/
		String label_valid_div = getFreshLabel("valid_div");
		String label_invalid_div = getFreshLabel("invalid_div");
		sir_MIPS_a_lot.getInstance().beq_zero(t2,label_invalid_div);
		sir_MIPS_a_lot.getInstance().jump(label_valid_div);
		sir_MIPS_a_lot.getInstance().label(label_invalid_div);
		sir_MIPS_a_lot.getInstance().print_string("string_illegal_div_by_0");
		sir_MIPS_a_lot.getInstance().jump("End");
		sir_MIPS_a_lot.getInstance().label(label_valid_div);

		/******************************************************/
		/* [0] Allocate a fresh temporary t4 for the addition */
		/******************************************************/
		TEMP t1_div_t2 = TEMP_FACTORY.getInstance().getFreshTEMP();

		/******************************************/
		/* [1] Allocate a fresh temporary INT_MAX */
		/******************************************/
		TEMP intMax = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP intMin = TEMP_FACTORY.getInstance().getFreshTEMP();
		
		/********************************/
		/* [2] intMax := 32767 (= 2^15) */
		/********************************/
		sir_MIPS_a_lot.getInstance().li(intMax,32767);
        sir_MIPS_a_lot.getInstance().li(intMin,-32768);

		/****************************************************/
		/* [3] Allocate a fresh label for possible overflow */
		/****************************************************/
		String label_end         = getFreshLabel("end");
		String label_upoverflow    = getFreshLabel("up_overflow");
        String label_downoverflow    = getFreshLabel("down_overflow");
		String label_no_overflow = getFreshLabel("no_overflow");

		/*********************/
		/* [4] t4 := t1 - t2 */
		/*********************/
		sir_MIPS_a_lot.getInstance().div(t1_div_t2,t1,t2);
		
		/********************************************************/
		/* [5] if (32767 <  t1_div_t2) goto label_overflow;    */
		/*     if (32767 >= t1_div_t2) goto label_no_overflow; */
		/********************************************************/
		sir_MIPS_a_lot.getInstance().blt(intMax,t1_div_t2,label_upoverflow);
		sir_MIPS_a_lot.getInstance().bge(intMin,t1_div_t2,label_downoverflow);
        sir_MIPS_a_lot.getInstance().jump(label_no_overflow);

		/***********************/
		/* [3] label_upoverflow: */
		/*                     */
		/*         t3 := 32767 */
		/*         goto end;   */
		/*                     */
		/***********************/
		sir_MIPS_a_lot.getInstance().label(label_upoverflow);
		sir_MIPS_a_lot.getInstance().li(dst,32767);
		sir_MIPS_a_lot.getInstance().jump(label_end);
        
        /***********************/
		/* [3] label_downoverflow: */
		/*                     */
		/*         t3 := -32768 */
		/*         goto end;   */
		/*                     */
		/***********************/
		sir_MIPS_a_lot.getInstance().label(label_downoverflow);
		sir_MIPS_a_lot.getInstance().li(dst,-32768);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/**************************/
		/* [4] label_no_overflow: */
		/*                        */
		/*         t3 := t1/t2    */
		/*         goto end;      */
		/*                        */
		/**************************/
		sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		sir_MIPS_a_lot.getInstance().div(dst,t1,t2);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);	
	}
}
