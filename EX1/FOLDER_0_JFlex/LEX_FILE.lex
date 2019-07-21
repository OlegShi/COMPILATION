/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
MINUS_SIGN		= -
INTEGER			= 0 |[1-9][0-9]* |{MINUS_SIGN}[1-9][0-9]*
ID				= [A-Za-z][A-Za-z0-9]*
String			= [\"][A-Za-z0-9]*[\"]

COMMENT_LINE = "//" .* {LineTerminator}
LONG_COMMENT = "/*"[^*]*|[*]*"*/" 
COMMENT = {LONG_COMMENT} | {COMMENT_LINE}
COMMENT_ERROR = "/*" 

UNCLOSED_STRING = "\""[^\"]*

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

"+"					{ return symbol(TokenNames.PLUS, "PLUS");}
"-"					{ return symbol(TokenNames.MINUS, "MINUS");}
"*"					{ return symbol(TokenNames.TIMES, "TIMES");}
"/"					{ return symbol(TokenNames.DIVIDE, "DIVIDE");}
"("					{ return symbol(TokenNames.LPAREN, "LPAREN");}
")"					{ return symbol(TokenNames.RPAREN, "RPAREN");}
"{"					{ return symbol(TokenNames.LBRACE, "LBRACE");}
"}"					{ return symbol(TokenNames.RBRACE, "RBRACE");}
"["					{ return symbol(TokenNames.LBRACK, "LBRACK");}
"]"					{ return symbol(TokenNames.RBRACK, "RBRACK");}
","					{ return symbol(TokenNames.COMMA, "COMMA");}
"."					{ return symbol(TokenNames.DOT, "DOT");}
";"					{ return symbol(TokenNames.SEMICOLON, "SEMICOLON");}
":="				{ return symbol(TokenNames.ASSIGN, "ASSIGN");}
"="					{ return symbol(TokenNames.EQ, "EQ");}
"<"					{ return symbol(TokenNames.LT, "LT");}
">"					{ return symbol(TokenNames.GT, "GT");}



"class"	  	 		{ return symbol(TokenNames.CLASS, "CLASS"); }
"nil" 				{ return symbol(TokenNames.NIL, "NIL"); }
"array" 			{ return symbol(TokenNames.ARRAY, "ARRAY"); }
"while" 			{ return symbol(TokenNames.WHILE, "WHILE"); }
"extends" 			{ return symbol(TokenNames.EXTENDS, "EXTENDS"); }
"return" 			{ return symbol(TokenNames.RETURN, "RETURN"); }
"new" 				{ return symbol(TokenNames.NEW, "NEW"); }
"if" 				{ return symbol(TokenNames.IF, "IF"); }


{INTEGER}			{ 
						Integer num = new Integer(yytext());
						if(num >= -32768 && num <= 32767)
							return symbol(TokenNames.NUMBER, "INT("+num+")");
						return symbol(TokenNames.error);
					}
{COMMENT}			{ /* just skip what was found, do nothing */ }
					
{ID}				{ return symbol(TokenNames.ID, new String("ID("+yytext()+")") );}   
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF);}



{String}			{ return symbol(TokenNames.STRING, new String("STRING("+yytext()+")"));}
{COMMENT_ERROR} 	{ return symbol(TokenNames.error);}
{UNCLOSED_STRING}	{ return symbol(TokenNames.error);}

}
