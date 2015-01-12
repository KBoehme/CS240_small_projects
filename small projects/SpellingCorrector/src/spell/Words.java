/**
 * 
 */
package spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Kevin
 * 
 */
public class Words implements Trie {

	public WordNode[] letters = new WordNode[26];
	public ArrayList<String> stringwords;
	public TreeMap<String, Integer> WordCountMap = new TreeMap<String, Integer>();
	public static int WordCount;
	public static int NodeCount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((WordCountMap == null) ? 0 : WordCountMap.hashCode());
		result = prime * result + Arrays.hashCode(letters);
		result = prime * result
				+ ((stringwords == null) ? 0 : stringwords.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		Trie t = (Trie) o;
		for (String s : stringwords) {
			Node check1 = t.find(s);
			Node check2 = this.find(s);
			if (t != null && this != null) {
				if (t.getNodeCount() != this.getNodeCount())
					return false;
				else if (t.getWordCount() != this.getWordCount())
					return false;

				if (check1 != null && check2 != null) {
					if (check1.getValue() != check2.getValue()) {
						return false;
					}
				} else if (check1 == null && check2 == null) {

				} else {
					return false;
				}
			}
		}
		return true;
	}

	public Words() {
		// TODO Auto-generated constructor stub
		stringwords = new ArrayList<String>();
		NodeCount = 1;
		// WordCountMap = new Map<String,Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spell.Trie#add(java.lang.String)
	 */
	@Override
	public void add(String word) {
		stringwords.add(word);
		// TODO Auto-generated method stub
		// recieve the string of words and populate trie.
		char c = word.charAt(0);
		String newstring = word.substring(1);
		if (letters[c - 'a'] == null) { // we dont have the node.
			WordNode newnode = new WordNode(c, word);
			letters[c - 'a'] = newnode;
			Words.incNodeCount();
			if (!newstring.isEmpty()) { // we are not done with the word.
				letters[c - 'a'].addNode(newstring, word);
			} else {
				Words.incWordCount();
				letters[c - 'a'].incFreq();
			}
		} else { // we already have the node..

			if (!newstring.isEmpty()) {
				// we are done now and we can increment the counter.
				letters[c - 'a'].addNode(newstring, word);
			} else { // already have first node and its empty. we are done.
				letters[c - 'a'].incFreq();
				Words.incWordCount();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spell.Trie#find(java.lang.String)
	 */
	@Override
	public Node find(String word) {
		// TODO Auto-generated method stub
		// this finds the given word in the tree..
		String wordlowercase = word.toLowerCase();
		if (wordlowercase.isEmpty())
			return null;
		char c = wordlowercase.charAt(0);
		String newstring = wordlowercase.substring(1);

		if (letters[c - 'a'] == null) {
			return null;
		} else if (newstring.isEmpty() && letters[c - 'a'].getValue() > 0) {
			return letters[c - 'a'];
		} else {
			return letters[c - 'a'].findNode(newstring);
		}
	}

	static void incWordCount() {
		WordCount++;
	}

	static void incNodeCount() {
		NodeCount++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spell.Trie#getWordCount()
	 */
	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return WordCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spell.Trie#getNodeCount()
	 */
	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return NodeCount;
	}

	public String toString() {

		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		// StringBuilder s = new StringBuilder();
//		 for(int i = 0; i < alphabet.length; i++) {
//			 if(letters[i] == )
//		 }

		for (String word : stringwords) {
			WordNode no = (WordNode) this.find(word);
			if (no != null)
				WordCountMap.put(word, no.getValue());
		}
		StringBuilder bigolstring = new StringBuilder();
		for (Map.Entry<String, Integer> entry : WordCountMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			bigolstring.append(key + " " + value + '\n');
		}

		String s = bigolstring.toString();
		return s;
	}

	public String editDistance1(String word) {

		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };

		HashMap<String, Integer> mylist = new HashMap<String, Integer>();
		ArrayList<String> finallist = new ArrayList<String>();
		// Delete
		for (int i = 0; i < word.length(); i++) {
			String str = "";
			if (i == 0) {
				str = word.substring(1, word.length());
			} else if (i == word.length() - 1) {
				str = word.substring(0, word.length() - 1);
			} else {
				str = word.substring(0, i)
						+ word.substring(i + 1, word.length());
			}
//			System.out.println(str);

			// lets look for it.
			WordNode check = null;
			if (str.length() > 0) {
				check = (WordNode) this.find(str);
				if (check != null) {
					mylist.put(str, check.getValue());
				} else {
					mylist.put(str, 0);
				}
			}
		}

		// transverse
		for (int i = 0; i < word.length() - 1; i++) {
			String str = "";
			if (i == 0) {
				str = word.substring(i + 1, i + 2) + word.substring(i, i + 1)
						+ word.substring(i + 2, word.length());
			} else if (i == word.length() - 1) {
				str = word.substring(0, i - 1) + word.substring(i, i + 1)
						+ word.substring(i - 1, i);
			} else {
				str = word.substring(0, i) + word.substring(i + 1, i + 2)
						+ word.substring(i, i + 1)
						+ word.substring(i + 2, word.length());
			}

			// lets look for it.
			WordNode check = (WordNode) this.find(str);
			if (check != null) {
				mylist.put(str, check.getValue());
			} else {
				mylist.put(str, 0);
			}
		}

		// alteration
		for (int i = 0; i < word.length(); i++) {
			for (int k = 0; k < alphabet.length; k++) {
				String str = "";
				if (i == 0) {
					str = alphabet[k] + word.substring(1, word.length());
				} else if (i == word.length() - 1) {
					str = word.substring(0, word.length() - 1) + alphabet[k];
				} else {
					str = word.substring(0, i) + alphabet[k]
							+ word.substring(i + 1, word.length());
				}
				// lets look for it.

				WordNode check = (WordNode) this.find(str);
				if (check != null) {
					mylist.put(str, check.getValue());
				} else {
					mylist.put(str, 0);
				}
			}
		}

		// insertion
		for (int i = 0; i <= word.length(); i++) {
			for (int k = 0; k < alphabet.length; k++) {
				String str = "";
				if (i == 0) {
					str = alphabet[k] + word;
				} else {
					str = word.substring(0, i) + alphabet[k]
							+ word.substring(i, word.length());
				}
				// lets look for it.
				WordNode check = (WordNode) this.find(str);
				if (check != null) {
					mylist.put(str, check.getValue());
				} else {
					mylist.put(str, 0);
				}
			}
		}
		// ok we have a big ol hashmap...
		int bestvalue = 0;
		for (Entry<String, Integer> item : mylist.entrySet()) {
			String key = item.getKey();
			//System.out.println(key);
			int value = item.getValue();
			if (value > bestvalue) {
				bestvalue = value;
			}
		}

		for (Entry<String, Integer> item : mylist.entrySet()) {
			String key = item.getKey();
			int value = item.getValue();
			if (value == bestvalue) {
				finallist.add(key);
			}
		}
		if (finallist.isEmpty() || bestvalue == 0) { // well we dont have any
														// matches at this
			// point...
			// run editdistance2 on every word in the hashmap.
			HashMap<String, Integer> bigollist = new HashMap<String, Integer>();

			bigollist = editDistance2(mylist);

			ArrayList<String> finallist1 = new ArrayList<String>();

			// ok we have a big ol hashmap...
			int bestvalue1 = 0;
			for (Entry<String, Integer> item : bigollist.entrySet()) {
				String key = item.getKey();
				int value = item.getValue();
				if (value > bestvalue1) {
					bestvalue1 = value;
				}
			}
			for (Entry<String, Integer> item : bigollist.entrySet()) {
				String key = item.getKey();


				int value = item.getValue();
				if (value == bestvalue1) {
					finallist1.add(key);
				}
			}
			if (finallist1.isEmpty() || bestvalue1 == 0) {
				return null;
			} else {
				Collections.sort(finallist1);
				return finallist1.get(0);
			}

		} else {
			Collections.sort(finallist);
			return finallist.get(0);
		}
	}

	public HashMap<String, Integer> editDistance2(HashMap<String, Integer> mylist) {

		HashMap<String, Integer> biglist = new HashMap<String, Integer>();
		//System.out.println("hey");
		for (Entry<String, Integer> item : mylist.entrySet()) {
			String word = item.getKey();
			// Delete
			for (int i = 0; i < word.length(); i++) {
				String str = "";
				if (i == 0) {
					str = word.substring(1, word.length());
				} else if (i == word.length() - 1) {
					str = word.substring(0, word.length() - 1);
				} else {
					str = word.substring(0, i)
							+ word.substring(i + 1, word.length());
				}

				//System.out.println(str);
				// lets look for it.
				WordNode check = (WordNode) this.find(str);
				if (check != null) {
					biglist.put(str, check.getValue());
				} else {
					biglist.put(str, 0);
				}
			}

			// transverse
			for (int i = 0; i < word.length() - 1; i++) {
				String str = "";
				if (i == 0) {
					str = word.substring(i + 1, i + 2)
							+ word.substring(i, i + 1)
							+ word.substring(i + 2, word.length());
				} else if (i == word.length() - 1) {
					str = word.substring(0, i - 1) + word.substring(i, i + 1)
							+ word.substring(i - 1, i);
				} else {
					str = word.substring(0, i) + word.substring(i + 1, i + 2)
							+ word.substring(i, i + 1)
							+ word.substring(i + 2, word.length());
				}

				// lets look for it.
				WordNode check = (WordNode) this.find(str);
				if (check != null) {
					biglist.put(str, check.getValue());
				} else {
					biglist.put(str, 0);
				}
			}

			char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
					'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
					'v', 'w', 'x', 'y', 'z' };
			// alteration
			for (int i = 0; i < word.length(); i++) {

				for (int k = 0; k < alphabet.length; k++) {
					String str = "";
					if (i == 0) {
						str = alphabet[k] + word.substring(1, word.length());
					} else if (i == word.length() - 1) {
						str = word.substring(0, word.length() - 1)
								+ alphabet[k];
					} else {
						str = word.substring(0, i) + alphabet[k]
								+ word.substring(i + 1, word.length());
					}

					// lets look for it.
					WordNode check = (WordNode) this.find(str);
					if (check != null) {
						biglist.put(str, check.getValue());
					} else {
						biglist.put(str, 0);
					}
				}
			}

			// insertion
			for (int i = 0; i <= word.length(); i++) {
				for (int k = 0; k < alphabet.length; k++) {
					String str = "";
					if (i == 0) {
						str = alphabet[k] + word;
					} else {
						str = word.substring(0, i) + alphabet[k]
								+ word.substring(i, word.length());
					}
					// lets look for it.
					WordNode check = (WordNode) this.find(str);
					if (check != null) {
						biglist.put(str, check.getValue());
					} else {
						biglist.put(str, 0);
					}
				}
			}
		}
		return biglist;
	}

}
