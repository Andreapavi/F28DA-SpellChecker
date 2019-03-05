package F28DA_CW1;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModificationsProvidedTest {

	@Test
	public void testOmission() {

		IWords dict = new HTableWords(); //LLinkedList class changed to HTableWords class.
		try {
			dict.addWord("cats");
			dict.addWord("like");
			dict.addWord("on");
			dict.addWord("of");
			dict.addWord("to");
			dict.addWord("play");
		} catch (WException e) {
			fail("Error with linked list implementation");
		}
		IWords sugg = SpellChecker.suggestions("catts", dict);
		assertTrue(sugg.wordExists("cats"));
	}

}
