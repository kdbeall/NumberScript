package edu.numberscript.compiler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests our CompilerEngine
 * 
 * @author Keng-Hui Beall
 *
 */
public class CompilerEngineTest {

	/** Compiling is stateful we need a compiler engine **/
	CompilerEngine myEngine;

	/**
	 * Sets up our tests
	 * 
	 * @throws Exception
	 *             if setup fails
	 */
	@Before
	public void setUp() throws Exception {
		myEngine = new CompilerEngine();
	}

	@Test
	public void testTranslate() {
		fail("Not yet implemented");
	}

	@Test
	public void testValid() {
		fail("Not yet implemented");
	}

}
