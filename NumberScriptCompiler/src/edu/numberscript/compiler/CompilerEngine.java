package edu.numberscript.compiler;

/**
 * Translates statements from our NumberScript source to C Depends on C for
 * error checking
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerEngine {
	/** sets whether or not to debug */
	private static final boolean DEBUG_COMPILE = true;
	/** assign line regex **/
	private static final String ASSIGN_REGEX = "[a-zA-Z]+=.+;";
	/** comment line regex **/
	private static final String COMMENT_REGEX = "#.*";
	/** print line regex **/
	private static final String PRINT_REGEX = "print(.+);";
	/** empty line regex **/
	private static final String EMPTY_REGEX = " *";

	/**
	 * Constructs a new CompilerEngine
	 */
	public CompilerEngine() {

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
		throw new IllegalArgumentException();
	}

	/**
	 * Checks if our line is valid Note, this does not check for invalid
	 * arithmetic expressions
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return if the line is likely valid NumberScript
	 */
	public boolean valid(String line) {
		// check if a valid print statement
		if (isPrint(line)) {
			return true;
			// check if a valid assign statement
		} else if (isAssign(line)) {
			return true;
			// check if a valid comment statement
		} else if (isComment(line)) {
			return true;
		} else if (isEmpty(line)) {
			return true;
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
	 * Checks if a line is a comment line
	 * 
	 * @param line
	 *            a line of NumberScript source code
	 * @return if the line is a comment
	 */
	private boolean isComment(String line) {
		if (line.matches(COMMENT_REGEX)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a line is empty
	 */
	private boolean isEmpty(String line) {
		if (line.matches(EMPTY_REGEX)) {
			return true;
		}
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

		} else if (isAssign(validLine)) {
			String doubleStart = "double ";
			validLine = doubleStart.concat(validLine);
		}

		return validLine;
	}

}
