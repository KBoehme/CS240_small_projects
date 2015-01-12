/**
 * 
 */
package spell;

import spell.Trie.Node;

/**
 * @author Kevin
 * 
 */
public class WordNode implements Trie.Node {

	WordNode[] letterNodes;
	String myWord;
	char myletter;
	public int freq;

	public WordNode(char let, String word) {
		myWord = word;
		myletter = let;
		letterNodes = new WordNode[26];
	}

	public void addNode(String str, String word) {
		// recieve the string of words and populate trie.

		char c = str.charAt(0);
		String newstring = str.substring(1);
		if (letterNodes[c - 'a'] == null) { // we dont have the node.
			WordNode newnode = new WordNode(c, word);
			letterNodes[c - 'a'] = newnode;
			Words.incNodeCount();
			if (!newstring.isEmpty()) { // we are not done with the word.
				letterNodes[c - 'a'].addNode(newstring, word);
			} else {
				// here we are done with the word.
				letterNodes[c - 'a'].incFreq();
				Words.incWordCount();
			}
		} else { // we already have the node..

			if (!newstring.isEmpty()) {
				// we are done now and we can increment the counter.
				letterNodes[c - 'a'].addNode(newstring, word);
			} else { // already have first node and its empty. we are done.
				letterNodes[c - 'a'].incFreq();
				Words.incWordCount();
			}
		}
	}

	public WordNode findNode(String str) {
		
		if(str.isEmpty())
			return null;
		char c = str.charAt(0);
		String newstring = str.substring(1);
		if (letterNodes[c - 'a'] == null) {
			return null;
		} else if (newstring.isEmpty() && letterNodes[c - 'a'].getValue() > 0) {
			return letterNodes[c-'a'];
		} else if (newstring.isEmpty())  {
			return null;
		} else {
			return letterNodes[c - 'a'].findNode(newstring);
		}
	}

	public void incFreq() {
		freq++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spell.Trie.Node#getValue()
	 */
	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return freq;
	}
	
	public String toString() {
		return myWord;
	}

}
