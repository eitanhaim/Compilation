package ic;

import java.io.*;
import java_cup.runtime.*;
import lir.TranslationVisitor;
import ast.*;
import parser.*;
import semantic_analysis.*;
import symbol_table.SymbolTableBuilder;
import type_table.TypeTableBuilder;
import type_table.TypeValidator;

/** 
 * The entry point of the IC application.
 */
public class Main {	
	private static final String DEFAULT_LIB_PATH = "libic.sig";
	private static final String LIB_NAME = "Library";

	private static String icPath = null, libPath = DEFAULT_LIB_PATH;
	private static boolean printast = false;
	
	/** 
	 * Reads an IC program and compile it partially crossing the stages:
	 * Lexical Analysis, Syntax Analysis and Semantic Analysis.
	 * 
	 * @param args	The name of the file containing an IC program.
	 */
	public static void main(String[] args) {		
		try {	
			parseInput(args);
			
			// parse the library file
			ICClass libRoot = parseLibFile();
			if (libRoot == null)
				return;
			//System.out.println("Parsed " + libPath +" successfully!");
			
			// parse the IC file
			Program icRoot = parseICFile();
			if (icRoot == null)
				return;
			//System.out.println("Parsed " + icPath +" successfully!");
			
			// make sure that the Library class has the correct name
			if(!libRoot.getName().equals(LIB_NAME)) 
				throw new SemanticError(1, "Library class has incorrect name: "+ libRoot.getName() + ", expected " + LIB_NAME);
			else  // append the library
				icRoot.getClasses().add(0, libRoot); 
			
			// analyze the IC file semantically 
			TypeTableBuilder typeTableBuilder = new TypeTableBuilder(new File(icPath));
			typeTableBuilder.buildTypeTable(icRoot);
			SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(typeTableBuilder.getBuiltTypeTable(), icPath);
			symbolTableBuilder.buildSymbolTables(icRoot);

			TypeValidator TypeValidator = new TypeValidator(typeTableBuilder.getBuiltTypeTable());
			TypeValidator.validate(icRoot);
			
			// translate the IC file to LIR
			TranslationVisitor trv = new TranslationVisitor();
			trv.translate(icRoot);
			
			PrintWriter writer = new PrintWriter("output.lir");
			writer.print(trv.printInstructions());
			writer.close();
			
			//System.out.println("Created LIR file successfully!");
            
            // pretty-print the AST to System.out, if requested
            if (printast && (icRoot != null))
            { 
            	PrettyPrinter printer = new PrettyPrinter(args[0], icRoot);
            	printer.print();
            }            
		} catch (SemanticError semanticError) {
            System.err.println("Semantic Error: " + semanticError.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
	}
	
	/**
	 * Parses the command line arguments
	 * 
	 * @param args	The command line arguments.
	 */
	private static void parseInput(String[] args)
	{
		if (args.length == 0 || args.length > 3) { // invalid input
			if (args.length == 0)
				System.out.println("Error: Missing input file argument!");
			else
				System.out.println("Error: Too much arguments!");
			printUsage();
			System.exit(-1);
		}
		else if (args.length >= 2) { // parse optional arguments
			for (String arg : args) {
				if (arg.equals("-printast"))
					printast = true;
				else if (arg.startsWith("-L"))
					libPath = arg.substring(2);
			}	
		}
		
		icPath = args[0];
	}
	
	/** 
	 * Prints usage information about this application to System.out
	 */
	private static void printUsage() {
		System.out.println("Usage: <ic file> [-printast] [-L<lib file>]");
	}
	
	/**
	 * Parses the library file.
	 * 
	 * @return A ICClass AST node constituting the AST root of the Library program.
	 */
	private static ICClass parseLibFile()
	{
		FileReader libFileReader = null;
		Symbol libParseSymbol = null;
		
		try {
			File libFile = new File(libPath);
			libFileReader = new  FileReader(libFile);
			LibLexer libScanner = new LibLexer(libFileReader);
			LibParser libParser = new LibParser(libScanner);
			
			libParseSymbol = libParser.parse();
			if(libParser.errorFlag)
				return null;
			
			return (ICClass) libParseSymbol.value;
		} catch (FileNotFoundException fnfException) {
            System.err.println("The file " + libPath + " not found");
        } catch (LexicalError lexicalError) {
            System.err.println("Lexical Error: " + lexicalError.getMessage());
        } catch (SyntaxError syntaxError) {
            System.err.println("Syntax Error: " + syntaxError.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("IO Error (brutal exit) " + ex.getMessage() + " " + libParseSymbol.value);
        } finally {
        	try {
        		if (libFileReader != null) 
        			libFileReader.close();
        	} catch (IOException ex) {
        		System.err.println("Error: closing the lib file reader has failed");
        	}
        }
		
    	return null;
	}
	
	/**
	 * Parses the IC file.
	 * 
	 * @return	A Program AST node constituting the AST root of the IC program.
	 */
	private static Program parseICFile() {
		FileReader icFileReader = null;	
		Symbol parseSymbol = null;

        try {
        	File icFile = new File(icPath);
			icFileReader = new FileReader(icFile);
			Lexer scanner = new Lexer(icFileReader);
			Parser parser = new Parser(scanner);
			
			parseSymbol = parser.parse();
			if(parser.errorFlag)
				return null;

            return (Program) parseSymbol.value;
        } catch (FileNotFoundException fnfException) {
            System.err.println("The file " + icPath + " not found");
        } catch (LexicalError lexicalError) {
            System.err.println("Lexical Error: " + lexicalError.getMessage());
        } catch (SyntaxError syntaxError) {
            System.err.println("Syntax Error: " + syntaxError.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("IO Error (brutal exit) " + ex.getMessage() + " " + parseSymbol.value);
        } finally {
        	try {
        		if (icFileReader != null) 
                    icFileReader.close();
        	} catch (IOException ex) {
        		System.err.println("Error: closing the ic file reader has failed");
        	}
        }

        return null;
    }
}