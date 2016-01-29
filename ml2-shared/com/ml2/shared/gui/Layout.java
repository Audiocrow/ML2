package com.ml2.shared.gui;

import com.badlogic.gdx.utils.Array;

/** Stores an insert-ordered array of children; arranges them within a bounded area.
 * <p> Call {@link GUIDrawer#draw} directly on the layout to have it drawn. 
 * @author Alexander Edgar
 */
public class Layout extends GUIObject {
	private static enum LayoutType { Vertical, Horizontal };
	/** See {@see LayoutType}. Defaults to Vertical*/
	private LayoutType type;
	private Array<Object> children;
	
	public Layout(Layout other) {
		super((GUIObject)other);
		type = other.type;
		children = new Array<Object>(true, other.children.items.length, GUIObject.class);
	}
	/**@param type {@link Vertical} or {@link Horizontal}*/
	public Layout(LayoutType type, GUIObject parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height);
		this.type = type;
		children = new Array<Object>(true, 4, GUIObject.class);
	}
}
