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
	File sourceFile;
	File compiledFile;
	FileWriter fWriter;
	PrintWriter pWriter;
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
		pWriter = new PrintWriter(pWriter);
		myEngine = new CompilerEngine();
	}

	public void compile() throws FileNotFoundException {
		printHeader();
		Scanner sc = new Scanner(sourceFile);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			pWriter.println(myEngine.compile(line));
		}
		sc.close();
		printClose();
		pWriter.close();
	}

	private void printHeader() {
		pWriter.println("#include<stdio.h>");
		pWriter.println("");
		pWriter.println("int main(){");
	}

	private void printClose() {
		pWriter.println("}");
	}

}
