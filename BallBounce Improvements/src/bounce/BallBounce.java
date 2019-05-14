//Silas Bartha, 25/03/2019, Advanced Bouncing Ball Program
package bounce;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import hsa2x.GraphicsConsole;

public class BallBounce {
	
	//Main
	public static void main(String[] args){
		new BallBounce();
	}
	
	//Window Dimensions
	int vw = 640, vh = 480;
	//Start x and y for wall placement, as well as dragged dimensions for wall placement.
	int mx= 0, my = 0, drx = 0, dry = 0;
	//Boolean for detecting whether a box is currently being dragged
	boolean dragging = false;
	//Boolean for toggling gravity
	boolean gravity = false;
	//Graphics Console
	GraphicsConsole gc = new GraphicsConsole(vw,vh,"BallBounce");
	//Window Coordinates
	Point cc = new Point(gc.getLocation());
	int cx = cc.x;
	int cy = cc.y;
	//Window Velocity Handling (x/y/vw(view width)/vh(view height)), p = previous, d = diff, c = change
	int px = 0, py = 0, dx = 0, dy = 0, pvw = 0, pvh = 0, vwc = 0, vhc = 0;
	//Ball ArrayList
	ArrayList<Ball> balls = new ArrayList<Ball>();
	//Wall ArrayList, and walls to be removed ArrayList
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Wall> removeWalls = new ArrayList<Wall>();
	//Font for buttons
	Font buttonFont = new Font("Arial", Font.PLAIN, 14); 
	//Resize Button String
	String resizeString = "Enable";
	
	//Control
	BallBounce(){
		//Setup Console
		setup();	
		//Initialize Buttons: add ball, remove ball, enable resize, toggle gravity
		Button addButton = new Button();
		Button removeButton = new Button();
		Button resizeButton = new Button();
		Button gravityButton = new Button();

		//Main Loop
		while(true) {
			synchronized(gc){
				//Modify window vars if necessary.
				modWindow();
				//Clear Graphics
				gc.clear();
				//Move Balls
				for(Ball b : balls){
					b.move();
					b.draw();
				}
				//Place walls if needed
				dragWall();
				//Draw Walls
				for(Wall w : walls) {
					w.draw();
				}
				//Draw Buttons
				addButton.draw(buttonFont, gc, "Add Ball", Color.GREEN, Color.BLACK, (int)(gc.getDrawWidth()/2.0-gc.getDrawWidth()/640.0*100), (int)(gc.getDrawHeight()/480.0*10), 5, true, true);
				removeButton.draw(buttonFont, gc, "Remove Ball", Color.RED, Color.BLACK, (int)(gc.getDrawWidth()/2.0), (int)(gc.getDrawHeight()/480.0*10), 5, true, true);
				resizeButton.draw(buttonFont, gc, resizeString +" Resizing (Unstable!)", Color.YELLOW, Color.RED, (int)(gc.getDrawWidth()-gc.getDrawWidth()/640.0*100), (int)(gc.getDrawHeight()/480.0*10), 5, true, true);
				gravityButton.draw(buttonFont, gc, "Toggle Gravity", Color.YELLOW, Color.RED, (int)(gc.getDrawWidth()-gc.getDrawWidth()/640.0*60), (int)(gc.getDrawHeight()/480.0*50), 5, true, true);
			}
			//Check if buttons are pressed
			//Add Balls
			if(addButton.isClicked(gc)){
				addBall();
			}
			//Remove Balls
			if(removeButton.isClicked(gc)){
				removeBall();
			}
			//Resize
			if(resizeButton.isClicked(gc)){
				toggleResize();
			}
			//Gravity
			if(gravityButton.isClicked(gc)){
				gravity^=true;
			}
			//Wall Buttons (Close and toggle Active)
			for(Wall w : walls){
				if(w.closeButton.isClicked(gc)){
					//Add wall to "remove" list
					removeWalls.add(w);
				}
				if(w.toggleButton.isClicked(gc)){
					w.active ^= true;
				}
			}
			//Remove removed walls
			walls.removeAll(removeWalls);
			//Sleep, 60fps
			gc.sleep(Math.round(1000/60.0));
		}
	}
	
	//Setup GraphicsConsole
	void setup(){
		//Create Balls
		for(int i = 0; i < 3; i++){
			Ball b = new Ball((int)(Math.random()*gc.getDrawWidth()),(int)(Math.random()*gc.getDrawHeight()),(int)(Math.random()*4+4),(int)(Math.random()*4+4),this);
			b.num = i;
			balls.add(b);
		}
		//Place Window Near Origin
		gc.setLocation(10,10);
		//Setup Window Background
		gc.setAntiAlias(true);
		gc.setBackgroundColor(Color.BLACK);
		gc.setBackground(Color.BLACK);
		gc.clear();
		//Enable Mouse
		gc.enableMouse();
		gc.enableMouseMotion();
	}

	//Window Modifications
	void modWindow(){
		//Get Screen Bounds, and Change in Screen Bounds
		if(gc.isResizable()){
			//Width
			pvw = vw;
			vw = gc.getDrawWidth();
			vwc = pvw - vw;
			//Height
			pvh = vh;
			vh = gc.getDrawHeight();
			vhc = pvh-vh;
		}
		//Get Window Coord, and Window Velocity
		cc = new Point(gc.getLocation());
		//DX (x-velocity)
		px = cx;
		cx = cc.x;
		dx = px-cx;
		//DY (y-velocity)
		py = cy;
		cy = cc.y;
		dy = py-cy;
	}
	
	//Wall placement method
	void dragWall(){
		//Create Top-left Corner
		if(gc.getMouseButton(0)&&!dragging){
			mx = gc.getMouseX();
			my = gc.getMouseY();
		}
		//Dragging Wall
		if(gc.isMouseDragged()){
			dragging = true;
			drx = gc.getMouseDX();
			dry = gc.getMouseDY();	
			gc.setColor(new Color(255,0,0,100));
			gc.fillRect(mx, my, drx, dry);
			gc.setColor(Color.RED);
			gc.drawRect(mx, my, drx, dry);
		}else if(dragging){
			//Place Wall
			if(drx>20&&dry>20){
				Wall w = new Wall(mx, my, drx, dry, this);
				walls.add(w);
			}
			//Allow new wall to be created
			dragging = false;
		}
	}
	
	//Add Ball
	void addBall(){
		if(balls.size()<50){
			Ball b = new Ball((int)(Math.random()*gc.getDrawWidth()),(int)(Math.random()*gc.getDrawHeight()),(int)(Math.random()*4+4),(int)(Math.random()*4+4),this);
			b.num = balls.size();
			balls.add(b);
		}
	}
	
	//Remove Ball
	void removeBall(){
		if(balls.size()>1){
			balls.remove(balls.size()-1);
		}
	}
	
	//Toggle Resizable
	void toggleResize(){
		gc.setResizable(!gc.isResizable());
		//Change button text
		if(gc.isResizable()){
			resizeString = "Disable";
		}else{
			resizeString = "Enable";
		}
	}
}

