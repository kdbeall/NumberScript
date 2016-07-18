package edu.numberscript.token;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles tokenizing arithmetic expressions; Code from
 * http://stackoverflow.com/questions/16498649/advanced-tokenizer-for-a-complex-math-expression
 * 
 * @author Keng-Hui Beall
 *
 */
public class ArithmeticTokenizer {
	private static final String LEFT = "(";
	private static final String RIGHT = ")";
	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String MULTIPLY = "*";
	private static final String DIVIDE = "/";
	private static final String NUMBER_REGEX = "\\d+(\\.\\d+)?";
	private static final String VARIABLE_REGEX = "[a-zA-Z]*";

	/**
	 * Tokenizes an arithmetic expression
	 * 
	 * @param s
	 *            a string that is an arithmetic expression
	 * @return the tokenized arithmetic expression
	 * @throws IOException
	 *             if reading fails
	 */
	public static List<Token> tokenize(String s) throws IOException {
		StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
		tokenizer.ordinaryChar('-'); // Don't parse minus as part of numbers.

		// Make a list of strings representing possible tokens
		List<String> tokBuf = new ArrayList<String>();
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			switch (tokenizer.ttype) {
			case StreamTokenizer.TT_NUMBER:
				tokBuf.add(String.valueOf(tokenizer.nval));
				break;
			case StreamTokenizer.TT_WORD:
				tokBuf.add(tokenizer.sval);
				break;
			default: // operator
				tokBuf.add(String.valueOf((char) tokenizer.ttype));
			}
		}
		// Let's check the individual strings and
		// convert them into proper tokens
		List<Token> tokens = new ArrayList<Token>();
		Token currentToken;
		String current;
		for (int i = 0; i < tokBuf.size(); i++) {
			current = tokBuf.get(i);
			if (current.equals(LEFT)) {
				currentToken = new Token(TokenType.LEFT, LEFT);
			} else if (current.equals(RIGHT)) {
				currentToken = new Token(TokenType.RIGHT, RIGHT);
			} else if (current.equals(PLUS)) {
				currentToken = new Token(TokenType.PLUS, PLUS);
			} else if (current.equals(MINUS)) {
				currentToken = new Token(TokenType.MINUS, MINUS);
			} else if (current.equals(MULTIPLY)) {
				currentToken = new Token(TokenType.MULTIPLY, MULTIPLY);
			} else if (current.equals(DIVIDE)) {
				currentToken = new Token(TokenType.DIVIDE, DIVIDE);
			} else if (current.matches(NUMBER_REGEX)) {
				currentToken = new Token(TokenType.NUMBER, current);
			} else if (current.matches(VARIABLE_REGEX)) {
				currentToken = new Token(TokenType.VARIABLE, current);
			} else {
				throw new IllegalArgumentException("Illegal Token\n");
			}
			tokens.add(currentToken);
		}
		return tokens;
	}

}
