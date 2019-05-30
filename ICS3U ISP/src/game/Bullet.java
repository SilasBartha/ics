package game;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import hsa2x.GraphicsConsole;

@SuppressWarnings("serial")
class Bullet extends Rectangle{
		
	
		GraphicsConsole gc;
		double x,y,dx,dy;
		Player player;
		
		Bullet(int x, int y, Player player, GraphicsConsole gc){
			this.x = x;
		    this.y = y;
		    this.player = player;
		    this.gc = gc;
		    Random random = new Random();
		    double rand = Math.random()/15 * (random .nextBoolean() ? -1 : 1);
		    double dxmag = gc.getMouseX() - x + player.dx*20;
		    double dymag = gc.getMouseY() - y + player.dy*20;
		    double dir = Math.atan2(dymag, dxmag);
		    dx = 5*Math.cos(dir+rand);
		    dy = 5*Math.sin(dir+rand);
			width = 8;
			height = 8;
		}
		
		void draw(){
			gc.setColor(new Color(100,100,0));
			gc.fillRect((int)x, (int)y, width, height);
		}
		
		void move(){
			x+=dx;
			y+=dy;
			setBounds((int)x,(int)y,width,height);
		}
	}