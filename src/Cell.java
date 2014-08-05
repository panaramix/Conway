import java.awt.Rectangle;


/**
 * Conway's Game of Life Cell Object.
 * 
 *  <P> This class created the cells object.  It is used in conjunction with the Board class to display cells on the screen.
 * 
 * @author Sean Hettinger
 * @version 1.0
 * Created: 8/5/14
 * 
 */

public class Cell extends Rectangle {
 
	public boolean isAlive;
	public Rectangle rect;
	
	/**
	 * Constructor.
	 * 
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param size The size of the cell
	 * */
	
	public Cell(int x, int y, int size){
		rect = new Rectangle (x, y, size, size);
		isAlive = false;
	}
}

