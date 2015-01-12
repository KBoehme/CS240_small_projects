package listem;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This class will be inherited from both Grep and LineCounter.
 * 
 * It needs to traverse file systems.
 * 
 */

public abstract class Base {

	public Base() {

	}
	
	public abstract void scan(File f, String s);

	public void searchdirectories(File directory, String fileSelectionPattern,
			String substringSelectionPattern, boolean recursive) {

		if (!directory.exists()) {
			return;
		}
		File[] mylist = directory.listFiles();
		for (File child : mylist) {

			//System.out.println(child.getName());
			Pattern mypattern = Pattern.compile(fileSelectionPattern);
			//Matcher m = mypattern.matcher(child.getName());
			Matcher m = mypattern.matcher(child.getName());
			
			if(child.isDirectory() && recursive) {
				searchdirectories(child, fileSelectionPattern,substringSelectionPattern, recursive);
			}
			else if (child.isFile()) {
				if(m.matches()) {
					//System.out.println("hey");
					scan(child, substringSelectionPattern);
				}
			}
			
			
		}
		
	}

}
