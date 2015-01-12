/**
 * 
 */
package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Kevin
 * 
 */
public class LineCount extends Base implements LineCounter {

	int LineCount;
	TreeMap<File, Integer> myMap;
	/**
	 * 
	 */
	public LineCount() {
		myMap = new TreeMap<File, Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see listem.LineCounter#countLines(java.io.File, java.lang.String,
	 * boolean)
	 */
	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) {

		myMap.clear();
		searchdirectories(directory, fileSelectionPattern, null, recursive);
		// TODO Auto-generated method stub
		return myMap;
	}

	public void scan(File f, String s) {
		// this need to count up the lines.
		//s is not used.
		//System.out.println("hey 2");
		try {
			int count = 0;
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				count++;
				LineCount++;
				scanner.nextLine();
			}
			//System.out.println(count);
			if(count == 0) {
				count++;
			}
			myMap.put(f, count);
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
