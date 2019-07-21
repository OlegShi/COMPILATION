/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;
import java.util.ArrayList;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class sir_MIPS_a_lot
{
	public static int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;
	
	public static ArrayList<String> stringsDec;
	
	public static int strCounter;
    
  public static String szOutPath = null;
  
  public static final String end_label ="End";

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.format(end_label+":\n");
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	//Oleg
	public void initializeFile(int globalVarsCount)
	{

		/*****************************************************/
		/* [3] Print data section with error message strings */
		/*****************************************************/
		instance.fileWriter.print(".data\n");
		instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
		instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
		instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		
		/************************************************/
		/* [4] Print declared Strings to text section   */
		/************************************************/
		for(int i=0; i<strCounter; ++i) {
			instance.fileWriter.print(stringsDec.get(i)+"\n");
		}
		
		/* Print global variables to .data */
        	for (int globalVarIndex = 0; globalVarIndex < globalVarsCount; globalVarIndex++) {
            		instance.fileWriter.format("global_var_%s:   .word   0\n", globalVarIndex);
        	}
		
		/************************************************/
		/* [4] Print text section with entry point main */
		/************************************************/
		instance.fileWriter.print(".text\n");
		instance.fileWriter.print("main:\n");

		/******************************************/
		/* [5] Will work with <= 10 variables ... */
		/******************************************/
		instance.fileWriter.print("\taddi $fp,$sp,40\n");
		
	}
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
    fileWriter.format("\tli $a0, 32\n");
		fileWriter.format("\tli $v0, 11\n");
		fileWriter.format("\tsyscall\n");
    GraphNode.addNode( new GraphNode(  new int[] {idx},null));
	}
    
	        public TEMP addressGlobalVar(int globalVarOrder)
	    {
		TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
		int idx = t.getSerialNumber();

		fileWriter.format("\tla Temp_%d, global_var_%s\n",idx, globalVarOrder);
		GraphNode.addNode( new GraphNode( null,idx,null));
		return t;
	    }
    
	public TEMP addressLocalVar(int serialLocalVarNum)
	{
		TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
		int idx = t.getSerialNumber();

		fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
        GraphNode.addNode( new GraphNode( null,idx,null));
		
		return t;
	}
	

 
 
  public TEMP addressLocalVarWithOffset(TEMP offset ){
    
    TEMP ret  = TEMP_FACTORY.getInstance().getFreshTEMP();    
    int idxret=ret.getSerialNumber();
		int idxoffset=offset.getSerialNumber();

    // ret = fp -  offset = fp - (word_size*offset + var_offst*word_size)
    fileWriter.format("\tsub Temp_%d,$fp,Temp_%d\n",idxret,idxoffset);
    GraphNode.addNode( new GraphNode( new int[] {idxoffset},idxret,null));
    
    return ret;
  }
  
	public void load(TEMP dst,TEMP src)
	{
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,0(Temp_%d)\n",idxdst,idxsrc);		
        GraphNode.addNode( new GraphNode( new int[] {idxsrc},idxdst,null));
	}
    
    
	public void store(TEMP dst,TEMP src)
	{
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,0(Temp_%d)\n",idxsrc,idxdst);		
        GraphNode.addNode( new GraphNode( new int[] {idxdst,idxsrc},null));
	}
    
    /* Load imidiate: load value into register */ 
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);
        GraphNode.addNode( new GraphNode( null,idx,null));
	}
    
    
    
    
    
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
        GraphNode.addNode( new GraphNode( new int[] {i1,i2},dstidx,null));
	}
    public void minus(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
        GraphNode.addNode( new GraphNode( new int[] {i1,i2},dstidx,null));
	}
    public void multu(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmult Temp_%d,Temp_%d\n",i1,i2);
        GraphNode.addNode( new GraphNode( new int[] {i1,i2},null));
        fileWriter.format("\tmflo Temp_%d\n", dstidx);
        GraphNode.addNode( new GraphNode( null,dstidx,null));
	}
    public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d\n",i1,i2);
        GraphNode.addNode( new GraphNode( new int[] {i1,i2},null));
        fileWriter.format("\tmflo Temp_%d\n", dstidx);
        GraphNode.addNode( new GraphNode( null,dstidx,null));
	}
    
    
    
	public void label(String inlabel)
	{
		fileWriter.format("%s:\n",inlabel);
        GraphNode.addLabel(inlabel);
	}	
    
    
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
        GraphNode.addAJump(inlabel);
	}	
    
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));
	}
    
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);		
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));		
	}
    
    public void bgt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbgt Temp_%d,Temp_%d,%s\n",i1,i2,label);		
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));			
	}
    
	public void ble(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tble Temp_%d,Temp_%d,%s\n",i1,i2,label);	
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));				
	}
    
    public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);	
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));				
	}
  
    public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);	
        GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));				
	}
    
    
	public void malloc(TEMP size)
	{
        int idx =size.getSerialNumber();
		
        fileWriter.format("\tmove $a0,Temp_%d\n",idx);		
        GraphNode.addNode( new GraphNode( new int[] {idx},null));
		
        fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
	}
 
 //Oleg
 	public void mallocInt(int size)
	{   
		fileWriter.format("\tli $a0,%d\n",size);
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
	}
    
	public void la(TEMP dst, TEMP src)
	{
		int idxDst=dst.getSerialNumber();
		int idxSrc=src.getSerialNumber();

		fileWriter.format("\tla Temp_%d,0(Temp_%d)\n",idxDst,idxSrc);
        GraphNode.addNode( new GraphNode( new int[] {idxSrc},idxDst,null));	
	}
	
	public void la_hc(TEMP dst, String str)
	{
		int idxDst=dst.getSerialNumber();

		fileWriter.format("\tla Temp_%d,%s\n",idxDst,str);
        GraphNode.addNode( new GraphNode( null,idxDst,null));
	}
	
	public void lb(TEMP dst,TEMP src)
	{
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tlb Temp_%d,0(Temp_%d)\n",idxdst,idxsrc);		
        GraphNode.addNode( new GraphNode( new int[] {idxsrc},idxdst,null));
	}
    
	public void sb(TEMP src,TEMP dst)
	{
		int idxsrc=src.getSerialNumber();
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tsb Temp_%d,0(Temp_%d)\n",idxsrc,idxdst);	
        GraphNode.addNode( new GraphNode( new int[] {idxdst,idxsrc},null));
	}
    
	public void beqz(TEMP t,String label)
	{
		int idxdst=t.getSerialNumber();
		fileWriter.format("\tbeqz Temp_%d,%s\n",idxdst,label);		
        GraphNode.addNode( new GraphNode(  new int[] {idxdst},label));	
	}
    
	public void addi(TEMP dst,TEMP oprnd1,int value)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\taddi Temp_%d,Temp_%d,%d\n",dstidx,i1,value);
        GraphNode.addNode( new GraphNode( new int[] {i1},dstidx,null));
	}
    
	public void addi(String dst,String oprnd1,int value)
	{
		fileWriter.format("\taddi %s,%s,%d\n",dst,oprnd1,value);
        //hen: should only be used for s= $a0 or something like that	
	}
    
	public void beq_zero (TEMP t, String label){
		int tidx=t.getSerialNumber();
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",tidx,label);
        GraphNode.addNode( new GraphNode(  new int[] {tidx},label));	
	}
    
	public void move(TEMP dst,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,Temp_%d\n",idxdst,idxsrc);	
        GraphNode.addNode( new GraphNode( new int[] {idxsrc},idxdst,null));	
	}
    
	public void moveReturned(TEMP dst)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,$v0\n",idxdst);		
        GraphNode.addNode( new GraphNode( null,idxdst,null));	
	}

	public void li_v0(int opNum)
	{
		fileWriter.format("\tli $v0,%d\n",opNum);
	}
    
	public void la_v0(TEMP dst)
	{
		int idxdst=dst.getSerialNumber();

		fileWriter.format("\tla Temp_%d,0($v0)\n",idxdst);
        GraphNode.addNode( new GraphNode( null,idxdst,null));
	}
    
	public void move_a0_zer0(){
		fileWriter.format("\tmove $a0,$zero\n");
	}
    
	public void syscall()
	{
		fileWriter.format("\tsyscall\n");
	}
    
	public void print_string(TEMP t)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
        GraphNode.addNode( new GraphNode(  new int[] {idx},null));
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
    fileWriter.format("\tli $a0, 32\n");
		fileWriter.format("\tli $v0, 11\n");
		fileWriter.format("\tsyscall\n");
	}
    
	public void print_string(String lbl)
	{
		fileWriter.format("\tla $a0,%s\n",lbl);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
    fileWriter.format("\tli $a0, 32\n");
		fileWriter.format("\tli $v0, 11\n");
		fileWriter.format("\tsyscall\n");
	}
	
	public void moveSp(int num) {
        fileWriter.format("\taddi $sp,$sp,%d\n", -num*WORD_SIZE);
	}
	
	public void saveRegsOnStack() {
        fileWriter.format("\tsw $t0, %d($sp)\n",0*WORD_SIZE);
        fileWriter.format("\tsw $t1, %d($sp)\n",1*WORD_SIZE);
        fileWriter.format("\tsw $t2, %d($sp)\n",2*WORD_SIZE);
        fileWriter.format("\tsw $t3, %d($sp)\n",3*WORD_SIZE);
        fileWriter.format("\tsw $t4, %d($sp)\n",4*WORD_SIZE);
        fileWriter.format("\tsw $t5, %d($sp)\n",5*WORD_SIZE);
        fileWriter.format("\tsw $t6, %d($sp)\n",6*WORD_SIZE);
        fileWriter.format("\tsw $t7, %d($sp)\n",7*WORD_SIZE);
       
	}
	
	public void storeOnStack(TEMP t, int offset) {
		
		int idx=t.getSerialNumber();
        fileWriter.format("\tsw Temp_%d, %d($sp)\n",idx ,offset*WORD_SIZE);
        GraphNode.addNode( new GraphNode(  new int[] {idx},null));

		
	}
	
    public void jal(String label) {
    	
    	fileWriter.format("\tjal %s\n",label);
    }
    
	public void storeReturnAddrOnStack(int offSet) {
		
		//fileWriter.format("\taddi $sp,$sp,%d\n", -WORD_SIZE);
        fileWriter.format("\tsw $ra, %d($sp)\n" ,offSet*WORD_SIZE);

	}
	
	public void loadReturnValueFromStack(TEMP t) {
		
		int idx=t.getSerialNumber();
        fileWriter.format("\tmove Temp_%d, $v0\n",idx);
        GraphNode.addNode( new GraphNode( null,idx,null));     

	}
 
 	public void storeReturnValueONStack(TEMP t) {
		
		int idx=t.getSerialNumber();
    fileWriter.format("\tmove $v0, Temp_%d\n",idx);
    GraphNode.addNode( new GraphNode(  new int[] {idx},null));

	}
	
	public void RestoreArguments(int num) {
	    fileWriter.format("\tlw $t0, %d($sp)\n",(2+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t1, %d($sp)\n",(3+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t2, %d($sp)\n",(4+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t3, %d($sp)\n",(5+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t4, %d($sp)\n",(6+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t5, %d($sp)\n",(7+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t6, %d($sp)\n",(8+num)*WORD_SIZE);
	    fileWriter.format("\tlw $t7, %d($sp)\n",(9+num)*WORD_SIZE);
	}
	

	
	public void funEpiloge(int num) {
		
		


    //poping parameters
   fileWriter.format("\taddi $sp,$sp,%d\n", (num)*WORD_SIZE);
   
   //loading fp and popong it
   fileWriter.format("\tlw $fp,%d($sp)\n",0);
   fileWriter.format("\taddi $sp,$sp,%d\n", WORD_SIZE);
   
   //loading ra and popong it
   fileWriter.format("\tlw $ra,%d($sp)\n",0);
   fileWriter.format("\taddi $sp,$sp,%d\n", WORD_SIZE);     
   
        
    
    //loading registers and popong them

  /*  fileWriter.format("\tlw $t0, %d($sp)\n",0*WORD_SIZE);
    fileWriter.format("\tlw $t1, %d($sp)\n",1*WORD_SIZE);
    fileWriter.format("\tlw $t2, %d($sp)\n",2*WORD_SIZE);
    fileWriter.format("\tlw $t3, %d($sp)\n",3*WORD_SIZE);
    fileWriter.format("\tlw $t4, %d($sp)\n",4*WORD_SIZE);
    fileWriter.format("\tlw $t5, %d($sp)\n",5*WORD_SIZE);
    fileWriter.format("\tlw $t6, %d($sp)\n",6*WORD_SIZE);
    fileWriter.format("\tlw $t7, %d($sp)\n",7*WORD_SIZE);*/
       
	
    fileWriter.format("\taddi $sp,$sp,%d\n", 8*WORD_SIZE);

	}
	
	public void saveFpOnStack() {

		fileWriter.format("\taddi $sp,$sp,%d\n", -WORD_SIZE);
        fileWriter.format("\tsw $fp, %d($sp)\n" ,0);
	}
	
	public void moveSptoFp() {
		fileWriter.format("\tmove $fp,$sp\n");
		
	}

	public void moveFptoSp() {
		fileWriter.format("\tmove $sp,$fp\n");
		
	}
	
	public void retrieveArg(int i) {
        fileWriter.format("\tlw $a%d,%d($sp)\n",0,0);

	}
	
	public void loadRetrunAddr(int offset) {
		
        fileWriter.format("\tlw $ra,%d($sp)\n",offset*WORD_SIZE);

		
	}
	
	public void moveZero(TEMP dst) {
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tmove Temp_%d, $zero\n",idxdst);
        GraphNode.addNode( new GraphNode( null,idxdst,null));	

	}
	
	public void jr() {
        fileWriter.format("\tjr $ra\n");
		
	}


	
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static sir_MIPS_a_lot instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected sir_MIPS_a_lot() {}
	
	public void IncStrCounter(){
		strCounter++;
	}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static sir_MIPS_a_lot getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new sir_MIPS_a_lot();
			strCounter = 0;
			stringsDec = new ArrayList<String>();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./FOLDER_5_OUTPUT/";
				String filename=String.format("MIPS.txt");
                
				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				//instance.fileWriter = new PrintWriter(dirname+filename);
                if (szOutPath != null)
                    instance.fileWriter = new PrintWriter(szOutPath);
                else {
                    System.out.println("Output path is null\n");
                    System.exit(0);
                }
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		return instance;
	}
    
    public static void setOutputFile(String szPath) {
        if (szOutPath == null)
            szOutPath = szPath;
    }













}
/*
  Signature
    GraphNode.addNode( new GraphNode( array of registered used,register defined, label that might be jumped-to from here));
    or
    GraphNode.addNode( new GraphNode( array of registered used, label that might be jump to from here));

  examples: 
  1)
    fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
    GraphNode.addNode( new GraphNode( new int[] {i1,i2},dstidx,null));
  2)
    fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);	
    GraphNode.addNode( new GraphNode(  new int[] {i1,i2},label));		

*/
