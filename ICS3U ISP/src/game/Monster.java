package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import hsa2x.GraphicsConsole;

@SuppressWarnings("serial")
class Monster extends Rectangle{
	
	static final int GHOUL = 0;
	static final int GOBLIN = 1;
	Point target;
	Player player;
	GraphicsConsole gc;
	double x, y, dx, dy, dir, hp, mhp;
	double dxmag, dymag;
	Color color;
	Color dColor;
	
	class HitControl extends TimerTask{
		public void run(){
			color = dColor;
		}
	}
	
	Monster(int x, int y, Player player, GraphicsConsole gc, int type){
		this.x = x;
		this.y = y;
		this.player = player;
		this.gc = gc;
		width = 32;
		height = 32;
		target = new Point(player.x,player.y);
		dxmag = target.x - x;
	    dymag = target.y - y;
	    dir = Math.atan2(dymag, dxmag);
	    dx = Math.cos(dir);
	    dy = Math.sin(dir);
	    switch(type){
	    case GHOUL:
	    	hp = 2;
	    	mhp = hp;
	    	dColor = new Color(100,0,100);
	    	color = dColor;
	    	break;
	    case GOBLIN:
	    	hp = 10;
	    	mhp = hp;
	    	dColor = new Color(0,100,0);
	    	color = dColor;
	    	break;
	    }
	}
	
	void draw(){
		gc.setColor(color);
		gc.fillRect((int)x, (int)y, width, height);
	}
	
	void drawGUI(){
		if(hp!=mhp){
			gc.setColor(Color.RED);
			gc.fillRect((int)x, (int)y-15, width, 10);
			gc.setColor(Color.GREEN);
			gc.fillRect((int)x, (int)y-15, (int)(width*(hp/mhp)), 10);
		}
	}
	
	void seek(){
		if(Math.random()<=.10){
			target = new Point(player.x,player.y);
			dxmag = target.x - x;
		    dymag = target.y - y;
		    dir = Math.atan2(dymag, dxmag);
		    dx = Math.cos(dir);
		    dy = Math.sin(dir);
		}
	}
	
	void move(){
		//collisions();
		x+=dx;
		y+=dy;
		setBounds((int)x,(int)y,width,height);
	}
	
	void collisions(){
		for(Monster m : TheCalm.monsters){
			if(m.intersects(this)&&m!=this){
				//right Side 
				if(x-dx*2 <= m.x){
					x -= dx+1;
				}
				//left Side
				if(x-dx*2 >= m.getMaxX()){
					x -= dx+1;
				}
				//Bottom Side
				if(y-dy*2 <= m.y){
					y-=dy+1;
				}
				//Top Side
				if(y-dy*2 >= m.getMaxY()){
					y-=dy+1;
				}
			}
		}
	}
	
	void hurt(Bullet b){
		color = new Color(100,0,0);
		Timer hitTimer = new Timer();
		TimerTask hitTask = new HitControl();
		hitTimer.schedule(hitTask, 200);
		x+=b.dx*3;
		y+=b.dy*3;
	}
	
}
