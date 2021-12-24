package game;

/*
 *	When originally coding this game, I used:
 *  	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 *  to get the width and height of the screen, which I then used as seen in the other class files.
 *  I then played this game on another PC and found out that it changes for each computer (obviously).
 *  Since I'm not recoding everything to work based on numbers I'm just using this to return the
 *  original values for width and height. I then import this into the classes instead of using
 *  Toolkit's stuff, which means that I don't have to do more math but the source code looks weird.
 *  									¯\_(ツ)_/¯
 *  
 */

public class Screen_Size {
	
	public int getWidth() {
		return 1536;
	}
	public int getHeight() {
		return 864;
	}
	
	
}
