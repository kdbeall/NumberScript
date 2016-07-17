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
	/**
	 * Tokenizes an arithmetic expression
	 * 
	 * @param s
	 *            a string that is an arithmetic expression
	 * @return the tokenized arithmetic expression
	 * @throws IOException
	 *             if reading fails
	 */
	public static List<String> tokenize(String s) throws IOException {
		StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
		tokenizer.ordinaryChar('-'); // Don't parse minus as part of numbers.
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
		
		return tokBuf;
	}

}
