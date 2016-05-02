package eu.farstar.cervi;

import java.util.ArrayList;

import eu.farstar.cervi.menu.MenuItem;
import eu.farstar.cervi.menu.MenuItemColor;
import eu.farstar.cervi.menu.MenuItemNumber;
import eu.farstar.cervi.menu.MenuItemSlider;
import eu.farstar.cervi.menu.MenuItemYN;

public class Menu {

	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	private int x = 50;
	
	private void makeSelection() {
		if (menuItems.size()==1) {
			menuItems.get(0).setActive();
		}
	}

//	public void addColor() {
//		menuItems.add(new MenuItemColor(x,500-menuItems.size()*40,"Color 1",1));
//		makeSelection();
//	}

	public void addColor(MessageQueueInterface queue, String name, String label) {
		menuItems.add(new MenuItemColor(x,500-menuItems.size()*40,label,1,queue,name));
		makeSelection();
	}
	
//	public void addNumber() {
//		menuItems.add(new MenuItemNumber(x,500-menuItems.size()*40,"Players",2,2,8));
//		makeSelection();
//	}

	public void addNumber(MessageQueueInterface queue, String name, String label) {
		menuItems.add(new MenuItemNumber(x,500-menuItems.size()*40,label,2,2,8,queue,name));
		makeSelection();
	}

//	public void addSlider() {
//		menuItems.add(new MenuItemSlider(x,500-menuItems.size()*40,"Worm speed",10));
//		makeSelection();
//	}

	public void addSlider(MessageQueueInterface queue, String name, String label) {
		menuItems.add(new MenuItemSlider(x,500-menuItems.size()*40,label,10,queue,name));
		makeSelection();
	}
	
	public void addYN() {
		menuItems.add(new MenuItemYN(x,500-menuItems.size()*40,"Speed up", true));
		makeSelection();
	}
	
	public void addButton() {
		menuItems.add(new MenuItem.MenuItemBuilder().setX(x).setY(500-menuItems.size()*40).caption("Start game").create(false));
		//		menuItems.add(new MenuItem(x,500-menuItems.size()*40,"Start game"));
		makeSelection();
	}

	public void addButton(MessageQueueInterface queue, String name, String label) {
		menuItems.add(new MenuItem(x,500-menuItems.size()*40,label,queue,name));
		makeSelection();
	}
	
	public void clearMenu() {
		menuItems.clear();
	}

	public void draw() {
		for (MenuItem i:menuItems) {
			i.draw();
		}
	}
	
	
	public void next() {
		//find selected
		int counter=0;
		for (MenuItem i:menuItems) {
			if (i.isActive()) {
				//unset it
				menuItems.get(counter).unsetActive();
				//and set next or first
				if(counter+1>=menuItems.size()) {
					menuItems.get(0).setActive();
				} else {
					menuItems.get(counter+1).setActive();
				}
				break;
			}
			counter++;	
		}
	}

	public void prev() {
		//find selected
		int counter=0;
		for (MenuItem i:menuItems) {
			if (i.isActive()) {
				//unset it
				menuItems.get(counter).unsetActive();
				//and set next or first
				if(counter<=0) {
					menuItems.get(menuItems.size()-1).setActive();
				} else {
					menuItems.get(counter-1).setActive();
				}
				break;
			}
			counter++;	
		}
	}

	public void moveLeft() {
		for (MenuItem i:menuItems) {
			if (i.isActive()) {
				i.moveLeft();
			}
		}
	}

	public void moveRight() {
		for (MenuItem i:menuItems) {
			if (i.isActive()) {
				i.moveRight();
			}

		}
	}
}
