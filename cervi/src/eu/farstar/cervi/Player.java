package eu.farstar.cervi;

import java.util.ArrayList;
import java.awt.Color;

public class Player {

	private boolean isDead = false;
	
	private boolean turnLeft = false;
	private boolean turnRight = false;
	
	private float dx = 0;
	private float dy = 40;
	
	private float lastx;
	private float lasty;
	
	private Color color; 
	private String name; 
	
	ArrayList<MyQuad> worm = new ArrayList<MyQuad>();
	
	public Player(float startx, float starty, Color color, String name){
		MyQuad myQuad = new MyQuad(startx, starty, 2, 2, color);
		worm.add(myQuad);
		lastx = startx;
		lasty = starty;
		this.color = color;
		this.name = name;
	}
	
	public void move(float delta){
		if (isDead) {
			return;
		}
		
		double a =  0;
		double r = 40;
		
		if (turnLeft ^ turnRight){
			r=Math.sqrt(dx*dx+dy*dy);
			if (turnRight) {
				a=Math.atan2(dy, dx)-(delta*Math.PI/2000); // 45 degrees/sec
			}
			if (turnLeft) {
				a=Math.atan2(dy, dx)+(delta*Math.PI/2000); // 45 degrees/sec
			}
			
			dx = (float)(Math.cos(a) * r);
			dy = (float)(Math.sin(a) * r);
		}
		
		lastx += ((turnLeft && turnRight)?2:1)*(delta * dx) / 1000;
		lasty += ((turnLeft && turnRight)?2:1)*(delta * dy) / 1000;
		
		float distx = lastx-(worm.get(worm.size()-1)).x;
		float disty = lasty-(worm.get(worm.size()-1)).y;
		
		if (distx>=1 || disty>=1 || distx<=-1 || disty<=-1) {
//		if (distx>=8 || disty>=8 || distx<=-8 || disty<=-8) {
			MyQuad myQuad = new MyQuad(lastx, lasty, 2, 2, color);
			worm.add(myQuad);
		}
	}
	
	public void setTurnLeft(boolean turnLeft) {
		this.turnLeft = turnLeft;
	}

	public void setTurnRight(boolean turnRight) {
		this.turnRight = turnRight;
	}

	public void draw() {
		for (MyQuad i:worm){
			i.drawMyQuad();
		}
	}

	public boolean collides(Player other) {
		if (isDead) {
			return false;
		}

		
		int last=worm.size()-1;
		int lastchecked=other.getWorm().size()-1;

		if (other == this) {
			// ignore first three if selfcheck
			lastchecked -= 3;
		}
	
		MyQuad lastQuad = worm.get(last);
		ArrayList<MyQuad> otherWorm = other.getWorm();
		
		for (int i=0; i<=(lastchecked); i++){
			if (lastQuad.intersects(otherWorm.get(i))){
					isDead = true;
					return true;
			}
		}
		return false;
	}
	
	public boolean isOut() {
		if (isDead) {
			return false;
		}
		boolean result = (lastx>799 || lastx<-1 || lasty>599 || lasty<-1);
		isDead = isDead || result;
		return result;
	}

	public boolean isDead() {
		return isDead;
	}

	public ArrayList<MyQuad> getWorm() {
		return worm;
	}

	public String getName() {
		return name;
	}
	
}
