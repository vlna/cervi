package eu.farstar.cervi.menu;

public class MenuItemYN extends MenuItem {
	private final String[] sliderStrings = { "<N>", //no
											 "<Y>", //yes
	};
	
	private int minValue;
	private int maxValue;
	private boolean status;
	
	public MenuItemYN(int x, int y, String caption, boolean status) {
		super(x,y,caption);
		this.status = status;
		setCaption(caption + " " + sliderStrings[status?1:0]);
	}
	
	@Override
	public void moveLeft() {
		status = !status;
		setCaption(caption + " " + sliderStrings[status?1:0]);
	}

	@Override
	public void moveRight() {
		moveLeft();
	}

	public int getIntValue() {
		return minValue + maxValue*(status?1:0)/10;
	}

	public float getFloatValue() {
		return (float)minValue + (float)(maxValue*(status?0f:1f))/10f;
	}
}
