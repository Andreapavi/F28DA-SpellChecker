package F28DA_CW1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/** Main class for the Spell-Checker program */
public class SpellChecker {


	/** Suggests word modifications for a given word and a given word dictionary. */
	static public IWords suggestions(String word, IWords dict) {
		
		// TO IMPLEMENT
		HTableWords allSuggestions = new HTableWords();
		
//		Try to substitute each letter to find a word in the dictionary
		char[] wordChars = word.toCharArray();
		
		String newWord;
		for(int i = 0; i < word.length(); i++) {
			wordChars = word.toCharArray();
			for (char alphabet = 'a';  alphabet <= 'z'; alphabet++) {
				wordChars[i] = alphabet;
				newWord = String.valueOf(wordChars);
				if(dict.wordExists(newWord)) {
					try {
						allSuggestions.addWord(newWord);
					} catch (WException e) {}
				}
			}
		}
		
//		Try to omit each letter to find a word in the dictionary
		for(int i = 0; i < word.length(); i++) {
		    newWord = word.substring(0, i) + word.substring(i+1, word.length());
		    if(dict.wordExists(newWord)) {
		    	try {
		    		allSuggestions.addWord(newWord);
		    	} catch (WException e) {}
		    }
		}
		
//		Try to add a letter in each position to find a word in the dictionary
		for (int i = 0; i <= word.length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {	
				newWord = word.substring(0, i) + c + word.substring(i, word.length());
				if(dict.wordExists(newWord)) {
					try {
						allSuggestions.addWord(newWord);
					} catch (WException e) {}
				}
			}
		 }
		
//		Try to reverse every 2 adjacent letters to find a word in the dictionary 
		wordChars = word.toCharArray();
		char tempChar;
		for (int i = 0; i < word.length()-1; i++) {
			wordChars = word.toCharArray();
			tempChar = wordChars[i];
			wordChars[i] = wordChars[i+1];
			wordChars[i+1] = tempChar;
			newWord = String.valueOf(wordChars);
			
			if(dict.wordExists(newWord)) {
				try {
					allSuggestions.addWord(newWord);
				} catch (WException e) {}	
			}
		}
		return allSuggestions;
		//^^^IMPLEMENTED^^^
	}
	
	
	
	/**
	 * Main method for the Spell-Checker program. The program takes two input
	 * filenames in the command line: the word dictionary file and the file
	 * containing the words to spell-check. .
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: SpellChecker dictionaryFile.txt inputFile.txt ");
			System.exit(1);
		}

		try {
			
			BufferedInputStream dict, file;
			
			dict = new BufferedInputStream(new FileInputStream(args[0]));
			
			//TO IMPLEMENT
			
			long start = System.currentTimeMillis();
			
			HTableWords wordList = new HTableWords();
			FileWordRead readWords = new FileWordRead(dict);
			
			while(readWords.hasNextWord()) {
				try {
					wordList.addWord(readWords.nextWord());
				} catch (WException e) {
//					System.err.println(e.getMessage());
				}
			}
			
			long time = System.currentTimeMillis() - start;
			float timeSeconds = (float) time / 1000;
			System.out.println( args[0] + ", " + args[1] + ": Added " + wordList.nbWords() + " words in " + timeSeconds + " seconds. \n");
			
			//^^^IMPLEMENTED^^^
			
			dict.close();
			
			//TO IMPLEMENT
			
			file = new BufferedInputStream(new FileInputStream(args[1]));
			
			//Changing file to read
			readWords = new FileWordRead(file);
			IWords wrongWords = new HTableWords();
			String word;
			while(readWords.hasNextWord()) {
				word = readWords.nextWord();
				if (!wordList.wordExists(word)) {
					try {
						wrongWords.addWord(word);
					} catch(WException e) {
//						System.err.println(e.getMessage());
					}
				}
			}
			
			//Create an Iterator that goes over every misspelled word
			Iterator<String> itWrongWords = wrongWords.allWords();
			//Create an HashTable that will contains all the suggested words
			IWords S = new HTableWords();
			String misWord;
			System.out.println("List of misspelled words with given suggestions: \n");
			while(itWrongWords.hasNext()) { //Goes through each misspelled word
				misWord = itWrongWords.next();
				S = suggestions(misWord, wordList);
				Iterator<String> itS = S.allWords(); //Create an iterator of all the suggestions
				
				if(itS.hasNext()) { //This checks if the given word has any suggestions
					System.out.print(misWord + " => ");
				} else {
					System.err.println(misWord + ": No suggestions available. ");
				}
				
				
				while (itS.hasNext()) { //Print out every suggestions for each word
					System.out.print(itS.next());
					if(itS.hasNext()) { //If it's not the last word, print out a comma. 
						System.out.print(", ");
					} 
					else { //If it is the last word, print out a period and go on a new line.
						System.out.print(". \n");
					}
				}
			}

			//^^^IMPLEMENTED^^^
			
			file.close();

		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.err.println("Missing input file, check your filenames");
			System.exit(1);
		}
	}

}
