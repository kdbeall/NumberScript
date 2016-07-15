package edu.numberscript.compiler;

import java.util.HashSet;

/**
 * Translates statements from our NumberScript source to C
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerEngine {
	/* set to hold variable names */
	HashSet<String> variables;

	/**
	 * Constructs a new CompilerEngine
	 */
	public CompilerEngine() {
		// initialze an empty set
		variables = new HashSet<String>();
	}

	/**
	 * Translates our line of NumberScript code to C
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return C code equivalent to the line
	 */
	public String translate(String line) {
		if (valid(line)) {
			return compile(line);
		}
		return null;
	}

	/**
	 * Checks if our line is valid
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return if the line is valid NumberScript
	 */
	public boolean valid(String line) {
		// check if a valid print statement
		if (isPrint(line)) {
			return validPrint(line);
			// check if a valid assign statement
		} else if (isAssign(line)) {
			return validAssign(line);
			// check if a valid comment statement
		} else if (isComment(line)) {
			return validComment(line);
		}
		// must be invalid if not a print, assign, or comment statement
		return false;
	}

	private boolean isPrint(String line) {
		if (line.matches("print(.*);")) {
			return true;
		}
		return false;
	}

	private boolean validPrint(String line) {
		return true;
	}

	private boolean isAssign(String line) {
		if (line.matches("[a-zA-z]*=.*;")) {
			return true;
		}
		return false;
	}

	private boolean validAssign(String line) {
		return false;
	}

	private boolean isComment(String line) {
		if (line.matches("#.*")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a line is a valid comment
	 * 
	 * @param line
	 *            a comment line of NumberScript source code
	 * @return if the line is a valid comment
	 */
	private boolean validComment(String line) {
		// can verify with regex; so if it matches "#.*"
		// it's always a comment
		return true;
	}

	/**
	 * Compiles our valid String
	 * 
	 * @param validLine
	 *            a valid String of NumberScript source code
	 * @return C source code representing our NumberScript source code
	 */
	public String compile(String validLine) {
		return null;
	}

	private String compilePrint(String validLine) {
		return null;
	}

	private String compileAssign(String validLine) {
		return null;
	}

	private String compileComment(String validLine) {
		return null;
	}

}
