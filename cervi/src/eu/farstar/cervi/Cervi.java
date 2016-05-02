package eu.farstar.cervi;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;



import java.awt.Color;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Cervi implements MessageQueueInterface {

	enum gameState {PLAY, WAITFORPLAYERS, STARTGAME, GOTOMENUMAIN, MENUMAIN, GOTOMENUCOLOR, MENUCOLOR, MENUAUDIO, MENUVIDEO};
	private gameState myGameState = gameState.GOTOMENUMAIN;
	private HashMap queue = new HashMap();
	
	private int noOfPlayers=2;
	private int wormSpeed=40;
	private boolean speedUp=true;
	
	ArrayList<Player> myPlayers = new ArrayList<Player>();
	ArrayList<MyChar> myChars = new ArrayList<MyChar>();
	//ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	Menu myMenu = new Menu();
	
	private final Color[] defaultPlayerColor = new Color[]{Color.YELLOW, Color.CYAN, Color.RED, Color.GREEN,
			Color.ORANGE, Color.BLUE, Color.PINK, Color.GRAY};

	private Color[] playerColor = defaultPlayerColor.clone();
	
	final int[][] playerKeys = new int[][]{
			{Keyboard.KEY_Q, Keyboard.KEY_W}, //player 1
			{Keyboard.KEY_X, Keyboard.KEY_C}, //player 2
			{Keyboard.KEY_N, Keyboard.KEY_M}, //player 3
			{Keyboard.KEY_O, Keyboard.KEY_P}, //player 4
			{Keyboard.KEY_DELETE, Keyboard.KEY_END}, //player 5
			{Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT}, //player 6
			{Keyboard.KEY_NUMPAD1, Keyboard.KEY_NUMPAD2}, //player 7
			{Keyboard.KEY_MULTIPLY, Keyboard.KEY_MINUS}, //player 8
			};

	final int[] startKeys = new int[]{
			Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4, Keyboard.KEY_5,
			Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8,
	};
	
	static Random randomno = new Random();
	
	/** time at last frame */
	long lastFrame;
	
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
    public void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(800,600));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}

	// init OpenGL
	GL11.glMatrixMode(GL11.GL_PROJECTION);
	GL11.glLoadIdentity();
	GL11.glOrtho(0, 800, 0, 600, 1, -1);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);

	getDelta(); // call once before loop to initialise lastFrame
	lastFPS = getTime(); // call before loop to initialise fps timer
	
	while (!Display.isCloseRequested()) {
		// Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		
	    switch (myGameState) {
	    case STARTGAME:
	    	myMenu.clearMenu();
	    	buildGame();
	    	myGameState = gameState.PLAY;
	    	break;
	    case GOTOMENUMAIN:
	    	buildMainMenu();
	    	myGameState = gameState.MENUMAIN;
	    	break;
	    }
	    
	    // draw quads
		for (Player i:myPlayers){
			i.draw();
		}

	    // draw chars
		for (MyChar i:myChars){
			i.draw();
		}

		
	    // draw menu
		myMenu.draw();
		processQueue();
//		for (MenuItem i:menuItems){
//			i.draw();
//		}
		
		Display.update();

		int delta = getDelta();
		updateFPS();
		Display.sync(60); // cap fps to 60fps
		pollInput();
	    
	    //make move

		if (myGameState == gameState.PLAY) {
			for (Player i:myPlayers){
				i.move(delta);
			}
			for (Player i:myPlayers){
				for (Player j:myPlayers){
					i.collides(j);
				}
			}
		}

		//lepe asi vracet t/f a hrat zvuk vybuchu pri t
		//kolize hledam
		
		
		int counter=0;
		String winner = "";
		for (Player i:myPlayers){
			i.isOut();
			if (!i.isDead()) {
				winner = i.getName();
			};
			counter += i.isDead()?0:1; //count not dead
		}
		if (counter <= 1 && myGameState == gameState.PLAY) { //last player left
			myGameState = gameState.WAITFORPLAYERS;
	    	int i=0;
			for (char c:(counter==0?"Nobody won.":"Winner is").toCharArray()){
				myChars.add(new MyChar(50+64*(i++),400,8,8,8,8,1,1,c));
	    	}
	    	i=0;
			for (char c:(counter==0?"###########".toCharArray():winner.toCharArray())){
				myChars.add(new MyChar(50+64*(i++),200,8,8,8,8,1,1,c));
	    	}
		}
	}
 
	Display.destroy();
    }
    
    
    public void buildGame(){
        
    	//i reset hry
    	myPlayers.clear();
    	myChars.clear();
    	for (int i=0;i<noOfPlayers;i++){
    		myPlayers.add(new Player((i+1)*(800/(noOfPlayers+1)),100,playerColor[i],"Player "+(i+1)));
    	}
    }

    
    public void buildMainMenu(){
//    	myChars.clear();
//    	int i=0;
//		for (char c:("Press 2-8 for 2-8 players".toCharArray())){
//			myChars.add(new MyChar(50+24*(i++),200,2,3,2,3,1,1,c));
//    	}
//		i=0;
//		for (char c:("Press H to show/hide this help".toCharArray())){
//			myChars.add(new MyChar(50+24*(i++),100,2,3,2,3,1,1,c));
//    	}
//		menuItems.add(new MenuItemNumber(200,200+(50*menuItems.size()),"Player "+(menuItems.size()+1)+":",5,2,8));
//		menuItems.add(new MenuItemYN(200,200+(50*menuItems.size()),"Player "+(menuItems.size()+1)+":",false));
//		menuItems.get(0).setActive();

		myMenu.clearMenu();
    	myMenu.addNumber(this,"players","Players");
		myMenu.addSlider(this,"speed","Speed");
		//myMenu.addColor(this,"color1");
		myMenu.addYN();
		myMenu.addButton(this,"gotomenucolor","Colors");
		myMenu.addButton(this,"startgame","Start Game");
    }

    public void buildColorMenu(){
		myMenu.clearMenu();
		
		for(int i = 1; i <= 8; i++){
			myMenu.addColor(this,"color"+i,"Player "+i+" color");
			}
		
		myMenu.addButton(this,"endcolor","Back");
    }

    
    public void pollInput() {
		
//        if (Mouse.isButtonDown(0)) {
//	    int x = Mouse.getX();
//	    int y = Mouse.getY();
//			
//	    System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
//	}
//		
//	if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//	    System.out.println("SPACE KEY IS DOWN");
//	}
	//System.out.println(myQuads.size());	
	while (Keyboard.next()) {
//	    if (Keyboard.getEventKeyState()) {
		switch (myGameState) {
		case PLAY :
			for (int i=0;i<myPlayers.size();i++){
				if (Keyboard.getEventKey() == playerKeys[i][0]) {
					myPlayers.get(i).setTurnLeft(Keyboard.getEventKeyState());
				}
				if (Keyboard.getEventKey() == playerKeys[i][1]) {
					myPlayers.get(i).setTurnRight(Keyboard.getEventKeyState());
				}
			}
			break;
		case WAITFORPLAYERS: case MENUMAIN: case MENUCOLOR: case MENUAUDIO: case MENUVIDEO:
			for (int i=0;i<startKeys.length;i++){
				if (!Keyboard.getEventKeyState() && (Keyboard.getEventKey() == startKeys[i])) {
					noOfPlayers = i+2;
					myGameState = gameState.STARTGAME;
				}
			}

			if (myGameState == gameState.MENUMAIN && Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_LEFT)) {
				myMenu.moveLeft();
			}
			if (myGameState == gameState.MENUMAIN && Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_RIGHT)) {
				myMenu.moveRight();
			}

			if (myGameState == gameState.MENUMAIN && Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_UP)) {
				myMenu.prev();
			}
			if (myGameState == gameState.MENUMAIN && Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_DOWN)) {
				myMenu.next();
			}
			
			break;

		case GOTOMENUMAIN:
			if (Keyboard.getEventKeyState() && (Keyboard.getEventKey() == Keyboard.KEY_H)) {
				if (myGameState == gameState.WAITFORPLAYERS) {
					buildMainMenu();
					myGameState = gameState.MENUMAIN;
				} else {
					myChars.clear();
//					menuItems.clear();
					myGameState = gameState.WAITFORPLAYERS;
				}
			}
			
			
	    }
//	    	for (int i=0;i<myPlayers.size();i++){
//		    	if (Keyboard.getEventKey() == playerKeys[i][0]) {
//		        	myPlayers.get(i).setTurnLeft(false);
//		        }
//		    	if (Keyboard.getEventKey() == playerKeys[i][1]) {
//		        	myPlayers.get(i).setTurnRight(false);
//		        }
//	        }
//	    }
	}
}
    
    public static void main(String[] argv) {
    	Cervi cervi = new Cervi();
    	cervi.start();
    }
    
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps + " / Quads: " + myPlayers.size() +
											 " / Game State: "+ myGameState);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}


	@Override
	public void add(String messageId, int messageValue) {
		queue.put(messageId, messageValue);
	}


	@Override
	public boolean processQueue() {
		// TODO Auto-generated method stub
		if (!queue.isEmpty()) { 
			if (queue.get("speed") instanceof Integer) {
				wormSpeed = (int)(queue.get("speed"));
			}
			if (queue.get("players") instanceof Integer) {
				noOfPlayers = (int)(queue.get("players"));
			}
			if (queue.get("speedup") instanceof Integer) {
				speedUp = (int)(queue.get("speedup"))==1;
			}
			if (queue.get("gotomenucolor") instanceof Integer) {
				if ((int)(queue.get("gotomenucolor"))==1) {
					buildColorMenu();
				};
			}
			//delete rest of queue
			queue.clear();
			System.out.println("color1: " + "?");
		}
		
		return false;
	}
}