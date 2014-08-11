import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Conway's Game of Life Board Object.
 * 
 *  <P> The board class is used to display the cells in the game.  It contains methods to draw and manipulate the game board.
 * 
 * @author Sean Hettinger
 * @version 1.0
 * Created: 8/5/14
 * 
 */


public class Board extends JPanel {

	
	Random random = new Random();
	
	public int rowCount;
	public int columnCount;
	public Cell[][] grid;
	public int size;
	
	/**
	 * Constructor. 
	 * 
	 * @param row The number of rows on the board
	 * @param col The number of columns on the board
	 * @param size The size of each cell
	 */
	
	public Board(int row, int col, int size){
		
		setBackground(Color.BLACK);
		this.rowCount = row;
		this.columnCount = col;
		this.size = size;
		grid = new Cell[rowCount][columnCount];
		for (int x = 0; x < rowCount; x++){
			for(int y = 0; y < columnCount; y++){
				grid[x][y] = new Cell(x*size, y*size, size, x, y);
			}
		}
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				for (int x = 0; x < rowCount; x++){
					for (int y = 0; y < columnCount; y++){
						if(grid[x][y].rect.contains(e.getX(),e.getY())){
							if(grid[x][y].isAlive){
								grid[x][y].isAlive = false;
							}
							else{
								grid[x][y].isAlive = true;
							}
							repaint();
						}
					}
				}
			}
		});
		
		
	}
	
	
	/** Return the height of the board */
	public int getHeight(){
		return columnCount * size;
	}
	
	/** Return the width of the board */
	public int getWidth(){
		return rowCount * size;
	}
	
	/**  Abstract paintComponent method.  Used to paint the board onto the screen  */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
			
		for (int x = 0; x < rowCount; x++){
			for(int y = 0; y < columnCount; y++){
				if(grid[x][y].isAlive == true){	
					if(Game.partyOn){
						float r = random.nextFloat();
						float gr = random.nextFloat();
						float b = random.nextFloat();
						Color randomColor = new Color(r, gr, b);
					
						g2d.setColor(randomColor);
						g2d.fill(grid[x][y].rect);
					}
					else{
						g2d.setColor(Color.GRAY);
						g2d.fill(grid[x][y].rect);}
					}
				else{
					if(Game.gridOn){
						g2d.setColor(Color.GRAY);
						g2d.draw(grid[x][y].rect);
					}
					else{
						g2d.setColor(Color.BLACK);
						g2d.draw(grid[x][y].rect); }
				}
			}
		}
	}
	
	/** Clears the game board. */
	
	public void clearBoard(){
		for(int x = 0; x < rowCount; x++){
			for(int y = 0; y < columnCount; y++){
				grid[x][y].isAlive = false;
			}
		}
		repaint();
	}
	
	/**
	 * Checks a particular cell and counts the number of neighbors
	 * 
	 * @param x The x-coordinate of the cell to be checked
	 * @param y The y-coordinate of the cell to be checked
	 * @return neighborCount The number of neighbors for the cell checked
	 * */
	public int neighborCount(Cell cell){
		 int neighborCount = 0;  
				 		 
		 			if (hasNorthEast(cell)){
						 neighborCount++;}
		 			if (hasNorth(cell)){
						 neighborCount++;}
		 			if (hasNorthWest(cell)){
						 neighborCount++;}
		 			if (hasEast(cell)){
						 neighborCount++;}
		 			if (hasWest(cell)){
						 neighborCount++;}
		 			if (hasSouthEast(cell)){
						 neighborCount++;}
		 			if (hasSouth(cell)){
						 neighborCount++;}
		 			if (hasSouthWest(cell)){
						 neighborCount++;}
		 			 
			return neighborCount;
								
		}
		 
	/** Updates the board the next game state */	
	public void Update(){
			
			Cell[][] tempGrid = new Cell[rowCount][columnCount];
			for (int x = 0; x < rowCount; x++){
				for(int y = 0; y < columnCount; y++){
					tempGrid[x][y] = new Cell(x*size, y*size, size, x, y);
				}
			}
			
			
			for (int x = 0; x < rowCount; x++){
				for(int y = 0; y < columnCount; y++){
					switch (neighborCount(grid[x][y])){
						case 0: case 1:case 4: case 5: case 6: case 7: case 8:
							tempGrid[x][y].isAlive = false;
							break;
						case 2:
							if (grid[x][y].isAlive)
								tempGrid[x][y].isAlive = true;
							break;
						case 3:
							tempGrid[x][y].isAlive = true;
							break;
					}
				}
			}
		System.arraycopy(tempGrid, 0, grid, 0, grid.length);
	}
	
		
	/** Produces a random set of cells on the screen */
	public void randomBoard(){
		for(int x = 0; x < rowCount; x++){
			for(int y = 0; y < columnCount; y++){
				grid[x][y].isAlive = random.nextBoolean();
			}
		}
		repaint();
	}

	public boolean hasNorthEast(Cell input){
		if ((input.xCoord > 0) && (input.yCoord > 0)){ 
			 return grid[input.xCoord-1][input.yCoord-1].isAlive;}
		else {
			return false;}
	}
	public boolean hasNorth(Cell input){
		if (input.xCoord > 0){
			return grid[input.xCoord-1][input.yCoord].isAlive;}
		else {
			return false;}
	}
	public boolean hasNorthWest(Cell input){
		if ((input.xCoord > 0) && (input.yCoord < columnCount-1)){
			return grid[input.xCoord-1][input.yCoord+1].isAlive;}
		else {
			return false;}
	}
	public boolean hasEast(Cell input){
		if (input.yCoord > 0){
			
			return grid[input.xCoord][input.yCoord-1].isAlive;}
		else {
			return false;}
	}
	public boolean hasWest(Cell input){
		if (input.yCoord < columnCount-1){
			return grid[input.xCoord][input.yCoord+1].isAlive;}
		else {
			return false;}
	}
	public boolean hasSouthEast(Cell input){
		if ((input.xCoord < rowCount-1) && (input.yCoord > 0)){
			return grid[input.xCoord+1][input.yCoord-1].isAlive;}
		else {
			return false;}
	}
	public boolean hasSouth(Cell input){
		if (input.xCoord < rowCount-1){
			return grid[input.xCoord+1][input.yCoord].isAlive;}
		else {
			return false;}
	}
	public boolean hasSouthWest(Cell input){
		if ((input.xCoord < rowCount-1) && (input.yCoord < columnCount-1)){
			return grid[input.xCoord+1][input.yCoord+1].isAlive;}
		else {
			return false;}
	}
}



	
