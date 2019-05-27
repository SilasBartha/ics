package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import hsa2x.GraphicsConsole;

@SuppressWarnings("serial")
public class TheCalm {
	
	GraphicsConsole gc = new GraphicsConsole(640,480,"The Calm.");
	Player player = new Player();
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	public static void main(String[] args) {
		new TheCalm();
	}
	
	TheCalm(){
		setup();
		while(true){
			step();
			draw();
		}
	}
	
	void setup(){
		//Graphics
		gc.setBackgroundColor(Color.GREEN.darker());
		gc.clear();
		//Monsters
		for(int i = 0; i < 3; i++){
			int x  = (int)((Math.random()*640)+1);
			int y  = (int)((Math.random()*480)+1);
			Monster m = new Monster(x,y);
			monsters.add(m);
		}
	}
	
	void step(){
		player.move();
		for(Monster m : monsters){
			m.seek();
			m.move();
		}
	}
	
	void draw(){
		synchronized(gc){
			gc.clear();
			player.draw();
			for(Monster m : monsters){
				m.draw();
			}
		}
		gc.sleep(10);
	}
	
	private class Player extends Rectangle{
		double hp = 100;
		double v = 2;
		int dx = 0, dy = 0;
		
		Player(){
			x = gc.getDrawWidth()/2-16;
			y = gc.getDrawHeight()/2-16;
			width = 32;
			height = 32;
		}
		
		void move(){
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
			}
			x += dx*v;
			y += dy*v;
			
		}
		
		void draw(){
			gc.setColor(Color.DARK_GRAY);
			gc.fillRect(x, y, width, height);
		}
	}
	
	private class Monster extends Rectangle{
		
		Point target = new Point(player.x,player.y);
		
		Monster(int x, int y){
			this.x = x;
			this.y = y;
			width = 32;
			height = 32;
		}
		
		void draw(){
			gc.setColor(new Color(100,0,100));
			gc.fillRect(x, y, width, height);
		}
		
		void seek(){
			if(Math.random()<=.10){
				target = new Point(player.x,player.y);
			}
		}
		
		void move(){
			x+=(int)-Math.signum(x-target.x);
			y+=(int)-Math.signum(y-target.y);
		}
	}
	
	boolean keyDown(char key){
		return gc.isKeyDown(key);
	}
}
