package slp;

import java_cup.runtime.*;

%%

%cup
%class Lexer
%type Token
%line
%scanerror RuntimeException

/****************/
/* DECLARATIONS */
/****************/
%{
	StringBuilder string = new StringBuilder();
	public int getLineNumber() { return yyline+1; }
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

%state STRING

%%

<YYINITIAL> {

/* keywords */
"class"				{ return new Token(yyline, yytext(), sym.CLASS); }
"extends"			{ return new Token(yyline, yytext(), sym.EXTENDS); }
"static"			{ return new Token(yyline, yytext(), sym.STATIC); }
"void"				{ return new Token(yyline, yytext(), sym.VOID); }
"int"				{ return new Token(yyline, yytext(), sym.INT); }
"boolean"			{ return new Token(yyline, yytext(), sym.BOOLEAN); }
"string"			{ preturn new Token(yyline, yytext(), sym.STRING); }
"return"			{ return new Token(yyline, yytext(), sym.RETURN); }
"if"				{ return new Token(yyline, yytext(), sym.IF); }
"else"				{ return new Token(yyline, yytext(), sym.ELSE); }
"while"				{ return new Token(yyline, yytext(), sym.WHILE); }
"break"				{ return new Token(yyline, yytext(), sym.BREAK); }
"continue"			{ return new Token(yyline, yytext(), sym.CONTINUE); }
"this"				{ return new Token(yyline, yytext(), sym.THIS); }
"new"				{ return new Token(yyline, yytext(), sym.NEW); }
"length"			{ return new Token(yyline, yytext(), sym.LENGTH); }

/* boolean literals */
"true"				{ return new Token(yyline, yytext(), sym.TRUE); }
"false"				{ return new Token(yyline, yytext(), sym.FALSE); }

/* null literal */
"null"				{ return new Token(yyline, yytext(), sym.NULL); }

/* separators */
"("					{ return new Token(yyline, yytext(), sym.LP); }
")"					{ return new Token(yyline, yytext(), sym.RP); }
"{"					{ return new Token(yyline, yytext(), sym.LCBR); }
"}"					{ return new Token(yyline, yytext(), sym.RCBR); }
"["					{ return new Token(yyline, yytext(), sym.LB); }
"]"					{ return new Token(yyline, yytext(), sym.RB); }
";"					{ return new Token(yyline, yytext(), sym.SEMI); }
","					{ return new Token(yyline, yytext(), sym.COMMA); }
"."					{ return new Token(yyline, yytext(), sym.DOT); }

/* operators */
"+"					{ return new Token(yyline, yytext(), sym.PLUS); } 
"-"					{ return new Token(yyline, yytext(), sym.MINUS); }
"*"					{ return new Token(yyline, yytext(), sym.MULTIPLY); }
"/"					{ return new Token(yyline, yytext(), sym.DIVIDE); }
"%"					{ return new Token(yyline, yytext(), sym.MOD); }
"="					{ return new Token(yyline, yytext(), sym.ASSIGN); }
"=="				{ return new Token(yyline, yytext(), sym.EQUAL); }
"!="				{ return new Token(yyline, yytext(), sym.NEQUAL); }
">"					{ return new Token(yyline, yytext(), sym.GT); }
"<"					{ return new Token(yyline, yytext(), sym.LT); }
">="				{ return new Token(yyline, yytext(), sym.GTE); }
"<="				{ return new Token(yyline, yytext(), sym.LTE); }
"&&"				{ return new Token(yyline, yytext(), sym.LAND); }
"||"				{ return new Token(yyline, yytext(), sym.LOR); }
"!"					{ return new Token(yyline, yytext(), sym.LNEG); }

/* library functions */
"readi" 	{ return new Token(yyline, yytext(), sym.READI); }
"print" 	{ return new Token(yyline, yytext(), sym.PRINT); }

/* string literal */
\"                  { 
						string.setLength(0); unescapedString.setLength(0);
						yybegin(STRING);
					}  

/* numeric literals */
{INTEGER}			{ return new Token(yyline, "INTEGER", sym.INTEGER, new Integer(yytext())); }
					 
/* identifiers */  
{IDENTIFIER}		{ return new Token(yyline, "ID", sym.ID, yytext()); }
{CLASS_IDENTIFIER}	{ return new Token(yyline, "CLASS_ID", sym.CLASS_ID, yytext()); }
					
/* comments */					
{Comment}           { /* just skip what was found, do nothing */ }

/* whitespace */
{WhiteSpace}		{ /* just skip what was found, do nothing */ }   
}

<STRING> {
\"                  { 
						yybegin(YYINITIAL);
						return new Token(yyline, "QUOTE", sym.QUOTE, string.toString());
                    } 
{StringCharacter}+  { string.append(yytext()); }

/* escape sequences */
\\t                 { string.append('\t'); }
\\n                 { string.append('\n'); }
\\\"                { string.append('\"'); }
\\\\                { string.append('\\'); }

/* error cases */
\\.                 { throw new RuntimeException("Lexical error: Illegal character at line " + (yyline+1) + " : \"" + yytext() + "\""); }
{LineTerminator}    { throw new RuntimeException("Lexical error: Unterminated string at end of line " + (yyline+1)); }
}

/* error fallback */
[^]                	{ throw new RuntimeException("Lexical error: Illegal character at line " + (yyline+1) + " : '" + yytext() + "'"); }

<<EOF>> 	{ return new Token(yyline, "EOF", sym.EOF); }