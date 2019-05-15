package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import hsa2x.GraphicsConsole;

public class TicTacToe {
	//Window
	int vs = 800;
	GraphicsConsole gc = new GraphicsConsole(vs,vs,"Tic Tac Toe");
	//Colors
	Color wood = new Color(205, 136, 102);
	Color carve = wood.darker().darker();
	Color highlight = new Color(0,255,0,100);
	Color shadow = new Color(0,0,0,100);
	Color bgColor = new Color(250,235,215);
	
	//Spaces dimensional array
	static final int GRID_NUM = 3;
	int[][] spaces = new int[GRID_NUM][GRID_NUM];
	//Board Dimensions
	static final int BOARD_SIZE = 600;
	static final int GRID_SIZE = 580/GRID_NUM;
	static final double OFFSET = (((GRID_NUM*GRID_SIZE)/(double)BOARD_SIZE)*15);;
	//Constants for claiming space
	static final int EMPTY = 0;
	static final int XSPACE = 1;
	static final int OSPACE = -1;
	//Variables for keeping track of winner
	int winner = 0;
	int p1Wins = 0;
	int p2Wins = 0;
	//Mouse Location
	Point m = new Point(0,0);
	//Boolean to keep track of turn, player 2's turn if false
	boolean player1Turn = true;
	//Variable to check if game is in progress
	boolean gameOver = false;
	//Font
	InputStream h1Stream = TicTacToe.class.getResourceAsStream("/fnt/BabelStoneModern.ttf");
	Font h1;
	//Buttons
	Button resetButton = new Button(); 
	Button startButton = new Button();
	
	//Main
	public static void main(String[] args) {
		new TicTacToe();
	}
	
	//Game
	TicTacToe(){
		//setup Window, fonts, sounds, etc.
		setup();
		//Main loop
		while(true){
			//Draw graphics
			draw();
			//Game Control
			gameStep();
		}
	}
	
	//Setup
	void setup(){
		//Font
		try {
			h1 = Font.createFont(Font.TRUETYPE_FONT, h1Stream).deriveFont(24f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		//Window Icon
		gc.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
		//Background
		gc.setBackgroundColor(bgColor);
		gc.clear();
		//Enable Anti-Alias
		gc.setAntiAlias(true);
		//Enable Mouse
		gc.enableMouse();
		gc.enableMouseMotion();
		//Place Grid Squares
		createGrid();
	}
	
	//Draw
	void draw(){
		//Get Mouse Position
		m.x = gc.getMouseX();
		m.y	= gc.getMouseY();
		//Draw To Screen
		synchronized(gc){
			//Clear Screen
			gc.clear();
			//Draw Board
			drawBoard();
			//Drawing for During Game
			if(!gameOver){
				drawGameObjects();
			}else{
				drawGameOver();
			}
			//Sleep
			gc.sleep(1);
		}	
	}
	
	//Game Loop
	void gameStep(){
		//Play game
		if(!gameOver){
			//Player Turn Handling, click checking
			play();
		}
		//Reset Game if Button Pressed
		checkReset();
	}
	
	//Grid Drawing
	void createGrid(){
		for(int row = 0; row < GRID_NUM; row++){
			for(int column = 0; column < GRID_NUM; column++){
				spaces[column][row] = EMPTY;
			}
		}
	}
	
	//Board Drawing
	void drawBoard(){
		gc.setStroke(5);
		//Add Faux Depth
		gc.setColor(carve);
		gc.fillRoundRect(25,25,750,60,25,25);
		gc.fillRoundRect(100, 100, BOARD_SIZE, BOARD_SIZE+10,25,25);
		//Top
		gc.setColor(wood);
		gc.fillRoundRect(25,25,750,50,25,25);
		gc.fillRoundRect(100, 100, BOARD_SIZE, BOARD_SIZE,25,25);
		drawGrid();
	}

	void drawGrid(){
		gc.setColor(carve);
		for(int row = 0; row < GRID_NUM; row++){
			for(int column = 0; column < GRID_NUM; column++){
				gc.drawRoundRect(100+(int)OFFSET+(GRID_SIZE*column), 100+(int)OFFSET+(GRID_SIZE*row), GRID_SIZE, GRID_SIZE,5,5);
				//Draw X or O or nothing, based on occupied state.
				switch(spaces[row][column]){
				//P2
				case OSPACE:
					gc.drawOval(100+(int)OFFSET+(GRID_SIZE*column)+(int)OFFSET, 100+(int)OFFSET+(GRID_SIZE*row)+(int)OFFSET, GRID_SIZE-(int)(OFFSET*2), GRID_SIZE-(int)(OFFSET*2));
					break;
				//Empty
				case EMPTY:
					break;
				//P1
				case XSPACE:
					gc.drawLine(100+(GRID_SIZE*column)+2*(int)OFFSET, 100+(GRID_SIZE*row)+2*(int)OFFSET, 100+(GRID_SIZE*column)+GRID_SIZE, 100+(GRID_SIZE*row)+GRID_SIZE);
					gc.drawLine(100+(GRID_SIZE*column)+GRID_SIZE, 100+(GRID_SIZE*row)+2*(int)OFFSET, 100+(GRID_SIZE*column)+2*(int)OFFSET, 100+(GRID_SIZE*row)+GRID_SIZE);
					break;
				}
			}
		}
	}
	
	//Draw Game Objects
	void drawGameObjects(){
		//Larger Stroke Size Before Placement
		gc.setStroke(10);
		//X Selection
		drawGUI();
		if(player1Turn){
			drawPlayer1Turn();
		}else{
			//O Selection
			drawPlayer2Turn();
		}
	}

	//Draw Player 1's Potential Selection
	void drawPlayer1Turn(){
		for(int row = 0; row < GRID_NUM; row++){
			for(int column = 0; column < GRID_NUM; column++){
				//Green X if available, red if occupied
				Rectangle rect = new Rectangle(100+(int)OFFSET+(GRID_SIZE*column), 100+(int)OFFSET+(GRID_SIZE*row), GRID_SIZE, GRID_SIZE);
				if(rect.contains(m)){
					if(spaces[row][column] == EMPTY){
						gc.setColor(Color.GREEN);
					}else{
						gc.setColor(Color.RED);
					}
					gc.drawLine(m.x-(GRID_SIZE-30)/2, m.y-(GRID_SIZE-30)/2, m.x+(GRID_SIZE-30)/2, m.y+(GRID_SIZE-30)/2);
					gc.drawLine(m.x-(GRID_SIZE-30)/2, m.y+(GRID_SIZE-30)/2,  m.x+(GRID_SIZE-30)/2, m.y-(GRID_SIZE-30)/2);
				}
			}
		}
	}
	
	//Draw Player 2's Potential Selection
	void drawPlayer2Turn(){
		for(int row = 0; row < GRID_NUM; row++){
			for(int column = 0; column < GRID_NUM; column++){
				//Green X if available, red if occupied
				Rectangle rect = new Rectangle(100+(int)OFFSET+(GRID_SIZE*column), 100+(int)OFFSET+(GRID_SIZE*row), GRID_SIZE, GRID_SIZE);
				if(rect.contains(m)){
					if(spaces[row][column] == EMPTY){
						gc.setColor(Color.GREEN);
					}else{
						gc.setColor(Color.RED);
					}
					gc.drawOval(m.x-(GRID_SIZE-30)/2, m.y-(GRID_SIZE-30)/2, GRID_SIZE-(int)(OFFSET*2), GRID_SIZE-(int)(OFFSET*2));
				}
			}
		}
	}

	//Draw Info And Reset Button
	void drawGUI(){
		//Set font
				gc.setFont(h1);
				//Draw Reset Button
				resetButton.draw(h1, gc, "Reset", carve, wood, 400, 30, 5, true, true);
				//Display which player's turn it is.
				gc.setColor(carve);
				if(player1Turn){
					gc.drawString("Player 1's Turn (X)", 30, 60);
				}else{
					gc.drawString("Player 2's Turn (O)", 30, 60);
				}
				//Wins Tally Counter
				gc.fillRoundRect(485, 30, 100, 40, 5, 5);
				gc.setColor(wood);
				gc.drawString(p1Wins + " : " + p2Wins, 500, 60);
	}
	
	//Draw Title
	void drawTitle(){
		//Set font
		gc.setFont(h1);
		gc.setColor(carve);
		gc.drawString("Tic Tac Toe", 325, 60);
	}
	
	//Draw Game Objects (Shadow, dialog box, etc)
	void drawGameOver(){
		//Shadow
		drawShadow();
		//Draw line over winning spaces
		drawHighlight();
		//Game Over  Dialog Box
		drawGameOverDialog();
	}
	
	//Draw Shadow Over Screen
	void drawShadow(){
		//Draw Shadow over screen
				gc.setColor(shadow);
				gc.fillRect(0, 0, vs, vs);
				gc.setStroke(GRID_SIZE);
	}

	//Draw Highlight Over Winning Combinations
	void drawHighlight(){
		gc.setColor(highlight);
		//Column
		columnHighlight();
		//Row
		rowHighlight();
		//Diagonals
		diagonalHighlight();
	}
	
	//Check Which Columns to Highlight
	void columnHighlight(){
		for(int column = 0; column < GRID_NUM; column++){
			int sum = 0;
			for(int i = 0; i<GRID_NUM; i++){
				sum += spaces[i][column];
			}
			if(Math.abs(sum) == 3){
				gc.drawLine(
					//X1
					100+(int)OFFSET+column*GRID_SIZE+GRID_SIZE/2,
					//Y1
					100+(int)OFFSET+GRID_SIZE/2,
					//X2
					100+(int)OFFSET+column*GRID_SIZE+GRID_SIZE/2,
					//Y2
					100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2
				);
			}
		}
	}

	//Check Which Rows to Highlight
	void rowHighlight(){
		for(int row = 0; row < GRID_NUM; row++){
			int sum = 0;
			for(int i = 0; i<GRID_NUM; i++){
				sum += spaces[row][i];
			}
			if(Math.abs(sum) == 3){
				gc.drawLine(
						//X1
						100+(int)OFFSET+GRID_SIZE/2,
						//Y1
						100+(int)OFFSET+row*GRID_SIZE+GRID_SIZE/2,
						//X2
						100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2,
						//Y2
						100+(int)OFFSET+row*GRID_SIZE+GRID_SIZE/2
				);
			}
		}
	}

	//Check Which Diagonals to Highlight
	void diagonalHighlight(){
		//Top to bottom
				int sum = 0;
				for(int i = 0; i<GRID_NUM; i++) {
					sum += spaces[i][i];
				}
				if(Math.abs(sum) == 3){
					gc.drawLine(
							//X1
							100+(int)OFFSET+GRID_SIZE/2,
							//Y1
							100+(int)OFFSET+GRID_SIZE/2,
							//X2
							100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2,
							//Y2
							100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2
					);
				}
				//Bottom to top
				sum = 0;
				for(int i = 0; i<GRID_NUM; i++) {
					sum += spaces[i][GRID_NUM-1-i];
				}
				if(Math.abs(sum) == 3){
					gc.drawLine(
							//X1
							100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2,
							//Y1
							100+(int)OFFSET+GRID_SIZE/2,
							//X2
							100+(int)OFFSET+GRID_SIZE/2,
							//Y2
							100+(int)OFFSET+GRID_SIZE*GRID_NUM-GRID_SIZE/2
					);
				}
	}

	//Draw Game Over Dialog Box
 	void drawGameOverDialog(){
		gc.setStroke(5);
		gc.setColor(carve);
		gc.fillRoundRect(25,((vs/2)-50),750,110,5,5);
		gc.setColor(wood);
		gc.fillRoundRect(25,((vs/2)-50),750,100,5,5);
		gc.setColor(carve);
		//Winner
		if(winner!=0){
			gc.drawString("Game over, player " + winner + " wins.", 45, ((vs/2)-15));
		}else{
			//Tie Game
			gc.drawString("Game over, game tied.", 45, ((vs/2)-15));
		}
		//Draw Reset Button on Dialog Box
		resetButton.draw(h1, gc, "Reset", carve, wood, 500, ((vs/2)-15), 5, true, true);
		gc.setColor(carve);
	}

 	//Play Placement Sound
	void playPlaceSound(){
		AudioInputStream placeSound;
		Clip placeClip;
		try {
			placeSound = AudioSystem.getAudioInputStream(this.getClass().getResource("/snd/place.wav"));
			placeClip = AudioSystem.getClip();
			placeClip.open(placeSound);
			placeClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	//Claim Space for Current Player
	void claimSpace(int row, int column){
		if(player1Turn){
			spaces[row][column] = XSPACE;
		}else{
			spaces[row][column] = OSPACE;
		}
	}

	//Play Denial Sound
	void playDenialSound(){
		AudioInputStream denySound;
		Clip denyClip;
		try {
			denySound = AudioSystem.getAudioInputStream(this.getClass().getResource("/snd/deny.wav"));
			denyClip = AudioSystem.getClip();
			denyClip.open(denySound);
			denyClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	//Check if all Spaces are Occupied,to Decide Whether the Game is Tied 
	void checkTieGame(){
		gameOver = true;
		for(int r = 0; r < GRID_NUM; r++){
			for(int c = 0; c < GRID_NUM; c++){
				if(spaces[r][c]==EMPTY){
					gameOver = false;
				}
			}
		}
	}

	//Check if Any Columns are Occupied by One Player, and Call them the Winner if so
	void checkColumns(){
		for(int c = 0; c < GRID_NUM; c++){
			int sum = 0;
			//Player 1's spaces have a value of 1, Player 2's have a value of -1.
			for(int i = 0; i<GRID_NUM; i++){
				sum += spaces[i][c];
			}
			//Three of P1's in a row will total gridNum, -gridNum for P2. 
			if(sum == XSPACE*3){
				gameOver = true;
				winner = 1;
				p1Wins++;
			}else if(sum == OSPACE*3){
				gameOver = true;
				winner = 2;
				p2Wins++;
			}
		}
	}

	//Check if Any Rows are Occupied by One Player, and Call them the Winner if so
	void checkRows(){
		for(int r = 0; r < GRID_NUM; r++){
			int sum = 0;
			//Player 1's spaces have a value of 1, Player 2's have a value of -1.
			for(int i = 0; i<GRID_NUM; i++){
				sum += spaces[r][i];
			}
			//Three of P1's in a row will total gridNum, -gridNum for P2. 
			if(sum == XSPACE*3){
				gameOver = true;
				winner = 1;
				p1Wins++;
			}else if(sum == OSPACE*3){
				gameOver = true;
				winner = 2;
				p2Wins++;
			}
		}
	}

	//Check if Any Diagonals are Occupied by One Player, and Call them the Winner if so
	void checkDiagonals(){
		//Top to bottom
		int sum = 0;
		//Player 1's spaces have a value of 1, Player 2's have a value of -1.
		for(int i = 0; i<GRID_NUM; i++) {
			sum += spaces[i][i];
		}
		//Three of P1's in a row will total gridNum, -gridNum for P2.
		if(sum == XSPACE*3){
			gameOver = true;
			winner = 1;
			p1Wins++;
		}else if(sum == OSPACE*3){
			gameOver = true;
			winner = 2;
			p2Wins++;
		}
		//Bottom to top
		sum = 0;
		//Player 1's spaces have a value of 1, Player 2's have a value of -1.
		for(int i = 0; i<GRID_NUM; i++) {
			sum += spaces[i][GRID_NUM-1-i];
		}
		//Three of P1's in a row will total gridNum, -gridNum for P2.
		if(sum == XSPACE*3){
			gameOver = true;
			winner = 1;
			p1Wins++;
		}else if(sum == OSPACE*3){
			gameOver = true;
			winner = 2;
			p2Wins++;
		}
	}

	//Player Interaction with the Game
	void play(){
		for(int row = 0; row < GRID_NUM; row++){
			for(int column = 0; column < GRID_NUM; column++){
				int s = spaces[row][column];
				Rectangle rect = new Rectangle(100+(int)OFFSET+(GRID_SIZE*column), 100+(int)OFFSET+(GRID_SIZE*row), GRID_SIZE, GRID_SIZE);
				if(rect.contains(m)&&gc.getMouseClick()>0&&!gc.isMouseMoved()){
					//Check if space is taken
					if(s == EMPTY){
						//Placement Sound
						playPlaceSound();
						//Claim Space
						claimSpace(row,column);
						//Next Turn
						player1Turn ^= true;
					}else{
						//Deny Placement, Play Sound
						playDenialSound();
					}
					//Tie Game if All Spaces Taken
					checkTieGame();
					//Column Checking
					checkColumns();
					//Row Checking
					checkRows();
					//Diagonal Checks
					checkDiagonals();
				}
			}
		}
	}

	//Check if Game is Reset
	void checkReset(){
		if(resetButton.isClicked(gc)){
			//Play Reset Sound
			AudioInputStream resetSound;
			Clip resetClip;
			try {
				resetSound = AudioSystem.getAudioInputStream(this.getClass().getResource("/snd/reset.wav"));
				resetClip = AudioSystem.getClip();
				resetClip.open(resetSound);
				resetClip.start();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
			//Reset Turn
			player1Turn = true;
			//Reset Game State 
			gameOver = false;
			//Reset Winner
			winner = 0;
			//Reset Each Space
			for(int row = 0; row < GRID_NUM; row++){
				for(int column = 0; column < GRID_NUM; column++){
					spaces[row][column] = EMPTY;
				}
			}
		}
	}
}
