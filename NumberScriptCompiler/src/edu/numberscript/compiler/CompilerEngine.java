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
		if (line.matches("print(.*);")) {
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

	private boolean isAssign(String line) {
		if (line.matches("[a-zA-z]*=.*;")) {
			return true;
		}
		return false;
	}

	private boolean validAssign(String line) {
		List<String> expressionTokens = null;

		try {
			expressionTokens = ArithmeticTokenizer.tokenize(line.substring(line.indexOf('=') + 1, line.length() - 1));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Stack<String> tokens = new Stack<String>();
		tokens.push(expressionTokens.get(0));
		if (isOperator(tokens.peek()) || isRightBracket(tokens.peek()) || !validVariableName(tokens.peek())) {
			return false;
		}
		for (int i = 1; i < expressionTokens.size(); i++) {
			String token = expressionTokens.get(i);
			// case if token is a variable name
			if (validVariableName(token)) {
				// is the variable name in the set?
				if (!variables.contains(token)) {
					// no?
					return false;
					// check if the variable before is a right bracket
				} else if (isRightBracket(tokens.peek())) {
					return false;
				}
				// looks good add to stack
				tokens.push(expressionTokens.get(i));
			}

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
		if (variable.matches("[a-zA-Z]*")) {
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
		if (operator.matches("[+-*/]")) {
			return true;
		}
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
		if (isComment(validLine)) {
			validLine = "//".concat(validLine);

		} else if (isPrint(validLine)) {
			validLine = "printf(%f,".concat(validLine.substring(validLine.indexOf('('), validLine.indexOf(')')))
					.concat(";");

		} else {
			validLine = "double ".concat(validLine);
		}

		return validLine;
	}

}
