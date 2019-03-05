package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;

public class LListWords implements IWords {

	private LinkedList<String> W;
	
	public LListWords() {
		W = new LinkedList<String>();
	}
	
	@Override
	public void addWord(String word) throws WException {
		
		for (String w : W) {
			if(w.equals(word)) {
				throw new WException(word + " already present.");
			}
		} 
		W.add(word);
//		System.out.println( "'" + word + "' added.");
	}

	@Override
	public void delWord(String word) throws WException {
		
		for (String w : W) {
			if(w.equals(word)) {
				W.remove(w);
				
				System.out.println("'" + w + " removed.");
				return;
			}
		}
		throw new WException("Word not found.");
	}

	@Override
	public boolean wordExists(String word) {

		for (String w : W) {
			if (w.equals(word)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int nbWords() {
		return W.size();
	}

	@Override
	public Iterator<String> allWords() {
		
		return W.iterator();
	}

}
