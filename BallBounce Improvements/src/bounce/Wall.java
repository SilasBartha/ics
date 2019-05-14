package bounce;

import java.awt.Color;
import java.awt.Rectangle;

public class Wall extends Rectangle{
	private static final long serialVersionUID = 1L;
	
	BallBounce bb = null;
	Button closeButton = new Button();
	Button toggleButton = new Button();
	boolean active = true;
	
	//Constructor
	Wall(int x, int y, int w, int h, BallBounce bb){
		//Set Bounds
		setBounds(x,y,w,h);
		//BallBounce Class
		this.bb = bb;
	}
	
	//Draw Wall
	void draw(){
		//Check if active
		if(active) {
			//Green
			bb.gc.setColor(new Color(0, 255, 0,100));
			bb.gc.fillRect(x, y, width, height);
			bb.gc.setColor(Color.GREEN);
			bb.gc.drawRect(x, y, width, height);
		}else{
			//Yellow
			bb.gc.setColor(new Color(255, 255, 0,100));
			bb.gc.fillRect(x, y, width, height);
			bb.gc.setColor(Color.YELLOW);
			bb.gc.drawRect(x, y, width, height);
		}
		//Buttons
		closeButton.draw(bb.buttonFont, bb.gc, "x", Color.RED, Color.BLACK, x+3, y, 5, true, false);
		toggleButton.draw(bb.buttonFont, bb.gc, "^/v", Color.YELLOW, Color.BLACK, x+width-18, y, 5, true, false);
	}
}
