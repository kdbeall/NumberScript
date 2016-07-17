package edu.numberscript.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages our NumberScript compilation process
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerManager {
	/** source file **/
	File sourceFile;
	/** our output compiled file **/
	File compiledFile;
	/** helps write out **/
	FileWriter fWriter;
	/** writes text out **/
	PrintWriter pWriter;
	/** computes our compilation **/
	CompilerEngine myEngine;

	/**
	 * Creates a new CompilerManager
	 * 
	 * @param sourceFileName
	 *            the NumberScript sourcefile we are compiling
	 */
	public CompilerManager(String sourceFileName) throws IOException {
		sourceFile = new File(sourceFileName);
		String compiledFileName = sourceFileName.substring(0, sourceFileName.indexOf('.'));
		compiledFileName = compiledFileName.concat(".c");
		compiledFile = new File(compiledFileName);
		fWriter = new FileWriter(compiledFile);
		pWriter = new PrintWriter(fWriter);
		myEngine = new CompilerEngine();
	}

	/**
	 * Compiles our source file to C
	 * 
	 * @throws FileNotFoundException
	 *             if we cannot find the input file
	 */
	public void compile() throws FileNotFoundException {
		printHeader();
		Scanner sc = new Scanner(sourceFile);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (myEngine.valid(line)) {
				pWriter.println(myEngine.compile(line));
			} else {
				throw new IllegalStateException("Invalid line\n");
			}
		}
		sc.close();
		printClose();
		pWriter.close();
	}

	/**
	 * Prints the header of our C source file
	 */
	private void printHeader() {
		pWriter.println("#include<stdio.h>");
		pWriter.println("");
		pWriter.println("int main(){");
	}

	/**
	 * Closes our C source file
	 */
	private void printClose() {
		pWriter.println("}");
	}

}
