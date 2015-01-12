/**
 * 
 */
package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Kevin
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int num_guesses = 0;
		// TODO Auto-generated method stub
		//String dictionary;
		File dictionary = null;
		int wordLength = 0;
		int guesses = 0;
		String partial_word = "";
		ArrayList<Character> word = new ArrayList<Character>();
		Set<String> Possible_Words = new HashSet<String>();
		boolean wins = false;
		
		try {
			//dictionary = args[0];
			dictionary = new File(args[0]);
			wordLength = Integer.valueOf(args[1]);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < wordLength; i++) {
				sb.append("-");
			}
			partial_word = sb.toString();
			guesses = Integer.valueOf(args[2]);
		} catch (Exception e) {
			System.out.println("USAGE: java [your main class name] dictionary wordLength guesses\n\ndictionary is the path to a text file with whitespace seperated words (no numbers, punctuactions, etc.)\nwordLength is an integer > or = 2.\nguesses is an integer > of = 1.");
		}
		
		EvilHangman game = new EvilHangman();
		ArrayList<Character> guessed_letters = new ArrayList<Character>();
		game.startGame(dictionary, wordLength);
		while(guesses > 0) {
			System.out.println("You have " + (guesses-num_guesses) + " guesses left.");
			StringBuilder str = new StringBuilder();
			Collections.sort(guessed_letters);
			if(!guessed_letters.isEmpty()) {
				for(char c : guessed_letters) {
					str.append(c);
					str.append(" ");
				}
			}
			System.out.println("Used Letters: " + str);
			System.out.println("Word: " + partial_word);
			boolean again = true;
			String guess = "";
			while(again) {
				System.out.print("Enter guess: ");
				Scanner s = new Scanner(System.in);
				guess = s.nextLine();

				if(guess.length() > 1 || guess.length() == 0) {
					again = true;
				}
				else if(Character.isLetter(guess.charAt(0))) {
					if(guessed_letters.contains(guess.charAt(0))) {
						System.out.println("You already guessed that letter.");
						again = true;

					} else {
						guessed_letters.add(guess.charAt(0));
						again = false;
					}
				}
			}
			try {
				Possible_Words = game.makeGuess(guess.charAt(0));
				String test = (String) Possible_Words.toArray()[0];
				StringBuilder sb = new StringBuilder();
				int count = 0;
				int count_for_win = 0;
				for (int i = 0; i < test.length(); i++) {
					if (test.charAt(i) == guess.charAt(0)) {
						sb.append(guess.charAt(0));
						count++;
						count_for_win++;
					} else if(partial_word.charAt(i) != '-') {
						sb.append(partial_word.charAt(i));
						count_for_win++;
					} else {
						sb.append("-");
					}
				}
				partial_word = sb.toString();
				if(count == 0) {
					guesses--;
					if(guesses != 0)
						System.out.println("Sorry, there are no " + guess + "'s");
					else
						break;
				} 
				else {
//					String num = 
					System.out.println("Yes, there is " + count + " "  + guess);
					if(count_for_win == wordLength) {
						guesses = 0;
						System.out.println("You Win!");
						wins = true;
					}
				}
			} catch (GuessAlreadyMadeException e) {
				e.printStackTrace();
			}
		}
		if(wins) {
			System.out.println("The word was: " + partial_word);
		} else {
			//Ahh you lost.
			int size = Possible_Words.size();
			int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
			int i = 0;
			String Lolword = "";
			for(Object obj : Possible_Words) {
			    if (i == item)
			    	Lolword = obj.toString();
			    i = i + 1;
			}
			System.out.println("You lose!");
			System.out.println("The word was: " + Lolword);
		}
	}
}
