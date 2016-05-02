package eu.farstar.cervi.menu;

import eu.farstar.cervi.MessageQueueInterface;

public class MenuItemNumber extends MenuItem {
		
		private int minValue;
		private int maxValue;
		private int status;
		
		public MenuItemNumber(int x, int y, String caption, int status, int minValue, int maxValue) {
			super(x,y,caption);
			this.minValue = minValue;
			this.maxValue = maxValue;
			this.status = status;
			setCaption(caption + " <" + status + ">");
		}

		public MenuItemNumber(int x, int y, String caption, int status, int minValue, int maxValue, MessageQueueInterface queue, String name) {
			super(x,y,caption);
			this.minValue = minValue;
			this.maxValue = maxValue;
			this.status = status;
			setCaption(caption + " <" + status + ">");
			this.name=name;
			this.queue=queue;
		}

		
		@Override
		public void moveLeft() {
			if (status>minValue) {
				status--;
			}
			setCaption(caption + " <" + status + ">");
			if (name != null) {
				queue.add(name,status);
			}
		}

		@Override
		public void moveRight() {
			if (status<maxValue) {
				status++;
			}
			setCaption(caption + " <" + status + ">");
			if (name != null) {
				queue.add(name,status);
			}
		}

		public int getIntValue() {
			return minValue + maxValue*status/10;
		}

		public float getFloatValue() {
			return (float)minValue + (float)(maxValue*status)/10f;
		}
	}
