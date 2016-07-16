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

	/**
	 * Tests if statements are valid
	 */
	@Test
	public void testValid() {
		// valid assignment statements
		assertEquals(true, myEngine.valid("x=5.0;"));
		assertEquals(true, myEngine.valid("y=x+3.0;"));

		// valid comment statements
		assertEquals(true, myEngine.valid("#a"));
		assertEquals(true, myEngine.valid("#"));

		// valid print statements
		assertEquals(true, myEngine.valid("print(x);"));
		assertEquals(true, myEngine.valid("print(x+y);"));

		// invalid assignment statements
		assertEquals(false, myEngine.valid("z=(x;"));
		assertEquals(false, myEngine.valid("w=k;"));
		assertEquals(false, myEngine.valid("z=(1.0+2.0));"));
		assertEquals(false, myEngine.valid("z=1.0+;"));

		// invalid print statements
		assertEquals(false, myEngine.valid("print(z);"));
		assertEquals(false, myEngine.valid("print((z+);"));

	}

}
