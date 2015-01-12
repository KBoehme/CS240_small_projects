/**
 * 
 */
package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Kevin
 *
 */
public class SpellingCorrector implements SpellCorrector {


	private Words Dict = new Words();
	
	public SpellingCorrector() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see spell.SpellCorrector#useDictionary(java.lang.String)
	 */
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// TODO Auto-generated method stub
		File dict = new File(dictionaryFileName);
		Scanner s = new Scanner(dict);
		while(s.hasNext()) {
			String tok = s.next();
			if(!tok.matches("([A-Z][a-z])+")) {
				//we have a word to add to our Trie.
				Dict.add(tok.toLowerCase());
			}
			else {
			}
		}
		Dict.toString();
	}

	/* (non-Javadoc)
	 * @see spell.SpellCorrector#suggestSimilarWord(java.lang.String)
	 */
	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		// TODO Auto-generated method stub
		if(inputWord.isEmpty()) {
			NoSimilarWordFoundException e = new NoSimilarWordFoundException();
			throw e;
		}
		WordNode see = (WordNode) Dict.find(inputWord.toLowerCase());
		if(see != null && see.getValue() > 0) { //we found the word in the trie.
			return inputWord.toLowerCase();
		}
		else {
			//lets run edit distance on it.
			String anwser = Dict.editDistance1(inputWord.toLowerCase());
			if(anwser == null) {
				NoSimilarWordFoundException e = new NoSimilarWordFoundException();
				throw e;
			}
			else
				return anwser;
		}
	}
}
