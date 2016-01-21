package com.ml2.shared.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ml2.shared.utils.Dimensions;

public abstract class GUIObject {
	public static final String TAG = GUIObject.class.getName();
	
	private GUIObject parent;
	private Dimensions dims;
	
	public GUIObject(GUIObject parent) { this.parent = parent; }
	public GUIObject(GUIObject parent, int x, int y, int width, int height) {
		this.parent = parent;
		dims.set(x, y, width, height);
	}
	
	public int getX() { return dims.getX(); }
	public void setX(int x) { dims.setX(x); }
	public int getY() { return dims.getY(); }
	public void setY(int y) { dims.setY(y); }
	public int getWidth() { return dims.getWidth(); }
	public void setWidth(int width) { dims.setWidth(width); }
	public int getHeight() { return dims.getHeight(); }
	public void setHeight(int height) { dims.setHeight(height); }
}
