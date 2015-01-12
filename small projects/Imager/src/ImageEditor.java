import java.io.FileNotFoundException;

/**
 * 
 */

/**
 * @author kboehme1 Main class which will run the program.
 */
public class ImageEditor {

	public ImageEditor() {
	}

	/**
	 * @param args
	 *            Our Arguments are here java ImageEditor bike.ppm
	 *            bike-inverted.ppm invert java program_name output_file command
	 * @throws FileNotFoundException
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Image image = new Image(args);
		image.go();
	}
}
