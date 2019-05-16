//Silas Bartha, May 14, 2019, Lights Out Game

package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import hsa2x.GraphicsConsole;

public class LightsOut {
	/**
	 * Lights Out
	 * 
	 * A single-player game in which the player attempts to put out all of the lights
	 * Clicking a square will cause it and all adjacent (except diagonal) squares to invert from lighted to out and vice-versa 
	 * 
	 * @author Silas Bartha
	 */
	
	//Constants to determine board size, tile size, and the size of the window
	int BOARD_SIZE = 150;
	static final int SQUARE_SIZE = 50;
	static final int OFFSET = 75;
	
	//Mouse Position
	int x, y;
	
	//Number of Turns Taken
	int turns = 0;
	
	//Game State
	boolean won = false;
	
	//Array of squares on board
	boolean[][] lights = new boolean[BOARD_SIZE/SQUARE_SIZE][BOARD_SIZE/SQUARE_SIZE];
	//Window
	GraphicsConsole gc = new GraphicsConsole(BOARD_SIZE+OFFSET*2, BOARD_SIZE+OFFSET*2, "Lights Out");
	//Reset Button
	SilasButton resetButton = new SilasButton();
	//Game Font
	Font fnt = new Font("Times New Roman", Font.PLAIN, 14);
	
	//Main
	public static void main(String[] args){
		new LightsOut();
	}
	
	//Constructor
	LightsOut(){
		//Setup Window, Font, Board
		setup();
		//Main Game Loop
		while(true){
			//Draw Game Things
			draw();
			//If the Reset Button is Clicked
			if(resetButton.isClicked(gc)){
				//Reset the Game
				resetGame();
			}
			//If Mouse Clicked and Game is NOT Won
			if(gc.getMouseClick()>0&&!won){
				//Get Which Row and Column The Mouse is In
				x = ((gc.getMouseX()-OFFSET)/SQUARE_SIZE);
				y = ((gc.getMouseY()-OFFSET)/SQUARE_SIZE);
				//If Mouse is on the Board
				if(mouseOnBoard()) {
					//Increase Turn Count
					turns++;
					//Toggle Self
					lights[y][x] ^= true;
					//Attempt to Toggle Adjacent Squares
					try{	
						lights[y][x+1] ^= true;
					}catch(ArrayIndexOutOfBoundsException e){
					}
					try{	
						lights[y][x-1] ^= true;
					}catch(ArrayIndexOutOfBoundsException e){
					}
					try{	
						lights[y-1][x] ^= true;
					}catch(ArrayIndexOutOfBoundsException e){
					}
					try{	
						lights[y+1][x] ^= true;
					}catch(ArrayIndexOutOfBoundsException e){
					}
					//Check if Game is Won
					won = true;
					//If Any Square is Lit, Player has Not Won
					for(int row = 0; row < lights[1].length; row++){
						for(int col = 0; col < lights[0].length; col++){
							if(lights[col][row]) won = false;
						}
					}
				}
			}
		}
	}
	
	//Set Up Game
	void setup(){
		//Window
		gc.setLocationRelativeTo(null);
		gc.setAntiAlias(true);
		gc.setBackgroundColor(Color.LIGHT_GRAY);
		gc.clear();
		//Mouse
		gc.enableMouse();
		gc.enableMouseMotion();
		gc.setFont(fnt);
		//Game
		resetGame();
	}
	
	//Draw Game
	void draw(){
		synchronized(gc){
			//Refresh Screen
			gc.clear();
			
			//Draw Button
			resetButton.draw(fnt, gc, "Reset", Color.BLACK, Color.WHITE, 50, 25, 5, true, true);
			
			//Draw Unlit Squares
			for(int row = 0; row < lights[1].length; row++){
				for(int col = 0; col < lights[0].length; col++){
					if(!lights[row][col]){
						gc.setColor(Color.DARK_GRAY);
					}else{
						gc.setColor(Color.WHITE);
					}
					gc.fillRect(SQUARE_SIZE*col+OFFSET, SQUARE_SIZE*row+OFFSET, SQUARE_SIZE, SQUARE_SIZE);
				}
			}	
			
			//Draw Number of Turns
			gc.setColor(Color.BLACK);
			gc.drawCenteredString("Turn #: " + turns,200,50,fnt);
			
			//Draw Gridlines
			for(int row = 0; row <= lights[1].length; row++){
				gc.drawLine(OFFSET, SQUARE_SIZE*row+OFFSET, BOARD_SIZE+OFFSET, SQUARE_SIZE*row+OFFSET);
			}
			for(int col = 0; col <= lights[0].length; col++){
				gc.drawLine(SQUARE_SIZE*col+OFFSET, OFFSET, SQUARE_SIZE*col+OFFSET, BOARD_SIZE+OFFSET);
			}
			
			//Congratulate Player
			if(won){
				gc.setColor(Color.RED);
				gc.drawCenteredString("You Win!",200,OFFSET+(SQUARE_SIZE*lights[1].length+50),fnt);
			}
			
		}
		
	}
	
	//reset Game
	void resetGame(){
		//Reset Turns
		turns = 0;
		//Set Won to False
		won = false;
		//Randomize Board
		for(int row = 0; row < lights[1].length; row++){
			for(int col = 0; col < lights[0].length; col++){
				//1 in 5 Chance a Light is Burnt Out
				if((int)(Math.random()*5+1)==5){
					lights[col][row] = false;
				}else{
					lights[col][row] = true;
				}
			}
		}
	}
	
	boolean mouseOnBoard(){
		return(gc.getMouseX()>OFFSET&&gc.getMouseY()>OFFSET&&x>=0&&x<=lights[0].length-1&&y>=0&&y<=lights[2].length-1);
	}
	
}

/*------------------------------
 * 		Custom Button Class
 *------------------------------
 */
class SilasButton extends Rectangle{
	private static final long serialVersionUID = 1L;

	boolean clicked = false;
	Point mp = new Point();
	int w = 0;
	int h = 0;
	
	public SilasButton(){
	}
	
	/**
	 * Draws a button, accounting for hover. Note that positioning is relative to the top center of the button.
	 * @param f Font for FontMetrics
	 * @param gc GraphicsConsole upon which to draw button
	 * @param s  String to be written upon the button
	 * @param c1 Backing Color
	 * @param c2 Text Color
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 * @param padding Amount of padding to be used
	 * @param backed Decide if to use backing
	 * @param rounded Decide if backing should be rounded
	 */
	public void draw(Font f, GraphicsConsole gc, String s, Color c1, Color c2, int x, int y, int padding, boolean backed, boolean rounded){
		
		FontMetrics fm = gc.getFontMetrics(f);
		
		mp = new Point(gc.getMouseX(),gc.getMouseY()); 
		w = fm.stringWidth(s);
		h = fm.getHeight(); 
		
		this.setBounds(x-w/2, y, w+padding*2, h+padding*2);
		if(backed){
			//Draw Backing
			gc.setColor(c1);
			if(rounded){
				gc.fillRoundRect(x-w/2, y, w+padding*2, h+padding*2, padding, padding);
			}else{
				gc.fillRect(x-w/2, y, w+padding*2, h+padding*2);
			}
			//Hover Effect
			if(this.contains(mp)){
				gc.setColor(new Color(0,0,0,50));
				if(rounded){
					gc.fillRoundRect(x-w/2, y, w+padding*2, h+padding*2, padding, padding);
				}else{
					gc.fillRect(x-w/2, y, w+padding*2, h+padding*2);
				}
			}
		}
		gc.setColor(c2);
		gc.drawString(s, x+padding-w/2, y+padding+(int)(h*(3/4.0)));
		if(this.contains(mp)){
			gc.setColor(new Color(0,0,0,50));
			gc.drawString(s, x+padding-w/2, y+padding+(int)(h*(3/4.0)));
		}
	}
	
	public boolean isClicked(GraphicsConsole gc){
		if(this.contains(mp)){
			if(gc.getMouseClick()>0&&!gc.isMouseMoved()){
				return true;
			}
		}
		return false;
	}
}

