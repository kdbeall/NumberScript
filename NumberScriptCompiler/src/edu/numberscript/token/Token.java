package edu.numberscript.token;

/**
 * Represents a Token in an arithmetic expression
 * 
 * @author Keng-Hui Beall
 *
 */
public class Token {
	/** is the token a variable, parenthesis, or operation **/
	private TokenType type;
	private String value;
	private int precedence;

	/**
	 * Creates a new Token
	 * 
	 * @param type
	 *            the type of the token
	 * @param value
	 *            the value of the token
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
		this.precedence = -1;
		setPrecedence(type);
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean isOperator() {
		switch (type) {
		case DIVIDE:
			return true;
		case LEFT:
			return false;
		case MINUS:
			return true;
		case MULTIPLY:
			return true;
		case NUMBER:
			return false;
		case PLUS:
			return true;
		case RIGHT:
			return false;
		case VARIABLE:
			return false;

		}
		return false;
	}

	public int getPrecedence() {
		return precedence;
	}

	private void setPrecedence(TokenType type) {
		switch (type) {
		case PLUS:
			precedence = 2;
			break;
		case MINUS:
			precedence = 2;
			break;
		case DIVIDE:
			precedence = 3;
			break;
		case MULTIPLY:
			precedence = 3;
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case VARIABLE:
			break;
		case NUMBER:
			break;
		}
	}

}
