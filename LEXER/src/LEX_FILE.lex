/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
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
    
/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup
   
/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied letter to letter into the Lexer class code.                */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{   
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    StringBuilder string = new StringBuilder();
	StringBuilder unescapedString = new StringBuilder();    
	
    private Symbol symbol(int type)               { return new Symbol(type, yyline, yycolumn); }
    private Symbol symbol(int type, Object value) { return new Symbol(type, yyline, yycolumn, value); }
    
    /**
     * Prints token in the following format: 
     *		#line-number: token
     */
    private void printlnWithLineNumber(String token) { System.out.println((yyline+1) + ": " + token); }
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
/* main character classes */
LineTerminator	 = \r|\n|\r\n
InputCharacter   = [^\r\n]

WhiteSpace		 = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

/* identifiers */
IDENTIFIER		 = [a-z_][A-Za-z_0-9]*
CLASS_IDENTIFIER = [A-Z][A-Za-z_0-9]*

/* types */
INTEGER			 = 0 | [1-9][0-9]*
StringCharacter = [^\r\n\"\\]

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%state STRING

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

/* keywords */
"class"				{ printlnWithLineNumber("CLASS");    	return symbol(sym.CLASS);}
"extends"			{ printlnWithLineNumber("EXTENDS");    	return symbol(sym.EXTENDS);}
"static"			{ printlnWithLineNumber("STATIC");    	return symbol(sym.STATIC);}
"void"				{ printlnWithLineNumber("VOID");    	return symbol(sym.VOID);}
"int"				{ printlnWithLineNumber("INT");    		return symbol(sym.INT);}
"boolean"			{ printlnWithLineNumber("BOOLEAN");    	return symbol(sym.BOOLEAN);}
"string"			{ printlnWithLineNumber("STRING");    	return symbol(sym.STRING);}
"return"			{ printlnWithLineNumber("RETURN");    	return symbol(sym.RETURN);}
"if"				{ printlnWithLineNumber("IF");    		return symbol(sym.IF);}
"else"				{ printlnWithLineNumber("ELSE");    	return symbol(sym.ELSE);}
"while"				{ printlnWithLineNumber("WHILE");    	return symbol(sym.WHILE);}
"break"				{ printlnWithLineNumber("BREAK");    	return symbol(sym.BREAK);}
"continue"			{ printlnWithLineNumber("CONTINUE");    return symbol(sym.CONTINUE);}
"this"				{ printlnWithLineNumber("THIS");    	return symbol(sym.THIS);}
"new"				{ printlnWithLineNumber("NEW");    		return symbol(sym.NEW);}
"length"			{ printlnWithLineNumber("LENGTH");    	return symbol(sym.LENGTH);}

/* boolean literals */
"true"				{ printlnWithLineNumber("TRUE");    	return symbol(sym.TRUE);}
"false"				{ printlnWithLineNumber("FALSE");    	return symbol(sym.FALSE);}

/* null literal */
"null"				{ printlnWithLineNumber("NULL");    	return symbol(sym.NULL);}

/* separators */
"("					{ printlnWithLineNumber("LP");    		return symbol(sym.LP);}
")"					{ printlnWithLineNumber("RP");    		return symbol(sym.RP);}
"{"					{ printlnWithLineNumber("LCBR");    	return symbol(sym.LCBR);}
"}"					{ printlnWithLineNumber("RCBR");    	return symbol(sym.RCBR);}
"["					{ printlnWithLineNumber("LB");    		return symbol(sym.LB);}
"]"					{ printlnWithLineNumber("RB");    		return symbol(sym.RB);}
";"					{ printlnWithLineNumber("SEMI"); 		return symbol(sym.SEMI);}
","					{ printlnWithLineNumber("COMMA");    	return symbol(sym.COMMA);}
"."					{ printlnWithLineNumber("DOT");    		return symbol(sym.DOT);}

/* operators */
"+"					{ printlnWithLineNumber("PLUS");      	return symbol(sym.PLUS);}
"-"					{ printlnWithLineNumber("MINUS");    	return symbol(sym.MINUS);}
"*"					{ printlnWithLineNumber("MULTIPLY");    return symbol(sym.MULTIPLY);}
"/"					{ printlnWithLineNumber("DIVIDE");    	return symbol(sym.DIVIDE);}
"%"					{ printlnWithLineNumber("MOD");    		return symbol(sym.MOD);}
"="					{ printlnWithLineNumber("ASSIGN");    	return symbol(sym.ASSIGN);}
"=="				{ printlnWithLineNumber("EQUAL");    	return symbol(sym.EQUAL);}
"!="				{ printlnWithLineNumber("NEQUAL");    	return symbol(sym.NEQUAL);}
">"					{ printlnWithLineNumber("GT");    		return symbol(sym.GT);}
"<"					{ printlnWithLineNumber("LT");    		return symbol(sym.LT);}
">="				{ printlnWithLineNumber("GTE");    		return symbol(sym.GTE);}
"<="				{ printlnWithLineNumber("LTE");    		return symbol(sym.LTE);}
"&&"				{ printlnWithLineNumber("LAND");    	return symbol(sym.LAND);}
"||"				{ printlnWithLineNumber("LOR");    		return symbol(sym.LOR);}
"!"					{ printlnWithLineNumber("LNEG");    	return symbol(sym.LNEG);}

/* string literal */
\"                  { string.setLength(0); unescapedString.setLength(0) ; yybegin(STRING); }  

/* numeric literals */
{INTEGER}			{
						printlnWithLineNumber("INTEGER(" + yytext() +")");
						return symbol(sym.INTEGER, new Integer(yytext()));
					} 
					 
/* identifiers */  
{IDENTIFIER}		{
						printlnWithLineNumber("ID(" + yytext() + ")");
						return symbol(sym.ID, new String(yytext()));
					}
{CLASS_IDENTIFIER}	{
						printlnWithLineNumber("CLASS_ID(" + yytext() + ")");
						return symbol(sym.CLASS_ID, new String(yytext()));
					}
					
/* comments */					
{Comment}           { /* just skip what was found, do nothing */ }

/* whitespace */
{WhiteSpace}		{ /* just skip what was found, do nothing */ }   
}

<STRING> {
\"                  { 
						yybegin(YYINITIAL);
						printlnWithLineNumber("QUOTE(\"" + unescapedString.toString() + "\")"); 
						return symbol(sym.QUOTE, string.toString());
                    } 
{StringCharacter}+  { string.append(yytext()); unescapedString.append(yytext()); }

/* escape sequences */
\\t                 { string.append('\t'); unescapedString.append(yytext()); }
\\n                 { string.append('\n'); unescapedString.append(yytext()); }
\\\"                { string.append('\"'); unescapedString.append(yytext()); }
\\\\                { string.append('\\'); unescapedString.append(yytext()); }

/* error cases */
\\.                 { printlnWithLineNumber("Lexical error: illegal escape sequence \"" + yytext() + "\""); System.exit(1); }
{LineTerminator}    { printlnWithLineNumber("Lexical error: Unterminated string at end of line"); System.exit(1); }
}

/* error fallback */
[^]                	{ printlnWithLineNumber("Lexical error: illegal character '" + yytext() + "'"); System.exit(1); }

<<EOF>>     		{ System.out.println((yyline+2) + ": EOF"); return symbol(sym.EOF); }

