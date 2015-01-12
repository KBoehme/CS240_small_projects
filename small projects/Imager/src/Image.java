import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Kevin A class that does all the work.
 * @param Arguments
 *            .
 * @return the transformed picture (.ppm file)
 */
public class Image {

	public String MAGIC_NUMBER;
	public int WIDTH;
	public int HEIGHT;
	public int MAX_VAL;

	File myfile;
	String outfilename;
	File outfile;
	String transformation;
	int blurlength;
	PixelList myImage;

	/**
	 * @param args
	 * 
	 */
	public Image(String[] args) {
		// TODO Auto-generated constructor stub
		try {
			myfile = new File(args[0]);
			outfilename = args[1];
			transformation = args[2].toLowerCase();
			if (transformation.equals("motionblur")) {
				blurlength = Integer.valueOf(args[3]);
				if (blurlength <= 0) {
					System.out
							.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)"
									+ "hint: blurlength must be greater than 0.");
				}
			}
		} catch (Exception e) {
			System.out
					.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		}
	}

	// This starts us off.
	void go() {
		readImageFile();
		performTransformation();
		generateNewImage();
	}

	void readImageFile() {
		Scanner s = null;

		try {
			s = new Scanner(new BufferedReader(new FileReader(myfile)));
			s.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)|(#[^\\n]*\\n)");
			MAGIC_NUMBER = s.next();
			WIDTH = s.nextInt();
			HEIGHT = s.nextInt();
			MAX_VAL = s.nextInt();
			int[][][] image;

			image = new int[HEIGHT][WIDTH][3];
			while (s.hasNext()) {
				for (int j = 0; j < HEIGHT; j++) {
					for (int i = 0; i < WIDTH; i++) {
						for (int k = 0; k < 3; k++) {
							image[j][i][k] = s.nextInt();
						}
					}
				}
			}
			myImage = new PixelList(MAX_VAL, WIDTH, HEIGHT, image);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	void performTransformation() {
		if (transformation.equals("invert")) {
			myImage = myImage.Invert();
		} else if (transformation.equals("grayscale")) {
			myImage = myImage.Grayscale();
		} else if (transformation.equals("emboss")) {
			myImage = myImage.Emboss();
		} else if (transformation.equals("motionblur")) {
			myImage = myImage.MotionBlur(blurlength);
		}
	}

	File generateNewImage() {
		StringBuilder s = new StringBuilder();
		s.append(MAGIC_NUMBER + "\n" + WIDTH + " " + HEIGHT + "\n" + MAX_VAL
				+ "\n");
		outfile = myImage.writeFile(outfilename, s);
		return null;
	}
}
