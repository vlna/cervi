package eu.farstar.cervi;

import java.awt.Color;
import java.util.ArrayList;
//import com.vlna.game.quads.MyFont;

public class MyChar{

	int x;
	int y;

	int width;
	int height;

	int pointWidth;
	int pointHeight;

	int xspacing;
	int yspacing;
	
	char c;
	
	ArrayList<MyQuad> charElements = new ArrayList<MyQuad>();

	public MyChar(int x, int y, int width, int height,
			int pointWidth, int pointHeight, int xspacing, int yspacing, char c) {
		this(x, y, width, height, pointWidth, pointHeight, xspacing, yspacing, c, Color.WHITE);
	}

	
	public MyChar(int x, int y, int width, int height,
			int pointWidth, int pointHeight, int xspacing, int yspacing, char c, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
		this.xspacing = xspacing;
		this.yspacing = yspacing;
		this.c = c;
		
		MyFont.selectChar(c);
		
		for (int i=0;i<=63;i++) {
			if (MyFont.getNext()) {
				charElements.add(new MyQuad(x+(pointWidth+xspacing)*(7-i%8),y+(pointHeight+yspacing)*(8-i/8),pointWidth,pointHeight,color));
			}
		}
	}
	
	public void draw(){
		for (MyQuad i:charElements){
			i.drawMyQuad();
		}
	}
}
