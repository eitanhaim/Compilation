package ic;

import java.io.*;
import java_cup.runtime.*;

import ast.*;
import parser.*;
import semantic_analysis.SemanticError;
import symbol_table.SymbolsTableBuilder;
import type_table.TypeTableBuilder;
import type_table.TypeValidator;

/** 
 * The entry point of the IC application.
 */
public class Main {
	private static boolean printtokens = false;
	private static boolean printast = false;
	
	/** 
	 * Reads an IC program and compile it partially crossing the stages:
	 * Lexical Analysis, Syntax Analysis and Semantic Analysis.
	 * 
	 * @param args	The name of the file containing an IC program.
	 */
	public static void main(String[] args) {
		FileReader txtFile = null;
		Symbol parseSymbol = null;
		
		try {			
			// parse command line arguments
			if (args.length == 0) {
				System.out.println("Error: Missing input file argument!");
				printUsage();
				System.exit(-1);
			}
			if (args.length >= 2)
			{
				for (String arg : args) {
					if (arg.equals("-printtokens"))
						printtokens = true;
					else if (arg.equals("-printast"))
						printast = true;						
				}
				
				if (!printtokens && !printast)
				{
					printUsage();
					System.exit(-1);
				}	
			}
			
			// TODO: add code regarding Library
			
			// scan the IC program
			txtFile = new FileReader(args[0]);
			Lexer scanner = new Lexer(txtFile);
			
			// parse the IC program
			Parser parser = new Parser(scanner); 
			parser.printTokens = printtokens;
			parseSymbol = parser.parse();
			//System.out.println("Parsed " + args[0] + " successfully!");

			Program root = (Program) parseSymbol.value;	
			
			// analyze the IC program semantically 
			TypeTableBuilder typeTableBuilder = new TypeTableBuilder(new File(args[0]));
			typeTableBuilder.buildTypeTable(root);
			SymbolsTableBuilder s = new SymbolsTableBuilder(typeTableBuilder.getBuiltTypeTable(), args[0]);
			s.buildSymbolTables(root);

			TypeValidator tv = new TypeValidator(typeTableBuilder.getBuiltTypeTable());
			tv.validate(root);
            
            // pretty-print the program to System.out, if requested
            if (printast && (root != null))
            { 
            	PrettyPrinter printer = new PrettyPrinter(args[0], root);
            	printer.print();
            }
            
		} catch (FileNotFoundException fnfException) {
            System.err.println("The file " + args[0] + " not found");
        } catch (LexicalError lexicalError) {
            System.err.println("LexicalError: " + lexicalError.getMessage());
        } catch (SyntaxError syntaxError) {
            System.err.println("SyntaxError: " + syntaxError.getMessage());
        } catch (SemanticError semanticError) {
            System.err.println("SemanticError: " + semanticError.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("IO Error (brutal exit) " + ex.getMessage() + " " + parseSymbol.value);
        } finally {
            try {
                if (txtFile != null) {
                    txtFile.close();
                }
            } catch (IOException ex) {
                System.err.println("txtFile.close()");
            }
        }
		
	}
	
	/** 
	 * Prints usage information about this application to System.out
	 */
	public static void printUsage() {
		System.out.println("Usage: ic file [-printtokens] [-printast]");
	}
}