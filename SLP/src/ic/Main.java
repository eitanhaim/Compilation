package ic;

import java.io.*;

import java_cup.runtime.*;
import ast.*;
import parser.*;
import semantic_analysis.*;

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
			
			
			txtFile = new FileReader(args[0]);
			
			 // create a scanning object
			Lexer scanner = new Lexer(txtFile);
			
			// create a parsing object
			Parser parser = new Parser(scanner); 
			parser.printTokens = printtokens;
			parseSymbol = parser.parse();
			//System.out.println("Parsed " + args[0] + " successfully!");

			Program root = (Program) parseSymbol.value;	
			
			// pretty-print the program to System.out, if requested
			if (printast && (root != null))
			{ 
				PrettyPrinter printer = new PrettyPrinter(args[0], root);
				printer.print();
			}
			
			// execute semantic analysis
            TypeTableConstructor typeTableConstructor = new TypeTableConstructor(root);
            typeTableConstructor.run();

            SymbolTableConstructor symbolTableConstructor = new SymbolTableConstructor(args[0], root);
            symbolTableConstructor.construct();

            Tester scopeChecker = new ScopeChecker(root);
            scopeChecker.test();

            Tester analyzer = new TypeAnalyzer(root);
            analyzer.test();
            
			//SemanticAnalayzer sa = new SemanticAnalayzer(root);
			//root.accept(sa);
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