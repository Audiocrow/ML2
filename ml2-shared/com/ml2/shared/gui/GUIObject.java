package com.ml2.shared.gui;

import com.ml2.shared.utils.Dimensions;

/** Stores only position and a parent - everything else is left to subclasses.
 * @author Alexander Edgar
 */
public abstract class GUIObject {
	public static final String TAG = GUIObject.class.getName();
	
	private GUIObject parent;
	private Dimensions dims;
	
	/** Copies another GUIObject's values ({@link GUIObject} fields only).
	 * @param other the GUIObject to copy.
	 */
	public GUIObject(GUIObject other) {
		parent = other.getParent();
		dims.set(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	}
	public GUIObject(GUIObject parent, int x, int y, int width, int height) {
		this.parent = parent;
		dims.set(x, y, width, height);
	}
	public GUIObject getParent() { return parent; }
	public int getX() { return dims.getX(); }
	public void setX(int x) { dims.setX(x); }
	public int getY() { return dims.getY(); }
	public void setY(int y) { dims.setY(y); }
	public int getWidth() { return dims.getWidth(); }
	public void setWidth(int width) { dims.setWidth(width); }
	public int getHeight() { return dims.getHeight(); }
	public void setHeight(int height) { dims.setHeight(height); }
}
