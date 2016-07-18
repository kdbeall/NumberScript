package edu.numberscript.compiler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import edu.numberscript.token.ArithmeticTokenizer;
import edu.numberscript.token.ReversePolishConverter;
import edu.numberscript.token.Token;
import edu.numberscript.token.TokenType;

/**
 * Translates statements from our NumberScript source to C
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerEngine {
	/** sets whether or not to debug */
	private static final boolean DEBUG_COMPILE = true;
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
	private Hashtable<String, Double> variables;

	/**
	 * Constructs a new CompilerEngine
	 */
	public CompilerEngine() {
		// initialze an empty set
		variables = new Hashtable<String, Double>();
	}

	/**
	 * Translates our line of NumberScript code to C
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return C code equivalent to the line
	 * @throws Exception
	 */
	public String translate(String line) throws Exception {
		// remove all white space from the line
		line = line.replaceAll("\\s", "");
		if (valid(line)) {
			return compile(line);
		}
		throw new Exception("Invalid line");
	}

	/**
	 * Checks if our line is valid
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return if the line is valid NumberScript
	 * @throws InterruptedException
	 */
	public boolean valid(String line) throws InterruptedException {
		if (DEBUG_COMPILE) {
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
	 * @throws InterruptedException
	 */
	private boolean validAssign(String line) throws InterruptedException {
		// TODO Fix all of this!
		return true;

		/**
		 * 
		 * // get the arithmetic expression and tokenize it List<Token>
		 * expressionTokens = null; try { // start of arithmetic expression is
		 * after equals int start = line.indexOf('=') + 1; // end is before the
		 * semicolon int end = line.length() - 1; expressionTokens =
		 * ArithmeticTokenizer.tokenize(line.substring(start, end)); } catch
		 * (IOException e) { e.printStackTrace(); } // check that all variables
		 * are in the hash table for (int i = 0; i < expressionTokens.size();
		 * i++) { if (expressionTokens.get(i).getType() == TokenType.VARIABLE) {
		 * if (!variables.contains(expressionTokens.get(i).getValue())) { throw
		 * new IllegalArgumentException(); } expressionTokens.add(i, new
		 * Token(TokenType.NUMBER, ); } } // convert it to reverse polish
		 * notation LinkedBlockingQueue<Token> rpnTokens =
		 * ReversePolishConverter.convert(expressionTokens);
		 * 
		 * // let's attempt to evaluate expression Stack<Double> evaluate = new
		 * Stack<Double>(); while (!rpnTokens.isEmpty()) { Token current =
		 * rpnTokens.take(); if (current.getType() == TokenType.VARIABLE ||
		 * current.getType() == TokenType.NUMBER) { evaluate.push(current); }
		 * else if (evaluate.size() < 2) { if (current.getType() ==
		 * TokenType.MINUS && evaluate.size() == 1) { evaluate.push(new
		 * Token(evaluate.pop().getType(),
		 * "-".concat(evaluate.pop().getValue()))); } else { throw new
		 * IllegalArgumentException(); } } else { Token first = evaluate.pop();
		 * Token second = evaluate.pop(); double value1 =
		 * Double.parseDouble(first.getValue()); double value2 =
		 * Double.parseDouble(first.getValue()); if(current.getType() ==
		 * TokenType.PLUS){ evaluate.push() } } }
		 * 
		 * 
		 * return true;
		 */
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
			int start = validLine.indexOf('(') + 1;
			int end = validLine.length();
			validLine = printStart.concat(validLine.substring(start, end));

		} else {
			String doubleStart = "double ";
			validLine = doubleStart.concat(validLine);
		}

		return validLine;
	}

}
