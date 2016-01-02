package ic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
import lir.TranslationVisitor;
import parser.*;
import semantic_analysis.SemanticError;
import semantic_analysis.SemanticErrorThrower;
import symbol_table.*;
import type_table.*;
import ast.*;

public class Compiler {
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

		if (args.length == 0 || args.length > 4) {
			System.err.println("Error: invalid arguments");
			System.exit(-1);
		}
		ICClass libRoot = null;

		try {
			if(getLibraryArgument(args)!=null) { //if library in arguments
				//parse library file
				File libFile = new File(getLibraryArgument(args));
				FileReader libFileReader = new  FileReader(libFile);
				LibLexer libScanner = new LibLexer(libFileReader);
				LibParser libParser = new LibParser(libScanner);

				Symbol libParseSymbol = libParser.parse();
				libRoot = (ICClass) libParseSymbol.value;
				System.out.println("Parsed " + libFile.getName() +" successfully!");

			}

			//parse IC file
			File icFile = new File(args[0]);
			FileReader icFileReader = new FileReader(icFile);

			Lexer scanner = new Lexer(icFileReader);
			Parser parser = new Parser(scanner);

			Symbol parseSymbol = parser.parse(); // TODO right now: keeps running after exception!!!
			Program ICRoot = (Program) parseSymbol.value;
			
			if (libRoot != null) { 
				if(!libRoot.getName().equals(LIB_NAME)) { //Make sure that the library class has the correct name
					(new SemanticErrorThrower(1, "Library class has incorrect name: "+libRoot.getName()+ ", expected "+LIB_NAME+".")).execute();
				} else {
					ICRoot.getClasses().add(0, libRoot); //append the library
				}
			}

			System.out.println("Parsed " + icFile.getName() +" successfully!");
			System.out.println();

			TypeTableBuilder typeTableBuilder = new TypeTableBuilder(icFile.getName());
			typeTableBuilder.buildTypeTable(ICRoot);
			SymbolTableBuilder s = new SymbolTableBuilder(typeTableBuilder.getBuiltTypeTable(), icFile.getName());
			s.buildSymbolTables(ICRoot);

			TypeValidator tv = new TypeValidator(typeTableBuilder.getBuiltTypeTable());
			tv.validate(ICRoot);
			
			TranslationVisitor trv=new TranslationVisitor();
			trv.translate(ICRoot);
		//	ICRoot.accept(trv);
			//System.out.println(trv.getEmissionString());

			if(isInArgs(args, "-print-ast")) {
				//Pretty-print the program to System.out
				PrettyPrinter printer = new PrettyPrinter(args[0]);
				System.out.println(printer.visit(ICRoot));
				if(isInArgs(args, "-dump-symtab"))
					System.out.println();
			}
			
			if(isInArgs(args, "-dump-symtab")) {
				s.getSymbolTable().printTable();
				typeTableBuilder.getBuiltTypeTable().printTable();
			}
			
			if(isInArgs(args, "-print-lir"))
			{
				System.out.println("printing LIR!!!!!");
				PrintWriter writer = new PrintWriter("output.lir");
				writer.print(trv.printInstructions());
				writer.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (LexicalError e) {
			System.out.println(e.getMessage());
		} catch (SemanticError e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getLibraryArgument(String args[]) {
		for (int i=0; i<args.length; i++)
			if(args[i].substring(0, 2).equals("-L"))
				return args[i].substring(2);
		return null;
	}
	
	private static boolean isInArgs(String args[], String arg) {
		for (int i=0; i<args.length; i++)
			if(args[i].equals(arg))
				return true;
		return false;
	}
}