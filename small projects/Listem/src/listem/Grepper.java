/**
 * 
 */
package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin
 * 
 */
public class Grepper extends Base implements Grep {

	
	 Map<File, List<String>> myMap;
	/*
	 * (non-Javadoc)
	 * 
	 * @see listem.Grep#grep(java.io.File, java.lang.String, java.lang.String,
	 * boolean)
	 */
	public Grepper() {
		myMap = new TreeMap<File, List<String>>();
	}

	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {

		myMap.clear();
		searchdirectories(directory, fileSelectionPattern,
				substringSelectionPattern, recursive);
		// TODO Auto-generated method stub
		return myMap;
	}

	public void scan(File f, String s) {
		//K lets scan this sucker for substrings.
		try {
			Scanner scanner = new Scanner(f);
			boolean foundmatch = false;
			Pattern mypattern = Pattern.compile(s);
			List<String> mylinelist = new ArrayList<String>();
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Matcher m = mypattern.matcher(line);
				if(m.find()) {
					foundmatch = true;
					mylinelist.add(line);
				}
			}
			if(foundmatch)
				myMap.put(f, mylinelist);
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
