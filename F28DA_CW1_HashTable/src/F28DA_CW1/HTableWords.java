
package F28DA_CW1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.SliderUI;


public class HTableWords implements IWords, IHashing, IMonitor{

	private String[] hTable; 
	private int size;
	private float maxLF;
	private int nWords;
	//Defunct is an empty String that works as a placeholder when a word is deleted from the hash table
	private final String DEFUNCT = ""; 
	private int totalProbes;
	private int totalOperations;
	
	public HTableWords() {
		size = 7;
		hTable = new String[size];
		nWords = 0;
		maxLF = 0.5f;
		totalProbes = 0;
		totalOperations = 0;
	}
	
	public HTableWords(float newMaxLF) {
		size = 7;
		hTable = new String[size];
		nWords = 0;
		maxLF = newMaxLF;
		totalProbes = 0;
		totalOperations = 0;
	}
	
	@Override
	public float maxLoadFactor() {
		return maxLF;
	}

	@Override
	public float loadFactor() {
		return (float)nWords/size;
	}

	@Override
	public float averageProbes() {
		return (float)totalProbes/totalOperations;
	}

	@Override
	public int giveCode(String s) { //Uses Polynomial accumulation
		s.hashCode();
		int k = 0;
		int pos = 1;
		for (int i = 0; i < s.length(); i++) {
			k += Integer.valueOf(s.charAt(i)) * pos;
			pos *= 33;
			}
		return Math.abs(k); //Returns the absolute value of the polynomial accumulation result
	}

	@Override
	public void addWord(String word) throws WException {
		// If current load factor is bigger than the maximum allowed load factor, call resize().
		if (loadFactor() > maxLoadFactor()) {
			resize();
		}
		int hCode = giveCode(word) % size; //Give the HashCode using polynomial accumulation and compression function. 
		int dHash = 5 - giveCode(word) % 5; //Give DoubleHashing value in base 5
		totalOperations++; //Increases the operation count by 1
		totalProbes++; //Increases the number of probes by 1
		
		//Checks the cell pointed by hashCode, if hCode cell of W is empty or is DEFUNCT, adds the word 
		while (hTable[hCode] != null && !hTable[hCode].equals(DEFUNCT)) {
			if (hTable[hCode].equals(word)) { //If given word is equal, raises a WException
				throw new WException(word + " already present.");
			}
			hCode = (hCode + dHash) % size;
			totalProbes++; //Increase the number of probes each time a cell is visited
		}
		
		//Add the word in array[hCode], increase size of nWords.
		hTable[hCode] = word;
		nWords++;
		
//		System.out.println("The average visited probes are: " + averageProbes());
//		System.out.println(nWords + ". " + word + " successfully added.");
	}

	@Override
	public void delWord(String word) throws WException {
		int hCode = giveCode(word) % size; //Gives the HashCode using polynomial accumulation and compression function. 
		int dHash = 5 - (giveCode(word) % 5); //Gives DoubleHashing value in base 5
		totalOperations++; //Increases the operation count by 1
		totalProbes++; //Increases the number of probes by 1
		
		//Checks the cell pointed by hashCode, if hCode cell of W is empty, raises a WException 
		while (hTable[hCode] != null) {
			if (hTable[hCode].equals(word)) { //If given word is equal, deletes word
				hTable[hCode] = DEFUNCT; //Replaces word with DEFUNCT
				nWords--; // Decreases number count by 1.
				return;
			}
			hCode = (hCode + dHash) % size;
			totalProbes++; //Increase the number of probes each time a cell is visited
		}
		throw new WException(word + " not present.");
	}

	@Override
	public boolean wordExists(String word) {
		int hCode = giveCode(word) % size; //Gives the HashCode using polynomial accumulation and compression function. 
		int dHash = 5 - (giveCode(word) % 5); //Gives DoubleHashing value in base 5
		totalOperations++; //Increases the operation count by 1
		totalProbes++; //Increases the number of probes by 1
		
		//Checks the cell pointed by hashCode, if hCode cell of W is empty, returns false.
		while (hTable[hCode] != null) {
			
			if (hTable[hCode].equals(word)) { //if given word is equal, returns true.
				return true;
			}
			hCode = (hCode + dHash) % size;
			totalProbes++; //Increase the number of probes each time a cell is visited
		}
		return false;
	}

	@Override
	public int nbWords() {
		return nWords;
	}

	@Override
	public Iterator<String> allWords() {
		ArrayList<String> wordList = new ArrayList(); 
		for (String s : hTable) {
			if(s != null && !s.equals(DEFUNCT)) { //Leaves out null and DEFUNCT from the iterator
				wordList.add(s);
			}
		}
		return wordList.iterator();
	}
	
	private void resize () {
		//Increases array size to the next prime number at least twice larger than the current array size
		size *= 2;
		while(!primeNum(size)) {
			size++;
		}
		
		Iterator<String> hTableIT = allWords();
		String[] newHTable = new String[size];
		nWords = 0;
		hTable = newHTable; //Overwrites the old array with the new bigger array
		
		//Goes through each word from the old array and adds it to the new one
		while(hTableIT.hasNext()) {
			try {
				addWord(hTableIT.next());
			} catch (WException e) {}
		}
//		System.out.println("Hash Table resized to " + size);
	}
	
	private boolean primeNum (int num) { //Checks if the given number if prime or not
		for(int i = 2; i < num; i++) {
			if((num%i) == 0) {
				return false;
			}
		}
		return true;
	}
}
