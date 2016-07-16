package edu.numberscript.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.numberscript.compiler.CompilerManager;

/**
 * Handles commandline arguments
 * 
 * @author Keng-Hui Beall
 *
 */
public class Command {
	/**
	 * Starts our NumberScript compiler/interpreter. Compiles our NumberScript
	 * to C using GCC from commandline. Executes our C code.
	 * 
	 * @param args
	 *            our command line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		CompilerManager myManager = null;

		try {
			myManager = new CompilerManager(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myManager.compile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
