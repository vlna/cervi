package eu.farstar.cervi.menu;

import eu.farstar.cervi.MessageQueueInterface;

public class MenuItemSlider extends MenuItem {
	private final String[] sliderStrings = { "<O__________>",
											 "<_O_________>",
											 "<__O________>",
											 "<___O_______>",
											 "<____O______>",
											 "<_____O_____>",
											 "<______O____>",
											 "<_______O___>",
											 "<________O__>",
											 "<_________O_>",
											 "<__________O>",
	};
	
	private int minValue;
	private int maxValue;
	private int status;
	
	public MenuItemSlider(int x, int y, String caption, int status) {
		super(x,y,caption);
		this.status = status;
		setCaption(caption + " " + sliderStrings[status]);
	}
	
	public MenuItemSlider(int x, int y, String caption, int status, MessageQueueInterface queue, String name) {
		super(x,y,caption);
		this.status = status;
		setCaption(caption + " " + sliderStrings[status]);
		this.name=name;
		this.queue=queue;
	}
	
	
	
	@Override
	public void moveLeft() {
		if (status>0) {
			status--;
		}
		setCaption(caption + " " + sliderStrings[status]);
		if (name != null) {
			queue.add(name,status);
//			System.out.println("move left value added");
		}
	}

	@Override
	public void moveRight() {
		if (status<10) {
			status++;
		}
		setCaption(caption + " " + sliderStrings[status]);
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
}
