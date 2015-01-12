package spell;

import java.io.IOException;

import spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {

	/**
	 * Give the dictionary file name as the first argument and the word to
	 * correct as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException,
			IOException {

		String dictionaryFileName = "";
		String inputWord = "";

		if(args.length == 0) {
			IOException e = new IOException();
			throw e;
		} else if (args.length == 1) {
			NoSimilarWordFoundException e = new NoSimilarWordFoundException();
			throw e;
		} else {
			dictionaryFileName = args[0];
			inputWord = args[1];
		}

		/**
		 * Create an instance of your corrector here
		 */
		SpellingCorrector corrector = new SpellingCorrector();

		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);

		System.out.println("Suggestion is: " + suggestion);

	}
}