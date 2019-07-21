   
   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import IR.*;
import MIPS.*;
import java.util.*;


public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST = null;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{   
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			//file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
            
            /*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l);

			
			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
            try {
                AST = (AST_PROGRAM) p.parse().value;
                
            /*************************/
            /* [6] Print the AST ... */
            /*************************/
                AST.PrintMe();
            }
            catch (Exception e) {
	        			//file_writer.print("ERROR(");
		        		//file_writer.print(l.getLine());
		        		//file_writer.print(")");
                System.exit(0);
            }
            
            /**************************/
            /* [7] Semant the AST ... */
            /**************************/
            try {          
    			  AST.SemantMe();
                  //file_writer.print("OK\n");                
            }
            catch (SemanticException e) {
                System.out.print(e.error);
                //file_writer.format("ERROR(%d)\n",e.line+1);
                System.exit(0);
            }          
            
            /********************************/
			/* [8.5] Set MIPS output      */
			/********************************/
            sir_MIPS_a_lot.setOutputFile(outputFilename);
         
            /***********************************************/
			/* [8] start kick to the MIPS Instance         */
			/***********************************************/ 
            sir_MIPS_a_lot.getInstance();
            
            /**********************/
			/* [8] IR the AST ... */
			/**********************/
			AST.IRme();
			
			/**************************************/
			/* [10] initiaize MIPS file */
			/**************************************/
            int globalVarsCount = AST.globalVarsCount;
			sir_MIPS_a_lot.getInstance().initializeFile(globalVarsCount);
            
			/***********************/
			/* [9] MIPS the IR ... */
			/***********************/
			IR.getInstance().MIPSme();
			

			/**************************************/
			/* [10] Finalize AST GRAPHIZ DOT file */
			/**************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();	
			


			/***************************/
			/* [11] Finalize MIPS file */
			/***************************/
			sir_MIPS_a_lot.getInstance().finalizeFile();			
      
      // Hens' register alloc algorithm
      GraphNode.runTranlation(outputFilename);
           
      			System.out.print("Global: ");
                System.out.print(globalVarsCount);
            /**************************/
			/* [12] Close output file */
			/**************************/
			//file_writer.close();
    	}
	     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


