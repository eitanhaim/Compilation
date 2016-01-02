package lir;

import java.util.ArrayList;
import java.util.List;

import lir_instructions.StringLiteral;

public class StringLiterals {
	List<String> literals;
	List<StringLiteral> sLiterals;

	public StringLiterals() {
		literals = new ArrayList<String>();
		sLiterals = new ArrayList<StringLiteral>();
	}

	/**
	 * @param s string to add to string literals
	 * @return index of string in string literals
	 */
	public int add(String s) {
		if(!literals.contains(s)) {
			s = escapeICString(s);
			literals.add(s);
			StringLiteral sl = new StringLiteral("str"+literals.indexOf(s),"\""+ s+"\"");
			sl.assignAddress(literals.indexOf(s));
			sLiterals.add(sl);
		}
		return literals.indexOf(s);
	}
	
	/**
	 * Escapes the characters in a string using IC String rules.
	 * 
	 * @param str	A string optionally containing standard IC escape sequences.
	 * @return 		The translated string.
	 */
	public String escapeICString(String str) {
	    StringBuilder result = new StringBuilder(str.length());
	    
	    for (int i = 0; i < str.length(); i++) {
	        char currChar = str.charAt(i);
	        switch (currChar) {
            case '\n':
	        	result.append("\\n");
                break;
            case '\t':
	        	result.append("\\t");
                break;
            case '\r':
	        	result.append("\\r");
                break;
            case '\"':
	        	result.append("\\\"");
                break;
	        case '\\':
	        	result.append("\\\\");
                break;
            default:
    	        result.append(currChar);
            }  
	    }
	    
	    return result.toString();
	}
	
	/**
	 * @return all strings literals in a form of a list of string literals.
	 */
	public List<StringLiteral> toStringLiteralList() {
		return sLiterals;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		for(int i=0; i<literals.size(); i++) {
			res.append("str"+i+": \""+literals.get(i)+"\"\n");
		}

		return res.toString();
	}
}
