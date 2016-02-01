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
	private Array<GUIObject> children;
	
	public Layout(Layout other) {
		super((GUIObject)other);
		type = other.type;
		children = new Array<GUIObject>(true, other.children.items.length, GUIObject.class);
		for(GUIObject obj : children)
			obj.setParent(this);
	}
	/**@param type {@link Vertical} or {@link Horizontal}*/
	public Layout(LayoutType type, GUIObject parent, short x, short y, short width, short height) {
		super(x, y, width, height);
		this.type = type;
		children = new Array<GUIObject>(true, 4, GUIObject.class);
	}
}
