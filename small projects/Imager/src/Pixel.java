
/**
 * 
 */

/**
 * @author kboehme1
 *
 */
public class Pixel {

	/**
	 * Will hold the value of one pixel so a red/green/blue value
	 */
	public Pixel(int r, int g, int b) {
		// TODO Auto-generated constructor stub
		Red = r;
		Green = g;
		Blue = b;
	}
	
	int Red;
	int Green;
	int Blue;
	
	public String toString() {
		//System.out.println("(" + Red + "," + Green + "," + Blue + ")");
		return null;
	}
	
	String Build() {
		String string;
		string = Red+"\n"+Green+"\n"+Blue+"\n";
		return string;
	}
	
	//Invert
	void Invert() {
		Red = 255 - Red;
		Green = 255 - Green;
		Blue = 255 - Blue;
		this.toString();
	}

	//GRAYSCALE
	void GrayScale() {
		int total = Red+Green+Blue;
		int average = total/3;
		Red = average;
		Green = average;
		Blue = average;
	}
}