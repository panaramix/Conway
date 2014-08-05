import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Conway's Game of Life Game Object.
 * 
 *  <P> Used to create a display panel to hold the game and controls.
 * 
 * @author Sean Hettinger
 * @version 1.0
 * Created: 8/5/14
 * 
 */
public class Game extends JPanel {

	public static boolean running = false;
	public static boolean partyOn = false;
	public static boolean gridOn = true;
	public static final int GRID_ROWS = 100; //Number of Rows on the board
	public static final int GRID_COLUMNS = 100;  //Number of Columns on the board
	public static final int CELL_SIZE = 7;  //Size of each cell
	public static final int GAME_SPEED = 50;  //Delay between updates in milliseconds (Higher number == Slower game)
 	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException  ex){
			ex.printStackTrace();
		}
		
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createGUI();
			
			}
		});
	}
	
	
	
	/**
	 * Creates the GUI and runs the game.
	 * */	
	private static void createGUI(){
	
 		final JFrame frame = new JFrame("Conway's Game of Life");
		final Board board = new Board(GRID_ROWS,GRID_COLUMNS,CELL_SIZE);

		
		frame.setSize(board.getHeight(),board.getWidth());
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
				
		
		JLabel title = new JLabel("Conway's Game of Life", JLabel.CENTER);
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		JButton clear = new JButton("Clear");
		JButton random = new JButton("Random Board");
		JButton party = new JButton("Party Mode");
		JButton grid = new JButton("Grid On/Off");
		JButton close = new JButton("Close");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		
		JPanel leftBorder = new JPanel();
		leftBorder.setBackground(Color.WHITE);
		
		JPanel rightBorder = new JPanel();
		rightBorder.setBackground(Color.WHITE);
		
		
		
		
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				running = true;
				new Thread(new Runnable()
					{
					public void run(){
						
						while(running){
							board.Update();
							board.repaint();
							try {
								Thread.sleep(GAME_SPEED);
							} catch (InterruptedException e) {
								}
						}}
					}).start();
				}
		});
		
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				running = false;
				
			}
		});
		
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				board.clearBoard();
			}
		});
		
		random.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				board.randomBoard();
			}
		});
		
		party.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(partyOn){
					partyOn = false;
					board.repaint();
				}
				else{
					partyOn = true;
					board.repaint();
				}
			}
		});
		grid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(gridOn){
					gridOn = false;
					board.repaint();
				}else{
					gridOn = true;
					board.repaint();}
			}
		});
		
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
			}
		});
		
		buttonPanel.add(start);
		buttonPanel.add(stop);
		buttonPanel.add(clear);
		buttonPanel.add(random);
		buttonPanel.add(party);
		buttonPanel.add(grid);
		buttonPanel.add(close);
		
		titlePanel.add(title);
		

		
		
		frame.add(titlePanel, BorderLayout.PAGE_START);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
		frame.add(leftBorder, BorderLayout.LINE_START);
		frame.add(rightBorder, BorderLayout.LINE_END);
		frame.add(board, BorderLayout.CENTER);
 		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		
		
		
	}	
}

