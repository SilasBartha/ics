package bounce;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import hsa2x.GraphicsConsole;

public class Button extends Rectangle{
	private static final long serialVersionUID = 1L;

	boolean clicked = false;
	Point mp = new Point();
	int w = 0;
	int h = 0;
	
	public Button(){
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
	
	//Test if clicked
	public boolean isClicked(GraphicsConsole gc){
		if(this.contains(mp)){
			if(gc.getMouseClick()>0&&gc.isMouseMoved()==false){
				return true;
			}
		}
		return false;
	}
}
