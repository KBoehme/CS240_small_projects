import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author kboehme1
 * 
 */
public class PixelList {

	// Lets make a 2D array for the size of the image..
	// A list of pixels is unbearably slow!!
	int[][][] pixels;
	int HEIGHT;
	int WIDTH;
	int MAX_VAL;

	// first go height is 0.. width increases by one... the 3 increment
	public PixelList(int max, int width, int height, int[][][] pix) {
		MAX_VAL = max;
		HEIGHT = height;
		WIDTH = width;
		pixels = pix;
	}

	/**
	 * 
	 public PixelList(String[] s) { // TODO Auto-generated constructor stub //
	 * When created it must create all the pixels. // s contains all the pixel
	 * information. for (int i = 0; i < s.length; i = i + 3) { int r =
	 * Integer.valueOf(s[i]); int g = Integer.valueOf(s[i + 1]); int b =
	 * Integer.valueOf(s[i + 2]);
	 * 
	 * Pixel pix = new Pixel(r, g, b); myPixelList.add(pix); } }
	 */

	// INVERT
	PixelList Invert() {
		for (int j = 0; j < HEIGHT; j++) {
			for (int i = 0; i < WIDTH; i++) {
				for (int k = 0; k < 3; k++) {
					pixels[j][i][k] = 255 - pixels[j][i][k];
				}
			}
		}
		return this;
	}

	// GRAYSCALE
	PixelList Grayscale() {
		for (int j = 0; j < HEIGHT; j++) {
			for (int i = 0; i < WIDTH; i++) {
				int r;
				int g;
				int b;
				r = pixels[j][i][0];
				g = pixels[j][i][1];
				b = pixels[j][i][2];
				int average = (r + g + b) / 3;
				pixels[j][i][0] = average;
				pixels[j][i][1] = average;
				pixels[j][i][2] = average;
			}
		}
		return this;
	}

	/*
	 * Emboss Assume an image is stored in a structure called image, with
	 * â€œheightâ€� rows and â€œwidthâ€� columns. For every pixel p at row r,
	 * column c (p = image[r,c]), set its red, green, and blue values to the
	 * same value doing the following: Calculate the differences between red,
	 * green, and blue values for the pixel and and the pixel to its upper left.
	 * redDiff = p.redValue - image[r-1,c-1].redValue greenDiff = p.greenValue -
	 * image[r-1,c-1].greenValue blueDiff = p.blueValue - image[r-1,
	 * c-1].blueValueFind the largest difference (positive or negative). We will
	 * call this maxDifference. We then add 128 to maxDifference. If there are
	 * multiple equal differences with differing signs (e.g. -3 and 3), favor
	 * the red difference first, then green, then blue. v = 128 + maxDifference
	 * If needed, we then scale v to be between 0 and 255 by doing the
	 * following: If v < 0, then we set v to 0. If v > 255, then we set v to
	 * 255. The pixelâ€™s red, green, and blue values are all set to v. Be sure
	 * to account for the situation where r-1 or c-1 is less than 0. V should be
	 * 128 in this case.
	 */

	PixelList Emboss() {
		int[][][] newimage = new int[HEIGHT][WIDTH][3];
		for (int j = 0; j < HEIGHT; j++) {
			for (int i = 0; i < WIDTH; i++) {
				if(j==0 || i == 0) {
					newimage[j][i][0] = 128;
					newimage[j][i][1] = 128;
					newimage[j][i][2] = 128;
				}
				else {
					int reddif;
					int greendif;
					int bluedif;
					int absr;
					int absg;
					int absb;
					int biggest = 0;
					
					reddif = pixels[j][i][0] - pixels[j-1][i-1][0];
					greendif = pixels[j][i][1] - pixels[j-1][i-1][1];
					bluedif = pixels[j][i][2] - pixels[j-1][i-1][2];
					
					absr = Math.abs(reddif);
					absg = Math.abs(greendif);
					absb = Math.abs(bluedif);
					
					if(absr >= absg && absr >= absb) {
							biggest = reddif;
					}
					else {
						if(absg >= absb) {
							biggest = greendif;
						}
						else
							biggest = bluedif;
					}

					int red;
					int green;
					int blue;
					red = 128 + biggest;
					green = 128 + biggest;
					blue = 128 + biggest;
					
					if(red > MAX_VAL) {
						newimage[j][i][0] = MAX_VAL;
					}
					else if (red < 0) { newimage[j][i][0] = 0; }
					else
						newimage[j][i][0] = red;
					
					if(green > MAX_VAL) {
						newimage[j][i][1] = MAX_VAL;
					}
					else if (green < 0) { newimage[j][i][1] = 0; }
					else
						newimage[j][i][1] = green;
					
					if(blue > MAX_VAL) {
						newimage[j][i][2] = MAX_VAL;
					}
					else if (blue < 0) { newimage[j][i][2] = 0; }
					else
						newimage[j][i][2] = blue;
				}
			}
		}
		pixels = newimage;
		return this;
	}

	/*
	 * Motion blur A number will be provided in the command line arguments if
	 * the command is â€œmotionblur.â€� We will call this number n. n must be
	 * greater than 0. The value of each color of each pixel is the average of
	 * that color value for n pixels (from the current pixel to n-1)
	 * horizontally. Example: if we store the pixels in a 2d array, the motion
	 * blur would average each color from pixel[ x ][ y ] to pixel[ x ][ y+n-1 ]
	 * Be sure to account for the situations where one or more of the values
	 * used in the computing the average do not exist. For example, if an image
	 * has width w and we are considering the pixel on row r, column c, if c + n
	 * >= w, then we only average the pixels up to w.
	 */
	PixelList MotionBlur(int n) {
		int[][][] newimage = new int[HEIGHT][WIDTH][3];

		try {
			for (int x = 0; x < HEIGHT; x++) {
				for (int y = 0; y < WIDTH; y++) {				
					if((y + n) >= WIDTH) {
						int raverage = 0;
						int gaverage = 0;
						int baverage = 0;
						int z = y;
						int counter = 0;
						for(; z < WIDTH; z++) {
							raverage = raverage + pixels[x][z][0];
							gaverage = gaverage + pixels[x][z][1];
							baverage = baverage + pixels[x][z][2];
							counter++;
						}
						raverage = raverage/counter;
						gaverage = gaverage/counter;
						baverage = baverage/counter;
						newimage[x][y][0] = raverage;
						newimage[x][y][1] = gaverage;
						newimage[x][y][2] = baverage;
					}
					else {
						int raverage = 0;
						int gaverage = 0;
						int baverage = 0;
						for(int k = 0; k < n; k++) {
							raverage = raverage + pixels[x][y+k][0];
							gaverage = gaverage + pixels[x][y+k][1];
							baverage = baverage + pixels[x][y+k][2];
						}
						raverage = raverage/n;
						gaverage = gaverage/n;
						baverage = baverage/n;
						newimage[x][y][0] = raverage;
						newimage[x][y][1] = gaverage;
						newimage[x][y][2] = baverage;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		pixels = newimage;
		return this;
	}

	File writeFile(String out, StringBuilder s) {
		File outfile = new File(out);

		for (int j = 0; j < HEIGHT; j++) {
			for (int i = 0; i < WIDTH; i++) {
				for (int k = 0; k < 3; k++) {
					s.append(pixels[j][i][k] + "\n");
				}
			}
		}

		try {
			PrintWriter writer;
			writer = new PrintWriter(outfile);
			writer.println(s);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outfile;
	}
}