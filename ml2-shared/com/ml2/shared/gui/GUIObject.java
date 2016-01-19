package com.ml2.shared.gui;

import com.badlogic.gdx.math.GridPoint2;

public class GUIObject {
	private GUIObject parent;
	private GridPoint2 pos;
	private GridPoint2 dims; //Width, height
	
	public GUIObject(GUIObject parent) { this.parent = parent; }
	public GUIObject(GUIObject parent, int x, int y, int width, int height) {
		this.parent = parent;
		pos.set(x, y);
		dims.set(width, height);
	}
	
	public int getX() { return pos.x; }
	public void setX(int x) { pos.set(x, pos.y); }
	public int getY() { return pos.y; }
	public void setY(int y) { pos.set(pos.x, y); }
}
