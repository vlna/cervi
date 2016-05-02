package eu.farstar.cervi.menu;

import java.awt.Color;

import eu.farstar.cervi.MessageQueueInterface;
import eu.farstar.cervi.MyChar;

public class MenuItemColor extends MenuItem {

	final Color[] playerColor = new Color[]{Color.YELLOW, Color.CYAN, Color.RED, Color.GREEN,
									Color.ORANGE, Color.BLUE, Color.PINK, Color.GRAY};
	
	private int minValue=0;
	private int maxValue=playerColor.length-1;
	private int status;
	
	public MenuItemColor(int x, int y, String caption, int status) {
		super(x,y,caption);
		this.status = status;
		setCaption(caption + " " + " <@>");
	}
	
	public MenuItemColor(int x, int y, String caption, int status, MessageQueueInterface queue, String name) {
		super(x,y,caption);
		this.status = status;
		setCaption(caption + " <@>");
		this.name=name;
		this.queue=queue;
	}
	
	
	
	@Override
	public void moveLeft() {
		if (status>0) {
			status--;
		}
		setCaption(caption + " <@>");
		if (name != null) {
			queue.add(name,status);
//			System.out.println("move left value added");
		}
	}

	@Override
	public void moveRight() {
		if (status<maxValue) {
			status++;
		}
		setCaption(caption + " <@>");
		if (name != null) {
			queue.add(name,status);
//			System.out.println("move right value added");
		}
	}

	public int getIntValue() {
		return minValue + maxValue*status/10;
	}

	public float getFloatValue() {
		return (float)minValue + (float)(maxValue*status)/10f;
	}
	
	@Override
	public void setCaption(String caption) {
    	myChars.clear();
		int i=0;
		for (char c:caption.toCharArray()){
			if (c=='@') {
				myChars.add(new MyChar(x+24*(i++),y,2,2,2,2,1,1,c,playerColor[status]));
			} else {
				myChars.add(new MyChar(x+24*(i++),y,2,2,2,2,1,1,c));
			}
    	}

	}

}
