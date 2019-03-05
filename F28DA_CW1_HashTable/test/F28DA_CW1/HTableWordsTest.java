package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

public class HTableWordsTest {

	/** Test 1 - Adds 6 names to dictionary, asserts if 3 existent names returns true  */
	@Test
	public void test1() {
		HTableWords ht = new HTableWords();
		String w1 = "Andrea";
		String w2 = "Marco";
		String w3 = "Luca";
		String w4 = "Mario";
		String w5 = "Antonio";
		String w6 = "Francesco";
		try {
			ht.addWord(w1);
			ht.addWord(w2);
			ht.addWord(w3);
			ht.addWord(w4);
			ht.addWord(w5);
		} catch (WException e) {
			fail(e.getMessage());
		}
		assertTrue(ht.wordExists(w1));
		assertTrue(ht.wordExists(w2));
		assertFalse(ht.wordExists(w6));
	}	
	
	/** Test 2 - Adds 1000 words to the dictionary, remove 200 value, then check if all other value exist  */
	@Test
	public void test2() {
		HTableWords ht = new HTableWords();
		String w = "word";
		
		//Adds 1000 words to the dictionary
		for (int i = 0 ; i < 1000; i++) {
			try {
				ht.addWord(w+i);
			} catch (WException e) {
				fail(e.getMessage());
			}
		}
		
		//Delete 200 words from the dictionary
		for (int i = 0; i < 200; i++) {
			try {
				ht.delWord(w+i);
			} catch(WException e) {
				fail(e.getMessage());
			}
		}
		
		//Checks if other 800 words exist
		for (int i = 200; i< 1000; i++) {
			assertTrue(ht.wordExists(w+i));
		}
	}
	
}
