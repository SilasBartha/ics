package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import hsa2x.GraphicsConsole;

public class TheCalm {
	
	GraphicsConsole gc = new GraphicsConsole(640,480,"The Calm.");
	Player player = new Player(gc);
	static ArrayList<Monster> monsters = new ArrayList<Monster>();
	Timer monsterSpawnTimer = new Timer();
	TimerTask monsterSpawnTask = new monsterSpawn();
	Timer monsterNumTimer = new Timer();
	TimerTask monsterNumTask = new monsterNumber();
	Iterator<Bullet> bi = player.getBullets().iterator();
	Iterator<Monster> mi = monsters.iterator();
	
	int monsterNum = 1;
	
	private class monsterSpawn  extends TimerTask{
		public void run(){
			for(int i = 0; i < monsterNum; i++) {
				int x  = (int)((Math.random()*640)+1);
				int y  = (int)((Math.random()*480)+1);
				Monster m = new Monster(x, y, player, gc, (int)(Math.random()*2));
				monsters.add(m);
			}
		}
	}
	
	private class monsterNumber  extends TimerTask{
		public void run(){
			monsterNum++;
		}
	}
	
	public static void main(String[] args) {
		new TheCalm();
	}
	
	TheCalm(){
		setup();
		while(true){
			step();
			synchronized(gc){
				draw();
				drawGUI();
			}
		}
	}
	
	void setup(){
		//Graphics
		gc.setBackgroundColor(Color.GREEN.darker());
		gc.clear();
		gc.enableMouse();
		gc.enableMouseMotion();
		//Monsters
		monsterSpawnTimer.schedule(monsterSpawnTask, 0, 5000);
		monsterNumTimer.schedule(monsterNumTask, 5000, 10000);
	}
	
	void step(){

		Collections.sort(monsters, Comparator.comparing(Monster::getY));
		mi = monsters.iterator();
		while(mi.hasNext()){
			Monster m = mi.next();
			m.seek();
			m.move();
		}

		bi = player.getBullets().iterator();
		while(bi.hasNext()){
			Bullet b = bi.next();
			b.move();
		}
		
		player.input();
		bi = player.getBullets().iterator();
		while(bi.hasNext()){
			Bullet b = bi.next();
			if(!gc.contains((int)b.x,(int)b.y)){
				bi.remove();
			}
			
		}
		mi = monsters.iterator();
		while(mi.hasNext()){
			Monster m = mi.next();
			bi = player.getBullets().iterator();
			boolean removeM = false;
			while(bi.hasNext()){
				Bullet b = bi.next();
				if(m.contains(b.getCenterX(),b.getCenterY())){
					m.hurt(b);
					bi.remove();
					m.hp--;
					if(m.hp<=0){
						removeM = true;
					}
				}
			}
			if(removeM) {
				mi.remove();
			}
		}
	}
	
	void draw(){
		gc.clear();
		player.draw();
		bi = player.getBullets().iterator();
		while(bi.hasNext()){
			Bullet b = bi.next();
			b.draw();
		}
		mi = monsters.iterator();
		while(mi.hasNext()){
			Monster m = mi.next();
			m.draw();
		}
	}
	void drawGUI(){
		mi = monsters.iterator();
		while(mi.hasNext()){
			Monster m = mi.next();
			m.drawGUI();
		}
		gc.sleep(10);
	}
}
