package eu.farstar.cervi;

//import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
//import java.awt.geom.Rectangle2D.Float;
import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class MyQuad extends Rectangle2D.Float {

	public Color color;


	public MyQuad(float x, float y, float width, float height, Color color) {
		//MyQuad(x,y,width,height,color,0f,0f);
		super(x, y, width, height);
		this.color = color;
	}
	
	public void drawMyQuad() {
		// a kreslime
	    GL11.glColor3f((float)color.getRed()/255,  (float)color.getGreen()/255,
	    		       (float)color.getBlue()/255);
		GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+width,y);
        GL11.glVertex2f(x+width,y+height);
        GL11.glVertex2f(x,y+height);
        GL11.glEnd();
        
	};
	
}
