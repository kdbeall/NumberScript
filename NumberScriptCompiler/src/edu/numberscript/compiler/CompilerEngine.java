package edu.numberscript.compiler;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import edu.numberscript.tokenizer.ArithmeticTokenizer;

/**
 * Translates statements from our NumberScript source to C
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerEngine {
	/** sets whether or not to debug */
	private static final boolean DEBUG = true;
	/** assign line regex **/
	private static final String ASSIGN_REGEX = "[a-zA-Z]*=.*;";
	/** comment line regex **/
	private static final String COMMENT_REGEX = "#.*";
	/** print line regex **/
	private static final String PRINT_REGEX = "print(.*);";
	/** variable name regex **/
	private static final String VARIABLE_REGEX = "[a-zA-Z]*";
	/** operator regex **/
	private static final String OPERATOR_REGEX = "[+-*/]";
	/** set to hold variable names */
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
		// remove all white space from the line
		line = line.replaceAll("\\s", "");
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
		if (DEBUG) {
			return true;
		}
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
		if (line.matches(PRINT_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a print line is a valid print
	 * 
	 * @param line
	 * @return
	 */
	private boolean validPrint(String printLine) {
		return false;
	}

	/**
	 * Checks if a line is an assign line
	 * 
	 * @param line
	 *            a line of source code
	 * @return if the line is an assign line
	 */
	private boolean isAssign(String line) {
		if (line.matches(ASSIGN_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether an assignment line is valid
	 * 
	 * @param line
	 *            an assignment statement
	 * @return whether the assignment is valid
	 */
	private boolean validAssign(String line) {
		// TODO Fix all of this!
		List<String> expressionTokens = null;

		try {
			int start = line.indexOf('=') + 1;
			int end = line.length() - 1;
			expressionTokens = ArithmeticTokenizer.tokenize(line.substring(start, end));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Checks if a String is a valid variable name
	 * 
	 * @param variable
	 *            a possible variable
	 * @return if the variable is valid
	 */
	private boolean validVariableName(String variable) {
		if (variable.matches(VARIABLE_REGEX)) {
			return true;
		}
		return false;
	}

	private boolean isRightBracket(String operator) {
		if (operator.matches(")")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a valid operator
	 * 
	 * @param operator
	 *            a possible operator
	 * 
	 * @return if valid or not
	 */
	private boolean isOperator(String operator) {
		if (operator.matches(OPERATOR_REGEX)) {
			return true;
		}
		return false;
	}

	private boolean isComment(String line) {
		if (line.matches(COMMENT_REGEX)) {
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
		if (isComment(validLine)) {
			String commentStart = "//";
			validLine = commentStart.concat(validLine);

		} else if (isPrint(validLine)) {
			String printStart = "printf(\"%f\\n\",";
			validLine = printStart.concat(validLine.substring(validLine.indexOf('(') + 1, validLine.length()));

		} else {
			String doubleStart = "double ";
			validLine = doubleStart.concat(validLine);
		}

		return validLine;
	}

}
