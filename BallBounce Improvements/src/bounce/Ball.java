package bounce;


import java.awt.Color;
import java.awt.Rectangle;

public class Ball extends Rectangle{
	private static final long serialVersionUID = 1L;
	//Ball Position and Velocity, maximum vy velocity
	int vx = 0, num = 0, d = 20, r = 10, gnum = 0, mvy = 0;
	double  vy = 0, gravity = 0.8;
	boolean canCollide;
	BallBounce bb = null;
	//Random Color
	Color c = new Color(Color.HSBtoRGB((float)Math.random(), 1.0f, 1.0f));
	
	//Constructor
	Ball(int x,int y, int vx, int vy, BallBounce bb){
		//Set Values
		//BallBounce class (For references)
		this.bb = bb;
		//velocity
		this.vx = vx;
		this.vy = vy;
		mvy = vy;
		//Set Bounds
		this.setBounds(x,y,d,d);
	}
	
	//Move
	void move(){
		//Move, Apply Window Velocity
		x += vx+bb.dx;
		y += vy+bb.dy;
		
		if(bb.gravity){
			//Apply Gravity
			vy += gravity;
			gnum++;
		}
		
		//Account for Window Resize
		if(bb.dx!=0){
			x += bb.vwc;
		}
		if(bb.dy!=0){
			y += bb.vhc;
		}
		
		//Collisions
		windowCollide();
		wallCollide();
	}		
		
	//Wall Collision detection
	void wallCollide(){
		for(Wall w: bb.walls) {	
			//Only collide if wall is active.
			if(w.intersects(this)&&w.active){
				//right Side 
				if(x-vx*2 <= w.x){
					vx*=-1; 
					x = w.x-d;
				}
				//left Side
				if(x-vx*2 >= w.getMaxX()){
					vx*=-1; 
					x = (int)w.getMaxX();
				}
				//Bottom Side
				if(y-vy*2 <= w.y){
					if(bb.gravity){
						vy=-mvy*mvy;
						gnum = 0;
					}else{
						vy=-mvy;
					} 
					
					y = w.y-d;
				}
				//Top Side
				if(y-vy*2 >= w.getMaxY()){
					if(bb.gravity){
						vy = 1;
					}else{
						vy=mvy;
					}
					y = (int)w.getMaxY();
				}
			}
		}
	}
	
	//Window Collision detection
	void windowCollide(){
		//Right Side of Window
		if( x >= bb.vw-20){
			vx*=-1; 
			c = new Color(Color.HSBtoRGB((float)Math.random(), 1.0f, 1.0f));
			x = bb.vw-20;
		}
		
		//Left Side of Window
		if( x <= 0){
			vx*=-1;
			c = new Color(Color.HSBtoRGB((float)Math.random(), 1.0f, 1.0f));
			x = 0;
		}

		//Bottom Side of Window
		if( y >= bb.vh-20){
			if(bb.gravity){
				vy=-mvy*mvy;
				gnum = 0;
			}else{
				vy=-mvy;
			}
			c = new Color(Color.HSBtoRGB((float)Math.random(), 1.0f, 1.0f));
			y = bb.vh-20;
		}
			
		//Top Side of Window
		if( y <= 0){
			if(bb.gravity){
				vy = 1;
			}else{
				vy=mvy;
			}
			c = new Color(Color.HSBtoRGB((float)Math.random(), 1.0f, 1.0f));
			y = 0;
		}
	}
	
	//Draw Ball
	void draw(){
		bb.gc.setColor(c);
		bb.gc.fillOval(x,y,width,height);
	}
}