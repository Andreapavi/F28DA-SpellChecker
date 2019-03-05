package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class ModificationsTest {

	/** Test 1 - Asserts if suggestions returns a correct array of misspelled words  */
	@Test
	public void test1() {
		IWords dict = new HTableWords(); //Changed LLinkedList class to HTableWords class.
		String [] arr1 = new String[5];
		String [] arr2 = {"bat","cat","fat","hat","rat"};
		try {
			dict.addWord("bat");
			dict.addWord("cat");
			dict.addWord("hat");
			dict.addWord("rat");
			dict.addWord("fat");
			dict.addWord("gap");
			dict.addWord("tap");
			dict.addWord("cap");
			dict.addWord("lap");
		} catch (WException e) {
			fail();
		}
		IWords sugg = SpellChecker.suggestions("kat", dict);
		
		Iterator<String> it = sugg.allWords();
		int i = 0;
		while (it.hasNext()) {
			arr1[i] = it.next();
			i++;
		}
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		
		assertArrayEquals(arr1, arr2);
	}
	
	
	/** Test 2 - Asserts if the Iterator returned contains only 1 valid value. */
	@Test
	public void test2() {
		IWords dict = new HTableWords(); 
		
		try {
			dict.addWord("take");
			dict.addWord("lake");
			dict.addWord("make");
			dict.addWord("wake");
			dict.addWord("fake");
			
		} catch (WException e) {
			fail();
		}
		
		IWords sugg = SpellChecker.suggestions("lkae", dict);
		Iterator it = sugg.allWords();
		assertEquals(it.next(),"lake");
		
		try {
			it.next(); //Iterator should contain only 1 value, exception should be raised.
			fail(); //Unreachable code 
		} catch(Exception e) {
			assertTrue("Exception raised correctly", true);
		}
	}

}
