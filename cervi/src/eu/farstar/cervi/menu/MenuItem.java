package eu.farstar.cervi.menu;

import java.util.ArrayList;

import eu.farstar.cervi.MessageQueueInterface;
import eu.farstar.cervi.MyChar;
import eu.farstar.cervi.MyQuad;

import java.awt.Color;

//import org.lwjgl.opengl.GL11;

public class MenuItem {
	
	private boolean active;
	
	protected int x;
	protected int y;
	
	protected String caption;
	protected MessageQueueInterface queue;
	protected String name;

	
	protected ArrayList<MyChar> myChars = new ArrayList<MyChar>();
	
	public MenuItem() {};
		
	public MenuItem(int x, int y, String caption) {
		super();
		this.x = x;
		this.y = y;
		this.caption = caption;
		this.active = false;
		this.setCaption(caption);
	}

	
	public MenuItem(int x, int y, String caption, MessageQueueInterface queue, String name) {
		super();
		this.x = x;
		this.y = y;
		this.caption = caption;
		this.active = false;
		this.setCaption(caption);
		this.name=name;
		this.queue=queue;
	}

	
	public void setCaption(String caption) {
    	myChars.clear();
		int i=0;
		for (char c:caption.toCharArray()){
			myChars.add(new MyChar(x+24*(i++),y,2,2,2,2,1,1,c));
    	}

	}

	public void setActive() {
		this.active=true;
	}

	public void unsetActive() {
		this.active=false;
	}
	
	public void draw() {
		if (active) {
			//too expensive?
			new MyQuad(x-30,y,24,24,Color.CYAN).drawMyQuad();
		}
		for (MyChar i:myChars){
			i.draw();
		}
	}

	public boolean isActive() {
		return active;
	}
	
	public void moveLeft() {
		// just send click, rest implement in subclasses
		if (name != null) {
			queue.add(name,1);
		}

	}

	public void moveRight() {
		// just send click, rest implement in subclasses
		moveLeft();
	}

	public int getIntValue() {
		return 0;
		// do nothing - implement in subclasses
	}

	public float getFloatValue() {
		return 0;
		// do nothing - implement in subclasses
	}	

	public String getStringValue() {
		return "";
		// do nothing - implement in subclasses
	}
	
	public static class MenuItemBuilder {
		
		private MenuItem tmpMenuItem;
		
		public MenuItemBuilder setX(int x) {
			tmpMenuItem.x = x;
			return this;
		}
			
		public MenuItemBuilder setY(int y) {
			tmpMenuItem.y = y;
			return this;
		}

		public MenuItemBuilder caption(String caption) {
			tmpMenuItem.caption = caption;
			return this;
		}

		public MenuItemBuilder name(String name) {
			tmpMenuItem.name = name;
			return this;
		}

		public MenuItem create(boolean isActive) {
			tmpMenuItem.active = isActive;
			return tmpMenuItem;
		}
	}
	
	
}
