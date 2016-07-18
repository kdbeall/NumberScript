package edu.numberscript.token;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class ReversePolishConverter {

	/**
	 * Converts our arithmetic expression to reverse polish notation using the
	 * Shunting-yard Algorithm
	 * 
	 * @param tokens
	 *            a list of tokens of representing a possible arithmetic
	 *            expression in infix notation
	 * @return a list of tokens in reverse polish notation
	 */
	public static LinkedBlockingQueue<Token> convert(List<Token> tokens) {
		Stack<Token> operators = new Stack<Token>();
		LinkedBlockingQueue<Token> output = new LinkedBlockingQueue<Token>();
		for (Token t : tokens) {
			switch (t.getType()) {
			case NUMBER:
				output.add(t);
				break;
			case DIVIDE:
				while (operators.peek().isOperator()) {
					if (t.getPrecedence() <= operators.peek().getPrecedence()) {
						output.add(operators.pop());
					}
				}
				operators.push(t);
				break;
			case LEFT:
				operators.push(t);
				break;
			case MINUS:
				while (operators.peek().isOperator()) {
					if (t.getPrecedence() <= operators.peek().getPrecedence()) {
						output.add(operators.pop());
					}
				}
				operators.push(t);
				break;
			case MULTIPLY:
				while (operators.peek().isOperator()) {
					if (t.getPrecedence() <= operators.peek().getPrecedence()) {
						output.add(operators.pop());
					}
				}
				operators.push(t);
				break;
			case PLUS:
				while (operators.peek().isOperator()) {
					if (t.getPrecedence() <= operators.peek().getPrecedence()) {
						output.add(operators.pop());
					}
				}
				operators.push(t);
				break;
			case RIGHT:
				try {
					while (operators.peek().getType() != TokenType.LEFT) {
						output.add(operators.pop());
					}
				} catch (EmptyStackException e) {
					throw new EmptyStackException();
				}
				operators.pop();
				break;
			case VARIABLE:
				output.add(t);
				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		return output;

	}

}
