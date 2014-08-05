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
		for (int i = 0; i < rowCount; i++){
			for(int j = 0; j < columnCount; j++){
				grid[i][j] = new Cell(i*size, j*size, size);
			}
		}
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				for (int i = 0; i < rowCount; i++){
					for (int j = 0; j < columnCount; j++){
						if(grid[i][j].rect.contains(e.getX(),e.getY())){
							if(grid[i][j].isAlive){
								grid[i][j].isAlive = false;
							}
							else{
								grid[i][j].isAlive = true;
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
			
		for (int i = 0; i < rowCount; i++){
			for(int j = 0; j < columnCount; j++){
				if(grid[i][j].isAlive == true){	
					if(Game.partyOn){
						float r = random.nextFloat();
						float gr = random.nextFloat();
						float b = random.nextFloat();
						Color randomColor = new Color(r, gr, b);
					
						g2d.setColor(randomColor);
						g2d.fill(grid[i][j].rect);
					}
					else{
						g2d.setColor(Color.GRAY);
						g2d.fill(grid[i][j].rect);}
					}
				else{
					if(Game.gridOn){
						g2d.setColor(Color.GRAY);
						g2d.draw(grid[i][j].rect);
					}
					else{
						g2d.setColor(Color.BLACK);
						g2d.draw(grid[i][j].rect); }
				}
			}
		}
	}
	
	/** Clears the game board. */
	
	public void clearBoard(){
		for(int i = 0; i < rowCount; i++){
			for(int j = 0; j < columnCount; j++){
				grid[i][j].isAlive = false;
			}
		}
		repaint();
	}
	
	/**
	 * Checks a particular cell and counts the number of neighbors
	 * 
	 * @param i The x-coordinate of the cell to be checked
	 * @param j The y-coordinate of the cell to be checked
	 * @return neighborCount The number of neighbors for the cell checked
	 * */
	public int neighborCount(int i, int j){
		 int neighborCount = 0;  
				 		 
		 		 if ((i > 0) && (j > 0)){ 
		 			 if (grid[i-1][j-1].isAlive){
						 neighborCount++;}}
		 		 if (j > 0){
				 	 if (grid[i][j-1].isAlive){
				 		neighborCount++;}}
		 		 if ((i < rowCount-1) && (j > 0)){
				 	 if (grid[i+1][j-1].isAlive){
				 		neighborCount++;}}
				 if (i > 0){
				 	 if (grid[i-1][j].isAlive){
				 		neighborCount++;}}
				 if (i < rowCount-1){
					 if (grid[i+1][j].isAlive){
					 	neighborCount++;}}
				 if ((i > 0) && (j < columnCount-1)){
				 	 if (grid[i-1][j+1].isAlive){
				 		neighborCount++;}}
				 if (j < columnCount-1){
				 	 if (grid[i][j+1].isAlive){
				 		neighborCount++;}}
				 if ((i < rowCount-1) && (j < columnCount-1)){
				 	 if (grid[i+1][j+1].isAlive){
				 		neighborCount++;}}
			
			return neighborCount;
								
		}
		 
	/** Updates the board the next game state */	
	public void Update(){
			
			Cell[][] newGrid = new Cell[rowCount][columnCount];
			for (int i = 0; i < rowCount; i++){
				for(int j = 0; j < columnCount; j++){
					newGrid[i][j] = new Cell(i*size, j*size, size);
				}
			}
			for (int i = 0; i < rowCount; i++){
				for(int j = 0; j < columnCount; j++){
					switch (neighborCount(i,j)){
						case 0: case 1:case 4: case 5: case 6: case 7: case 8:
							newGrid[i][j].isAlive = false;
							break;
						case 2:
							if (grid[i][j].isAlive)
								newGrid[i][j].isAlive = true;
							break;
						case 3:
							newGrid[i][j].isAlive = true;
							break;
					}
				}
			}
		System.arraycopy(newGrid, 0, grid, 0, grid.length);
	}
		
	/** Produces a random set of cells on the screen */
	public void randomBoard(){
		for(int i = 0; i < rowCount; i++){
			for(int j = 0; j < columnCount; j++){
				grid[i][j].isAlive = random.nextBoolean();
			}
		}
		repaint();
	}
}

	
