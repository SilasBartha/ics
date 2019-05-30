package game;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import hsa2x.GraphicsConsole;

@SuppressWarnings("serial")
class Player extends Rectangle{
	double hp = 100;
	double v = 2;
	int dx = 0, dy = 0, fireRateDelay = 30;
	GraphicsConsole gc;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Bullet> trashBullets = new ArrayList<Bullet>();
	boolean canFire = true;
	
	private class FireRateControl extends TimerTask 
	{ 
	    public void run() 
	    { 
	        canFire = true;
	    } 
	} 
			
	Player(GraphicsConsole gc){
		this.gc = gc;
		x = gc.getDrawWidth()/2-16;
		y = gc.getDrawHeight()/2-16;
		width = 32;
		height = 32;
	}
	
	void input(){
		dx=dy=0;
		if(gc.isFocused()){
			if(keyDown('W')){
				dy--;
			}
			if(keyDown('S')){
				dy++;
			}
			if(keyDown('A')){
				dx--;
			}
			if(keyDown('D')){
				dx++;
			}
			if((mouseButtonDown(0)&&canFire)){
				Bullet b = new Bullet(x+16,y+16, this, gc);
				bullets.add(b);
				canFire = false;
				Timer fireRateTimer = new Timer();
				TimerTask fireRateTask = new FireRateControl();
				fireRateTimer.schedule(fireRateTask, fireRateDelay);
			}
		}
		x += dx*v;
		y += dy*v;
		
	}
	
	void draw(){
		gc.setColor(Color.DARK_GRAY);
		gc.fillRect(x, y, width, height);
	}
	
	ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	boolean keyDown(char key){
		return gc.isKeyDown(key);
	}

	boolean mouseButtonClicked(int button){
		return gc.getMouseClick()>0 && gc.getMouseButton(button);
	}
	
	boolean mouseButtonDown(int button){
		return gc.getMouseButton(button);
	}
}
