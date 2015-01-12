/**
 * 
 */
package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Kevin
 * 
 */
public class EvilHangman implements EvilHangmanGame {

	private ArrayList<String> Words_dict = new ArrayList<String>();
	private Set<String> current_words = new HashSet<String>();
	private ArrayList<ArrayList<String>> wordgroups = new ArrayList<ArrayList<String>>();
	private Map<String, String> words_map = new HashMap<String, String>();

	int word_length;
	private ArrayList<Character> guessed_letters = new ArrayList<Character>();

	public EvilHangman() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see evilhangman.EvilHangmanGame#startGame(java.io.File, int)
	 */
	@Override
	public void startGame(File dictionary, int wordLength) {
		// TODO Auto-generated method stub
		word_length = wordLength;
		try {
			Scanner s = new Scanner(dictionary);
			while (s.hasNext()) {
				String str = s.nextLine();
				Words_dict.add(str);
				if (str.length() == wordLength) {
					current_words.add(str);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see evilhangman.EvilHangmanGame#makeGuess(char)
	 */
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		if (guessed_letters.contains(guess)) {
			GuessAlreadyMadeException e = new GuessAlreadyMadeException();
			throw e;
		} else {
			guessed_letters.add(guess);
			// OK we have the letter now we need to implement the "evil"
			// algorithm!
			return generateWordGroups(guess);
		}
	}

	public Set<String> generateWordGroups(
			char guess) {
		words_map.clear();
		// OK we have the letter now we need to implement the "evil" algorithm!
		/*
		 * Build a set of possible word groups. i.e. 001 100 010 111 etc...
		 */
		Set<String> setlist = new HashSet<String>();
		for (String s : current_words) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < word_length; i++) {
				if (s.charAt(i) == guess) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
			String keyword = sb.toString();
			words_map.put(s, keyword);
			setlist.add(keyword);
		}
		
		int largest_num = 0;
		for(String s: setlist) {
			int count = 0;
			for (Map.Entry<String, String> item : words_map.entrySet()) {
				String value = item.getValue();
				if(s.equals(value)) {
					count++;
				}
			}
			if(count > largest_num) {
				largest_num = count;
			}
		}
		
		ArrayList<String> BiggestGroup = new ArrayList<String>();
		for(String s: setlist) { 
			int count = 0;
			for (Map.Entry<String, String> item : words_map.entrySet()) {
				String value = item.getValue();
				if(s.equals(value)) {
					count++;
				}
			}
			if(count == largest_num) {
				BiggestGroup.add(s);
			}
		}
		//OK we have teh strings that are the largest..
		//if there is only one than that is what we need.

		if (BiggestGroup.size() == 1) {
			current_words.clear();
			for (Map.Entry<String, String> item : words_map.entrySet()) {
				String key = item.getKey();
				String value = item.getValue();
				if(BiggestGroup.get(0).equals(value)) {
					current_words.add(key);
				}
			}

			return current_words;
		}
		else {
			// We have more than one with the most amount of words.
			// We need to do the checker now to find which one we need to keep
			// around.
			//BiggestGroup has a list of keys that we will use.

			int min_num = 1000000000;
			for (String s : BiggestGroup) {
				// We have the final list lets.. get them out based on how many
				// 1's they have.
				if (countLetters(s) < min_num) {
					min_num = countLetters(s);
				}
			}
			ArrayList<String> finalcompare = new ArrayList<String>();
			// ok we know how many has the least number of letter. lets use that
			// to compare.
			for (String s : BiggestGroup) {
				if (countLetters(s) == min_num) {
					finalcompare.add(s);
				}
			}
			// ok we have our final list.. lets compare their integer values and
			// save the one we need.
			String final_string = "111111111111111111111111111111111111111111111111";
			for (String s : finalcompare) {
				if (Double.valueOf(s) < Double.valueOf(final_string)) {
					final_string = s;
				}
			}
			ArrayList<String> thisisit = new ArrayList<String>();
			// OK we have our final string we need to use this to get out our
			// group of words.
			current_words.clear();
			for (Map.Entry<String, String> item : words_map.entrySet()) {
				String key = item.getKey();
				String value = item.getValue();
				if (value.equals(final_string)) {
					current_words.add(key);
				}
			}

			return current_words;
		}
	}

	public int countLetters(String str) {
		int charCount = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1')
				charCount++;
		}
		return charCount;
	}

}
