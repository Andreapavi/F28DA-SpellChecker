# F28DA-SpellChecker
2nd year Computer Science coursework for F28DA. A spell checker using Hash Tables.

The main challenge of this coursework is to implement a Hash Table using only simple java arrays.
The following is a brief summary of how that is done:

This SpellChecker displays all the misspelled words and all the suggestions for each word, 
if a misspelled word has no suggestions, the program displays “No suggestions available”.
Also, to display results, my SpellChecker uses appropriate streams, as a standard it uses System.out,
but when it can’t find any suggestions it uses System.err.
Before checking the misspelled words, I’ve added them in a Hash Table to avoid duplicates and for more clarity.

The giveCode() method uses Polynomial Accumulation to return the Hash Code and my double Hashing is implemented in base 5. 

My design of Polynomial Accumulation: 
1. Declare a variable k and a variable pos, equals to 0 and 1.
2. Each time it goes trough the for loop, pos is multiplied by 33 and k is added to itself plus the ASCII code of the n character * pos.
3. The for loop run for each character of the String. 
4. At the end, the method returns k.
 
To add a word, First, it calculates the Hash code and the Double Hash code for a given word, 
then it tries to add the word at the cell with the given Hash Code, if the cell is empty or is DEFUNCT, then it adds the word.
If that’s not the case, it will compare the given word with the word already present in the array, if it’s equal my program raises an WException. 
If this is not the case either, using Double Hashing it finds the next probe to check. 
It will continue until the loop raises a WException or finds an available cell where to add the word.

The instance variable DEFUNCT (constant) is set to be an empty String, which replace a deleted word from the Hash Table. 
It works as a placeholder to keep the correct functionality of the Hash Table.

The resizing of the Hash Code is done by:
1. Creating an iterator with every word on the current HashTable;
2. Creating a new array that has the size of the next prime number at least twice larger than the current array size;
3. Rehashing each word from the old array and insert them in the new array.

primeNum() checks a given number and return True if the number is prime and False is it’s not.
